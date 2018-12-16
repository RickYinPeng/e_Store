package yp.e3mall.sso.service;

import yp.e3mall.common.utils.E3Result;

public interface LoginService {

    /**
     * 用户登录逻辑
     * @param username 用户名
     * @param password 密码
     * @return
     */
    E3Result userLogin(String username ,String password);

}
