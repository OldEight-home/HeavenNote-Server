package com.oldeighthome.heavennote;

import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.common.exception.CustomSqlException;
import com.oldeighthome.heavennote.entity.Note;
import com.oldeighthome.heavennote.entity.vo.NoteInfoVo;
import com.oldeighthome.heavennote.service.INoteService;
import com.oldeighthome.heavennote.service.impl.NoteServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class Test {
    @Autowired
    public INoteService noteService;

    String noteId="e30f6e231b524b06b67403bf01f6f682";
    String userId="oGv8y5H3Cbb4S4sIu28HD0bLURoU";
    @org.junit.Test
    /**
     * 测试查询社区笔记
     */
    public void testSelectCommunityNote(){
        List<NoteInfoVo> noteInfoVoList = noteService.showNoteInCommunityPage(1, 1);
        log.info(noteInfoVoList.toString());
        Assert.assertNotNull(noteInfoVoList);
    }
    @org.junit.Test(timeout = 1000)
    public void testGetNoteDetail(){
        Map<String, Object> noteDetail = noteService.getNoteDetail(noteId);
        log.info(noteDetail.toString());
        Assert.assertNotNull(noteDetail);
    }

    @org.junit.Test(expected = CustomSqlException.class)
    /**
     * 测试更改笔记
     */
    public void testEditNote(){
        Note note=new Note();
        note.setTitle("Test Edit")
            .setContent("Edit the test")
            .setNoteId(noteId);
        ApiResult result = noteService.editNote(userId, note);
        Assert.assertNotNull(result);
    }
}
