@UI
Feature: The Owesome Q/A tool screen validation

  Scenario Outline: I verify and validate QA tool page.
    Given I go to QA tool home page
    When I click on "Remove questions"
    Then I verify all Questions getting removed
    When I click on "Create question" without entering any question or answer
    Then I verify no "Question" is entered and shows a validation message "<validationMessage>"
    When I put "Question" as "<Question>"
    And I click on "Create question"
    Then I verify no "Answer" is entered and shows a validation message "<validationMessage>"
    When I put "Question" as "<Question>"
    And I put "Answer" as "<Answer>"
    And I click on "Create question"
    Then I verify "<Question>" and "<Answer>" getting saved
    Examples:
      | Question                                    | Answer | validationMessage           |
      | Number of primitive data types in Java are? | 8      | Please fill out this field. |