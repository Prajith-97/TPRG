package stepdefs;

import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.DBasket;
import pageobjects.DMagento;
import pageobjects.DPDP;
import utility.WebDriverManagedef;

public class DMagentoStepDefinitions {
    WebDriver driver = DyasHomeStepDefinitions.driver;
    //DHome homePage;
    DPDP pdp = new DPDP(driver);
    DBasket bag = new DBasket(driver);

    DMagento admin=new DMagento(driver);
    WebDriverManagedef webdef = new WebDriverManagedef();

 //   @Test
//    @Given("user is in the Magento admin page")
//    public void user_is_in_the_Magento_admin_page() throws Throwable {
//        try{
//            Thread.sleep(3000);
//            driver.navigate().to("https://mcstagadmin.robertdyas.co.uk/rymandyas/catalog/product/");
//            WebDriverManagedef.takeScreenshots(driver);
//            WebDriverManagedef.stepstatus = "Passed";
//        } catch (AssertionError | Exception e) {
//            WebDriverManagedef.stepstatus = "Failed";
//            WebDriverManagedef.error = (e.getMessage());
//        }
//        if (WebDriverManagedef.stepstatus == "Failed") {
//            Assert.assertTrue(false);
//        }
//        }
    }






