package com.oldeighthome.heavennote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.common.exception.CustomSqlException;
import com.oldeighthome.heavennote.entity.Note;
import com.oldeighthome.heavennote.entity.User;
import com.oldeighthome.heavennote.entity.vo.NoteInfoVo;
import com.oldeighthome.heavennote.mapper.NoteMapper;
import com.oldeighthome.heavennote.mapper.UserMapper;
import com.oldeighthome.heavennote.service.INoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oldeighthome.heavennote.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private UserMapper userMapper;
    private String[] columnToShowInNoteList={
            "is_public as isPublic",
            "update_time as updateTime",
            "note_id as noteId",
            "title",
            "description",
            "note_count as noteCount",
            "author_id as authorId"};
    private String[] columnToShowDetail={
            "content"
    };
    @Override
    public List<Map<String,Object>> getPersonalNote(String id) {
        /*
        List<Note> notes = list(new QueryWrapper<Note>().orderByDesc("update_time")
                .select(columnToShowInNoteList)
                .eq("author_id", id));
                */
        List<Map<String,Object>> notes=noteMapper.getDisplayNoteList(id);
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
                .setDescription(subDescription(note.getContent()))
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
    public List<NoteInfoVo> showNoteInCommunityPage( Integer currentPage,Integer size) {
        Page<Note> page =new Page<>(currentPage,size);
        Page<Note> notePage = noteMapper.selectPage(page, new QueryWrapper<Note>().orderByDesc("update_time")
                .select(columnToShowInNoteList));
        List<Note> records = notePage.getRecords();
        return getNoteInfoVoList(records);

    }

    @Override
    public Map<String,Object> getNoteDetail(String noteId) {
        Map<String,Object> responseData=new HashMap<>();
        Note note=getOne(new QueryWrapper<Note>().eq("note_id",noteId));
        //浏览数+1
        update(new UpdateWrapper<Note>().set("note_count",note.getNoteCount()+1).eq("note_id",noteId));
        log.info("noteId为：{}的笔记浏览数加1，当前为：{}",noteId,note.getNoteCount()+1);
        responseData.put("content",note.getContent());
        return responseData;
    }

    @Override
    public ApiResult editNote(String id, Note note) {
        LocalDateTime dateTime=LocalDateTime.now();
        note.setUpdateTime(dateTime)
                .setDescription(subDescription(note.getContent()));
        int updateSuccess = noteMapper.update(note, new UpdateWrapper<Note>().eq("note_id", note.getNoteId()));
        if(updateSuccess!=0){
            String success="笔记修改成功";
            log.info(success);
            return ApiResult.success(success);
        }
        else{
            throw new CustomSqlException("更改失败,服务器内部错误");
        }

    }

    @Override
    public ApiResult deleteNote(String id, String noteId) {
        boolean isRemove = remove(new QueryWrapper<Note>().eq("author_id", id).eq("note_id", noteId));
        if(isRemove){
            return ApiResult.success("笔记删除成功");
        }
        else {
            throw new CustomSqlException("删除失败，访问数据库出错");
        }
    }

    /**
     * 截取笔记内容的介绍
     * @param content
     * @return
     */
    private String subDescription(String content){
        return content.length()>=15? content.substring(0,15) : content;
    }

    /**
     * 通过note的authorId查询用户相关信息
     * @param noteList
     * @return
     */
    private List<NoteInfoVo> getNoteInfoVoList(List<Note> noteList){
        List<NoteInfoVo> noteInfoVoList=new ArrayList<>();
        for(Note note:noteList){
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_id", note.getAuthorId()));
            NoteInfoVo noteInfoVo=new NoteInfoVo();
            noteInfoVo.setNoteId(note.getNoteId())
                    .setDescription(note.getDescription())
                    .setTitle(note.getTitle())
                    .setUpdateTime(note.getUpdateTime())
                    .setUserName(user.getUsername())
                    .setUserAvatar(user.getAvatar());
            noteInfoVoList.add(noteInfoVo);
        }
        return noteInfoVoList;
    }

}
