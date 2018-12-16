package yp.e3mall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.pojo.TbUser;
import yp.e3mall.sso.service.RegisterService;

/**
 * @author RickYinPeng
 * @ClassName RegisterController
 * @Description
 * @date 2018/12/7/22:46
 */
@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping("/page/register")
    public String showRegister(){
        return "register";
    }

    @ResponseBody
    @RequestMapping("/user/check/{param}/{type}")
    public E3Result checkData(@PathVariable String param,@PathVariable Integer type){
        E3Result e3Result = registerService.checkData(param, type);
        return e3Result;
    }

    @ResponseBody
    @RequestMapping(value = "/user/register" ,method = RequestMethod.POST)
    public E3Result register(TbUser tbUser){
        E3Result result = registerService.register(tbUser);
        return result;
    }




}
