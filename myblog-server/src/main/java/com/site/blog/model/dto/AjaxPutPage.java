package com.site.blog.model.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.ToString;


@Data

public class AjaxPutPage<T> {
    /**
     * 当前页码
     */
    Integer page;

    /**
     * 每页显示
     */
    Integer limit;

    /**
     * 从多少开始
     */
    Integer start;
    Boolean show;
    /**
     * 删除
     */
    Boolean deleted;
    /**
     * 条件类
     */
    T condition;


    /**
     * 将符合Layui的格式转成mybtais-plus分页的page
     *
     * @return
     */
    public Page<T> putPageToPage() {
        return new Page<>(this.page, this.limit);
    }


}
