package yp.e3mall.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
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

/*    @RequestMapping(value = "/user/token/{token}",produces= MediaType.APPLICATION_JSON_UTF8_VALUE*//*"application/json;charset=utf-8"*//*)
    @ResponseBody
    public String getUserByToken(@PathVariable String token,String callback){
        E3Result result = tokenService.getUserByToken(token);
        //响应结果之前先判断是否为jsonp请求
        if(StringUtils.isNotBlank(callback)){
            //把结果封装成一个js语句响应出去
            return callback+"("+ JsonUtils.objectToJson(result)+");";
        }
        return JsonUtils.objectToJson(result);
    }*/

    @RequestMapping(value = "/user/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token,String callback){
        E3Result result = tokenService.getUserByToken(token);
        //响应结果之前先判断是否为jsonp请求
        if(StringUtils.isNotBlank(callback)){
            //把结果封装成一个js语句响应出去
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }
}
