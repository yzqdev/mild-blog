package com.site.blog.client;

import com.dtflys.forest.annotation.Request;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.Tag;

import java.util.List;

public interface MyClient {

    @Request("http://localhost:2801/v2/home/tags")
    Result<List<Tag>> helloForest();

}