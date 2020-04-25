package com.rendu.service;

import com.rendu.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author li
 * @since 2020-03-31
 */
public interface UserInfoService extends IService<UserInfo> {
    UserInfo getUserByName(String userName, String password);
}
