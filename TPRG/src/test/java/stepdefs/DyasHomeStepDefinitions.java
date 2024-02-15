package stepdefs;

import java.io.FileReader;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.cucumber.java.en.And;
import io.cucumber.java8.Th;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;


import pageobjects.DBasket;
import pageobjects.DHome;
import pageobjects.DPDP;

import utility.WebDriverManagedef;
import utility.WebDriverManagerAppium;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import static pageobjects.DHome.*;

public class DyasHomeStepDefinitions {
    static WebDriver driver;
    String driverstate;
    static DHome homePage;
    DPDP plp = new DPDP(driver);
    WebDriverManagedef webdef = new WebDriverManagedef();
    static Boolean executionmobile = false;
    static String RegionVal, Environment;



//   WebDriverWait wait = new WebDriverWait(driver,Duration.ofMillis(2000));


    @Test
    @Severity(SeverityLevel.MINOR)
    @Given("website RobertDyas url is launched for {string} in {string}")
    public void websiteRobertDyasUrlIsLaunchedForIn(String Region, String Device) throws Throwable {
        RegionVal = Region;
        Environment = System.getProperty("Environment");
        switch (Device.toUpperCase()) {
            case "MOBILE":
                try {
                    // OS = System.getProperty("OS");
                    String Android = System.getProperty("androidStatus");
                    String Apple = System.getProperty("iOSStatus");

                    // System.out.println(OS);

                    if (Device.equalsIgnoreCase("Mobile") && Android.equalsIgnoreCase("true")) {
                        // if (OS.equalsIgnoreCase("Android")
                        WebDriverManagerAppium.setUpSuiteAndriod(Region);
                        driver = WebDriverManagerAppium.driver;
                        homePage = new DHome(driver);
                        executionmobile = true;
                        WebDriverManagedef.stepstatus = "Passed";
                    }

                    if (Device.equalsIgnoreCase("Mobile") && Apple.equalsIgnoreCase("true")) {

                        WebDriverManagerAppium.setUpSuiteIOS(Region);
                        driver = WebDriverManagerAppium.driver;
                        homePage = new DHome(driver);
                        executionmobile = true;
                        WebDriverManagedef.stepstatus = "Passed";
                    }
                } catch (Exception e) {
                    WebDriverManagedef.stepstatus = "Failed";
                    WebDriverManagedef.error = (e.getMessage());
                    Assert.assertTrue(false, "pCloudy device connection failed.");

                }
//		if (WebDriverManagedef.stepstatus == "Failed") {
//			Assert.assertTrue(false);
//		}
                break;
            case "DESKTOP":
                try {
                    if (Device.equalsIgnoreCase("Desktop")) {
                        WebDriverManagedef.getRemoteWebDriver(Region);
                        driver = webdef.driver;
                        homePage = new DHome(driver);
                        WebDriverManagedef.takeScreenshots(driver);
                        WebDriverManagedef.setAllureEnvironment();
                        executionmobile = false;
                    }
                    WebDriverManagedef.stepstatus = "Passed";
                } catch (AssertionError | Exception e) {
                    WebDriverManagedef.stepstatus = "Failed";
                    WebDriverManagedef.error = (e.getMessage());
                    Assert.assertTrue(false, "Browser driver creation failed.");
                }
//		if (WebDriverManagedef.stepstatus == "Failed") {
//			Assert.assertTrue(false);
//		}

                break;
        }
    }


    @When("user accept the cookies")
    public void userAcceptTheCookies() throws Throwable {

        try {
            Thread.sleep(3000);
            WebDriverManagedef.takeScreenshots(driver);
            webdef.JsClick(homePage.acceptCookies, driver);
            WebDriverManagedef.takeScreenshots(driver);
            WebDriverManagedef.stepstatus = "Passed";
        } catch (AssertionError | Exception e) {
            WebDriverManagedef.stepstatus = "Failed";
            WebDriverManagedef.error = (e.getMessage());
        }
        if (WebDriverManagedef.stepstatus == "Failed") {
            Assert.assertTrue(false);
        }
    }


    @Then("user is on homepage")
    public void userIsOnHomepage() throws Throwable {
        try {
            Thread.sleep(3000);
            String expected_title = "Robert Dyas | Garden, DIY, Electricals & Homewares";
            String actual_title = driver.getTitle();
            Assert.assertEquals(expected_title, actual_title);

            WebDriverManagedef.takeScreenshots(driver);
            WebDriverManagedef.stepstatus = "Passed";
            WebDriverManagedef.stepstatus = "Passed";
        } catch (AssertionError | Exception e) {
            WebDriverManagedef.stepstatus = "Failed";
            WebDriverManagedef.error = (e.getMessage());
        }
        if (WebDriverManagedef.stepstatus == "Failed") {
            Assert.assertTrue(false);
        }
    }

