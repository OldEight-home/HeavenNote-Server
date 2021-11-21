package com.oldeighthome.heavennote.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author YY
 * @since 2021-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("note_id")
    private String noteId;

    @TableField("author_id")
    private String authorId;

    private String title;

    private String description;

    private String content;

    @TableField("note_count")
    private Integer noteCount;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("is_public")
    private Boolean isPublic;


}
