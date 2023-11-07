package com.takeout.takeoutshopservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
@Profile({"dev", "prod"})
public class SwaggerConfig {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 这里一定要标注你控制器的位置
                .apis(RequestHandlerSelectors.basePackage("com.takeout.takeoutshopservice.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * api 信息，建造者模式，根据自己的需求来进行添加
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("外卖微服务")
                .description("外卖接口文档")
                .termsOfServiceUrl("https://github.com")
                .contact(new Contact("zeng","https://github.com","xxx@qq.com"))
                .version("1.0")
                .build();
    }
}