    @Given("user search for a phrase")
    public void userSearchForAPhrase() throws Throwable {
        try {
            Thread.sleep(3000);
            webdef.JsClick(DHome.searchElement, driver);
            DHome.searchElement.sendKeys("Bags");
            WebDriverManagedef.takeScreenshots(driver);
            WebDriverManagedef.stepstatus = "Passed";
        } catch (AssertionError | Exception e) {
            WebDriverManagedef.stepstatus = "Failed";
            WebDriverManagedef.error = (e.getMessage());
        }
        if (WebDriverManagedef.stepstatus == "Failed") {
            Assert.assertTrue(false);
        }

    }

    @And("Press enter key")
    public void pressEnterKey() throws Throwable {
        try {
            Thread.sleep(3000);
            DHome.searchElement.sendKeys(Keys.ENTER);
            WebDriverManagedef.takeScreenshots(driver);
            WebDriverManagedef.stepstatus = "Passed";
        } catch (AssertionError | Exception e) {
            WebDriverManagedef.stepstatus = "Failed";
            WebDriverManagedef.error = (e.getMessage());
        }
        if (WebDriverManagedef.stepstatus == "Failed") {
            Assert.assertTrue(false);
        }

    }


    @When("user redirected to the SLP page")
    public void userRedirectedToTheSLPPage() throws Throwable {
        try {
            Thread.sleep(3000);
            String expected_title = "Search Results for: 'Bags'";
            String actual_title = driver.getTitle();
            Assert.assertEquals(expected_title, actual_title);

            WebDriverManagedef.takeScreenshots(driver);
            WebDriverManagedef.stepstatus = "Passed";
            WebDriverManagedef.stepstatus = "Passed";
        } catch (AssertionError | Exception e) {
            WebDriverManagedef.stepstatus = "Failed";
            WebDriverManagedef.error = (e.getMessage());
        }
        if (WebDriverManagedef.stepstatus == "Failed") {
            Assert.assertTrue(false);
        }

    }

    @Given("user is in the delivery to address option box")
    public void userIsInTheDeliveryToAddressOptionBox() throws Throwable {
        try {
            Thread.sleep(4000);
            webdef.JsClick(DHome.deliveryOptions, driver);
            WebDriverManagedef.takeScreenshots(driver);
            WebDriverManagedef.stepstatus = "Passed";
            WebDriverManagedef.stepstatus = "Passed";
        } catch (AssertionError | Exception e) {
            WebDriverManagedef.stepstatus = "Failed";
            WebDriverManagedef.error = (e.getMessage());
        }
        if (WebDriverManagedef.stepstatus == "Failed") {
            Assert.assertTrue(false);
        }
    }

    @When("user enter {string}, {string}, {string}, {string}")
    public void userEnter(String Firstname, String lastname, String EmailID, String PhoneNo) throws Throwable {
        try {
            Thread.sleep(3000);
            webdef.JsClick(DHome.firstName, driver);
            DHome.firstName.sendKeys(Firstname);
            webdef.JsClick(DHome.lastName, driver);
            DHome.lastName.sendKeys(lastname);
            Thread.sleep(3000);
            webdef.JsClick(DHome.EmailAddress, driver);
            DHome.EmailAddress.sendKeys(EmailID);
            Thread.sleep(3000);
            webdef.JsClick(DHome.PhoneNumber, driver);
            DHome.PhoneNumber.sendKeys(PhoneNo);
            WebDriverManagedef.takeScreenshots(driver);
            WebDriverManagedef.stepstatus = "Passed";
            WebDriverManagedef.stepstatus = "Passed";
        } catch (AssertionError | Exception e) {
            WebDriverManagedef.stepstatus = "Failed";
            WebDriverManagedef.error = (e.getMessage());
        }
        if (WebDriverManagedef.stepstatus == "Failed") {
            Assert.assertTrue(false);
        }
    }


    @Then("enter the address via {string}")
    public void enterTheAddressVia(String quickaddressfinder) throws Throwable {

        try {
            Thread.sleep(4000);
            webdef.JsClick(DHome.QuickAddress, driver);
            DHome.QuickAddress.sendKeys(quickaddressfinder);
            DHome.QuickAddress.sendKeys(Keys.ENTER);
            Thread.sleep(2000);
            DHome.QuickAddress.sendKeys(Keys.ENTER);
            Thread.sleep(2000);

//            for (int i = 0; i < 2; i++) {
//                DHome.QuickAddress.sendKeys(Keys.DOWN);
//                Thread.sleep(2000);
//            }
//            DHome.QuickAddress.click();

            WebDriverManagedef.takeScreenshots(driver);
            WebDriverManagedef.stepstatus = "Passed";
            WebDriverManagedef.stepstatus = "Passed";
        } catch (AssertionError | Exception e) {
            WebDriverManagedef.stepstatus = "Failed";
            WebDriverManagedef.error = (e.getMessage());
        }
        if (WebDriverManagedef.stepstatus == "Failed") {
            Assert.assertTrue(false);
        }
    }


