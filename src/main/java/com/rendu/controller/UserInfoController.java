package com.rendu.controller;



import com.alibaba.fastjson.JSONObject;
import com.rendu.annotation.JwtIgnore;
import com.rendu.common.response.Result;
import com.rendu.component.Audience;
import com.rendu.entity.RoleInfo;
import com.rendu.entity.UserInfo;
import com.rendu.service.impl.RoleInfoServiceImpl;
import com.rendu.service.impl.UserInfoServiceImpl;
import com.rendu.utils.JwtUtils.Base64Util;
import com.rendu.utils.JwtUtils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author li
 * @since 2020-03-30
 */
@RestController
@RequestMapping(value = "/user",produces = "application/json;charset=utf-8")
public class UserInfoController {
    private final static Logger logger = LoggerFactory.getLogger(UserInfoController.class);
    
    @Autowired
    Audience audience;
    
    @Autowired
    UserInfoServiceImpl userInfoService;
    
    @Autowired
    RoleInfoServiceImpl roleInfoService;
    
    @PostMapping("/login")
    @ApiOperation("用户登录接口")
    @JwtIgnore
    public Result UserLogin(HttpServletResponse response, @RequestBody JSONObject jsonParam){
    
        String userName = (String) jsonParam.get("username");
        String password = (String) jsonParam.get("password");
    
        
        String encryPassword = Base64Util.encode(password);
        UserInfo userInfo = userInfoService.getUserByName(userName,encryPassword);
        RoleInfo roleInfo = roleInfoService.getById(userInfo.getRoleId());
    
        // 创建token
        String token = JwtTokenUtil.createJWT(userInfo.getUserId(),userInfo.getUserName(),roleInfo.getRole(),roleInfo.getRoleName(),roleInfo.getIntroduction(),audience);
        logger.info("### 登录成功, token={} ###", token);

        // 将token放在响应头
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
        
        // 将token响应给客户端
        JSONObject result = new JSONObject();
        result.put("token", token);

        return Result.SUCCESS(result);
    }
    
    @GetMapping("/info")
    @ApiOperation("获取用户角色接口")
    public Result UserInfo(HttpServletRequest request){
        Map<String,Object> claims = (Claims) request.getAttribute("claims");
        String[] roles = new String[]{(String) claims.get("role")};
        JSONObject result = new JSONObject();
        result.put("roles",roles);
        result.put("roleName",claims.get("roleName"));
        result.put("introduction",claims.get("introduction"));
        result.put("userName",claims.get("userName"));
        result.put("userId",claims.get("userId"));
        return Result.SUCCESS(result);
    }
    
    @PostMapping("/")
    @ApiOperation("用户添加接口")
    public Result InsertUser(@RequestBody UserInfo jsonParam){
        jsonParam.setPassword(Base64Util.encode(jsonParam.getPassword()));
        return userInfoService.save(jsonParam) ? Result.SUCCESS() : Result.FAIL();
    }
}

