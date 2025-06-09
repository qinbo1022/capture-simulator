package com.tsing.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tsing.entity.Config;
import com.tsing.mapper.ConfigMapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 配置表 前端控制器
 * </p>
 *
 * @author tsing
 * @since 2025-05-28
 */
@Slf4j
@RestController
@RequestMapping("/config")
public class ConfigController {

    private final ConfigMapper configMapper;

    public ConfigController(ConfigMapper configMapper) {
        this.configMapper = configMapper;
    }

    @GetMapping("/list")
    public List<Config> listConfigs() {
        log.info("查询全部配置列表");
        return configMapper.selectList(null);
    }

    @GetMapping("/page")
    public IPage<Config> pageConfigs(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        log.info("分页查询配置: page={}, size={}", page, size);
        Page<Config> p = new Page<>(page, size);
        return configMapper.selectPage(p, null);
    }

    @PostMapping("/add")
    public boolean addConfig(@RequestBody Config config) {
        log.info("新增配置: {}", config);
        return configMapper.insert(config) > 0;
    }

    @PostMapping("/edit")
    public boolean editConfig(@RequestBody Config config) {
        log.info("编辑配置: {}", config);
        return configMapper.updateById(config) > 0;
    }

    @PostMapping("/delete")
    public boolean deleteConfig(@RequestParam Long id) {
        log.info("删除配置: id={}", id);
        return configMapper.deleteById(id) > 0;
    }
}