    @And("select the delivery options")
    public void selectTheDeliveryOptions() throws Throwable {
        try {
            Thread.sleep(4000);
            webdef.JsClick(DHome.standardDelivery, driver);
            WebDriverManagedef.takeScreenshots(driver);
            WebDriverManagedef.stepstatus = "Passed";
            WebDriverManagedef.stepstatus = "Passed";
        } catch (AssertionError | Exception e) {
            WebDriverManagedef.stepstatus = "Failed";
            WebDriverManagedef.error = (e.getMessage());
        }
        if (WebDriverManagedef.stepstatus == "Failed") {
            Assert.assertTrue(false);
        }

    }


    @And("click on continue to payment button")
    public void clickOnContinueToPaymentButton() throws Throwable {
        try {
//            WebDriverWait wait = new WebDriverWait(driver,Duration.ofMillis(2000));
//            wait.until(ExpectedConditions.visibilityOf(DHome.ContinuePayment));
            webdef.JsClick(DHome.ContinuePayment, driver);
            Thread.sleep(3000);
//            wait.until(ExpectedConditions.visibilityOf(DHome.creditCardRadioButton));
//           webdef.JsClick(DHome.creditCardRadioButton, driver);
            WebDriverManagedef.takeScreenshots(driver);
            WebDriverManagedef.stepstatus = "Passed";
            WebDriverManagedef.stepstatus = "Passed";
        } catch (AssertionError | Exception e) {
            WebDriverManagedef.stepstatus = "Failed";
            WebDriverManagedef.error = (e.getMessage());
        }
        if (WebDriverManagedef.stepstatus == "Failed") {
            Assert.assertTrue(false);
        }

    }


    @Given("user is in the Payment page")
    public void userIsInThePaymentPage() throws Throwable {
        try {
            Assert.assertTrue(driver.getTitle().contains("Checkout"), "User not on basket page");
            WebDriverManagedef.takeScreenshots(driver);
            WebDriverManagedef.stepstatus = "Passed";
            WebDriverManagedef.stepstatus = "Passed";
        } catch (AssertionError | Exception e) {
            WebDriverManagedef.stepstatus = "Failed";
            WebDriverManagedef.error = (e.getMessage());
        }
        if (WebDriverManagedef.stepstatus == "Failed") {
            Assert.assertTrue(false);
        }

    }
    @When("user select the payment method credit card")
    public void userSelectThePaymentMethodCreditCard() throws Throwable {

        try {
//            WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(20));
//            wait.until(ExpectedConditions.visibilityOfElementLocated((By) creditCardRadioButton));

            webdef.JsClick(DHome.creditCardRadioButton, driver);
//            Thread.sleep(2000);
            WebDriverManagedef.takeScreenshots(driver);
            WebDriverManagedef.stepstatus = "Passed";
            WebDriverManagedef.stepstatus = "Passed";
        } catch (AssertionError | Exception e) {
            WebDriverManagedef.stepstatus = "Failed";
            WebDriverManagedef.error = (e.getMessage());
        }
        if (WebDriverManagedef.stepstatus == "Failed") {
            Assert.assertTrue(false);
        }
    }
    @Then("user enter {string} {string} {string} {string} {string}")
    public void userEnter(String cardNumber, String holdername, String expirymonth, String expiryyear, String securitycode)throws Throwable {
        try {
//            Thread.sleep(3000);
//            WebDriverWait wait = new WebDriverWait(driver,Duration.ofMillis(2000));
//            wait.until(ExpectedConditions.visibilityOf(homePage.cardNumberopt1));
//            WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(20));
//            wait.until(ExpectedConditions.visibilityOfElementLocated((By) cardNumberopt1));

            //DHome.cardNumberopt1.isEnabled();
            webdef.JsClick(DHome.cardNumberopt1, driver);
            DHome.cardNumberopt1.clear();
            DHome.cardNumberopt1.sendKeys(cardNumber);
//            Thread.sleep(3000);
            webdef.JsClick(DHome.holderNameopt2, driver);
            DHome.holderNameopt2.clear();
            DHome.holderNameopt2.sendKeys(holdername);
//            Thread.sleep(3000);
//            webdef.JsClick(DHome.cardNumberopt1, driver);
//            DHome.cardNumberopt1.sendKeys(cardNumber);

            WebDriverManagedef.takeScreenshots(driver);
            WebDriverManagedef.stepstatus = "Passed";
            WebDriverManagedef.stepstatus = "Passed";
        } catch (AssertionError | Exception e) {
            WebDriverManagedef.stepstatus = "Failed";
            WebDriverManagedef.error = (e.getMessage());
        }
        if (WebDriverManagedef.stepstatus == "Failed") {
            Assert.assertTrue(false);
        }
    }


    @Then("clicks on Make payment button")
    public void clicksOnMakePaymentButton() {
    }
}















