package yp.e3mall.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yp.e3mall.common.utils.E3Result;
import yp.e3mall.mapper.TbUserMapper;
import yp.e3mall.pojo.TbUser;
import yp.e3mall.pojo.TbUserExample;
import yp.e3mall.sso.service.RegisterService;

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
}
