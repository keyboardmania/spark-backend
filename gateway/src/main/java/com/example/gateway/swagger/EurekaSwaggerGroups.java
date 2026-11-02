package com.example.gateway.swagger;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties.SwaggerUrl;

final class EurekaSwaggerGroups {

	private static final String API_DOCS = "/v3/api-docs";

	private EurekaSwaggerGroups() {
	}

	static Set<SwaggerUrl> fromServiceIds(Collection<String> serviceIds, String gatewayServiceId) {
		String gateway = gatewayServiceId == null ? "" : gatewayServiceId.toLowerCase(Locale.ROOT);
		return serviceIds.stream()
				.filter(Objects::nonNull)
				.map(serviceId -> serviceId.toLowerCase(Locale.ROOT))
				.filter(serviceId -> !serviceId.isBlank())
				.filter(serviceId -> !serviceId.equals(gateway))
				.filter(serviceId -> !serviceId.equals("eureka"))
				.distinct()
				.sorted()
				.map(serviceId -> new SwaggerUrl(serviceId, "/" + serviceId + API_DOCS, serviceId))
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

}
