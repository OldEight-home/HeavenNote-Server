package com.oldeighthome.heavennote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.entity.Note;
import com.oldeighthome.heavennote.entity.Subscription;
import com.oldeighthome.heavennote.entity.User;
import com.oldeighthome.heavennote.entity.vo.NoteInfoVo;
import com.oldeighthome.heavennote.mapper.NoteMapper;
import com.oldeighthome.heavennote.mapper.SubscriptionMapper;
import com.oldeighthome.heavennote.mapper.UserMapper;
import com.oldeighthome.heavennote.service.ISubscriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oldeighthome.heavennote.util.DateTimeUtil;
import com.oldeighthome.heavennote.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class SubscriptionServiceImpl extends ServiceImpl<SubscriptionMapper, Subscription> implements ISubscriptionService {
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
            "author_id as authorId",
            "note_count as noteCount"};
    @Override
    public ApiResult addSubscription(String userId, String noteId) {
        if(getOne(new QueryWrapper<Subscription>().eq("author_id",userId).eq("note_id",noteId))==null){
            Subscription subscription=new Subscription();
            String id= UUIDUtil.getUUID();
            subscription.setAuthorId(userId)
                    .setId(id)
                    .setNoteId(noteId);
            save(subscription);
            return ApiResult.success("订阅成功");
        }
        else {
            return ApiResult.error("已经订阅过了");
        }
    }

    @Override
    public List<NoteInfoVo> showSubscription(String userId) {
        List<Subscription> subscriptionList = list(new QueryWrapper<Subscription>().eq("author_id", userId));
        return getNoteInfoList(subscriptionList);
    }

    private List<NoteInfoVo> getNoteInfoList(List<Subscription> subscriptionList){

            List<NoteInfoVo> noteInfoVoList=new ArrayList<>();
            for(Subscription subscription:subscriptionList){
                Note note=noteMapper.selectOne(new QueryWrapper<Note>().eq("note_id",subscription.getNoteId()));
                User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_id", note.getAuthorId()));
                NoteInfoVo noteInfoVo=new NoteInfoVo();
                noteInfoVo.setNoteId(note.getNoteId())
                        .setDescription(note.getDescription())
                        .setTitle(note.getTitle())
                        .setUpdateTime(DateTimeUtil.timeToStringBrief(note.getUpdateTime()))
                        .setNoteCount(note.getNoteCount())
                        .setUserName(user.getUsername())
                ;
                noteInfoVoList.add(noteInfoVo);


        }
        return noteInfoVoList;
    }
}
