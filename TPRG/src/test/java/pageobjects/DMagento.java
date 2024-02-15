package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public class DMagento {
    WebDriver driver;
    Actions actions;

    public DMagento(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
