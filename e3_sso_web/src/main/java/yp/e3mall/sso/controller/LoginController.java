package yp.e3mall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import yp.e3mall.common.utils.CookieUtils;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.sso.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author RickYinPeng
 * @ClassName LoginController
 * @Description 用户登录处理
 * @date 2018/12/16/12:44
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/page/login")
    public String showLogin(String redirect, Model model){
        model.addAttribute("redirect",redirect);
        return "login";
    }

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public E3Result login(String username, String password
    , HttpServletRequest request, HttpServletResponse response){
        E3Result e3Result = loginService.userLogin(username, password);
        //判断是否登录成功
        if(e3Result.getStatus()==200){
            String token = e3Result.getData().toString();
            //如果登录成功需要将token写入cookie中
            CookieUtils.setCookie(request,response,"token",token);
        }
        return e3Result;
    }
}
