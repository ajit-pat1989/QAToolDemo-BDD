@UI
Feature: The Owesome Q/A tool features

  Scenario: I verify I can save and sort multiple questions and its answers
    Given I go to QA tool home page
    When I enter and save the Question and Answer
      | Question                                    | Answer                                                                                           |
      | Number of primitive data types in Java are? | 8                                                                                                |
      | What are the primitive data types in java?  | There are 8 types of primitive data types- int, char, boolean, byte, long, float, short, double. |
    Then I verify question and its answers getting saved
      | Question                                    | Answer                                                                                           |
      | Number of primitive data types in Java are? | 8                                                                                                |
      | What are the primitive data types in java?  | There are 8 types of primitive data types- int, char, boolean, byte, long, float, short, double. |
    And I verify sorting of Questions working correctly

