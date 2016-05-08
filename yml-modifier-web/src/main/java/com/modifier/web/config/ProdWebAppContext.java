package com.modifier.web.config;

import me.chanjar.tomcat.valves.MongoAccessLogValve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Profile("prod")
public class ProdWebAppContext extends WebMvcConfigurerAdapter implements EmbeddedServletContainerCustomizer{

    public static final Logger logger = LoggerFactory.getLogger(ProdWebAppContext.class.getSimpleName());

    public static final String MONGO_URI = "mongodb://46.101.166.4/tomcat";
    public static final String LOGGING_PATTERN = "all";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        if (container instanceof TomcatEmbeddedServletContainerFactory) {
            logger.info("Customize embedded servlet container");
            TomcatEmbeddedServletContainerFactory factory = (TomcatEmbeddedServletContainerFactory) container;
            MongoAccessLogValve valve = new MongoAccessLogValve();
            valve.setUri(MONGO_URI);
            valve.setPattern(LOGGING_PATTERN);
            factory.addContextValves(valve);
        }
    }
}