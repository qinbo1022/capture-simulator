package com.tsing.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tsing.entity.LibraryDetail;
import com.tsing.mapper.LibraryDetailMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 库详情表 前端控制器
 * </p>
 *
 * @author tsing
 * @since 2025-05-28
 */
@Slf4j
@RestController
@RequestMapping("/libraryDetail")
public class LibraryDetailController {

    private final LibraryDetailMapper libraryDetailMapper;

    public LibraryDetailController(LibraryDetailMapper libraryDetailMapper) {
        this.libraryDetailMapper = libraryDetailMapper;
    }

    @GetMapping("/list")
    public List<LibraryDetail> listLibraryDetails(@RequestParam Long libraryId) {
        log.info("查询库详情列表: libraryId={}", libraryId);
        QueryWrapper<LibraryDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("library_id", libraryId);
        return libraryDetailMapper.selectList(wrapper);
    }

    @GetMapping("/page")
    public IPage<LibraryDetail> pageLibraryDetails(@RequestParam Long libraryId,
                                               @RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        log.info("分页查询库详情: libraryId={}, page={}, size={}", libraryId, page, size);
        Page<LibraryDetail> p = new Page<>(page, size);
        QueryWrapper<LibraryDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("library_id", libraryId);
        return libraryDetailMapper.selectPage(p, wrapper);
    }

    @PostMapping("/add")
    public boolean addLibraryDetail(@RequestBody LibraryDetail detail) {
        log.info("新增库详情: {}", detail);
        return libraryDetailMapper.insert(detail) > 0;
    }

    @PostMapping("/edit")
    public boolean editLibraryDetail(@RequestBody LibraryDetail detail) {
        log.info("编辑库详情: {}", detail);
        return libraryDetailMapper.updateById(detail) > 0;
    }

    @PostMapping("/delete")
    public boolean deleteLibraryDetail(@RequestParam Long id) {
        log.info("删除库详情: id={}", id);
        return libraryDetailMapper.deleteById(id) > 0;
    }
}

