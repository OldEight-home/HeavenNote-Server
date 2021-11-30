package com.oldeighthome.heavennote.mapper;

import com.oldeighthome.heavennote.entity.Note;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YY
 * @since 2021-11-21
 */
public interface NoteMapper extends BaseMapper<Note> {
    String  columnToShowInNoteList=
            "SELECT title,description,is_public,update_time,note_id,note_count FROM note WHERE (author_id = #{id}) ORDER BY update_time DESC";

    @ResultMap("NoteListMap")
    @Select(columnToShowInNoteList)
    List<Map<String,Object>> getDisplayNoteList(String id);
}
