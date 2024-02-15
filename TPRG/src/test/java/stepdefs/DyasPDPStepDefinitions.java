package stepdefs;


import pageobjects.DBasket;
import pageobjects.DHome;
import pageobjects.DPDP;

import utility.WebDriverManagedef;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DyasPDPStepDefinitions {

    WebDriver driver = DyasHomeStepDefinitions.driver;
    DHome homePage;
    DPDP pdp = new DPDP(driver);
    DBasket bag = new DBasket(driver);
    WebDriverManagedef webdef = new WebDriverManagedef();








}