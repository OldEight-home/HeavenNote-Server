package com.oldeighthome.heavennote.common.handler;

import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.util.JwtTokenUtil;
import com.oldeighthome.heavennote.util.ResponseUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
@Component
@Slf4j
public class JwtHandler implements HandlerInterceptor {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=request.getHeader("token");
        PrintWriter printWriter= ResponseUtil.getPrintWriter(response);
        ApiResult result;
        if(token==null){
            result=ApiResult.error("没有收到token");
            ResponseUtil.output(printWriter,result);
            return false;
        }
        log.info("拦截器收到的token是:{}",token);
        Claims claims=jwtTokenUtil.validateToken(token);
        if(claims==null){
            String errMes="token验证失败，用户尚未登录";
            log.error(errMes);
            result= ApiResult.error(errMes);
            ResponseUtil.output(printWriter,result);
            return false;
        }
        return true;
    }
}
