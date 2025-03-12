package com.interview.web.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");

        FastAutoGenerator.create("jdbc:mysql://sanhost:3306/demo?useUnicode=true&characterEncoding=utf-8&useSSL=false",
                        "demo", "Aa#123456")
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("qikun") // 设置作者
                            .enableSwagger() // 开启Swagger模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + "/src/main/java") // 指定输出目录
                            .dateType(DateType.ONLY_DATE) // 定义生成的实体类中日期类型
                            .commentDate("yyyy-MM-dd"); // 注释日期
                })
                // 包配置
                .packageConfig(builder -> {
                    builder.parent("com") // 设置父包名
                            .moduleName("interview") // 设置父包模块名
                            .entity("pojo.entity") // 实体类包名
                            .service("service") // Service包名
                            .serviceImpl("service.impl") // ServiceImpl包名
                            .mapper("mapper") // Mapper包名
                            .controller("controller") // Controller包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml,
                                    projectPath + "/src/main/resources/mybatis")); // 设置mapperXml生成路径
                })
                // 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude("tbl_demo") // 设置需要生成的表名
                            .addTablePrefix("tbl_") // 设置过滤表前缀

                            // 实体策略配置
                            .entityBuilder()
                            .enableLombok() // 开启Lombok
                            .naming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                            .columnNaming(NamingStrategy.underline_to_camel) // 数据库表字段映射到实体的命名策略

                            // Controller策略配置
                            .controllerBuilder()
                            .enableRestStyle() // 开启生成@RestController控制器
                            .enableHyphenStyle() // 开启驼峰转连字符

                            // Service策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService") // 去掉Service接口的首字母I

                            // Mapper策略配置
                            .mapperBuilder()
                            .enableMapperAnnotation(); // 开启@Mapper注解
                })
                .templateEngine(new FreemarkerTemplateEngine())
                // 执行
                .execute();
    }
}