package com.tsing.entity.feign;


import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceServiceBaseResponse<T> implements Serializable {

    /**
     * response code
     */
    private Integer code;

    /**
     * response message
     */
    private String msg;

    /**
     * response data
     */
    private T data;

}
