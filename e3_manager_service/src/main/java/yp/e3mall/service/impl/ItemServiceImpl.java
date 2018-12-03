package yp.e3mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import yp.e3mall.common.jedis.JedisClient;
import yp.e3mall.common.pojo.EasyUIDataGridResult;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.common.utils.IDUtils;
import yp.e3mall.common.utils.JsonUtils;
import yp.e3mall.mapper.TbItemDescMapper;
import yp.e3mall.mapper.TbItemMapper;
import yp.e3mall.pojo.TbItem;
import yp.e3mall.pojo.TbItemDesc;
import yp.e3mall.pojo.TbItemExample;
import yp.e3mall.service.ItemService;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
import java.util.List;

/**
 * 商品管理Service
 * <p>Title: ItemServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;

	@Autowired
    private TbItemDescMapper tbItemDescMapper;

	@Autowired
    private JmsTemplate jmsTemplate;

	@Resource
    private Destination topicDestination;

	@Autowired
    private JedisClient jedisClient;

	@Value("${REDIS_ITEM_PRE}")
    private String REDIS_ITEM_PRE;

	@Value("${REDIS_ITEM_PRE}")
    private Integer ITEM_CACHE_EXPIRE;

	@Override
	public E3Result getDescById(long itemId) {
		System.out.println("进入ItemServiceImpl，说明通信已经成功！！！");

		//根据主键查询
		//TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		TbItemExample example = new TbItemExample();
		TbItemExample.Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andIdEqualTo(itemId);
		//执行查询
		List<TbItem> list = tbItemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
            return E3Result.ok(list.get(0));
		}
        return E3Result.build(404,"找不到该商品了！！！");
	}

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        //page:页码
        //rows:每页显示数量
        PageHelper.startPage(page,rows);
        //执行查询
        TbItemExample tbItemExample = new TbItemExample();
        List<TbItem> itemList = tbItemMapper.selectByExample(tbItemExample);
        //创建一个返回值对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(itemList);
        //取分页结果
        PageInfo pageInfo = new PageInfo(itemList);
        result.setTotal(pageInfo.getTotal());
	    return result;
    }

    @Override
    public E3Result addItem(TbItem tbItem, String desc) {
        //拿到的TbItem缺少ID，因为表单中的是cid，是商品的类目id，并不是商品的ID
        //所以我们需要先生成商品的ID

        //生成商品ID
        long itemId = IDUtils.genItemId();

        //补全TbItem的属性
        tbItem.setId(itemId);
        //商品状态，1-正常，2-下架，3-删除
        tbItem.setStatus((byte)1);
        //创建时间
        tbItem.setCreated(new Date());
        //更新时间
        tbItem.setUpdated(new Date());

        //向商品表插入数据
        tbItemMapper.insert(tbItem);

        //创建商品描述表对应的pojo对象
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());

        //向商品描述表插入数据
        tbItemDescMapper.insert(itemDesc);

        //发送商品添加消息,需要注入jmsTemple
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(itemId+"");
            }
        });

        //返回成功
	    return E3Result.ok();
    }

    @Override
    public E3Result deleteItem(String ids) {
        String[] split = ids.split(",");
        for(int i = 0;i<split.length;i++){
            Long id = Long.valueOf(split[i]);
            tbItemMapper.deleteByPrimaryKey(id);
        }
        return E3Result.ok();
    }

    @Override
    public TbItem getItemById(long itemId) {
	    //查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_PRE + ":" + itemId + ":BASE");
            if(StringUtils.isNotBlank(json)){
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return tbItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //缓存中没有，查询数据库

        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        if(tbItem!=null){
            //将结果添加到缓存
            try {
                jedisClient.set(REDIS_ITEM_PRE+":"+itemId+":BASE", JsonUtils.objectToJson(tbItem));
                //设置过期时间
                jedisClient.expire(REDIS_ITEM_PRE+":"+itemId+":BASE",ITEM_CACHE_EXPIRE);
            }catch (Exception e){
                e.printStackTrace();
            }
            return tbItem;
        }

        return null;
    }

    @Override
    public TbItemDesc getItemDescById(long itemId) {
        //查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_PRE+":"+itemId+":DESC");
            if(StringUtils.isNotBlank(json)){
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return itemDesc;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //缓存中没有，查询数据库

        TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        if(itemDesc!=null){
            //将结果添加到缓存
            try {
                jedisClient.set(REDIS_ITEM_PRE+":"+itemId+":DESC", JsonUtils.objectToJson(itemDesc));
                //设置过期时间
                jedisClient.expire(REDIS_ITEM_PRE+":"+itemId+":DESC",ITEM_CACHE_EXPIRE);
            }catch (Exception e){
                e.printStackTrace();
            }
            return itemDesc;
        }
        return null;
    }

}
