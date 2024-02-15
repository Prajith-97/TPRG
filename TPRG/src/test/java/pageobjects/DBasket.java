package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class DBasket {
    WebDriver driver;
    Actions actions;

    public DBasket(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.XPATH, using = "//a[@href=\"https://mcstag2.robertdyas.co.uk/237003-jml-vac-pk-replacement-bag-jum\"][1]")
    public static WebElement selectProduct;

    @FindBy(how = How.XPATH, using = "//div[@class=\"actions product-view-add-to-cart-actions\"]//button")
    public static WebElement AddtoBasket;

    @FindBy(how = How.XPATH, using = "//div//button[@class=\"add-to-cart-modal-checkout\"]")
    public static WebElement SecureCheckout;

    @FindBy(how = How.XPATH, using = "//button[@data-role=\"proceed-to-checkout\"][1]")
    public static WebElement checkoutDelivery;

    @FindBy(how = How.XPATH, using = "//div[@class=\"megamenu-container js-megamenu-container\"]//div[@class=\"megamenu-trigger js-megamenu-trigger\"]")
    public static WebElement menuClick;

    @FindBy(how = How.XPATH, using = "//div[@class=\"nav-ctgr-all js-nav-ctgr-all\"]//a[@class=\"nav-ctgr-link js-nav-link\"][1]")
    public static WebElement submenuClick;

    @FindBy(how = How.XPATH, using = "//a[contains(text(),\"Lawnmowers\")][1]")
    public static WebElement viewAll;

    @FindBy(how = How.XPATH, using = "//div[@class=\"filters-options-box\"]//span[@class=\"filters-options-checkbox\"][1]")
    public static WebElement filterBrand;

    @FindBy(how = How.XPATH, using = "//div[@class=\"product details product-item-details\"]//a")
    public static WebElement SelectReloadProduct;

    @FindBy(how = How.XPATH, using = "//div[@class=\"product-view-qty-stepper-plus js-stepper-plus\"]")
    public static WebElement Quantity;

    @FindBy(how = How.XPATH, using = "//div[@class=\"actions product-view-add-to-cart-actions\"]//button")
    public static WebElement Basket;







}



