package com.tsing.service.impl;

import com.tsing.entity.Config;
import com.tsing.mapper.ConfigMapper;
import com.tsing.service.IConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 配置表 服务实现类
 * </p>
 *
 * @author tsing
 * @since 2025-05-28
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

}
