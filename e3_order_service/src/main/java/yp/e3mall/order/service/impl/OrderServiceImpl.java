package yp.e3mall.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import yp.e3mall.common.jedis.JedisClient;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.mapper.TbOrderItemMapper;
import yp.e3mall.mapper.TbOrderMapper;
import yp.e3mall.mapper.TbOrderShippingMapper;
import yp.e3mall.order.pojo.OrderInfo;
import yp.e3mall.order.service.OrderService;
import yp.e3mall.pojo.TbOrderItem;
import yp.e3mall.pojo.TbOrderShipping;

import java.util.Date;
import java.util.List;

/**
 * @author RickYinPeng
 * @ClassName OrderServiceImpl
 * @Description 订单处理服务
 * @date 2019/1/2/15:45
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;

    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${ORDER_ID_GEN_KEY}")
    private String ORDER_ID_GEN_KEY;

    @Value("${ORDER_ID_START}")
    private String ORDER_ID_START;

    @Value("${OREDER_DETAIL_ID_GEN_KEY}")
    private String OREDER_DETAIL_ID_GEN_KEY;

    @Override
    public E3Result createOrder(OrderInfo orderInfo) {
        //生成订单号,使用redis的incr生成
        //给订单号设置初始值
        if(!jedisClient.exists(ORDER_ID_GEN_KEY)){
            jedisClient.set(ORDER_ID_GEN_KEY,ORDER_ID_START);
        }
        String order_id = jedisClient.incr(ORDER_ID_GEN_KEY).toString();

        //补全TbOrder的属性
        orderInfo.setOrderId(order_id);

        //状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        orderInfo.setStatus(1);

        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());

        //插入订单表
        tbOrderMapper.insert(orderInfo);

        //补全订单明细表信息
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem orderItem : orderItems) {
            //生成明细id
            String odid = jedisClient.incr(OREDER_DETAIL_ID_GEN_KEY).toString();
            //补全pojo的属性
            orderItem.setId(odid);
            orderItem.setOrderId(order_id);

            //向明细表插入数据
            tbOrderItemMapper.insert(orderItem);
        }

        //补全物流表信息
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(order_id);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());

        //向订单物流表插入数据
        tbOrderShippingMapper.insert(orderShipping);

        //返回E3Result，其中包含订单号

        return E3Result.ok(order_id);
    }
}
