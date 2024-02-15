@DESKTOP_AddToBasket
Feature:AddProduct_to_Basket_CheckoutProducts_UK
  Verify user able to add product to Basket and proceed to checkout

  Scenario:Land on homepage
    Given website RobertDyas url is launched for "UK" in "Desktop"
    #When user accept the cookies
    Then user is on homepage

  Scenario:Verify user redirected to the SLP Page
    Given user search for a phrase
    And Press enter key
    When user redirected to the SLP page

  Scenario: Verify user able to add product to the basket
    When user select the product from SLP page
    Then user click on Add to basket button
    And click on checkout securely button
    And user redirected to the basket page

  Scenario: Verify user able to proceed to checkout
    When user click on the checkout securely in the basket page
    Then user redirected to the checkout page

  Scenario Outline: Verify user able to enter all the delivery details
    Given user is in the delivery to address option box
    When user enter "<Firstname>", "<lastname>", "<EmailID>", "<PhoneNo>"
    Then enter the address via "<quickaddressfinder>"
    And select the delivery options
    And click on continue to payment button

    Examples:
      | Firstname | lastname | EmailID        | PhoneNo    |quickaddressfinder|
      | Parvathy  | RB       | Paru@gmail.com | 8888888888 |J P  N, Unit A2c, Redlands, Coulsdon, CR5 2HT|


  Scenario Outline: Verify user able to payment using credit card
  Given user is in the Payment page
    When user select the payment method credit card
  Then user enter "<cardNumber>" "<holdername>" "<expirymonth>" "<expiryyear>" "<securitycode>"
  Then clicks on Make payment button


  Examples:
  |cardNumber      |holdername|expirymonth|expiryyear|securitycode|
  |4111111111111111|RB        |02         |23        |123|







# Quantity.................................

#  Scenario:Verify user able to filter the products from PLP
#    Given user in the PLP page while clicking any of the menu from homepage
#    Then click on the sub menu
#    And And click on view all
#    Then filter using brand
#    And select the reloaded product
#
#  Scenario:Verify user able to change quantity of the products
#    When user change the quality by clicking on + sign from PDP
#    And click to Add to Basket button

