package com.crud.tasks.controller;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class PortCustomizer
        implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.setPort(8080);
    }
}
