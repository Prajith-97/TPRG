package com.automation.mobile;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;

/**
 * @author AnishKarampanathuTho
 * @param <MobileButtonHelper>
 * @param <MobileTextHelper>
 * @param <MobileCheckBoxHelper>
 *
 */
public class Mobile_Test<MobileButtonHelper, MobileTextHelper, MobileCheckBoxHelper> {

	private static final org.testng.log4testng.Logger OLOG = org.testng.log4testng.Logger.getLogger(Mobile_Test.class);
	AndroidDriver driver;

	@BeforeTest
	@Parameters({"deviceName","emailId","apiKey","automationName", "duration", "newCommandTimeout", "launchTimeout"})
	public void setUpSuite(String deviceName, String emailId, String apiKey, String automationName, 
			String duration, String newCommandTimeout,String launchTimeout) {
		try {
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			capabilities.setCapability("pCloudy_Username", emailId);
			capabilities.setCapability("pCloudy_ApiKey", apiKey);
			capabilities.setCapability("pCloudy_DurationInMinutes", duration);
			capabilities.setCapability("newCommandTimeout", newCommandTimeout);
			capabilities.setCapability("launchTimeout", launchTimeout);
			capabilities.setCapability("platformVersion", "10.0.0");
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("pCloudy_DeviceManafacturer", deviceName);
			capabilities.setCapability("automationName", automationName);
			capabilities.setBrowserName("Chrome");
			
			capabilities.setCapability("pCloudy_EnableDeviceLogs", "true");
			
			driver = new AndroidDriver(new URL("https://device.pcloudy.com/appiumcloud/wd/hub"), capabilities);
			
			Thread.sleep(30000);
			
			driver.get("https://www.hobbs.com/"); 
			
			driver.manage().window().maximize();
			
    		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			  
			driver.findElement(By.xpath("//*[@id=\"countryGateway\"]/div/div/div/button")).click();
				
			driver.findElement(By.xpath("/html/body/div[2]/header/nav/div/div/div[2]/a/img")).click();
				
			driver.findElement(By.xpath("/html/body/div[2]/header/nav/div/div/div[5]/div/form/input[1]"))
						.sendKeys("Shoes");

			driver.findElement(By.xpath(
						"/html/body/div[2]/header/nav/div/div/div[5]/div/form/div[1]/div/div[2]/div[1]/div[1]/div/div/ul[2]/li/a"))
						.click();
			
		    driver.findElement(By.xpath(
				  "/html/body/div[2]/header/nav/div/div/div[4]/div/div[5]/div[1]/a/span[2]") ).click();
				 
			driver.findElement(By.xpath(
						  "/html/body/div[2]/div[3]/div[2]/div[1]/div[2]/a") ).click();
						  
			driver.findElement(By.xpath(
						  "/html/body/div[2]/header/nav/div/div/div[4]/div/div[3]/a/div/i") ).click();
						  
			driver.findElement(By.xpath(
						  "/html/body/div[5]/div/div/div[2]/button/span") ).click();
						  
		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
			OLOG.error(exceptionAsString);
			Reporter.log(exceptionAsString);
		}
	}

	@AfterMethod
	public void endTest() throws IOException {
		if(null!=driver) {
			driver.quit();
		}
	}

}
