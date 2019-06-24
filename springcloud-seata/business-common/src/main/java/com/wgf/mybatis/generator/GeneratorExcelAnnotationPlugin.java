package com.wgf.mybatis.generator;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;
import java.util.Optional;

/**
 * @author: wgf
 * @create: 2019-02-19 15:12
 * @description: 扩展 mybatis-generator-maven-plugin
 * 使插件生成的model类的字段添加上@ExcelColumn注解
 **/
public class GeneratorExcelAnnotationPlugin extends PluginAdapter {

    private static int INDEX = 0;

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {

        String annotationPackage = properties.getProperty("annotationPackage");
        annotationPackage = Optional.ofNullable(annotationPackage).orElse("com.saturn.common.util.excel.ExcelColumn");

        topLevelClass.addImportedType(annotationPackage);

        String remark = introspectedColumn.getRemarks();
        if (StringUtils.isNotBlank(remark)) {
            remark = remark.replaceAll("(\r\n|\n)",  " ");
        }

        field.addAnnotation(String.format("@ExcelColumn(value = %d, name = \"%s\")", INDEX++, remark));
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }
}
