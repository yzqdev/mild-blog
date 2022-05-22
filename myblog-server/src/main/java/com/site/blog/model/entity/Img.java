package com.site.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author yanni
 * @date time 2021/10/29 12:42
 * @modified By:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Img extends Model<Img>  implements Serializable {
    @Serial
    private static final long serialVersionUID=1L;
    @TableId(  type = IdType.ASSIGN_ID)
    private String id;
    private String imgName;
    /**
     * 图片系统路径
     */
    private String imgPath;
    private Long imgSize;
    private String imgUrl;
    private String thumbnailPath;
    private String imgType;
    private String mediaType;
    private String md5;
    private Timestamp uploadTime;
}
