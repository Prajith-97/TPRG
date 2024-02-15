package utility;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.devtools.v85.network.model.Headers;
import java.util.Optional;
import com.sun.xml.messaging.saaj.util.Base64;

import com.google.common.collect.ImmutableMap;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Story;
import runner.RunTestNGTest;

public class WebDriverManagedef {

	public static WebDriver driver;

	public static String User_Name = null;
	public static String Password = null;
	static String URL = null;
	public static String Browser = null;
	static boolean Emulation = false;
	static boolean Headless = false;
	static String Device = null;
	public Boolean validatetrueresult;
	public String errormessage;
	StringBuffer buffer;
	public static String error = "";
	public static String stepstatus = "";

	@SuppressWarnings("deprecation")
	public static void getRemoteWebDriver(String Region)
			throws InterruptedException, AWTException, FileNotFoundException, IOException, ParseException {
		RunTestNGTest objruntest = new RunTestNGTest();
		String Obheadurl = null;
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(
					new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\logindetails.json"));
			JSONObject jsonObject = (JSONObject) obj;
			String Environment =System.getProperty("Environment");
			User_Name = (String) jsonObject.get("User_Name");
			Password = (String) jsonObject.get("Password");
			URL = (String) jsonObject.get("URL");
			Browser = objruntest.browser;
			// Browser = (String) jsonObject.get("Browser");
			Device = (String) jsonObject.get("Device");
			Headless = Boolean.parseBoolean((String) jsonObject.get("Headless"));

			WebDriverManagedef webdef = new WebDriverManagedef();
			URL = webdef.GetRegionURL(Region,Environment);
			Obheadurl = URL.substring(8);

			if (WebDriverManagedef.driver != null) {

				WebDriverManagedef.driver.quit();

			}
			switch (Browser.toUpperCase()) {
			case "CHROME":
				ChromeOptions chromeOptions = new ChromeOptions();
				if (Headless) {
//					DesiredCapabilities cap = DesiredCapabilities.chrome();
					chromeOptions.addArguments("disable-gpu");
					chromeOptions.addArguments("ignore-certificate-errors");
					chromeOptions.addArguments("disable-extensions");
					chromeOptions.setExperimentalOption("useAutomationExtension", false);
					chromeOptions.addArguments("--proxy-server='direct://'");
					chromeOptions.addArguments("--proxy-bypass-list=*");
					chromeOptions.addArguments("--headless");
					chromeOptions.addArguments("start-maximized");
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver(chromeOptions);
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					driver.get("https://" + User_Name + ":" + Password + "@" + Obheadurl + "");
					Thread.sleep(1000);
				} else {
					WebDriverManager.chromedriver().setup();
					driver=new ChromeDriver();
//					HandleUntrustedCertificate("Chrome",Environment);
//					DevTools dev =((ChromeDriver) driver).getDevTools();
//					dev.createSession();
//					dev.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
					
//					Map<String, Object> map = new HashMap<>();
//					String basicAuth ="Basic " + new String(new Base64().encode(String.format("%s:%s", User_Name, Password).getBytes()));
//					map.put("Authorization", basicAuth);
//					dev.send(Network.setExtraHTTPHeaders(new Headers(map)));
					driver.manage().window().maximize();
					driver.navigate().to(URL);//URL
					//driver.get(URL)			;
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					if(!Environment.equalsIgnoreCase("LIVE")) {
						userandpassword(User_Name, Password);
					}
					driver.manage().window().maximize();
//					driver.navigate().to(URL);
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
					Thread.sleep(1000);

				}
				break;
			case "FIREFOX":
				WebDriverManager.firefoxdriver().setup();
				HandleUntrustedCertificate("Firefox",Environment);
				driver.manage().window().maximize();
				driver.get(URL);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				if(!Environment.equalsIgnoreCase("LIVE"))
				userandpassword(User_Name, Password);
				Thread.sleep(1000);
//				driver.get("https://" + User_Name + ":" + Password + "@" + Obheadurl + "");
				Thread.sleep(1000);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				break;
			case "EDGE":
				
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				DevTools dev =((EdgeDriver) driver).getDevTools();
				dev.createSession();
				dev.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
//				Map<String, Object> map = new HashMap<>();
//				String basicAuth ="Basic " + new String(new Base64().encode(String.format("%s:%s", User_Name, Password).getBytes()));				
//				map.put("Authorization", basicAuth);
//				dev.send(Network.setExtraHTTPHeaders(new Headers(map)));
				driver.manage().window().maximize();
				driver.navigate().to(URL);
				HandleUntrustedCertificate("Edge",Environment);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				Thread.sleep(2000);
				if(!Environment.equalsIgnoreCase("LIVE"))
				userandpassword(User_Name, Password);
				Thread.sleep(1000);


				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void HandleUntrustedCertificate(String Browser, String Environment) throws InterruptedException
	{
			if (Browser.equalsIgnoreCase("Firefox"))
			{
				if (Environment.equalsIgnoreCase("Staging")) 
				{
				FirefoxOptions firefoxOptions = new FirefoxOptions();				
				firefoxOptions.setAcceptInsecureCerts(true);						
				driver = new FirefoxDriver(firefoxOptions);
				}
				else 
				{
				driver = new FirefoxDriver();
				}
			}
			
			if (Browser.equalsIgnoreCase("Chrome"))
			{
				ChromeOptions options = new ChromeOptions();				
				options.addArguments("--incognito");
				if (Environment.equalsIgnoreCase("Staging"))
				{
				options.addArguments("--ignore-ssl-errors=yes");
				options.addArguments("--ignore-certificate-errors");
				}
				driver = new ChromeDriver(options);
			}
			if(Browser.equalsIgnoreCase("Edge"))
			{
				if(Environment.equalsIgnoreCase("Staging") )
				{
				driver.findElement(By.id("details-button")).click();
				Thread.sleep(1000);
				driver.findElement(By.id("proceed-link")).click();
				}
			}
	}

	public static void userandpassword(String user, String Pass) throws AWTException, InterruptedException {

		Robot rb = new Robot();

		// Enter user name by ctrl-v
		StringSelection username = new StringSelection(user);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(username, null);
		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_V);
		rb.keyRelease(KeyEvent.VK_V);
		rb.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(3000);
		rb.keyPress(KeyEvent.VK_TAB);
		rb.keyRelease(KeyEvent.VK_TAB);
		Thread.sleep(1000);
		// Enter password by ctrl-v
		StringSelection pwd = new StringSelection(Pass);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(pwd, null);
		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_V);
		rb.keyRelease(KeyEvent.VK_V);
		rb.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(1000);
		// press enter
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);

		// wait
		Thread.sleep(1000);

	}

