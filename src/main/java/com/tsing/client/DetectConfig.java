package com.tsing.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author : qinbo
 * @version : 0.0.1
 * @date : 2023/7/26 13:10
 * @description : 人脸二次识别配置
 **/
@Data
@Component
@ConfigurationProperties(value = "detect")
public class DetectConfig {
    private String host = "10.1.0.67";
    private String port = "3154";
    private String imageDownloadTimeout = "3000";
    private Integer qps = 10;
    private Integer recover = 10;
    private String testImgDir = "D:\\detect\\test\\";
}
