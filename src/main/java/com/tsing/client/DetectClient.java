package com.tsing.client;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.google.common.util.concurrent.RateLimiter;
import com.tsing.entity.detect.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.tsing.service.IConfigService;
import com.tsing.entity.Config;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName DetectClient
 * @Description 用于二次识别的客户端
 * @Auhor Administrator
 * @Date 2023/7/25 13:38
 * @Version 1.0
 **/
@SuppressWarnings("ALL")
@Slf4j
@Component
public class DetectClient {
    private final DetectConfig detectConfig;

    public final String host;
    final AtomicInteger callCount = new AtomicInteger(0);
    public static Double currentQps = 0.0;

    @Autowired
    private IConfigService configService;

    DetectClient(DetectConfig detectConfig) {
        this.detectConfig = detectConfig;
        this.host = "http://" + detectConfig.getHost() + ":" + detectConfig.getPort();
    }


    public DetectResp detectImg(String faceImgUrl, String sceneImgUrl, String deviceId) {
        try {
            callCount.incrementAndGet();
            log.info("detect入参 大图url:{}，小图url:{}", sceneImgUrl, faceImgUrl);
            DetectReq detectReq = parseParam(faceImgUrl, sceneImgUrl);
            // 读取解析一体机配置
            String url = null;
            Config detectConfigItem = configService.lambdaQuery().eq(Config::getName, "解析一体机").eq(Config::getType, "undeletable").one();
            if (detectConfigItem != null && detectConfigItem.getContent() != null) {
                url = detectConfigItem.getContent() + DetectUrls.DETECT_URL;
            } else {
                url = "http://10.1.10.67:3154" + DetectUrls.DETECT_URL;
            }
            String jsonStr = JSONUtil.toJsonStr(detectReq);
            log.info("二次识别入参：" + jsonStr);
            HttpResponse response = HttpRequest.post(url).body(jsonStr).timeout(Integer.parseInt(detectConfig.getImageDownloadTimeout()) * 1000).execute();
            int responseCode = response.getStatus();
            if (responseCode == 200) {
                log.info("二次识别成功：{}！", response.body());
                String body = response.body();
                DetectResp detectResp = JSONUtil.toBean(JSONUtil.parseObj(body), DetectResp.class);
                return detectResp;
            } else {
                String body = response.body();
                log.error("二次识别失败（deviceId:{},time:{}）：{}！", deviceId, DateUtil.now(), body);
                return null;
            }
        } catch (HttpException e) {
            log.error("二次识别失败（deviceId:{},time:{}），error：{}！", deviceId, DateUtil.now(), e.getMessage());
            return null;
        }
    }

    public DetectResp detectImg(String sceneImgUrl, String deviceId) {
        try {
            callCount.incrementAndGet();
            log.info("detect入参 大图url:{}", sceneImgUrl);
            DetectReq detectReq = new DetectReq();
            ImageReq imageReq = new ImageReq();
            imageReq.setUrl(sceneImgUrl);
            detectReq.setImage(imageReq);
//            DetectReq detectReq = parseParam(faceImgUrl, sceneImgUrl);
            String url = host + DetectUrls.DETECT_URL;
            String jsonStr = JSONUtil.toJsonStr(detectReq);
            log.info("二次识别入参：" + jsonStr);
            HttpResponse response = HttpRequest.post(url).body(jsonStr).timeout(Integer.parseInt(detectConfig.getImageDownloadTimeout()) * 1000).execute();
            int responseCode = response.getStatus();
            if (responseCode == 200) {
                log.info("二次识别成功：{}！", response.body());
                String body = response.body();
                DetectResp detectResp = JSONUtil.toBean(JSONUtil.parseObj(body), DetectResp.class);
                if (log.isDebugEnabled()) {
                    log.debug(JSONUtil.toJsonStr(detectResp));
                }
                return detectResp;
            } else {
                String body = response.body();
                log.error("二次识别失败：{}！", body);
                return null;
            }
        } catch (HttpException e) {
            log.error("二次识别失败，error：{}！", e.getMessage());
            return null;
        }
    }

//    public static void main(String[] args) {
//       DetectReq aaa = new DetectReq();
//       log.info(aaa.toString());
//    }

