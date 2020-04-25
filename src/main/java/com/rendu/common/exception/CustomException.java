package com.rendu.common.exception;

import com.rendu.common.response.ResultCode;

import java.text.MessageFormat;

/**
 * @ClassName CustomException
 * @Description: TODO
 * @Author: li
 * @Date: 2020/3/7 16:42
 * @Version v1.0
 **/
public class CustomException extends RuntimeException{
    
    //错误代码
    ResultCode resultCode;
    
    public CustomException(ResultCode resultCode){
        super(resultCode.message());
        this.resultCode = resultCode;
    }
    
    public CustomException(ResultCode resultCode, Object... args){
        super(resultCode.message());
        String message = MessageFormat.format(resultCode.message(), args);
        resultCode.setMessage(message);
        this.resultCode = resultCode;
    }
    
    public ResultCode getResultCode(){
        return resultCode;
    }
    
}
