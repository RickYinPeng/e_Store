package yp.e3mall.sso.service;

import yp.e3mall.common.utils.E3Result;
import yp.e3mall.pojo.TbUser;

public interface RegisterService {

    /**
     * 检查数据是否存在接口
     * @param param 用户名/手机号/Email
     * @param type 1:用户名 2:手机号 3:Email
     * @return
     */
    E3Result checkData(String param,int type);

    /**
     * 用户注册
     * @param tbUser 需要注册的用户信息
     * @return
     */
    E3Result register(TbUser tbUser);

}
