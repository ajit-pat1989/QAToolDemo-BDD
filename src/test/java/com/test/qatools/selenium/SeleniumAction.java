package com.test.qatools.selenium;

import org.awaitility.Awaitility;
import org.awaitility.core.ConditionFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.rmi.Remote;
import java.sql.Time;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class SeleniumAction {

    @Autowired
    @Lazy
    protected RemoteWebDriver driver;

    public static ConditionFactory awaitility = Awaitility.await().atMost(60, TimeUnit.SECONDS)
            .pollInterval(10,TimeUnit.SECONDS).ignoreException(Exception.class);

    public void open(String url){
        driver.get(url);
    }

    public void click(By element){
        getWebElement(element).click();
    }

    public void enterValueInTextBox(By by,String input){
        WebElement element = getWebElement(by);
        element.clear();
        element.sendKeys(input);
    }

    public WebElement getWebElement(By by){
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public List<WebElement> getListOfWebElement(By webElement){
        try{
            return driver.findElements(webElement);
        }catch (Exception e){
            return null;
        }
    }

    public void clearCookies(){
        driver.manage().deleteAllCookies();
    }
}
