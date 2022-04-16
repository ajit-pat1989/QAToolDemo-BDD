package com.test.qatools.hooks;

import com.test.qatools.selenium.DriverProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;
import java.util.Map;

public class TestContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger LOG = LoggerFactory.getLogger(DriverProvider.class);

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        ConfigurableEnvironment configurableEnvironment = configurableApplicationContext.getEnvironment();
        if (configurableEnvironment.getProperty("env")==null){
            System.setProperty("env","demo");
        }
        try {
            String env = System.getProperty("env");
            LOG.info("Running tests on '%s' environments", env.toUpperCase());
            MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
            ResourcePropertySource propertySource = new ResourcePropertySource("classpath:config/"+env+"-config.properties");
            propertySources.addFirst(propertySource);
            Map<String,Object> source = propertySource.getSource();
            for (Map.Entry property: source.entrySet()){
                System.setProperty(property.getKey().toString(),property.getValue().toString());
                LOG.trace("Loading property---> name: {}, value: {}",property.getKey().toString(),property.getValue().toString());
            }
        } catch (IOException e) {
            LOG.error(String.format("Property file for '%s' environment not found in project classpath, Can't continue tests =. Exception below:\n",configurableEnvironment.getProperty("env")),e);
            System.exit(1);
        }
    }
}
