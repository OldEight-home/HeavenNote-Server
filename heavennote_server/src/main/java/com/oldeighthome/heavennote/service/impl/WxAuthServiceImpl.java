package com.oldeighthome.heavennote.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.common.global.GlobalValue;
import com.oldeighthome.heavennote.config.wx.WxConfig;
import com.oldeighthome.heavennote.entity.Note;
import com.oldeighthome.heavennote.entity.User;
import com.oldeighthome.heavennote.entity.WxSessionModel;
import com.oldeighthome.heavennote.mapper.UserMapper;
import com.oldeighthome.heavennote.service.WxAuthService;
import com.oldeighthome.heavennote.util.*;
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
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**重写的方法*/

    /**
     * 向微信服务器请求用户信息
     * @param wxLoginData
     * @return
     */
    @Override
    public ApiResult getWxSession(Map<String, String> wxLoginData) {
        WxSessionModel wxSessionModel=getInfoFromWxServer(wxLoginData.get(GlobalValue.CODE));
        if(wxSessionModel==null){
            log.error("登录出错，未找到用户");
            return ApiResult.error("登录出错，未找到用户");
        }
        else if (wxSessionModel.getErrcode() != null) {
            log.error("登录出错，错误信息:{}",wxSessionModel.getErrcode());
            return ApiResult.error(String.format("登录出错，错误信息:%s",wxSessionModel.getErrmsg()),wxSessionModel.getErrmsg());
        }
        log.info("openid: {}", wxSessionModel.getOpenid());
        String openId=wxSessionModel.getOpenid();
        //查询数据库，第一次访问为用户初始化数据库
        String userInfoStr=WxUtil.getUserInfo(wxLoginData.get(GlobalValue.ENCRYPTEDDATA),wxSessionModel.getSession_key(),
                wxLoginData.get(GlobalValue.IV));
        log.info(userInfoStr);
        JSONObject userInfo= JSONObject.parseObject(userInfoStr);
        String nickName=userInfo.get("nickName").toString();
        String avatarUrl=userInfo.get("avatarUrl").toString();
        if(getOne(new QueryWrapper<User>().eq("user_id",openId))==null){
            //为空则添加数据库
            userInfoIntoDatabase(nickName,avatarUrl,openId);
            log.info("{},注册成功",nickName);
        }
        else{
            //先查用户信息有无变动，有则更新
            User recordUser=getOne(new QueryWrapper<User>().eq("user_id",openId));
            if(!(recordUser.getUsername().equals(nickName)&&
            recordUser.getAvatar().equals(avatarUrl))){
                //更新用户信息
                userInfoIntoDatabase(nickName,avatarUrl,openId);
                log.info("已更新{}的信息",nickName);
            }

        }
        //生成token
        String token= jwtTokenUtil.generateToken(nickName);
        log.info("用户的token是:{}",token);
        Map<String,String> map=new HashMap<>();
        map.put("token",token);
        ApiResult result=ApiResult.success("登录成功",map);
        return result;
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
    private void userInfoIntoDatabase(String nickName,String avatarUrl,String openId){
        User user=new User();
        user.setUserId(openId)
                .setUsername(nickName)
                .setAvatar(avatarUrl)
                .setFollowerNum(0);
        saveOrUpdate(user);

    }


}
