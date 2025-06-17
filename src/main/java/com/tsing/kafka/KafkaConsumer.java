package com.tsing.kafka;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.tsing.entity.Config;
import com.tsing.entity.bindata.*;
import com.tsing.entity.kafka.AnalysisSyncRequest;
import com.tsing.entity.kafka.OriginImg;
import com.tsing.service.IConfigService;
import com.tsing.service.impl.TaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author tiger
 * @create 2022/1/24 11:17
 */
@Slf4j
@Component
public class KafkaConsumer {
	@Autowired
	private KafkaTemplate kafkaTemplate;
	@Autowired
	private IConfigService configService;
	@Autowired
	TaskServiceImpl taskService;

	public void processBinData(String key, Integer ObjType, String binData, Long tasId) {
		Bindata bData = JSONObject.parseObject(binData, Bindata.class);
		AnalysisSyncRequest analysisSyncRequest = null;
		//查询数据库，是否需要抽取其他特征，获取List，插入数据，然后发送kafka
		if (ObjType == 1) {
			//处理机动车
			List<RecVehicle> vehicles = bData.getVehicle();
			RecVehicle vehicle = CollUtil.getFirst(vehicles);
			analysisSyncRequest = buildData(vehicle, binData);
		}
		if (ObjType == 4) {
			//处理人体
			List<Pedestrian> pedestrians = bData.getPedestrian();
			Pedestrian pedestrian = CollUtil.getFirst(pedestrians);
			analysisSyncRequest = buildData(pedestrian, binData);
		}
		if (ObjType == 1024) {
			//处理人脸
			List<Face> faces = bData.getFaces();
			Face face = CollUtil.getFirst(faces);
			analysisSyncRequest = buildData(face, binData);
		}
		if (ObjType == 2 || ObjType == 3) {
			//处理非机动车
			List<NonMotorVehicle> nonMotorVehicles = bData.getNonMotorVehicles();
			NonMotorVehicle nonMotorVehicle = CollUtil.getFirst(nonMotorVehicles);
			analysisSyncRequest = buildData(nonMotorVehicle, binData);
		}
		if (ObjType == 5556) {
			//人体带人脸
			//先处理处理人体
			List<Pedestrian> pedestrians = bData.getPedestrian();
			Pedestrian pedestrian = CollUtil.getFirst(pedestrians);
			analysisSyncRequest = buildData(pedestrian, binData);
			if (ObjectUtil.isNotEmpty(pedestrian.getMatchedFace())) {
				//人体存在人脸，单独处理
				AnalysisSyncRequest requestFace = buildData(pedestrian.getMatchedFace(), binData);
				String type = requestFace.getAnalysisSyncResponseMetaDataObject().getElementType().get(0);
//                List<String> elements = analysisSyncRequest.getAnalysisSyncResponseMetaDataObject().getElementType();
				analysisSyncRequest.getAnalysisSyncResponseMetaDataObject().getElementType()
						.add(type);

				analysisSyncRequest.getAnalysisSyncResultObjectList().add(requestFace.getAnalysisSyncResultObjectList().get(0));
			}
		}
		JSONObject jsonObject = JSONObject.from(analysisSyncRequest);
		log.info("analysisSyncRequest json:{}", jsonObject);
		//查询设备对应的算法列表，看是否需要抽取阿里和海康的特征值,deviceId
		//MetaData
		Metadata metadata = bData.getMetadata();
		String deviceId = metadata.getAdditionalInfos().getDevice_id();

		// 取出抓拍id
		String imageId = null;
		if (analysisSyncRequest != null && CollUtil.isNotEmpty(analysisSyncRequest.getAnalysisSyncResultObjectList())) {
			List<AnalysisSyncRequest.ElementResult> resultObjectList = analysisSyncRequest.getAnalysisSyncResultObjectList().get(0).getElementResultObjectList();
			if (ObjectUtil.isNotEmpty(resultObjectList)) {
				List<AnalysisSyncRequest.ElementData> elementDataObjectList = resultObjectList.get(0).getElementDataObjectList();
				if (CollUtil.isNotEmpty(elementDataObjectList)) {
					imageId = elementDataObjectList.get(0).getMediaResourceId();
				}
			}
		}
		log.info("original key: {}. new key: {}", key, imageId);
		//发送kafka消息
		log.info("发送kafka消息:{}", jsonObject.toJSONString());
		JSONObject analysisSyncAlgList = new JSONObject();
		analysisSyncAlgList.put("AnalysisSyncAlgList", ListUtil.toList(jsonObject));
		// 推送Kafka前记录日志
		String kafkaJson = analysisSyncAlgList.toJSONString();
		taskService.logTask(tasId, "pushKafka", kafkaJson);
		sendKafka(StrUtil.isNotBlank(imageId) ? imageId : key, kafkaJson);
	}

