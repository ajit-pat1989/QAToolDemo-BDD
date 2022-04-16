package com.test.qatools.hooks;

import com.test.qatools.selenium.DriverProvider;
import com.test.qatools.spring.SpringConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import net.serenitybdd.core.Serenity;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {SpringConfig.class},initializers = {TestContextInitializer.class})
public class Hooks {

    private static final Logger LOG = LoggerFactory.getLogger(Hooks.class);
    @Autowired
    private RemoteWebDriver driver;

    private static Scenario scenario;


    @Before
    public void before(Scenario scenario){
        this.scenario = scenario;
        if (Serenity.getWebdriverManager().getCurrentDriver()==null){
            Serenity.getWebdriverManager().setCurrentDriver(driver);
            LOG.info("Before Scenarios....");
        }
    }

    @After
    public void after(){
        LOG.info("After Scenarios...");
        if (scenario.isFailed()){
            Serenity.takeScreenshot();
            System.out.println("Test is failed ---> "+ scenario.getName());
            LOG.error("Test is failed ---> "+ scenario.getName());
        }
        try{
            if (driver.switchTo().alert() != null){
                Alert alert = driver.switchTo().alert();
                alert.accept();
            }
        }catch(Exception e){
        }

    }


}
