package yp.e3mall.sso.service;

import yp.e3mall.common.utils.E3Result;

/**
 * 根据Token查询用户信息
 */
public interface TokenService {

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    E3Result getUserByToken(String token);

}
