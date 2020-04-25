package com.rendu.common.exception;

import com.rendu.common.response.ResultCode;

/**
 * @ClassName FileException
 * @Description: TODO
 * @Author: li
 * @Date: 2020/4/15 11:38
 * @Version v1.0
 **/
public class FileException extends CustomException {
    
    public FileException(ResultCode resultCode) {
        super(resultCode);
    }
    
    public FileException(ResultCode resultCode, Object... args) {
        super(resultCode, args);
    }
    
    @Override
    public ResultCode getResultCode() {
        return super.getResultCode();
    }
}
