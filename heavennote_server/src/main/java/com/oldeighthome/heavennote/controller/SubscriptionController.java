package com.oldeighthome.heavennote.controller;


import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.common.exception.CustomSqlException;
import com.oldeighthome.heavennote.common.web.HeaderMapRequestWrapper;
import com.oldeighthome.heavennote.entity.vo.NoteInfoVo;
import com.oldeighthome.heavennote.service.ISubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
@Slf4j
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {
    @Autowired
    private ISubscriptionService subscriptionService;
    @PostMapping("/add")
    public ApiResult addSubscription(HeaderMapRequestWrapper request,@RequestBody Map<String,String> data){
        String userId=request.getHeader("id");
        String noteId=data.get("noteId");
        log.info(noteId);
        return subscriptionService.addSubscription(userId,noteId);
    }
    @PostMapping("/show")
    public ApiResult showSubscription(HeaderMapRequestWrapper request){
        String userId=request.getHeader("id");
        List<NoteInfoVo> noteInfoVoList = subscriptionService.showSubscription(userId);
        if(noteInfoVoList!=null){
            return ApiResult.success(noteInfoVoList);
        }
        else{
            throw  new CustomSqlException("查询失败，服务器内部出错");
        }
    }

}
