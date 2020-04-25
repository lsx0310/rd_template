package com.rendu.utils.JwtUtils;

import com.rendu.common.exception.CustomException;
import com.rendu.common.response.ResultCode;
import com.rendu.component.Audience;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName jwtTokenUtil
 * @Description: TODO
 * @Author: li
 * @Date: 2020/3/7 15:09
 * @Version v1.0
 **/
public class JwtTokenUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    
    public static final String AUTH_HEADER_KEY = "Authorization";
    
    public static final String TOKEN_PREFIX = "Bearer ";
    
    public static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    

    
    /**
     * 解析jwt
     * @param jsonWebToken
     * @param base64Security
     * @return
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security) {
        try {
            System.out.println(jsonWebToken + "jsonWebToken");
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (ExpiredJwtException eje) {
            logger.error("===== Token过期 =====", eje);
            throw new CustomException(ResultCode.PERMISSION_TOKEN_EXPIRED);
        } catch (Exception e){
            logger.error("===== token解析异常 =====", e);
            throw new CustomException(ResultCode.PERMISSION_TOKEN_INVALID);
        }
    }
    
    /**构建jwt
    * @param username
    * @param role
    * @param audience
    * @return
    * */
    public static String createJWT(Integer userId,String username, String role, String roleName,
                                   String introduction, Audience audience){
        System.out.println(role);

        try{
            // 使用HS256加密算法
            
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            
            //生成签名密钥
            
            Key signingKey = createSecretKey(audience);
            
            //password是重要信息，进行加密下
//            String encryPassword = Base64Util.encode(password);
            
            //添加构成JWT的参数
            JwtBuilder builder = Jwts.builder().setHeaderParam("typ","JWT")
                    .claim("userName",username)
                    .claim("role",role)
                    .claim("roleName",roleName)
                    .claim("introduction",introduction)
                    .claim("userId",userId)
                    .setSubject(username)
                    .setIssuer(audience.getClientId())
                    .setIssuedAt(new Date())
                    .setAudience(audience.getName())
                    .signWith(signatureAlgorithm,signingKey);
            
            //添加token过期时间
            int TTLMillis = audience.getExpiresSecond();
            if (TTLMillis > 0){
                long expMillis = nowMillis + TTLMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp)
                        .setNotBefore(now);
            }
            
            return builder.compact();
        }catch (Exception e){
            logger.error("签名失败",e);
        }
        return null;
    }
    
    /**
     * 从token中获取用户名
     * @param token
     * @param base64Security
     * @return
     */
    public static String getUsername(String token,String base64Security){
        String username = parseJWT(token,base64Security).get("username",String.class);
        return Base64Util.decode(username);
    }
    
    /**
     * 从token中获取用户Role
     * @param token
     * @param base64Security
     * @return
     */
    public static String getUserRole(String token, String base64Security){
        String role = parseJWT(token, base64Security).get("role", String.class);
        return Base64Util.decode(role);
    }
    
    private static Key createSecretKey(Audience audience){
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(audience.getBase64Secret());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,signatureAlgorithm.getJcaName());
        return signingKey;
    }
    
    public static boolean checkToken(Claims claims){
        long exp = claims.getExpiration().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,5);
        long overdue = calendar.getTimeInMillis();
        
        if (exp > overdue){
            return false;
        }
        return true;
    }
    
}
