package com.oldeighthome.heavennote.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.entity.Note;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YY
 * @since 2021-11-21
 */
public interface INoteService extends IService<Note> {
    public List<Note> getPersonalNote(String id);
    public ApiResult addNote(String id,Note note);
    public IPage<Note> showNoteInCommunityPage(Integer currentPage,Integer size);
    public List<Note> showNoteInCommunity();
}
