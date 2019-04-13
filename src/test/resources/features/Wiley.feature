@web @Wiley
Feature: WebUI tests for Wiley

#  Scenario: Check that links are present in the top menu.
#    When Wiley page is opened
#    Then "Who we Serve" link should be visible in the top menu
#    And "Subjects" link should be visible in the top menu
#    And "About" link should be visible in the top menu

  Scenario: Check items under "Who we Serve" for sub-header.
    When Wiley page is opened
    And "Who we Serve" menu link is opened
    Then items count in drop-down menu is 12
    And next drop-down menu items are displayed
      | Students        |
      | Instructors     |
      | Professionals   |
      | Institutions    |
      | Librarians      |
      | Corporations    |
      | Societies       |
      | Journal Editors |
      | Bookstores      |
      | Government      |
