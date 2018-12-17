package yp.e3mall.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import yp.e3mall.common.jedis.JedisClient;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.common.utils.JsonUtils;
import yp.e3mall.pojo.TbUser;
import yp.e3mall.sso.service.TokenService;

/**
 * @author RickYinPeng
 * @ClassName TokenServiceImpl
 * @Description 根据token取用户信息
 * @date 2018/12/17/16:54
 */
@Service
public class TokenServiceImpl implements TokenService{

    @Autowired
    private JedisClient jedisClient;

    @Value("${SESSION_EXPIRE}")
    private String SESSION_EXPIRE;

    @Override
    public E3Result getUserByToken(String token) {
        //根据token到redis中获取用户信息
        String json = jedisClient.get("SESSION:" + token);
        //取不到用户信息，登录已经过期，返回登录过期
        if(StringUtils.isBlank(json)){
            return E3Result.build(400,"用户登录已经过期");
        }
        //取到用户信息更新token的过期时间
        jedisClient.expire("SESSION:"+token, Integer.parseInt(SESSION_EXPIRE));
        //返回结果，E3Result其中包含TbUser对象
        TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
        return E3Result.ok(tbUser);
    }
}
