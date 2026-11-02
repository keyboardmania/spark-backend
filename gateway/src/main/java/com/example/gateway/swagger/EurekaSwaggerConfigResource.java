package com.example.gateway.swagger;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties.SwaggerUrl;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.utils.Constants;
import org.springdoc.webflux.ui.SwaggerConfigResource;
import org.springdoc.webflux.ui.SwaggerWelcomeCommon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class EurekaSwaggerConfigResource extends SwaggerConfigResource {

	private final DiscoveryClient discoveryClient;
	private final SwaggerUiConfigProperties swaggerUiConfigProperties;
	private final String gatewayServiceId;

	public EurekaSwaggerConfigResource(
			SwaggerWelcomeCommon swaggerWelcomeCommon,
			DiscoveryClient discoveryClient,
			SwaggerUiConfigProperties swaggerUiConfigProperties,
			@Value("${spring.application.name}") String gatewayServiceId) {
		super(swaggerWelcomeCommon);
		this.discoveryClient = discoveryClient;
		this.swaggerUiConfigProperties = swaggerUiConfigProperties;
		this.gatewayServiceId = gatewayServiceId;
		if (swaggerUiConfigProperties.getUrls() == null) {
			swaggerUiConfigProperties.setUrls(new LinkedHashSet<>());
		}
	}

	@Override
	@Operation(hidden = true)
	@GetMapping(value = Constants.SWAGGER_CONFIG_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getSwaggerUiConfig(ServerWebExchange exchange) {
		Set<SwaggerUrl> urls = EurekaSwaggerGroups.fromServiceIds(discoveryClient.getServices(), gatewayServiceId);
		swaggerUiConfigProperties.setUrls(urls);
		if (!urls.isEmpty()) {
			swaggerUiConfigProperties.setUrlsPrimaryName(urls.iterator().next().getName());
		}

		Map<String, Object> config = new LinkedHashMap<>(super.getSwaggerUiConfig(exchange));
		if (!urls.isEmpty()) {
			config.remove("url");
			config.put("urls", urls);
			config.put("urls.primaryName", urls.iterator().next().getName());
		}
		return config;
	}

}
