package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DHome {


    public WebDriver driver;
    Actions actions;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    public DHome(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(how = How.ID, using = "onetrust-accept-btn-handler")
    public WebElement acceptCookies;

    @FindBy(how = How.XPATH, using = "//input[@class=\"header-search-input\"]")
    public static WebElement searchElement;

    // Login
    @FindBy(how = How.XPATH, using = "//a[@href=\"#social-login-popup\"][1]")
    public static WebElement signIn;

    @FindBy(how = How.XPATH, using = "//input[@name='username']")
    public static WebElement UsernameID;

    @FindBy(how = How.XPATH, using = "//input[@name=\"password\"][1]")
    public static WebElement passwordClick;

    @FindBy(how = How.XPATH, using = "//div[@class=\"actions-toolbar\"]//button[@type=\"button\"]")
    public static WebElement SignInButton;

    //Payment
    @FindBy(how = How.XPATH, using = "//*[@id='to-address-delivery']")
    public static WebElement deliveryOptions;


    @FindBy(how = How.XPATH, using = "(//input[@name=\"firstname\"])[2]")
    public static WebElement firstName;

    @FindBy(how = How.XPATH, using = "(//input[@name=\"lastname\"])[2]")
    public static WebElement lastName;

    @FindBy(how = How.XPATH, using = "(//input[@type=\"email\"])[4]")
    public static WebElement EmailAddress;

    @FindBy(how = How.XPATH, using = "(//input[@name=\"telephone\"])[2]")
    public static WebElement PhoneNumber;

    @FindBy(how = How.XPATH, using = "//input[@name=\"find-address\"][1]")
    public static WebElement QuickAddress;

    @FindBy(how = How.XPATH, using = "//div[@class=\"col col-method checkout-shipping-methods-cell-small\"]//input[@type=\"radio\"][1]")
    public static WebElement standardDelivery;

    @FindBy(how = How.XPATH, using = "//button[@type=\"submit\"][1]")
    public static WebElement ContinuePayment;

    @FindBy(how = How.XPATH, using = "//div[@class=\"payment-method-title field choice\"]//input")
    public static WebElement creditCardRadioButton;

    @FindBy(how = How.XPATH, using = "//*[@id='cardNumber']")
    public static WebElement cardNumberopt1;

    @FindBy(how = How.XPATH, using = "//input[@id=\"cardholderName\"]")
    public static WebElement holderNameopt2;















}
