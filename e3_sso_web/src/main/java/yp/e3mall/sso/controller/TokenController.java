package yp.e3mall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.sso.service.TokenService;

/**
 * @author RickYinPeng
 * @ClassName TokenController
 * @Description 根据token查询用户信息
 * @date 2018/12/17/18:24
 */
@Controller
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping("/user/token/{token}")
    @ResponseBody
    public E3Result getUserByToken(@PathVariable String token){
        E3Result result = tokenService.getUserByToken(token);
        return result;
    }
}
