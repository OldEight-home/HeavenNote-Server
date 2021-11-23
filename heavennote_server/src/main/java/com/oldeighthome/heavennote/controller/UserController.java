package com.oldeighthome.heavennote.controller;


import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.common.web.HeaderMapRequestWrapper;
import com.oldeighthome.heavennote.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YY
 * @since 2021-11-21
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    IUserService userService;
    @GetMapping("/followerNum")
    public ApiResult getFollowerNum(HeaderMapRequestWrapper request){
        String id=request.getHeader("id");
        int num= userService.getFollowerNum(id);
        ApiResult result=ApiResult.success(num);
        return result;
    }


}
