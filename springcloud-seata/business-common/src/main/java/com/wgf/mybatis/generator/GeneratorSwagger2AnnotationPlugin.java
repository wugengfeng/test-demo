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
 * @author wgf
 * @version 2019.2.14
 * 扩展 mybatis-generator-maven-plugin
 * 使插件生成的model类的字段添加上swagger2注解
 */
public class GeneratorSwagger2AnnotationPlugin extends PluginAdapter {
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        String tableRemark = introspectedTable.getRemarks();
        String classAnnotation = String.format("@ApiModel(value = \"%s\", description = \"%s\")", topLevelClass.getType().getShortName(), tableRemark);
        if (!topLevelClass.getAnnotations().contains(classAnnotation)) {
            topLevelClass.addAnnotation(classAnnotation);
        }

        String apiModelAnnotationPackage = properties.getProperty("apiModelAnnotationPackage");
        String apiModelPropertyAnnotationPackage = properties.getProperty("apiModelPropertyAnnotationPackage");

        apiModelAnnotationPackage = Optional.ofNullable(apiModelAnnotationPackage).orElse("io.swagger.annotations.ApiModel");
        apiModelPropertyAnnotationPackage = Optional.ofNullable(apiModelPropertyAnnotationPackage).orElse("io.swagger.annotations.ApiModelProperty");

        topLevelClass.addImportedType(apiModelAnnotationPackage);
        topLevelClass.addImportedType(apiModelPropertyAnnotationPackage);

        String remark = introspectedColumn.getRemarks();
        if (StringUtils.isNotBlank(remark)) {
            remark = remark.replaceAll("(\r\n|\n)",  " ");
        }

        field.addAnnotation("@ApiModelProperty(value = \"" + remark + "\")");
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }
}
