package com.tsing.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Collections;

/**
 * <p>
 * 基础代码结构生成器
 * </p>
 *
 * @author Tsing
 * @since 2025-04-16
 */
public class CodeGenerator {
	public static void main(String[] args) {
		// 1. 全局配置
		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
		globalConfig.setAuthor("tsing");
		globalConfig.setOpen(false); // 生成完是否自动打开资源管理器
		globalConfig.setSwagger2(true); // 是否启用swagger注解

		// 2. 数据源配置
		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setUrl("jdbc:mysql://10.4.0.65:32222/camera-simulator?useSSL=false&useUnicode=true&characterEncoding=utf-8");
		dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
		dataSourceConfig.setUsername("root");
		dataSourceConfig.setPassword("2m42ktg5");

		// 3. 包配置
		PackageConfig packageConfig = new PackageConfig();
		packageConfig.setParent("com.tsing");
//		packageConfig.setModuleName("generator");

		// 4. 策略配置
		StrategyConfig strategyConfig = new StrategyConfig();
		strategyConfig.setInclude("task"); // 多张表用逗号分隔
		strategyConfig.setNaming(NamingStrategy.underline_to_camel); // 表名驼峰
		strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel); // 字段名驼峰
		strategyConfig.setEntityLombokModel(true); // lombok
		strategyConfig.setRestControllerStyle(true); // REST 风格

		// 5. XML生成位置配置
		InjectionConfig injectionConfig = new InjectionConfig() {
			@Override
			public void initMap() {
				// 如果需要，可以在这里设置其他自定义配置
			}
		};

		// 指定 XML 文件的输出路径
		String xmlOutputDir = System.getProperty("user.dir") + "/src/main/resources/mapper";

		// 5. 代码生成器
		AutoGenerator autoGenerator = new AutoGenerator();
		autoGenerator.setGlobalConfig(globalConfig);
		autoGenerator.setDataSource(dataSourceConfig);
		autoGenerator.setPackageInfo(packageConfig);
		autoGenerator.setStrategy(strategyConfig);

		autoGenerator.setCfg(injectionConfig);
		injectionConfig.setFileOutConfigList(Collections.singletonList(
				new FileOutConfig("/templates/mapper.xml.vm") {
					@Override
					public String outputFile(TableInfo tableInfo) {
						// 这里定义 XML 文件的路径和文件名
						return xmlOutputDir + "/" + tableInfo.getMapperName() + ".xml";
					}
				}
		));

		autoGenerator.execute();
	}
}

