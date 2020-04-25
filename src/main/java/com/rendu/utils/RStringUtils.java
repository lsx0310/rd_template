package com.rendu.utils;

import java.util.regex.Pattern;

/**
 * @ClassName RStringUtils
 * @Description: TODO
 * @Author: li
 * @Date: 2020/3/18 11:28
 * @Version v1.0
 **/
public class RStringUtils {
    
    /*
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
