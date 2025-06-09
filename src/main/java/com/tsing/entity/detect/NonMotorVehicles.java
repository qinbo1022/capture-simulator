/**
 * Copyright 2023 json.cn
 */
package com.tsing.entity.detect;

import lombok.Data;
import com.tsing.entity.kafka.OriginImg;
import com.tsing.entity.kafka.Metadata;

import java.util.List;

/**
 * @author qinbo
 */
@Data
public class NonMotorVehicles {

    private int Id;
    private Img Img;
    private List<Attributes> Attributes;
    private List<Passengers> Passengers;
    private String Features;
    private boolean HasFace;
    private String MatchedNonmotor;
    private String UId;
    private String ReId;
    private double PrimaryObjectScore;
    private OriginImg OriginImg;
    private Metadata Metadata;
}