package yp.e3mall.cart.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import yp.e3mall.cart.service.CartService;
import yp.e3mall.common.utils.CookieUtils;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.common.utils.JsonUtils;
import yp.e3mall.pojo.TbItem;
import yp.e3mall.pojo.TbUser;
import yp.e3mall.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RickYinPeng
 * @ClassName CartController
 * @Description 购物车处理Controller
 * @date 2018/12/30/13:08
 */
@Controller
public class CartController {

    @Autowired
    private ItemService itemService;

    @Value("${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;

    @Autowired
    private CartService cartService;

    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
                          HttpServletRequest request, HttpServletResponse response){
        //判断用户是否为登录状态
        TbUser tbUser = (TbUser) request.getAttribute("user");
        //如果是登录状态，就把购物车写入redis
        if(tbUser!=null){
            //保存到服务端
            cartService.addCart(tbUser.getId(),itemId,num);
            //返回逻辑视图
            return "cartSuccess";
        }
        //如果未登录使用cookie

        //从cookie中取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);
        //判断商品在商品列表中是否存在
        boolean flag = false;
        for (TbItem tbItem : cartList) {
            /**
             * 这样比较是不行的，因为现在tbItem.getId()的返回值时Long
             * itemId也是Long类型，两个对象使用==比较，比较的是地址而不是值
             * 所以我们需要调用Long中一个方法longValue()来获取到long类型的值
             */
            //如果存在数量相加
            if(tbItem.getId()==itemId.longValue()){
                flag = true;
                //找到商品，数量相加
                tbItem.setNum(tbItem.getNum()+num);
                //跳出循环
                break;
            }
        }
        //如果不存在
        if(!flag){

            //根据商品id查询商品信息。得到一个TbItem对象
            TbItem tbItem = itemService.getItemById(itemId);

            //设置商品数量，因为现在这个数量是我们的库存，是从数据库中查出来的
            tbItem.setNum(num);

            //取一张图片
            String image = tbItem.getImage();

            if(StringUtils.isNotBlank(image)){
                tbItem.setImage(image.split(",")[0]);
            }

            //把商品添加到商品列表
            cartList.add(tbItem);
        }

        //写入cookie
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(cartList),COOKIE_CART_EXPIRE,true);

        //返回添加成功页面
        return "cartSuccess";
    }

    /**
     * 从cookie中取购物车列表的信息
     * @param request
     * @return
     */
    private List<TbItem> getCartListFromCookie(HttpServletRequest request){
        String json = CookieUtils.getCookieValue(request, "cart", true);
        //判断json是否为空
        if(StringUtils.isBlank(json)){
            return new ArrayList<>();
        }
        //将json转换为List
        List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
        return list;
    }

    /**
     * 展示购物车列表
     * @param request
     * @return
     */
    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request,HttpServletResponse response){
        //从cookie中取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);

        //判断用户是否为登录状态
        TbUser tbUser = (TbUser) request.getAttribute("user");
        //如果是登录状态
        if(tbUser!=null){
            //从cookie中取购物车列表(已经在外面取出了)
            //如果不为空，把cookie中的购物车商品和服务端的购物车商品合并
            cartService.mergeCart(tbUser.getId(), cartList);

            //把cookie中的购物车删除
            CookieUtils.deleteCookie(request,response,"cart");
            //从服务端取购物车列表
            cartList = cartService.getCartList(tbUser.getId());
        }
        //未登录状态
        //把列表传给页面
        request.setAttribute("cartList",cartList);

        //返回逻辑视图
        return "cart";
    }

    /**
     * 更新商品信息
     * @param itemId    商品ID
     * @param num       商品数量
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateCartNum(@PathVariable Long itemId,@PathVariable Integer num,
                                  HttpServletRequest request,HttpServletResponse response){
        //判断用户是否登录
        TbUser user = (TbUser) request.getAttribute("user");
        if(user!=null){
            cartService.updateCartNum(user.getId(),itemId,num);
            return E3Result.ok();
        }

        //从cookie中取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);
        //遍历商品列表找到对应商品
        for (TbItem tbItem : cartList) {
            if(tbItem.getId().longValue()==itemId){
                //更新数量
                tbItem.setNum(num);
                break;
            }
        }

        //把购物车列表写回cookie
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(cartList),COOKIE_CART_EXPIRE,true);

        //返回成功
        return E3Result.ok();
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,HttpServletRequest request,
                                 HttpServletResponse response){
        //判断用户是否登录
        TbUser user = (TbUser) request.getAttribute("user");
        if(user!=null){
            cartService.deleteCartItem(user.getId(),itemId);
            return "redirect:/cart/cart.html";
        }


        //从cookie中取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);
        //遍历列表找到需要删除的商品
        for (TbItem tbItem : cartList) {
            if(tbItem.getId().longValue()==itemId){
                //删除商品
                cartList.remove(tbItem);
                //跳出循环
                break;
            }
        }
        //把购物车列表写回cookie
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(cartList),COOKIE_CART_EXPIRE,true);
        //返回逻辑视图
        return "redirect:/cart/cart.html";
    }

}
