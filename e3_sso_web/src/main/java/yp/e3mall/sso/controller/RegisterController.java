package yp.e3mall.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author RickYinPeng
 * @ClassName RegisterController
 * @Description
 * @date 2018/12/7/22:46
 */
@Controller
public class RegisterController {

    @RequestMapping("/page/register")
    public String showRegister(){
        return "register";
    }

}
