package com.crmp.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Registers the Spring Security filter chain with the servlet container.
 * Must extend AbstractSecurityWebApplicationInitializer.
 */
public class SecurityWebAppInitializer extends AbstractSecurityWebApplicationInitializer {
    // No additional configuration needed — superclass handles filter registration.
}