	public static void takeScreenshots(WebDriver driver) throws Throwable {
		// Convert web driver object to TakeScreenshot
		// return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
		Allure.getLifecycle().addAttachment("screenshot", "image", ".png",
				((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
	}

	public static void takeSnapShot(WebDriver webdriver) throws Exception {

	}

	@Attachment(value = "Log")
	public static File parseXMLData() {

		try {
			return Paths.get("target\\TFGHobbs-0.0.1.log").toFile();
		} catch (Throwable ex) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));

		}
		return null;
	}

	@Story("log the output")
	@Test
	public void logData() throws Throwable {

		File file = parseXMLData();
		if (file != null) {
			Allure.getLifecycle().addAttachment("TFGHobbs-0.0.1", "log", ".log", FileUtils.readFileToByteArray(file));
		}
	}

	public static void setAllureEnvironment() {
		AllureEnvironmentWriter
				.allureEnvironmentWriter(
						ImmutableMap.<String, String>builder().put("Browser", Browser)
								.put("Browser.Version", "90.0.3538.77").put("URL", URL).build(),
						System.getProperty("user.dir") + "/allure-results/");
	}

	public void Click(WebElement Element, WebDriver driver) throws Throwable {
		Thread.sleep(1000);
		Element.click();
	}

	public void JsClick(WebElement Element, WebDriver driver) throws Throwable {
		Thread.sleep(1000);
		highlightObjects(driver,Element);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("return document.readyState").equals("complete");
		executor.executeScript("arguments[0].click();", Element);
		executor.executeScript("return document.readyState").equals("complete");
	}

	public String validatetrue(Boolean status) {
		String errorMessage = "";
		try {
			Assert.assertTrue(status);
			validatetrueresult = true;
		} catch (Exception e) {

			errorMessage = e.getMessage();
			validatetrueresult = false;
		}
		return errorMessage;

	}

