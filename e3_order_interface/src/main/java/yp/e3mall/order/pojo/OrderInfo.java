package yp.e3mall.order.pojo;

import yp.e3mall.pojo.TbOrder;
import yp.e3mall.pojo.TbOrderItem;
import yp.e3mall.pojo.TbOrderShipping;

import java.util.List;

/**
 * @author RickYinPeng
 * @ClassName OrderInfo
 * @Description 接受订单信息
 * @date 2019/1/2/15:23
 */
public class OrderInfo extends TbOrder{

    private List<TbOrderItem> orderItems;

    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
