package com.test.qatools.selenium;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DriverProvider {

    private static String BROWSER_NAME;
    private static WebDriver driver;
    private static final long DEFAULT_TIME_OUT = 60;
    private static final Logger LOG = LoggerFactory.getLogger(DriverProvider.class);

    @Bean
    public static RemoteWebDriver getDriver(){
        if(System.getProperty("browserName") != null){
            BROWSER_NAME =  System.getProperty("browserName");
        }else{
            BROWSER_NAME = "chrome";
        }

        switch (BROWSER_NAME){
            case "chrome": {
                return (RemoteWebDriver) startChromeDriver();
            }
            case "edge": {
                return (RemoteWebDriver) startEdgeDriver();
            }
        }
        driver.manage().timeouts().pageLoadTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        return (RemoteWebDriver) driver;
    }

    private static WebDriver startEdgeDriver(){
        LOG.info("Starting test locally on Edge....");
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    private static WebDriver startChromeDriver(){
        LOG.info("Starting test locally on chrome....");
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\browsers\\chromedriver.exe");
        driver = new ChromeDriver(getChromeOptions(true));
        driver.manage().window().maximize();
        ((JavascriptExecutor)driver).executeScript("document.body.style.transform='scale(0.75)'");
        return driver;
    }

    private static ChromeOptions getChromeOptions(boolean local){
        ChromeOptions options = new ChromeOptions();
        String filePath = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
        if(local) options.setBinary(filePath);
        options.addArguments("test-type");
        options.addArguments("--start-maximized");
        options.addArguments("--js-falgs=--expose-qc");
        options.addArguments("--enable-precise-memory-info");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-default-apps");
        options.addArguments("test-type=browser");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-extensions");
        options.setExperimentalOption("useAutomationExtension",false);
        DesiredCapabilities chrome = new DesiredCapabilities();
        chrome.setBrowserName(BrowserType.CHROME);
        chrome.setJavascriptEnabled(true);
        options.setCapability(ChromeOptions.CAPABILITY,chrome);
        return options;
    }

//    private static final Thread close_thread = new Thread() {
//        public void run() {
//            try {
//                driver.quit();
//            } catch (Throwable throwable) {
//            }
//        }
//    };
//
//    static {
//        Runtime.getRuntime().addShutdownHook(close_thread);
//    }

}
