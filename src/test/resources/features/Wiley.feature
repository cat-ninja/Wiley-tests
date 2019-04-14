@web @Wiley
Feature: WebUI tests for Wiley

  Scenario: Check that links are present in the top menu.
    When Wiley page is opened
    Then "Who we Serve" link should be visible in the top menu
    And "Subjects" link should be visible in the top menu
    And "About" link should be visible in the top menu

  Scenario: Check items under "Who we Serve" for sub-header.
    When Wiley page is opened
    Then next items are displayed under "Who we Serve" subheader
      | Students        |
      | Instructors     |
      | Book Authors    |
      | Professionals   |
      | Researchers     |
      | Institutions    |
      | Librarians      |
      | Corporations    |
      | Societies       |
      | Journal Editors |
      | Bookstores      |
      | Government      |

    And "Who we Serve" menu link is opened
    Then items count in drop-down menu is 12
    And next drop-down menu items are displayed
      | Students        |
      | Instructors     |
      | Book Authors    |
      | Professionals   |
      | Researchers     |
      | Institutions    |
      | Librarians      |
      | Corporations    |
      | Societies       |
      | Journal Editors |
      | Bookstores      |
      | Government      |

  Scenario: Check "Students" page is opened and rendered correctly.
    When Wiley page is opened
    Then "Who we Serve" menu link is opened
    And "Students" page is opened from drop-down menu
    Then there is 3 links to WileyPlus on the page

  Scenario: Check "Education" page is opened and rendered correctly and go back to home page.
    When Wiley page is opened
    Then "Subjects" menu link is opened
    And "Education" page is opened from drop-down menu
    Then next items are displayed under "Subjects" in side panel
      | Information & Library Science                     |
      | Education & Public Policy                         |
      | K-12 General                                      |
      | Higher Education General                          |
      | Vocational Technology                             |
      | Conflict Resolution & Mediation (School settings) |
      | Curriculum Tools- General                         |
      | Special Educational Needs                         |
      | Theory of Education                               |
      | Education Special Topics                          |
      | Educational Research & Statistics                 |
      | Literacy & Reading                                |
      | Classroom Management                              |
    When home page is opened
    Then Wiley page is opened

  Scenario: Press search button without entering any value to search field.
    When Wiley page is opened
    And search button is pressed
    Then Wiley page is opened

  Scenario: Searching for "Java".
    When Wiley page is opened
    And "java" is typed into the search field
    Then search autocomplete window should be visible
    And "Suggestions" section has 4 items that starts with "java"
    And "Products" section has 4 items that starts with "Java"

    When search button is pressed
    Then only titles contains "Java" are displayed
    And there is 10 items in the results
    And each item has at least 1 "Add to cart" button

    When search results is saved
    And search button is pressed
    Then search results should be equal to the saved ones



