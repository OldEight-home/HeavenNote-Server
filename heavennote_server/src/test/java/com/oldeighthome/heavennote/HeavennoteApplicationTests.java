package com.oldeighthome.heavennote;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oldeighthome.heavennote.entity.Note;
import com.oldeighthome.heavennote.entity.User;
import com.oldeighthome.heavennote.entity.vo.NoteInfoVo;
import com.oldeighthome.heavennote.mapper.UserMapper;
import com.oldeighthome.heavennote.service.INoteService;
import com.oldeighthome.heavennote.service.WxAuthService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class HeavennoteApplicationTests extends ServiceImpl<UserMapper, User> {
    @Autowired
    private INoteService noteService;
    @Autowired
    private WxAuthService wxAuthService;

    private String userId="oGv8y5H3Cbb4S4sIu28HD0bLURoU";

    private String noteId="e30f6e231b524b06b67403bf01f6f682";
    @Test
    void contextLoads() {
        log.info("xx");
    }
    @Test
    void testPlus(){
        User user = getOne(new QueryWrapper<User>().eq("user_id", 1));
        log.info(user.getUsername());
    }
    @Test
    void testJson(){
        String str="{\"name\":\"tom\",\"age\":\"12\"}";
        JSONObject jb=JSONObject.parseObject(str);
        String s=jb.get("name").toString();
        log.info(s);

    }
    @Test
    /**
     * 测试添加笔记
     */
    void testAddNote(){
        Note note=new Note();
        note.setContent("I will test testAddNote function. Hahahahahaha")
                .setIsPublic(true)
                .setTitle("Test Add Note");
        noteService.addNote(userId,note);

    }


    /**
     * 测试查询个人笔记
     */
    @Test
    void testGetPersonalNote(){
        List<Map<String, Object>> personalNote = noteService.getPersonalNote(userId);
        Assert.assertNotNull(personalNote.toString());
    }
    @Test
    /**
     * 测试查询社区笔记
     */
    void testSelectCommunityNote(){
        List<NoteInfoVo> noteInfoVoList = noteService.showNoteInCommunityPage(1, 1);
        log.info(noteInfoVoList.toString());
        Assert.assertNotNull(noteInfoVoList);
    }
    @Test
    /**
     * 测试查询笔记内容
     */
    void testGetNoteDetail(){
        Map<String, Object> noteDetail = noteService.getNoteDetail(noteId);
        log.info(noteDetail.toString());
    }
    @org.junit.Test(timeout = 10000)
    void testGetNoteDetail2(){
        Map<String, Object> noteDetail = noteService.getNoteDetail(noteId);
        log.info(noteDetail.toString());
    }
}
