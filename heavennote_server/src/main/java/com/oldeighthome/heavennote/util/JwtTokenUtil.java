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
    private  String secret;
    //jwt过期时间
    @Value("${jwt.expiration}")
    private  Long expiration;

    /**
     * 根据用户名生成token
     * @param userName
     * @return
     */
    public  String generateToken(String userName){
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
    private  String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(generateExpiration())
                    .signWith(SignatureAlgorithm.HS256,secret)
                    .compact();
    }

    private Date generateExpiration() {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public  String getUserNameFromToken(String token){
        String userName;
        try{
            Claims claims=getClaimsFromToken(token);
            userName=claims.getSubject();
        }catch (Exception e){
            userName=null;
        }
        return userName;

    }

    private Claims getClaimsFromToken(String token) {
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
     * 由token
     * @param token
     * @return
     */
    public Claims validateToken(String token){
        Claims claims = getClaimsFromToken(token);
        return claims;
    }
    private  Date getExpiredDateFromToken(String token){
        Claims claims=getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 判断token能否被刷新
     * @param token
     * @return
     */
    public  boolean canRefresh(String token){
        return  !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public  String refreshToken(String token){
        Claims claims=getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATE,new Date());
        return generateToken(claims);
    }

    private  boolean isTokenExpired(String token){
        return getExpiredDateFromToken(token).before(new Date());
    }


}
