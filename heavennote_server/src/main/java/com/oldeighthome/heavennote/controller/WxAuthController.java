package com.oldeighthome.heavennote.controller;

import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.service.WxAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class WxAuthController {
    @Autowired
    private WxAuthService wxAuthService;

    @PostMapping("/api/v1/wx/session")
    public ApiResult createSession(@RequestBody Map<String, String> wxLoginData){
        return wxAuthService.getWxSession(wxLoginData);
    }
    @PostMapping("/test")
    public ApiResult Test(@RequestBody Map<String,String>map){
        log.info(map.get("id"));
        return ApiResult.success("xxx",map);
    }

}
