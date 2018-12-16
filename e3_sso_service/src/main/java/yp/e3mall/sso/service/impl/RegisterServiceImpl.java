package yp.e3mall.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.mapper.TbUserMapper;
import yp.e3mall.pojo.TbUser;
import yp.e3mall.pojo.TbUserExample;
import yp.e3mall.sso.service.RegisterService;

import java.util.Date;
import java.util.List;

/**
 * @author RickYinPeng
 * @ClassName RegisterServiceImpl
 * @Description 用户注册处理
 * @date 2018/12/9/12:09
 */
@Service
public class RegisterServiceImpl implements RegisterService{

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public E3Result checkData(String param, int type) {
        //根据不同的type生成不同的查询条件
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        //1:用户名 2:手机号 3:邮箱
        if(type==1){
            criteria.andUsernameEqualTo(param);
        }else if(type==2){
            criteria.andPhoneEqualTo(param);
        }else if(type==3){
            criteria.andEmailEqualTo(param);
        }else {
            return E3Result.build(400,"数据类型错误");
        }
        //执行查询
        List<TbUser> tbUserList = tbUserMapper.selectByExample(tbUserExample);

        //判断结果中是否包含数据
        if(tbUserList!=null && tbUserList.size()>0){
            //如果有数据返回false
            return E3Result.ok(false);
        }
        //如果没有数据返回true
        return E3Result.ok(true);
    }

    @Override
    public E3Result register(TbUser tbUser) {
        //数据有效性校验
        if(StringUtils.isBlank(tbUser.getUsername())
                || StringUtils.isBlank(tbUser.getPassword())
                || StringUtils.isBlank(tbUser.getPhone())){
            return E3Result.build(400,"用户数据不完整，注册失败");
        }
        //1:用户名 2：手机号 3：邮箱
        E3Result result = checkData(tbUser.getUsername(), 1);
        if(!(boolean)result.getData()){
            return E3Result.build(400,"此用户名已经被占用");
        }
        result = checkData(tbUser.getPhone(),2);
        if(!(boolean)result.getData()){
            return E3Result.build(400,"此手机号已经被占用");
        }
        //补全pojo的属性
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        //对密码进行MD5加密
        String md5Pass = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        tbUser.setPassword(md5Pass);
        //把用户数据插入到数据库中
        tbUserMapper.insert(tbUser);

        return E3Result.ok();
    }
}
