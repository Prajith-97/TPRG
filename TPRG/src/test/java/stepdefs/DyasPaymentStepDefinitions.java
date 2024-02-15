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



public class DyasPaymentStepDefinitions {

    WebDriver driver = DyasHomeStepDefinitions.driver;
    DHome homePage;
    DPDP pdp = new DPDP(driver);
    DBasket bag = new DBasket(driver);
    DMagento admin = new DMagento(driver);
    WebDriverManagedef webdef = new WebDriverManagedef();

    @Test
    @Given("user in the PLP page while clicking any of the menu from homepage")
    public void userInThePLPPageWhileClickingAnyOfTheMenuFromHomepage() throws Throwable {
        try {
            Thread.sleep(4000);
            driver.navigate().to("https://mcstag2.robertdyas.co.uk/");
            Actions actions = new Actions(driver);
            actions.moveToElement(DBasket.menuClick).perform();


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

    @Then("click on the sub menu")
    public void clickOnTheSubMenu() throws Throwable {
        try {
            Thread.sleep(4000);
            WebDriverManagedef.takeScreenshots(driver);
            webdef.JsClick(DBasket.submenuClick, driver);
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

    @And("And click on view all")
    public void andClickOnViewAll() throws Throwable {
        try {
            Thread.sleep(4000);
            WebDriverManagedef.takeScreenshots(driver);
            webdef.JsClick(DBasket.viewAll, driver);
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

    @Then("filter using brand")
    public void filterUsingBrand() throws Throwable {
        try {
            Thread.sleep(3000);
            WebDriverManagedef.takeScreenshots(driver);
            webdef.JsClick(DBasket.filterBrand, driver);
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

    @And("select the reloaded product")
    public void selectTheReloadedProduct() throws Throwable {
        try {
            Thread.sleep(3000);
            WebDriverManagedef.takeScreenshots(driver);
            webdef.JsClick(DBasket.SelectReloadProduct, driver);
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

    @When("user change the quality by clicking on + sign from PDP")
    public void userChangeTheQualityByClickingOnSignFromPDP() throws Throwable {
        try {
            Thread.sleep(3000);
            WebDriverManagedef.takeScreenshots(driver);
            webdef.JsClick(DBasket.Quantity, driver);
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

    @And("click to Add to Basket button")
    public void clickToAddToBasketButton() throws Throwable {
        try {
            Thread.sleep(4000);
            WebDriverManagedef.takeScreenshots(driver);
            webdef.JsClick(DBasket.Basket, driver);
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












