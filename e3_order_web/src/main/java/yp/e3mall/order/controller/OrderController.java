package yp.e3mall.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import yp.e3mall.cart.service.CartService;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.order.pojo.OrderInfo;
import yp.e3mall.order.service.OrderService;
import yp.e3mall.pojo.TbItem;
import yp.e3mall.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author RickYinPeng
 * @ClassName OrderController
 * @Description 订单管理Controller
 * @date 2019/1/1/15:35
 */
@Controller
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/order/order-cart")
    public String showOrderCat(HttpServletRequest request){
        //取用户id
        TbUser user = (TbUser) request.getAttribute("user");

        //根据用户id取收获地址列表
        //使用静态数据

        //取支付方式列表
        //静态数据

        //根据用户id取购物车列表
        List<TbItem> cartList = cartService.getCartList(user.getId());

        //把购物车列表传入jsp
        request.setAttribute("cartList",cartList);

        //返回逻辑视图
        return "order-cart";
    }

    @RequestMapping(value = "/order/create",method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo,HttpServletRequest request){
        //取用户信息
        TbUser user = (TbUser) request.getAttribute("user");

        //把用户信息添加到orderInfo中
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());

        //调用服务生成订单
        E3Result e3Result = orderService.createOrder(orderInfo);

        //如果订单生成成功，需要删除购物车
        if(e3Result.getStatus()==200){
            //清空购物车
            cartService.clearCartItem(user.getId());
        }

        //把订单号传递给页面
        request.setAttribute("orderId",e3Result.getData());
        request.setAttribute("payment",orderInfo.getPayment());

        //返回逻辑视图
        return "success";
    }
}
