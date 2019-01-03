package yp.e3mall.cart.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import yp.e3mall.cart.service.CartService;
import yp.e3mall.common.jedis.JedisClient;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.common.utils.JsonUtils;
import yp.e3mall.mapper.TbItemMapper;
import yp.e3mall.pojo.TbItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RickYinPeng
 * @ClassName CartServiceImpl
 * @Description 购物车处理服务
 * @date 2018/12/30/22:14
 */
@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_CART_PRE}")
    private String REDIS_CART_PRE;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public E3Result addCart(Long userId, Long itemId,Integer num) {
        //向redis中添加购物车

        //数据类型是hash key：用户id  field：商品id value：商品信息

        //判断商品是否存在
        Boolean hexists = jedisClient.hexists(REDIS_CART_PRE + ":" + userId, itemId + "");

        //如果存在，数量相加
        if(hexists){
            String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
            //把json转换成Tbitem
            TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
            tbItem.setNum(tbItem.getNum()+num);
            //写回redis
            jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "",JsonUtils.objectToJson(tbItem));
            return E3Result.ok();
        }

        //如果不存在，根据商品id取商品信息
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);

        //设置购物车数量
        tbItem.setNum(num);

        String image = tbItem.getImage();
        if(StringUtils.isNotBlank(image)){
            tbItem.setImage(image.split(",")[0]);
        }
        //添加到购物车列表
        jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "",JsonUtils.objectToJson(tbItem));

        //返回成功
        return E3Result.ok();
    }

    @Override
    public E3Result mergeCart(Long userId, List<TbItem> itemList) {
        //遍历商品列表
        //把列表添加到购物车
        //判断购物车中是否有此商品
        //如果有，商品数量相加
        //如果没有，添加新的商品
        for (TbItem tbItem : itemList) {
            /**
             * 上面的这些逻辑我们可以直接调用商品的addCart方法，很简单
             */
            addCart(userId,tbItem.getId(),tbItem.getNum());
        }
        //返回成功
        return E3Result.ok();
    }

    @Override
    public List<TbItem> getCartList(Long userId) {
        //根据用户id查询购物车列表
        List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
        List<TbItem> tbItemList = new ArrayList<>();
        for (String s : jsonList) {
            //将json转换成TbItem对象
            TbItem tbItem = JsonUtils.jsonToPojo(s, TbItem.class);
            //添加到列表
            tbItemList.add(tbItem);
        }
        return tbItemList;
    }

    @Override
    public E3Result updateCartNum(Long userId, Long itemId, int num) {
        //从redis中取出商品信息
        String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
        TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
        tbItem.setNum(num);
        //写回redis
        jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "",JsonUtils.objectToJson(tbItem));

        return E3Result.ok();
    }

    @Override
    public E3Result deleteCartItem(Long userId, Long itemId) {
        jedisClient.hdel(REDIS_CART_PRE + ":" + userId, itemId + "");
        return E3Result.ok();
    }

    @Override
    public E3Result clearCartItem(long userId) {
        //删除购物车信息
        jedisClient.del(REDIS_CART_PRE + ":" + userId);

        return E3Result.ok();
    }
}
