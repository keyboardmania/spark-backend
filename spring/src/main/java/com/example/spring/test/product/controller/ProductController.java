package com.example.spring.test.product.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final Environment environment;

    @GetMapping("/product")
    public String product() {
        String serverPort = environment.getProperty("local.server.port");
        log.info("GET /product API called. Current server port: [{}]", serverPort);
        return "Product response from port: " + serverPort;
    }
}