	private <T> AnalysisSyncRequest buildData(T data, String binData) {
		Img originImg = null;
		OriginImg img = null;
		String features = "";
		String elementType = "";
		Bindata bData = JSONObject.parseObject(binData, Bindata.class);
		AnalysisSyncRequest analysisSyncRequest = new AnalysisSyncRequest();
		if (data instanceof Pedestrian) {
			Pedestrian pedestrian = (Pedestrian) data;
			features = pedestrian.getFeatures();
			originImg = pedestrian.getOriginImg();
			img = pedestrian.getImg().getImg();
			elementType = ElementCodeEnum.HUMAN_BODY_FEATURES.getElementCode();
		} else if (data instanceof Face) {
			Face face = (Face) data;
			features = face.getFeatures();
			originImg = face.getOriginImg();
			img = face.getImg().getImg();
			elementType = ElementCodeEnum.HUMAN_FACE_FEATURES.getElementCode();
		} else if (data instanceof RecVehicle) {
			RecVehicle vehicle = (RecVehicle) data;
			features = vehicle.getFeatures();
			originImg = vehicle.getOriginImg();
			img = vehicle.getImg().getImg();
			elementType = ElementCodeEnum.MOTOR_VEHICLE_FEATURES.getElementCode();
		} else if (data instanceof NonMotorVehicle) {
			NonMotorVehicle nonMotorVehicle = (NonMotorVehicle) data;
			features = nonMotorVehicle.getFeatures();
			originImg = nonMotorVehicle.getOriginImg();
			img = nonMotorVehicle.getImg().getImg();
			elementType = ElementCodeEnum.NON_MOTOR_VEHICLE_FEATURES.getElementCode();
		}
		AnalysisSyncRequest.AnalysisResponseMetaData analysisResponseMetaData = new AnalysisSyncRequest.AnalysisResponseMetaData();
		//MetaData

		analysisResponseMetaData.setRequestId(IdUtil.simpleUUID());
		List<String> elements = new ArrayList<>();
		elements.add(elementType);
		analysisResponseMetaData.setElementType(elements);
		analysisResponseMetaData.setResponseTime(DateUtil.current());
		analysisResponseMetaData.setRequestTime(DateUtil.current());
		analysisResponseMetaData.setVendor("al_001");
		analysisSyncRequest.setAnalysisSyncResponseMetaDataObject(analysisResponseMetaData);
		//解析结果
		List<AnalysisSyncRequest.AnalysisSyncResult> objList = new ArrayList<>();
		AnalysisSyncRequest.AnalysisSyncResult analysisSyncResult = new AnalysisSyncRequest.AnalysisSyncResult();

		analysisSyncResult.setMediaResourceType(MediaResourceTypeEnum.photo.getCode());
		analysisSyncResult.setMediaResourceId(img.getId());
		analysisSyncResult.setMediaResourceUrl(img.getURI());
		List<AnalysisSyncRequest.ElementResult> elementResults = new ArrayList<>();
		AnalysisSyncRequest.ElementResult elementResult = new AnalysisSyncRequest.ElementResult();
		elementResult.setElementType(elementType);
		elementResult.setAlgorithmVersion("001v1");
		List<AnalysisSyncRequest.ElementData> elementDataObjectList = new ArrayList<>();
		AnalysisSyncRequest.ElementData elementData = new AnalysisSyncRequest.ElementData();
		elementData.setFeatures(features);
		elementData.setMediaResourceId(img.getId());
		elementData.setMediaResourceUrl(img.getURI());

		elementDataObjectList.add(elementData);
		elementResult.setElementDataObjectList(elementDataObjectList);
		elementResults.add(elementResult);
		analysisSyncResult.setElementResultObjectList(elementResults);
		objList.add(analysisSyncResult);
		analysisSyncRequest.setAnalysisSyncResultObjectList(objList);

		AnalysisSyncRequest.AdditionalAttribute additionalAttribute = new AnalysisSyncRequest.AdditionalAttribute();
		additionalAttribute.setAlgorithmVersionCode("001v1");
		additionalAttribute.setBinData(JSONObject.parseObject(binData));
		analysisSyncRequest.setAdditionalAttribute(additionalAttribute);
		return analysisSyncRequest;
	}

	public void sendKafka(String key, String value) {
		// 读取kafka推送主题配置
		String topic = "multipy_alg_analyse_all";
		Config topicConfig = configService.lambdaQuery().eq(Config::getName, "kafka推送主题").eq(Config::getType, "undeletable").one();
		if (topicConfig != null && topicConfig.getContent() != null) {
			topic = topicConfig.getContent();
		}
		ListenableFuture<SendResult> future = kafkaTemplate.send(topic, key, value);
		try {
			SendResult sendResult = future.get(2, TimeUnit.SECONDS);
			log.info("check dest kafka success. sendResult:{}", sendResult.toString());
//            log.info("check dest kafka success");
		} catch (InterruptedException e) {
			log.error("check dest kafka fail", e);
		} catch (ExecutionException e) {
			log.error("check dest kafka fail", e);
		} catch (TimeoutException e) {
			log.error("check dest kafka fail", e);
		}
	}
}
