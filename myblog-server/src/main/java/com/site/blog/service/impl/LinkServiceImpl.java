package com.site.blog.service.impl;

import com.site.blog.model.entity.Link;
import com.site.blog.mapper.LinkMapper;
import com.site.blog.service.LinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 友情链接表 服务实现类
 * </p>
 *
 * @author: 南街
 * @since 2019-09-02
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

}
