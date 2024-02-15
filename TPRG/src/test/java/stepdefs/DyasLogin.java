package stepdefs;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java8.Th;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.thread.IThreadWorkerFactory;
import pageobjects.DBasket;
import pageobjects.DHome;
import pageobjects.DMagento;
import pageobjects.DPDP;
import stepdefs.DyasHomeStepDefinitions;
import utility.WebDriverManagedef;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;



public class DyasLogin {


    WebDriver driver = DyasHomeStepDefinitions.driver;
    DHome homePage;
    DPDP pdp = new DPDP(driver);
    DBasket bag = new DBasket(driver);
    DMagento admin = new DMagento(driver);
    WebDriverManagedef webdef = new WebDriverManagedef();

    @Test
    @When("user clicks on the sign in menu in the homepage")
    public void userClicksOnTheSignInMenuInTheHomepage() throws Throwable {
        try {
            Thread.sleep(3000);
            webdef.JsClick(DHome.signIn, driver);
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

//@Test
//@Then("enter <username> and <password>")
//    public void enterUsernameAndPassword(String username, String password) throws Throwable {
//        try {
//            DHome.enterUsername(username, password);
//            webdef.JsClick(DHome.UsernameID, driver);
//           // DHome.UsernameID.clear();
//            DHome.UsernameID.sendKeys(username);
//            webdef.JsClick(DHome.passwordClick, driver);
//          //  DHome.passwordClick.clear();
//            DHome.passwordClick.sendKeys(password);
//            WebDriverManagedef.takeScreenshots(driver);
//            WebDriverManagedef.stepstatus = "Passed";
//        } catch (AssertionError | Exception e) {
//            WebDriverManagedef.stepstatus = "Failed";
//            WebDriverManagedef.error = (e.getMessage());
//        }
//        if (WebDriverManagedef.stepstatus == "Failed") {
//            Assert.assertTrue(false);
//        }
//
//        }


    @And("clicks on sign In button")
    public void clicksOnSignInButton() throws Throwable {
        try {
            Thread.sleep(3000);
            webdef.JsClick(DHome.SignInButton, driver);
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


    @Then("enter {string} and {string}")
    public void enterAnd(String username, String password) throws Throwable {
        try {
//            DHome.enterUsername(username, password);
              webdef.JsClick(DHome.UsernameID, driver);
             DHome.UsernameID.clear();
             DHome.UsernameID.sendKeys(username);
            webdef.JsClick(DHome.passwordClick, driver);
            DHome.passwordClick.clear();
            DHome.passwordClick.sendKeys(password);
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
}

