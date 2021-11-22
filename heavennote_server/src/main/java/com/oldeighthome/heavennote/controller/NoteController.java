package com.oldeighthome.heavennote.controller;


import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.common.web.HeaderMapRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("/api/v1")
public class NoteController {
    @GetMapping("/note")
    public ApiResult getPersonalNote(HeaderMapRequestWrapper request, HttpServletRequest res){
        //String id=(HeaderMapRequestWrapper)request;
        String id=request.getHeader("id");
        log.info(id);
        return ApiResult.success();
    }
}
