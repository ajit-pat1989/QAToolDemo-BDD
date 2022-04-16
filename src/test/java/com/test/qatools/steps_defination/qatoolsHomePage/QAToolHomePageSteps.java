package com.test.qatools.steps_defination.qatoolsHomePage;

import com.test.qatools.hooks.TestContextInitializer;
import com.test.qatools.pages.Pages;
import com.test.qatools.spring.SpringConfig;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@ContextConfiguration(classes = {SpringConfig.class},initializers = {TestContextInitializer.class})
public class QAToolHomePageSteps {

    @Autowired
    private Pages pages;

    @Given("I go to QA tool home page")
    public void i_go_to_qa_tool_home_page() {
        assertTrue(pages.qaToolHomePage().openPage().isHomePageOpen());
    }

    @When("I click on {string}")
    public void i_click_on(String string) {
        pages.qaToolHomePage().clickActionOnHomePage(string);
    }

    @Then("I verify all Questions getting removed")
    public void i_verify_all_questions_getting_removed() {
        assertTrue(pages.qaToolHomePage().isNoQuestionYetAlertDisplayed());
    }

    @When("I click on {string} without entering any question or answer")
    public void i_click_on_without_entering_any_question_or_answer(String string) {
        i_click_on(string);
    }

    @Then("I verify no {string} is entered and shows a validation message {string}")
    public void i_verify_a_validation_message(String inputBox, String value) {
        assertEquals("Validation message "+ value +" is either not matching or not displayed",value,pages.qaToolHomePage().getValidationMessage(inputBox));
    }

    @When("I put {string} as {string}")
    public void i_put_as(String elementName, String value) {
        pages.qaToolHomePage().enter_TextBox(elementName,value);

    }

    @Then("I verify {string} and {string} getting saved")
    public void iVerifyQuestionAndAnswerAreSaved(String question, String answer){
       String actualAns = pages.qaToolHomePage().verifySavedQuestionAndAnswer(question);
       assertEquals("Expected question "+question+" and answer "+ answer +" are not matching or not saved.",answer,actualAns);
    }
    @When("I enter and save the Question and Answer")
    public void iEnterAndSaveQuestionsAndAnswers(DataTable dataTable){
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            pages.qaToolHomePage().enter_TextBox("Question",columns.get("Question"));
            pages.qaToolHomePage().enter_TextBox("Answer",columns.get("Answer"));
            pages.qaToolHomePage().clickActionOnHomePage("Create question");
        }
    }

    @Then("I verify question and its answers getting saved")
    public void iVerifyMultipleQuestionAndAnswerSaved(DataTable dataTable){
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            iVerifyQuestionAndAnswerAreSaved(columns.get("Question"),columns.get("Answer"));
        }
    }

    @Then("I verify sorting of Questions working correctly")
    public void verifySorting(){
        List<String> getQuestionListBeforeSorting = pages.qaToolHomePage().getListOfQuestions();
        pages.qaToolHomePage().clickActionOnHomePage("Sort questions");
        List<String> getQuestionListAfterSorting = pages.qaToolHomePage().getListOfQuestions();
        Collections.sort(getQuestionListBeforeSorting);
        assertTrue(getQuestionListBeforeSorting+" Expected is not matching actual "+getQuestionListAfterSorting,getQuestionListAfterSorting.equals(getQuestionListBeforeSorting));
    }

}
