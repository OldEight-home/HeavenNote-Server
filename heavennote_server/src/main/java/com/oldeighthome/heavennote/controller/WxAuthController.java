package com.oldeighthome.heavennote.controller;

import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.service.WxAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/v1/wx")
public class WxAuthController {
    @Autowired
    private WxAuthService wxAuthService;

    @PostMapping("/session")
    public ApiResult createSession(@RequestBody Map<String, String> wxLoginData){
        return wxAuthService.getWxSession(wxLoginData);
    }
    @GetMapping("/test")
    public ApiResult test(ServletRequest request){
        HttpServletRequest res= (HttpServletRequest) request;
        log.info(res.getHeader("token"));
        return ApiResult.success("通过");
    }

}
