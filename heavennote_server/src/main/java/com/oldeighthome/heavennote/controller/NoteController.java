package com.oldeighthome.heavennote.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.common.web.HeaderMapRequestWrapper;
import com.oldeighthome.heavennote.entity.Note;
import com.oldeighthome.heavennote.service.INoteService;
import com.oldeighthome.heavennote.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/api/v1/note")
public class NoteController {
    @Autowired
    private INoteService noteService;
    @GetMapping("/show")
    public ApiResult getPersonalNote(HeaderMapRequestWrapper request){
        String id=request.getHeader("id");
        List<Map<String,Object>> notes= noteService.getPersonalNote(id);

        ApiResult result=ApiResult.success(notes);
        return result;
    }
    @PostMapping("/showDetail")
    public ApiResult getNoteDetail(@RequestBody Map<String,String> data){
        String noteId=data.get("noteId");
        Map<String,Object> responseData= noteService.getNoteDetail(noteId);
        ApiResult result=ApiResult.success(responseData);
        return result;
    }
    @PostMapping("/communityPage")
    public ApiResult showInCommunityPage(HeaderMapRequestWrapper request,@RequestBody Map<String,Integer> data){
        String id=request.getHeader("id");
        Map<String,Object> map = noteService.showNoteInCommunityPage(data.get("currentPage"), data.get("size"),id);
        if(map!=null){
            return ApiResult.success(map);
        }
        else{
            return ApiResult.error("查询失败，服务器内部出错");
        }

    }

    @PostMapping("/add")
    public ApiResult addNote(HeaderMapRequestWrapper request, @RequestBody Note note){
        String id=request.getHeader("id");
        return noteService.addNote(id,note);
    }
    @PostMapping("/edit")
    public ApiResult editNote(HeaderMapRequestWrapper request,@RequestBody Note note){
        String id=request.getHeader("id");
        return noteService.editNote(id,note);

    }
}
