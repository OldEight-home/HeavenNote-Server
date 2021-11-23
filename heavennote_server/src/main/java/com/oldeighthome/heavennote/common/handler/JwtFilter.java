package com.oldeighthome.heavennote.common.handler;

import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.common.web.HeaderMapRequestWrapper;
import com.oldeighthome.heavennote.util.JwtTokenUtil;
import com.oldeighthome.heavennote.util.ResponseUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.LogRecord;
@Slf4j
@Component
@WebFilter(urlPatterns = "/*")
public class JwtFilter implements Filter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/api/v1/wx/session")));

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        boolean allowedPath = ALLOWED_PATHS.contains(path);

        if (allowedPath) {
            filterChain.doFilter(request, response);
        }
        else {
            String token=request.getHeader("token");

            ApiResult result;
            if(token==null){
                result=ApiResult.error("没有收到token");
                PrintWriter printWriter= ResponseUtil.getPrintWriter(response);
                ResponseUtil.output(printWriter,result);
                return;
            }
            log.info("过滤器收到的token是:{}",token);
            Claims claims=jwtTokenUtil.validateToken(token);
            if(claims==null){
                String errMes="token验证失败，用户尚未登录";
                log.error(errMes);
                result= ApiResult.error(errMes);
                PrintWriter printWriter= ResponseUtil.getPrintWriter(response);
                ResponseUtil.output(printWriter,result);
                return;
            }
            //正确的token
            HeaderMapRequestWrapper requestWrapper=new HeaderMapRequestWrapper(request);
            requestWrapper.addHeader("id",claims.getSubject());
            log.info("token正确");
            filterChain.doFilter(requestWrapper,response);
        }
    }
}
