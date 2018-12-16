package yp.e3mall.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import yp.e3mall.common.jedis.JedisClient;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.common.utils.JsonUtils;
import yp.e3mall.mapper.TbUserMapper;
import yp.e3mall.pojo.TbUser;
import yp.e3mall.pojo.TbUserExample;
import yp.e3mall.sso.service.LoginService;

import java.util.List;
import java.util.UUID;

/**
 * @author RickYinPeng
 * @ClassName LoginServiceImpl
 * @Description 用户登录处理
 * @date 2018/12/16/13:27
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${SESSION_EXPIRE}")
    private String SESSION_EXPIRE;

    @Override
    /**
     * 参数：用户名和密码
     * 业务逻辑：
     * 1：判断用户和密码是否正确
     * 2：如果不正确返回登录失败
     * 3：如果正确生成token
     * 4：把用户信息写入redis，key：token value：用户信息
     * 5：设置Session过期时间
     * 6：把token返回(在服务层将token写入cookie中)
     *
     * 返回值:E3Result,其中包含token信息
     */
    public E3Result userLogin(String username, String password) {
//      1：判断用户和密码是否正确
        //根据用户名查询用户信息
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        //执行查询
        List<TbUser> userList = tbUserMapper.selectByExample(tbUserExample);

//      2：如果不正确返回登录失败
        if(userList==null || userList.size()==0){
            //返回登录失败
            return E3Result.build(400,"用户名或密码错误");
        }
        //取用户信息
        TbUser tbUser = userList.get(0);
        //判断密码是否正确
        if(DigestUtils.md5DigestAsHex(password.getBytes()).equals(tbUser.getPassword())){
            return E3Result.build(400,"用户名或密码错误");
        }

//      3：如果正确生成token
        String token = UUID.randomUUID().toString();

//      4：把用户信息写入redis，key：token value：用户信息
        tbUser.setPassword(null);
        jedisClient.set("SESSION:"+token, JsonUtils.objectToJson(tbUser));

//      5：设置Session过期时间
        jedisClient.expire("SESSION:"+token, Integer.parseInt(SESSION_EXPIRE));

//      6：把token返回(在服务层将token写入cookie中)


//      返回值:E3Result
        return E3Result.ok(token);
    }
}
