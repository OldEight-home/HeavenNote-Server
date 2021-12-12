package com.oldeighthome.heavennote.controller;


import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.common.web.HeaderMapRequestWrapper;
import com.oldeighthome.heavennote.service.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YY
 * @since 2021-11-21
 */
@RestController
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {
    @Autowired
    private ISubscriptionService subscriptionService;
    @PostMapping("/add")
    public ApiResult addSubscription(HeaderMapRequestWrapper request, Map<String,String> data){
        String userId=request.getHeader("id");
        String noteId=data.get("noteId");
        return subscriptionService.addSubscription(userId,noteId);
    }
}
