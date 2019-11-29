package com.tieto.springbootdemo.swagger;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
//@Profile({"dev", "it"})
public class SwaggerConfig {

    @Value("${app.client1.id}")
    private String clientId;
    @Value("${app.client1.secret}")
    private String clientSecret;
    @Value("${oauth2.server.link}")
    private String authLink;

    @Bean
    public SecurityConfiguration securityInfo() {
        return new SecurityConfiguration(clientId, clientSecret, "", "alarm", "Authorization", ApiKeyVehicle.HEADER, "Authorization"," ");
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tieto.springbootdemo.controller"))
                .paths(PathSelectors.any())
                .build();
//                .securitySchemes(Collections.singletonList(securitySchema()))
//                .securityContexts(Collections.singletonList(securityContext())).pathMapping("/");
    }

    private OAuth securitySchema() {
        List<AuthorizationScope> authorizationScopeList = Lists.newArrayList();
        authorizationScopeList.add(new AuthorizationScope("create", "create all"));
        authorizationScopeList.add(new AuthorizationScope("query", "query all"));
        authorizationScopeList.add(new AuthorizationScope("update", "update all"));
        authorizationScopeList.add(new AuthorizationScope("delete", "delete all"));
        authorizationScopeList.add(new AuthorizationScope("trust", "trust all"));

        List<GrantType> grantTypes = Lists.newArrayList();
        GrantType passwordGrant = new ResourceOwnerPasswordCredentialsGrant(authLink + "/oauth/token");
        //GrantType applicationGrant = new ClientCredentialsGrant(authLink + "/oauth/token");
        grantTypes.add(passwordGrant);
        //grantTypes.add(applicationGrant);

        return new OAuth("oauth2schema", authorizationScopeList, grantTypes);
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/api/**"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope("read", "read all");
        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
        authorizationScopes[2] = new AuthorizationScope("write", "write all");

        return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
    }

    private ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    .title("Alarm Management Rest API")
                    .description("Rest API Doc for Alarm Management")
                    .termsOfServiceUrl("http://www.example.com/")
                    .version("1.0.0")
                    .contact(new Contact("Admin", "http://www.example.com/", "admin@example.com"))
                    .build();
        }
}
