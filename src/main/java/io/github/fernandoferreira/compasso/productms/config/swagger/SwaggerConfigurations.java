//package io.github.fernandoferreira.compasso.productms.config.swagger;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Component
//@EnableSwagger2
//public class SwaggerConfigurations {
//
//    @Bean
//    public Docket forumApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("io.github.fernandoferreira.compasso.productms"))
//                .paths(PathSelectors.ant("/**"))
//                .build();
//    }
//
//}
