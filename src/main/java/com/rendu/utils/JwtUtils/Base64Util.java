package com.rendu.utils.JwtUtils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName Base64Util
 * @Description: TODO
 * @Author: li
 * @Date: 2020/3/7 14:49
 * @Version v1.0
 **/
public class Base64Util {
    
    private static final Logger logger = LoggerFactory.getLogger(Base64.class);
    
    private static final String charset = "utf-8";
    
    /*
    * 解密
    * @param data
    * @return
    * */
    
    public static String decode(String data){
        try {
            if (null == data){
                return null;
            }
            return new String(Base64.decodeBase64(data.getBytes(charset)),charset);
        } catch (UnsupportedEncodingException e) {
            logger.error(String.format("字符串： %s,解密异常",data),e);
        }
        return null;
    }
    
    /*
    * 加密
    * @param data
    * @return
    * */
    
    public static String encode(String data){
        try {
            if (null == data){
                return null;
            }
            return new String(Base64.encodeBase64(data.getBytes(charset)),charset);
        } catch (UnsupportedEncodingException e) {
            logger.error(String.format("字符串： %s,加密异常",data),e);
        }
        return null;
    }
}
