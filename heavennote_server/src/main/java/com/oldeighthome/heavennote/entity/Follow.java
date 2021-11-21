package com.oldeighthome.heavennote.entity;

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
public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @TableField("author_id")
    private String authorId;

    @TableField("follow_id")
    private String followId;

    @TableField("follow_name")
    private String followName;


}
