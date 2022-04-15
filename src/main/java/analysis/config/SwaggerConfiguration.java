package analysis.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

	@Bean
	public Docket apiBiz() {
		return new Docket(DocumentationType.SWAGGER_2)
				.consumes(this.getConsumeContentTypes())
                .produces(this.getProduceContentTypes())
				.useDefaultResponseMessages(false) // false로 설정하면 Swagger에서 제공해주는 응답코드(200, 401, 403, 404)에 대한 기본 메시지를 제거해준다.
				.groupName("Biz") // Docket Bean이 한 개일 경우 생략해도 상관없으나, 둘 이상일 경우 충돌을 방지해야 하므로 설정해줘야 한다.
				.select() // ApiSelectorBuilder를 생성하여 apis()와 paths()를 사용할 수 있게 해준다.
				.apis(RequestHandlerSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("analysis.api.biz.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(this.getBizApiInfo())
				;
	}
	
	private ApiInfo getBizApiInfo() {
		return new ApiInfoBuilder()
				.title("Data Analysis Biz API")
				.version("v1.0")
				.description("데이터분석 Biz API")
				.build();
	}
	
	@Bean
	public Docket apiSample() {
		return new Docket(DocumentationType.SWAGGER_2)
				.consumes(this.getConsumeContentTypes())
                .produces(this.getProduceContentTypes())
				.useDefaultResponseMessages(true) // false로 설정하면 Swagger에서 제공해주는 응답코드(200, 401, 403, 404)에 대한 기본 메시지를 제거해준다.
				.groupName("Sample") // Docket Bean이 한 개일 경우 생략해도 상관없으나, 둘 이상일 경우 충돌을 방지해야 하므로 설정해줘야 한다.
				.select() // ApiSelectorBuilder를 생성하여 apis()와 paths()를 사용할 수 있게 해준다.
				.apis(RequestHandlerSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("analysis.api.sample.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(this.getSampleApiInfo())
				;
	}
	
	private ApiInfo getSampleApiInfo() {
		return new ApiInfoBuilder()
				.title("Data Analysis Sample API")
				.version("v1.0")
				.description("데이터분석 Sample API")
				.build();
	}
	
	private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }
}
