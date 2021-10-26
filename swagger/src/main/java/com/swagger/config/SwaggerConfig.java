package com.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration  //配置类
@EnableSwagger2   //开启swagger2的自动配置
public class SwaggerConfig {
    //配置swagger的Bean实例

    //配置分组
    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("分组1");
    }

    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("分组2");
    }

    @Bean
    public Docket docket3(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("分组3");
    }
    @Bean
    public Docket docket(Environment environment){

        //设置要显示swagger的环境
        Profiles of = Profiles.of("dev","test");
        //判断当前是否处于该环境
        //通过enable()接收此参数判断是否要显示
        boolean flag = environment.acceptsProfiles(of);

        //获取项目的环境

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(flag)  //enable是否启动swagger，如果为false，则swagger不能在浏览器中访问
                .select()
                //通过select()方法，去配置扫描接口，RequestHandlerSelectors配置要扫描接口的方式
                //basePackage：指定要扫描的包
                //any：扫描全部
                //none：不扫描
                //withClassAnnotation：扫描类上的注解，参数是一个注解的反射对象
                //withMethodAnnotation：扫描方法上的注解
                .apis(RequestHandlerSelectors.basePackage("com.swagger.swagger.controller"))

//                //paths：过滤什么路径
//                .paths(PathSelectors.ant("/swagger/**"))

                .build();
    }

    //配置swagger信息--->apiInfo
    private ApiInfo apiInfo(){
        Contact contact = new Contact("刘芳","http://message.liufang0321.com/singer","2531994628@qq.com");
        return new ApiInfo(
                "Swagger学习", // 标题
                "学习演示如何配置Swagger", // 描述
                "v1.0", // 版本
                "http://message.liufang0321.com/singer", // 网站
                contact, // 联系人信息
                "Apache 2.0 许可", // 许可
                "许可链接", // 许可连接
                new ArrayList<>()
        );
    }

}
