package com.example.gateway.swagger;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties.SwaggerUrl;

class EurekaSwaggerGroupsTest {

	@Test
	void listsRegisteredServicesExceptGatewayAndEureka() {
		var urls = EurekaSwaggerGroups.fromServiceIds(
				List.of("GATEWAY", "eureka", "member-service", "order-service", "member-service", " "),
				"gateway");

		assertThat(urls).extracting(SwaggerUrl::getName)
				.containsExactly("member-service", "order-service");
		assertThat(urls).extracting(SwaggerUrl::getUrl)
				.containsExactly("/member-service/v3/api-docs", "/order-service/v3/api-docs");
		assertThat(urls).extracting(SwaggerUrl::getDisplayName)
				.containsExactly("member-service", "order-service");
	}

}
