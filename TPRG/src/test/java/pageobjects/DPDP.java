package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public class DPDP {
    WebDriver driver;
    Actions actions;

    public DPDP(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }










}
