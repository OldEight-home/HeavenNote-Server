package com.oldeighthome.heavennote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.entity.Note;
import com.oldeighthome.heavennote.mapper.NoteMapper;
import com.oldeighthome.heavennote.service.INoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oldeighthome.heavennote.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YY
 * @since 2021-11-21
 */
@Service
@Slf4j
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements INoteService {
    @Autowired
    private NoteMapper noteMapper;
    @Override
    public List<Note> getPersonalNote(String id) {
        List<Note> notes = list(new QueryWrapper<Note>().orderByDesc("update_time").eq("author_id", id));
        return notes;

    }

    @Override
    public ApiResult addNote(String id,Note note) {
        String uuid= UUIDUtil.getUUID();
        LocalDateTime dateTime=LocalDateTime.now();
        note.setNoteId(uuid)
                .setAuthorId(id)
                .setTitle(note.getTitle())
                .setContent(note.getContent())
                .setDescription(note.getContent().substring(0,15))
                .setCreateTime(dateTime)
                .setUpdateTime(dateTime)
                .setNoteCount(0)
                .setIsPublic(note.getIsPublic());
        save(note);
        log.info("用户id为{}笔记添加成功，标题为:{}",id,note.getTitle());
        ApiResult result=ApiResult.success("笔记添加成功");
        return result;
    }

    @Override
    public IPage<Note> showNoteInCommunityPage( Integer currentPage,Integer size) {
        log.info(currentPage+"  "+size);
        Page<Note> page =new Page<>(currentPage,size);
        return noteMapper.selectPage(page, new QueryWrapper<Note>().orderByDesc("update_time"));
    }

    @Override
    public List<Note> showNoteInCommunity() {

        return null;
    }
}
