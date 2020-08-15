package cn.wolfshadow.gs.common.config;

import com.google.common.base.Predicate;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

//import lombok.NonNull;

@Data
@Component
@EnableSwagger2
@ConfigurationProperties("cn.wolfshadow.system.swager-config")
@Validated
public class SwaggerConfig {

    private String pathMapping = "/";

    //@NonNull
    private String projectName;//项目名
    //@NonNull
    private String description;//描述
   // @NonNull
    private String version;//版本
    //@NonNull
    private String serviceTerms;//服务条款
   // @NonNull
    private String author;//作者
   // @NonNull
    private String linkText;//链接显示文字
  //  @NonNull
    private String website;//网站连接

    private ApiInfo initApiInfo(){
        ApiInfo apiInfo = new ApiInfo(projectName,
                description,
                version,
                serviceTerms,
                author,
                linkText,
                website
        );

        return  apiInfo;
    }

    @Bean
    public Docket restfulApi() {
        System.out.println("http://localhost:8080" + pathMapping + "/swagger-ui.html");

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("RestfulApi")
//                .genericModelSubstitutes(DeferredResult.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .pathMapping(pathMapping) // base，最终调用接口后会和paths拼接在一起
                .select()
  //              .paths(doFilteringRules())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(initApiInfo());
    }

    /**
     * 设置过滤规则
     * 这里的过滤规则支持正则匹配
     * @return
     */
    private Predicate<String> doFilteringRules() {
        return or(
                regex("/"),
                regex("/hello.*"),
                regex("/vehicles.*")
        );
    }

}
