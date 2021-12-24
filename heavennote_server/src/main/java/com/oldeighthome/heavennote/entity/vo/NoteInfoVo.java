package com.oldeighthome.heavennote.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NoteInfoVo {
    private String userName;
    private String userAvatar;
    private String description;
    private String title;
    private String noteId;
    private int noteCount;
    private String updateTime;
}
