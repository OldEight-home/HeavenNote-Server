package com.oldeighthome.heavennote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.entity.User;

import java.util.Map;

public interface WxAuthService extends IService<User>{
    public ApiResult getWxSession(Map<String, String> wxLoginData);
}
