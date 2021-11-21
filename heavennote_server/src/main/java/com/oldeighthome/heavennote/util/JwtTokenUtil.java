package com.oldeighthome.heavennote.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    //荷载claim的名称
    private static final String CLAIM_KEY_USERNAME="sub";
    //荷载的创建时间
    private static final String CLAIM_KEY_CREATE="created";
    //jwt令牌的密钥
    @Value("${jwt.secret}")
    private static String secret;
    //jwt过期时间
    @Value("${jwt.expiration}")
    private static Long expiration;

    /**
     * 根据用户名生成token
     * @param userName
     * @return
     */
    public static String generateToken(String userName){
        Map<String,Object> claims=new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userName);
        claims.put(CLAIM_KEY_CREATE,new Date());
        return generateToken(claims);
    }

    /**
     * 根据荷载生成token
     * @param claims
     * @return
     */
    private static String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(generateExpiration())
                    .signWith(SignatureAlgorithm.HS256,secret)
                    .compact();
    }

    private static Date generateExpiration() {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public static String getUserNameFromToken(String token){
        String userName;
        try{
            Claims claims=getClaimsFromToken(token);
            userName=claims.getSubject();
        }catch (Exception e){
            userName=null;
        }
        return userName;

    }

    private static Claims getClaimsFromToken(String token) {
        Claims claims=null;
        try{
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return claims;

    }

    /**
     * 判断token是否有效，要求检验用户信息的名字是否与token中解析的名字相同以及token是否过期
     * @param token
     * @param userDetails
     * @return
     */
    /*
    public static boolean validateToken(String token, UserDetails userDetails){
        String userName=getUserNameFromToken(token);
        return  userName.equals(userDetails.getUsername())&& !isTokenExpired(token);
    }
*/
    private static Date getExpiredDateFromToken(String token){
        Claims claims=getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 判断token能否被刷新
     * @param token
     * @return
     */
    public static boolean canRefresh(String token){
        return  !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public static String refreshToken(String token){
        Claims claims=getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATE,new Date());
        return generateToken(claims);
    }

    private static boolean isTokenExpired(String token){
        return getExpiredDateFromToken(token).before(new Date());
    }

}