	public String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 5) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	public void dropdpwnvalue(WebElement Element, String value) throws Throwable {
		
		highlightObjects(driver,Element);
		Select objselect = new Select(Element);
		objselect.selectByVisibleText(value);
	}

	/*public Boolean sortedverifylist(ArrayList<Integer> myList) {

		boolean isSorted = true;
		for (int i = 0; i < myList.size() - 1; i++) {
			// current String is > than the next one (if there are equal list is still
			// sorted)
			if (myList.get(i).compareTo(myList.get(i + 1)) > 0) {
				isSorted = false;
				break;
			}
		}
		return isSorted;
	}*/
	
	public Boolean sortedverifylist(ArrayList<Integer> myList) {

		boolean isSorted = true;
		Collections.sort(myList);
		return isSorted;
	}

	/*public Boolean sortedverifylistdescending(ArrayList<Integer> myList) {

		boolean isSorted = true;

		for (int i = 0; i < myList.size() - 1; i++) {
			if (myList.get(i) < myList.get(i + 1)) {
				isSorted = false;
				break;
			}
		}
		return isSorted;
	}*/
	
	public Boolean sortedverifylistdescending(ArrayList<Integer> myList) {

		boolean isSorted = true;

		Collections.sort(myList, Collections.reverseOrder());  
		
		return isSorted;
	}
	
	

	public void selectDropdownOption(WebElement locator, String type, String value) {

		Select select = new Select(locator);
		switch (type) {
		case "index":
			select.selectByIndex(Integer.parseInt(value));
			break;
		case "value":
			select.selectByValue(value);
			break;

		case "visibletext":
			select.selectByVisibleText(value);
			break;

		}

	}

	public void waitVisibilityofElementLocated(WebDriver driver, By element, long Timeout) {

		WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(Timeout));
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));

	}

	public void waitvisibilityof(WebDriver driver, WebElement element, long Timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Timeout));
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	public void waitInvisibilityOf(WebDriver driver, By element, long Timeout) {

		WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(Timeout));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(element));

	}

	public void waiturlContains(WebDriver driver, String text, long Timeout) {

		WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(Timeout));
		wait.until(ExpectedConditions.urlContains(text));

	}

	public void waitTitleContains(WebDriver driver, String text, long Timeout) {

		WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(Timeout));
		wait.until(ExpectedConditions.titleContains(text));

	}

	public void waitElementClickable(WebDriver driver, WebElement element, long Timeout) {

		WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(Timeout));
		wait.until(ExpectedConditions.elementToBeClickable(element));

	}

	public void JsScrollBy(WebDriver driver, int xaxis, int yaxis) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(" + xaxis + "," + yaxis + ")");

	}

	public void JsScrollTo(WebDriver driver, int xaxis) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(" + xaxis + ",document.body.scrollHeight)");
	}

	public void JsScrollintoView(WebDriver driver, WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	public void selectGetOption(WebDriver driver, WebElement element) throws Throwable {
		List<WebElement> sortList;
		Select select = new Select(element);
		sortList = select.getOptions();
		for (int i = 2; i >= 0; i--) {
			sortList.get(i).click();
			String rating = sortList.get(i).getText();
			switch (rating) {
			case "LOWEST RATING":
				Assert.assertEquals(rating, "LOWEST RATING");
				Thread.sleep(3000);
				WebDriverManagedef.takeScreenshots(driver);
				break;
			case "HIGHEST RATING":
				Assert.assertEquals(rating, "HIGHEST RATING");
				Thread.sleep(3000);
				WebDriverManagedef.takeScreenshots(driver);
				break;
			case "MOST RECENT":
				Assert.assertEquals(rating, "MOST RECENT");
				Thread.sleep(3000);
				WebDriverManagedef.takeScreenshots(driver);
				break;
			}
		}
	}

	public void actionClass(WebDriver driver, WebElement element)
	{
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}
	
	public void actionPageDown(WebDriver driver)
	{
		Actions a = new Actions(driver);
		a.sendKeys(Keys.PAGE_DOWN).build().perform();
	}
	
	public String GetRegionURL(String Region, String Environment) throws FileNotFoundException, IOException, ParseException {
		String URL = null;
		JSONParser parser = new JSONParser();
		Object obj = parser
				.parse(new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\logindetails.json"));
		JSONObject jsonObject = (JSONObject) obj;
		switch (Region.toUpperCase()) {
		case "UK":
			URL = (String) jsonObject.get("UK_URL");
			break;
		case "US":
			URL = (String) jsonObject.get("US_URL");
			break;
		case "ROW":
			URL = (String) jsonObject.get("ROW_URL");
			break;
		case "AU":
			URL = (String) jsonObject.get("AUS_URL");
			break;
		case "EU":
			URL = (String) jsonObject.get("EU_URL");
			break;
		default:
			URL = (String) jsonObject.get("UK_URL");
			break;
		}
		if(Environment.equalsIgnoreCase("Staging")) 
		{
			URL=URL.replace("dev.", "stage.");
		}
		if(Environment.equalsIgnoreCase("LIVE"))
		{
			URL=URL.replace("dev.", "www.");
		}
		return URL;
	}
	

	public boolean checkDriverStatus(WebDriver driver) {
		boolean state = true;
		if (driver == null) {
			state = false;
			stepstatus = "Failed";
//			stepstatus= "Skipped";
		}
		return state;
	}
	public void highlightObjects(WebDriver driver, WebElement element) throws InterruptedException 
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red')", element);
		Thread.sleep(1000);
		js.executeScript("arguments[0].setAttribute('style', 'border: 0px solid red')", element);
	}
	public List<String> getExcelSheet(String SheetName, String Environment, String Region) throws IOException
	{
		ArrayList<String> list= new ArrayList();
		String Title = null,FirstName = null,LastName= null,MobileNum=null,Country= null,Address1= null,Address2= null,Address3= null,City= null,PostCode= null,State= null;
		String maincategory = null,subcategory = null, SearchProduct=null;
		int PhoneNumber,ZipCode;
		FileInputStream fi = new FileInputStream("testdata.xlsx");
		Workbook wb = new XSSFWorkbook(fi);		
		Sheet ws1 = wb.getSheet(SheetName); 
		int rowcount =  ws1.getLastRowNum();
		for(int i=1;i<=rowcount;i++)
			{
				Row r =  ws1.getRow(i);
				Cell c1 = r.getCell(0);
				String env=c1.getStringCellValue();
				if (env.equalsIgnoreCase(Environment) )
				  {
					Cell c2 = r.getCell(2);
					String regionVal=c2.getStringCellValue().toUpperCase();	
					if(regionVal.equals(Region))
					{
						switch (SheetName.toLowerCase()) 
						{
							case "address":
								{	
								   Title=r.getCell(3).getStringCellValue();
								   FirstName=r.getCell(4).getStringCellValue();
								   LastName=r.getCell(5).getStringCellValue();
								   PhoneNumber=(int)r.getCell(6).getNumericCellValue();
								   MobileNum= String.valueOf(PhoneNumber);
							   	   Country=r.getCell(7).getStringCellValue();
								   Address1=r.getCell(8).getStringCellValue();
								   Address2=r.getCell(9).getStringCellValue();
								   Address3=r.getCell(10).getStringCellValue();
								   City=r.getCell(11).getStringCellValue();
								   if(Region.equals("US") || Region.equals("ROW") )
								   {
									 ZipCode=(int)r.getCell(12).getNumericCellValue();
									 PostCode= String.valueOf(ZipCode);
									}
								   else	
									{
									 PostCode=r.getCell(12).getStringCellValue();
									}
								  if(Region.equals("US") || Region.equals("AU")) 
								  {
									 State= r.getCell(13).getStringCellValue();
								  }
								  list.add(Title);
									list.add(FirstName);
									list.add(LastName);
									list.add(MobileNum);
									list.add(Country);
									list.add(Address1);
									list.add(Address2);
									list.add(Address3);
									list.add(City);
									list.add(PostCode);
									if(Region.equals("US") || Region.equals("AU"))
									{
										list.add(State);	
									}
									 break;						
								}
							case "categories" :
							{
								maincategory=r.getCell(3).getStringCellValue();
								subcategory=r.getCell(4).getStringCellValue();
								list.add(maincategory);
								list.add(subcategory);
								break;		
							}
							case "searchdata" :
							{
								SearchProduct=r.getCell(3).getStringCellValue();
								list.add(SearchProduct);
								break;		
							}
						}
						break;	
					}
				}
			}
	

		fi.close();
		return list;
	}
}
