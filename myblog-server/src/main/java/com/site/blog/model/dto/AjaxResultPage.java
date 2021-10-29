package com.site.blog.model.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**分页表格数据返回
 * @author yanni
 **/
@Data
@ToString
public class AjaxResultPage<T> implements Serializable {

    /**
     * 状态码
     */
    private int code;

    /**
     * 提示消息
     */

    private String msg;

    /**
     * 总条数
     */
    private long count;

    /**
     * 表格数据
     */
    private List<T> data;


}
