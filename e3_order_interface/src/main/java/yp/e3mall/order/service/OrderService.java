package yp.e3mall.order.service;

import yp.e3mall.common.utils.E3Result;
import yp.e3mall.order.pojo.OrderInfo;

public interface OrderService {

    /**
     * 提交订单服务
     * @param orderInfo 接受表单信息的pojo
     * @return
     */
    E3Result createOrder(OrderInfo orderInfo);

}