    private DetectReq parseParam(String faceImgUrl, String sceneImgUrl) {
        //String gan_json = "[{\"PointType\":2,\"polygon_area\":[{\"x\":0.2,\"y\":0},{\"x\":0.2,\"y\":0.2},{\"x\":0, \"y\":0.2},{\"x\":0,\"y\":0.8},{\"x\":0.2,\"y\":0.8},{\"x\":0.2,\"y\":1},{\"x\":0.8,\"y\":1},{\"x\":0.8,\"y\":0.8},{\"x\":1,\"y\":0.8},{\"x\":1,\"y\":0.2},{\"x\":0.8,\"y\":0.2},{\"x\":0.8,\"y\":0}],\"filter_threshold\":1}]";
        DetectReq detectReq = new DetectReq();
        ImageReq imageReq = new ImageReq();
        imageReq.setUrl(sceneImgUrl);
        detectReq.setImage(imageReq);
        detectReq.setImageDownloadTimeout(Integer.parseInt(detectConfig.getImageDownloadTimeout()));
        //detectReq.setRois_polygon(JSON.parseArray(gan_json));
        Interested_objects interestedObjects = new Interested_objects();
        interestedObjects.setFilters(null);
        interestedObjects.setType("face");
        Interested_object interestedObject = new Interested_object();
        interestedObject.setUrl(faceImgUrl);
        interestedObject.setBox_in_target(false);
        interestedObjects.setInterested_object(interestedObject);
        //前场环境为1.8 只能用asList方法
        detectReq.setInterested_objects(Arrays.asList(interestedObjects));
        String jsonStr = JSONUtil.toJsonStr(detectReq);
//        log.warn("二次识别body:{} :", jsonStr);
        return detectReq;
    }

    private DetectReq parseParamLocal(String faceImgPath, String sceneImgPath) {
        DetectReq detectReq = new DetectReq();
        ImageReq imageReq = new ImageReq();
        byte[] imageBytes = FileUtil.readBytes(faceImgPath);
        String base64Image = Base64.encode(imageBytes);
        imageReq.setFile(base64Image);
        detectReq.setImage(imageReq);
        detectReq.setImageDownloadTimeout(Integer.parseInt(detectConfig.getImageDownloadTimeout()));
        Interested_objects interestedObjects = new Interested_objects();
        interestedObjects.setFilters(null);
        interestedObjects.setType("face");
        Interested_object interestedObject = new Interested_object();
        byte[] sceneImageBytes = FileUtil.readBytes(sceneImgPath);
        String sceneBase64Image = Base64.encode(imageBytes);
        interestedObject.setFile(sceneBase64Image);
        interestedObject.setBox_in_target(false);
        interestedObjects.setInterested_object(interestedObject);
        //前场环境为1.8 只能用asList方法
        detectReq.setInterested_objects(Arrays.asList(interestedObjects));
        return detectReq;
    }

    @Scheduled(fixedRate = 60 * 1000L)
    public void keepAlive() {
        String url = host + DetectUrls.DETECT_URL;
        String faceImg = detectConfig.getTestImgDir() + "/check_face.jpg";
        String sceneImg = detectConfig.getTestImgDir() + "/check_scene.jpg";
        DetectReq detectReq = parseParamLocal(faceImg, sceneImg);
        String jsonStr = JSONUtil.toJsonStr(detectReq);
        HttpResponse response = HttpRequest.post(url).body(jsonStr).timeout(Integer.parseInt(detectConfig.getImageDownloadTimeout()) * 1000).execute();
        int responseCode = response.getStatus();
        if (responseCode == 200) {
            log.info("detect Api {} keepalive success", DetectUrls.DETECT_URL);
        } else {
            log.info("kv接口状态码：{}！", responseCode);
        }
    }
}
