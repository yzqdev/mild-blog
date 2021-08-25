package com.site.blog.model.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.ToString;

/**
 * @program: FreeMarkeDemo
 * @description: 分页查询[带条件]输入映射
 * @author: 南街
 * @create: 2019-01-03 11:40
 **/
@Data
@ToString
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
