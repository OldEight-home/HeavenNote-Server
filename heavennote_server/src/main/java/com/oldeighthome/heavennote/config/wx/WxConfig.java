package com.oldeighthome.heavennote.config.wx;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxConfig {

    /**
     * 开放平台appid
     */
    @Value("${wx.appid}")
    public String appid;

    /**
     * 开放平台appsecret
     */
    @Value("${wx.appsecret}")
    public String appsecret;
}
