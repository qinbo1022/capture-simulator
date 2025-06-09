package com.tsing.service.impl;

import com.tsing.entity.Library;
import com.tsing.mapper.LibraryMapper;
import com.tsing.service.ILibraryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 库表 服务实现类
 * </p>
 *
 * @author tsing
 * @since 2025-05-28
 */
@Service
public class LibraryServiceImpl extends ServiceImpl<LibraryMapper, Library> implements ILibraryService {

}
