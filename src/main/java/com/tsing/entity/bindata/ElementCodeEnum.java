package com.tsing.entity.bindata;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 解析要素枚举
 *
 * @author liyy
 * @date 2022/8/11 20:24
 */
@Getter
@AllArgsConstructor
public enum ElementCodeEnum {

    /**
     * 人体 03002001 人脸 03001001 机动车 02999001 非机动车 02999008 机动车特征 08001001 非机动车特征 08001002 人体特征 08002001 人脸特征 08002002
     */

    HUMAN_FACE("人脸", "03001001"),
    HUMAN_BODY("人体", "03002001"),
    MOTOR_VEHICLE("机动车", "02999001"),
    NON_MOTOR_VEHICLE("非机动车", "02999008"),
    HUMAN_BODY_FEATURES("人体特征", "08002001"),
    HUMAN_FACE_FEATURES("人脸特征", "08002002"),
    MOTOR_VEHICLE_FEATURES("机动车特征", "08001001"),
    NON_MOTOR_VEHICLE_FEATURES("非机动车特征", "08001002");

    private final String desc;
    private final String elementCode;


}
