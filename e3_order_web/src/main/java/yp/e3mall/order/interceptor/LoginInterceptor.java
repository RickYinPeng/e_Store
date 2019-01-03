package yp.e3mall.order.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import yp.e3mall.cart.service.CartService;
import yp.e3mall.common.utils.CookieUtils;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.common.utils.JsonUtils;
import yp.e3mall.pojo.TbItem;
import yp.e3mall.pojo.TbUser;
import yp.e3mall.sso.service.TokenService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author RickYinPeng
 * @ClassName LoginInterceptor
 * @Description 用户登录拦截器
 * @date 2019/1/1/16:49
 */
public class LoginInterceptor implements HandlerInterceptor{

    @Value("${SSO_URL}")
    private String SSO_URL;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CartService cartService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //从cookie中取token
        String token = CookieUtils.getCookieValue(httpServletRequest, "token");

        //判断token是否存在
        if(StringUtils.isBlank("token")){
            //如果token不存在，未登录状态，跳转到登录页面，用户登录成功后，跳转当前请求的URL
            //要做rediect跳转，这里不能forward，因为是不同的工程啊，我们要去SSO工程啊，只能重定向，不能转发
            httpServletResponse.sendRedirect(SSO_URL+"/page/login?redirect="+httpServletRequest.getRequestURL());

            //拦截
            return false;
        }
        //如果token存在，调用sso系统的服务，根据token取用户信息
        E3Result e3Result = tokenService.getUserByToken(token);

        //如果取不到，用户登录已经过期，需要登录
        if(e3Result.getStatus()!=200){

            httpServletResponse.sendRedirect(SSO_URL+"/page/login?redirect="+httpServletRequest.getRequestURL());

            return false;
        }

        //如果取到用户信息，是登录状态，需要将用户信息写入request中
        TbUser tbUser = (TbUser) e3Result.getData();
        httpServletRequest.setAttribute("user",tbUser);

        //判断cookie中是否有购物车数据，如果有就合并到服务端
        String jsonCartList = CookieUtils.getCookieValue(httpServletRequest, "cart", true);
        if(StringUtils.isNotBlank(jsonCartList)){
            //合并购物车
            cartService.mergeCart(tbUser.getId(), JsonUtils.jsonToList(jsonCartList, TbItem.class));
        }

        //放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
