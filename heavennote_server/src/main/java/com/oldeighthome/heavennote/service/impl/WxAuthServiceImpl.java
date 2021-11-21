package com.oldeighthome.heavennote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.common.global.GlobalValue;
import com.oldeighthome.heavennote.config.wx.WxConfig;
import com.oldeighthome.heavennote.entity.User;
import com.oldeighthome.heavennote.entity.WxSessionModel;
import com.oldeighthome.heavennote.mapper.UserMapper;
import com.oldeighthome.heavennote.service.WxAuthService;
import com.oldeighthome.heavennote.util.HttpClientUtil;
import com.oldeighthome.heavennote.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service
public class WxAuthServiceImpl extends ServiceImpl<UserMapper, User> implements WxAuthService {
    @Autowired
    private WxConfig wxConfig;


    /**重写的方法*/

    /**
     * 向微信服务器请求用户信息
     * @param wxCode
     * @return
     */
    @Override
    public ApiResult getWxSession(String wxCode) {
        WxSessionModel wxSessionModel=getInfoFromWxServer(wxCode);
        if (wxSessionModel.getErrcode() != null) {
            return ApiResult.error(wxSessionModel.getErrmsg());

        }
        log.info("openid: {}", wxSessionModel.getOpenid());
        String openId=wxSessionModel.getOpenid();
        //查询数据库，第一次访问为用户初始化数据库
        //为空则添加数据库
        if(getOne(new QueryWrapper<User>().eq("user_id",openId))==null){

        }

        return null;
    }

    /**封装的方法*/

    private WxSessionModel getInfoFromWxServer(String wxCode){
        Map<String,String> params=new HashMap<>();
        params.put(GlobalValue.APP_ID,wxConfig.appid);
        params.put(GlobalValue.SECRET,wxConfig.appsecret);
        params.put(GlobalValue.JS_CODE,wxCode);
        params.put(GlobalValue.GRANT_TYPE,GlobalValue.AUTHORIZATION_CODE);
        String res= HttpClientUtil.doGet(GlobalValue.WX_SESSION_ADDRESS,params);
        if (res == null || "".equals(res)) {
            return null;
        }
        return JsonUtil.jsonToPojo(res, WxSessionModel.class);
    }
    private void InitUserInfo(){

    }
}
