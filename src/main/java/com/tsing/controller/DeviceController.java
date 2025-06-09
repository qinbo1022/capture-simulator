package com.tsing.controller;


import com.tsing.entity.Device;
import com.tsing.entity.feign.DeviceDto;
import com.tsing.mapper.DeviceMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsing.service.DeviceCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 设备表 前端控制器
 * </p>
 *
 * @author tsing
 * @since 2025-05-28
 */
@Slf4j
@RestController
@RequestMapping("/device")
public class DeviceController {
	private final DeviceMapper deviceMapper;
	@Autowired
	private DeviceCacheService deviceCacheService;

	public DeviceController(DeviceMapper deviceMapper) {
		this.deviceMapper = deviceMapper;
	}

	// 查询全部设备列表
	@GetMapping("/list")
	public List<Device> listDevices() {
		log.info("查询全部设备列表");
		return deviceMapper.selectList(null);
	}

	@GetMapping("/page")
	public IPage<Device> pageDevices(@RequestParam(defaultValue = "1") int page,
								 @RequestParam(defaultValue = "10") int size) {
		log.info("分页查询设备: page={}, size={}", page, size);
		Page<Device> p = new Page<>(page, size);
		return deviceMapper.selectPage(p, null);
	}

	@PostMapping("/add")
	public boolean addDevice(@RequestBody Device device) {
		log.info("新增设备: {}", device);
		return deviceMapper.insert(device) > 0;
	}

	@PostMapping("/edit")
	public boolean editDevice(@RequestBody Device device) {
		log.info("编辑设备: {}", device);
		return deviceMapper.updateById(device) > 0;
	}

	@PostMapping("/delete")
	public boolean deleteDevice(@RequestParam Long id) {
		log.info("删除设备: id={}", id);
		return deviceMapper.deleteById(id) > 0;
	}

	@GetMapping("/cacheList")
	public List<DeviceDto> cacheDeviceList() {
		log.info("获取设备缓存列表");
		return deviceCacheService.getDeviceList();
	}

	@PostMapping("/refreshCache")
	public boolean refreshDeviceCache() {
		log.info("手动刷新设备缓存");
		deviceCacheService.refreshCache();
		return true;
	}

	@GetMapping("/cacheSearch")
	public List<DeviceDto> cacheDeviceSearch(@RequestParam(value = "keyword", required = false) String keyword) {
		log.info("缓存设备模糊搜索: keyword={}", keyword);
		List<DeviceDto> all = deviceCacheService.getDeviceList();
		if (keyword == null || keyword.isEmpty()) return all.size() > 50 ? all.subList(0, 50) : all;
		return all.stream()
			.filter(d -> (d.getDeviceId() != null && d.getDeviceId().contains(keyword)) || (d.getName() != null && d.getName().contains(keyword)))
			.limit(50)
			.collect(Collectors.toList());
	}

	@GetMapping("/cachePage")
	@ResponseBody
	public IPage<DeviceDto> cacheDevicePage(@RequestParam(defaultValue = "1") int page,
										   @RequestParam(defaultValue = "10") int size) {
		List<DeviceDto> all = deviceCacheService.getDeviceList();
		int total = all.size();
		int from = Math.max(0, (page - 1) * size);
		int to = Math.min(from + size, total);
		List<DeviceDto> records = from < to ? all.subList(from, to) : java.util.Collections.emptyList();
		Page<DeviceDto> p = new Page<>(page, size, total);
		p.setRecords(records);
		return p;
	}
}

