package com.tsing.controller;

import com.tsing.entity.*;
import com.tsing.mapper.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * @ClassName CaptureControll
 * @Description
 * @Author bo.qin
 * @Date 2025/5/16 下午5:06
 * @Version 1.0
 **/

@Controller
public class IndexController {
	@Autowired
	private DeviceMapper deviceMapper;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private LibraryMapper libraryMapper;
	@Autowired
	private ConfigMapper configMapper;
	@Autowired
	private LibraryDetailMapper libraryDetailMapper;
	@Autowired
	private TaskLogMapper taskLogMapper;

	@GetMapping("/device")
	public String showDevicePage(Model model) {
		List<Device> deviceList = deviceMapper.selectList(null);
		model.addAttribute("deviceList", deviceList);
		return "device";
	}

	@GetMapping("/task")
	public String showTaskPage(Model model) {
		List<Task> taskList = taskMapper.selectList(null);
		model.addAttribute("taskList", taskList);
		return "task";
	}

	@GetMapping("/library")
	public String showLibraryPage(Model model) {
		List<Library> libraryList = libraryMapper.selectList(null);
		model.addAttribute("libraryList", libraryList);
		return "library";
	}

	@GetMapping("/config")
	public String showConfigPage(Model model) {
		List<Config> configList = configMapper.selectList(null);
		model.addAttribute("configList", configList);
		return "config";
	}

	@GetMapping("/library-detail")
	public String showLibraryDetailPage(@RequestParam("id") Long libraryId, Model model) {
		List<LibraryDetail> personList = libraryDetailMapper.selectList(
			new QueryWrapper<LibraryDetail>().eq("library_id", libraryId)
		);
		Library library = libraryMapper.selectById(libraryId);
		model.addAttribute("personList", personList);
		model.addAttribute("library", library);
		return "library-detail";
	}

	@GetMapping("/task-log")
	public String taskLog(Model model) {
		return "task-log";
	}
}

