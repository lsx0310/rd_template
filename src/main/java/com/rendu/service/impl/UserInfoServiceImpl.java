package com.rendu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rendu.common.exception.UserException;
import com.rendu.common.response.ResultCode;
import com.rendu.entity.UserInfo;
import com.rendu.mapper.UserInfoMapper;
import com.rendu.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author li
 * @since 2020-03-31
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    
    @Autowired
    UserInfoMapper userInfoMapper;
    
    @Override
    public UserInfo getUserByName(String userName, String password) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        Map<String,Object> map = new HashMap<>();
        map.put("user_name",userName);
        map.put("password",password);
        queryWrapper.allEq(map);
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        if (userInfo == null){
            throw new UserException(ResultCode.USER_LOGIN_ERROR);
        }
        return userInfo;
    }
}
