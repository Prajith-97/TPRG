package utility;

import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import stepdefs.reportdefinitions;

public class WebDriverManagerAppium {

	public static AndroidDriver driver;
//    public static IOSDriver iosdriver;
	static String User_Name = null;
	static String Password = null;
	static String URL = null;
	static String Browser = null;
	static boolean Emulation = false;
	static boolean Headless = false;
	static String Device = null;
	static String Environment =System.getProperty("Environment");
	
	public static void setUpSuiteAndriod(String Region) throws Exception {
		try {
			String Obheadurl = null;
			WebDriverManagedef webdef = new WebDriverManagedef();
			URL = webdef.GetRegionURL(Region,Environment);
			Obheadurl = URL.substring(8);
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(
					new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\logindetails.json"));
			JSONObject jsonObject = (JSONObject) obj;
			User_Name = (String) jsonObject.get("User_Name");
			Password = (String) jsonObject.get("Password");
			if (WebDriverManagerAppium.driver != null) {
				WebDriverManagerAppium.driver.quit();

			}

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("pCloudy_Username", "itsupport@g10x.com");
			capabilities.setCapability("pCloudy_ApiKey", "tprqb2mvkp4bhj4z2nmpwndr");
			capabilities.setCapability("pCloudy_DurationInMinutes", 10);
			capabilities.setCapability("newCommandTimeout", 600);
			capabilities.setCapability("launchTimeout", 90000);
			capabilities.setCapability("pCloudy_DeviceManufacturer", "SAMSUNG");
			capabilities.setCapability("pCloudy_DeviceVersion", "11.0.0");
			capabilities.setCapability("platformVersion", "11.0.0");
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("automationName", "uiautomator2");
			capabilities.setBrowserName("Chrome");
			capabilities.setCapability("pCloudy_WildNet", "false");
			capabilities.setCapability("pCloudy_EnableVideo", "false");
			capabilities.setCapability("pCloudy_EnablePerformanceData", "true");
			capabilities.setCapability("pCloudy_EnableDeviceLogs", "true");
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setExperimentalOption("w3c", false);
			capabilities.merge(chromeOptions);
			
			AndroidDriver adriver = new AndroidDriver(new URL("https://us.pcloudy.com/appiumcloud/wd/hub"), capabilities);
			driver = adriver;
			Thread.sleep(10000);
			if(!Environment.equals("LIVE")) {
				driver.get("https://" + User_Name + ":" + Password + "@" + Obheadurl + "");
			}else{
				driver.get(URL);
			}
			if(Environment.equalsIgnoreCase("Staging"))
			{
				Thread.sleep(1000);
				driver.findElement(By.id("details-button")).click();
				Thread.sleep(1000);
				driver.findElement(By.id("proceed-link")).click();
			}
			Thread.sleep(20000);

		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			// OLOG.error(exceptionAsString);
			Reporter.log(exceptionAsString);
			reportdefinitions.flag = true;
			throw ex;
		}
	}

	public static void setUpSuiteIOS(String Region) {
		try {
			String Obheadurl = null;
			WebDriverManagedef webdef = new WebDriverManagedef();
			URL = webdef.GetRegionURL(Region,Environment);
			Obheadurl = URL.substring(8);

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("pCloudy_Username", "ranjini.balakrishnan@g10x.com");
			capabilities.setCapability("pCloudy_ApiKey", "9jwdrynd7h3f3ctbphns4m5w");
			capabilities.setCapability("pCloudy_DurationInMinutes", 10);
			capabilities.setCapability("newCommandTimeout", 600);
			capabilities.setCapability("launchTimeout", 90000);
			capabilities.setCapability("pCloudy_DeviceManufacturer", "APPLE");
			capabilities.setCapability("pCloudy_DeviceVersion", "14.0.1");
			capabilities.setCapability("platformVersion", "14.0.1");
			capabilities.setCapability("platformName", "ios");
			capabilities.setCapability("acceptAlerts", true);
			capabilities.setCapability("automationName", "XCUITest");
			capabilities.setBrowserName("Safari");
			capabilities.setCapability("pCloudy_WildNet", "false");
			capabilities.setCapability("pCloudy_EnableVideo", "true");
			capabilities.setCapability("pCloudy_EnablePerformanceData", "true");
			capabilities.setCapability("pCloudy_EnableDeviceLogs", "true");
			IOSDriver iosdriver = new IOSDriver(
					new URL("https://device.pcloudy.com/appiumcloud/wd/hub"), capabilities);
//			driver = iosdriver;
			Thread.sleep(30000);
			driver.get("a");
			Thread.sleep(3000);
//			driver.get("https://storefront:tfg2019@dev.hobbs.com/");
			driver.get("https://" + User_Name + ":" + Password + "@" + Obheadurl + "");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			// OLOG.error(exceptionAsString);
			Reporter.log(exceptionAsString);
		}
	}

}
