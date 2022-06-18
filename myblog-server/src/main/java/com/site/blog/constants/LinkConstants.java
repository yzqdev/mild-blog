package com.site.blog.constants;


import lombok.Getter;

@Getter
public enum LinkConstants {
    /**
     * 链接类型友谊
     */
    LINK_TYPE_FRIENDSHIP(0,"friend"),
    /**
     * 链接类型推荐
     */
    LINK_TYPE_RECOMMEND(1,"recommend"),
    /**
     * 链接类型私人
     */
    LINK_TYPE_PRIVATE(2,"private");


    private final Integer linkTypeId;
    private final String linkTypeName;

    LinkConstants(Integer linkTypeId, String linkTypeName) {
        this.linkTypeId = linkTypeId;
        this.linkTypeName = linkTypeName;
    }


}
