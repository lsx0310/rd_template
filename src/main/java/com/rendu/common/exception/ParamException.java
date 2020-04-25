package com.rendu.common.exception;

import com.rendu.common.response.ResultCode;

/**
 * @ClassName ParamException
 * @Description: TODO
 * @Author: li
 * @Date: 2020/3/11 19:52
 * @Version v1.0
 **/
public class ParamException extends CustomException{
    
    
    public ParamException(ResultCode resultCode) {
        super(resultCode);
    }
    
    public ParamException(ResultCode resultCode, Object... args) {
        super(resultCode, args);
    }
}
