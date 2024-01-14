package com.thesis;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;


import java.sql.Types;
import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
    String url = "jdbc:mysql://localhost:3306/thesis";
    String username = "root";
    String password = "yangzl6271!";
    String moduleName = "sys";
    String mapperLocation = "/Users/yangyang/Desktop/thesis/demo1/src/main/resources/mapper/"+ moduleName;
    //这里需要根据数据库中的表名改，注意逗号后面不要加空格
    String tables = "report,uploaddoc";
    FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("yangyang") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            //.fileOverride() // 覆盖已生成文件
                            .outputDir("/Users/yangyang/Desktop/thesis/demo1/src/main/java"); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("com.thesis") // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperLocation)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的表名
                            //这里是生成前缀
                            .addTablePrefix("x_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
