/**
 * Copyright 2023 json.cn
 */
package com.tsing.entity.kafka;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * Auto-generated: 2023-07-27 11:18:24
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class OriginImg {

	@JSONField(name = "Id")
	private String Id;
	@JSONField(name = "Width")
	private int Width;
	@JSONField(name = "Height")
	private int Height;
	@JSONField(name = "URI")
	private String URI;
	@JSONField(name = "BinData")
	private String BinData;
	@JSONField(name = "Sn")
	private long Sn;

}