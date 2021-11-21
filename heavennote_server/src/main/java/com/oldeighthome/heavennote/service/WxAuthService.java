package com.oldeighthome.heavennote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.entity.User;

public interface WxAuthService extends IService<User>{
    public ApiResult getWxSession(String wxCode);
}
