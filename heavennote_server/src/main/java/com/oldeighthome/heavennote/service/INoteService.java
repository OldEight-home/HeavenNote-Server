package com.oldeighthome.heavennote.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.entity.Note;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YY
 * @since 2021-11-21
 */
public interface INoteService extends IService<Note> {
    public List<Map<String,Object>> getPersonalNote(String id);
    public ApiResult addNote(String id,Note note);
    public Map<String,Object> showNoteInCommunityPage(Integer currentPage,Integer size,String id);
    public Map<String,Object> getNoteDetail(String noteId);
    public ApiResult editNote(String id,Note note);
}
