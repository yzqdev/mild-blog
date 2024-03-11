package com.site.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.mapper.SysOpLogMapper;
import com.site.blog.model.entity.SysOpLog;
import com.site.blog.service.SysOpLogService;
import org.springframework.stereotype.Service;

/**
 * @author yanni
 * @date time 2022/6/16 2:10
 * @modified By:
 */
@Service
public class SysOpLogServiceImpl extends ServiceImpl<SysOpLogMapper, SysOpLog> implements SysOpLogService {
}
