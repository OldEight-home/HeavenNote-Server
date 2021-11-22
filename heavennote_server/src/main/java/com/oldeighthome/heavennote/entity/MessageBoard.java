package com.oldeighthome.heavennote.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("message_board")
public class MessageBoard implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @TableField("author_id")
    private String authorId;

    private String title;

    private String content;


}
