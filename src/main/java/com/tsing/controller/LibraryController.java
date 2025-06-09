package com.tsing.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tsing.entity.Library;
import com.tsing.mapper.LibraryMapper;
import com.tsing.mapper.LibraryDetailMapper;
import java.util.List;

/**
 * <p>
 * 库表 前端控制器
 * </p>
 *
 * @author tsing
 * @since 2025-05-28
 */
@Slf4j
@RestController
@RequestMapping("/library")
public class LibraryController {

    private final LibraryMapper libraryMapper;
    private final LibraryDetailMapper libraryDetailMapper;

    public LibraryController(LibraryMapper libraryMapper, LibraryDetailMapper libraryDetailMapper) {
        this.libraryMapper = libraryMapper;
        this.libraryDetailMapper = libraryDetailMapper;
    }

    @GetMapping("/list")
    public List<Library> listLibraries() {
        log.info("查询全部库列表");
        return libraryMapper.selectList(null);
    }

    @PostMapping("/add")
    public boolean addLibrary(@RequestBody Library library) {
        log.info("新建库: {}", library);
        if (library.getName() == null || library.getName().trim().isEmpty()) {
            return false;
        }
        return libraryMapper.insert(library) > 0;
    }

    @PostMapping("/edit")
    public boolean editLibrary(@RequestBody Library library) {
        log.info("编辑库: {}", library);
        if (library.getId() == null || library.getName() == null || library.getName().trim().isEmpty()) {
            return false;
        }
        return libraryMapper.updateById(library) > 0;
    }

    @PostMapping("/delete")
    public boolean deleteLibrary(@RequestParam Long id) {
        log.info("删除库: id={}", id);
        // 校验库下无人员
        int count = libraryDetailMapper.selectCount(new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<com.tsing.entity.LibraryDetail>().eq("library_id", id)).intValue();
        if (count > 0) {
            return false;
        }
        return libraryMapper.deleteById(id) > 0;
    }
}

