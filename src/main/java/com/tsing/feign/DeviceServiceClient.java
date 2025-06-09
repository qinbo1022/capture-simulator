package com.tsing.feign;

import com.tsing.entity.feign.DeviceDto;
import com.tsing.entity.feign.DeviceServiceBaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author bo.qin
 */
@FeignClient(value = "device-service")
public interface DeviceServiceClient {

	@RequestMapping(value = "/device/getDeviceInfo", method = RequestMethod.GET)
	DeviceServiceBaseResponse<List<DeviceDto>> getDeviceInfo();


	@RequestMapping(value = "/device/getDeviceByDeviceIdList", method = RequestMethod.GET)
	DeviceServiceBaseResponse<List<DeviceDto>> getDeviceByDeviceIdList(@RequestParam("deviceIds") List<String> deviceIds);
}
