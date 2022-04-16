package com.test.qatools.pages;

import com.test.qatools.hooks.Hooks;
import com.test.qatools.selenium.SeleniumAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class QAToolHomePage extends SeleniumAction {
    private static final Logger LOG = LoggerFactory.getLogger(QAToolHomePage.class);

    @Autowired
    private Pages pages;

    private By qaToolPageHeader = By.xpath("//h1[text()='The awesome Q/A tool']");
    private By alertOn_NoQuestions = By.xpath("//*[contains(text(),'No questions yet')]");
    private String input_HomePage = ("//*[@name='%s']");
    private String btn_HomePage = "//*[normalize-space(text())='%s']";
    private By list_QuestionAnswer = By.xpath("//li[@class='list-group-item question']");
    private By list_QuestionOnly = By.xpath("//*[@class='question__question']");

    @Bean
    QAToolHomePage qaToolHomePage() {
        return new QAToolHomePage();
    }


    public QAToolHomePage openPage() {
        String url = System.getProperty("appUrl");
        LOG.info("Opening url " + url);
        clearCookies();
        open(url);
        return this;
    }

    public boolean isHomePageOpen() {
        return getWebElement(qaToolPageHeader).isDisplayed();
    }

    public void clickActionOnHomePage(String buttonName) {
        String btn_Name = String.format(btn_HomePage, buttonName);
        click(By.xpath(btn_Name));
    }

    public boolean isNoQuestionYetAlertDisplayed() {
        return getWebElement(alertOn_NoQuestions).isDisplayed();
    }

    public void enter_TextBox(String elementName, String value) {
        String inputBox = String.format(input_HomePage, elementName.toLowerCase());
        enterValueInTextBox(By.xpath(inputBox), value);
    }

    public String getValidationMessage(String elementName) {
        String inputBox = String.format(input_HomePage, elementName.toLowerCase());
        return getWebElement(By.xpath(inputBox)).getAttribute("validationMessage").trim();
    }

    public String verifySavedQuestionAndAnswer(String expectedQuestion) {
        List<WebElement> webElements = getListOfWebElement(list_QuestionAnswer);
        if (webElements == null) {
            return "";
        }
        for (WebElement webElement : webElements) {
            String actualQuestion = webElement.getText().trim();
            if (actualQuestion.equals(expectedQuestion)) {
                webElement.click();
                String ele = String.format("//div[contains(text(),'%s')]/following-sibling::p", actualQuestion);
                String answer = getWebElement(By.xpath(ele)).getText();
                return answer;
            }
        }
        return "";
    }

    public List<String> getListOfQuestions() {
        List<WebElement> webElements = getListOfWebElement(list_QuestionOnly);
        List<String> questionList = new ArrayList<>();
        if (webElements == null) {
            return null;
        }
        for (WebElement webElement : webElements) {
            questionList.add(webElement.getText().trim());
        }
        return questionList;
    }

}
