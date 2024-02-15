@DESKTOP_Payment

  Feature: Payment_creditcard
    Verify user able to do successfull payment

    Scenario:Land on homepage
      Given website RobertDyas url is launched for "UK" in "Desktop"
      When user accept the cookies
      Then user is on homepage

#    Quantity.................................

  Scenario:Verify user able to filter the products from PLP
    Given user in the PLP page while clicking any of the menu from homepage
    Then click on the sub menu
    And And click on view all
    Then filter using brand
    And select the reloaded product

  Scenario:Verify user able to change quantity of the products
    When user change the quality by clicking on + sign from PDP
    And click to Add to Basket button


