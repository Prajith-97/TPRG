@DESKTOP_LOGIN

Feature:Login-page_UK
  Verify user able to login the page

  Scenario:Land on homepage
    Given website RobertDyas url is launched for "UK" in "Desktop"
    When user accept the cookies
    Then user is on homepage

  Scenario Outline: Verify user able to login successfully
    When user clicks on the sign in menu in the homepage
    Then enter "<username>" and "<password>"
    And clicks on sign In button

    Examples:
      | username                    | password    |
      | p@.com                      | wertyuSDF12 |
      | Parvathyrajparu97@gmail.com | Pass1234    |

