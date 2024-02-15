package com.automation.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Web_Test<MobileTextHelper, MobileCheckBoxHelper, MobileButtonHelper> {

	private static final org.testng.log4testng.Logger OLOG = org.testng.log4testng.Logger.getLogger(Web_Test.class);

	WebDriver driver;
	MobileTextHelper mTextHelper;
	MobileButtonHelper mButtonHelper;
	MobileCheckBoxHelper mCheckBoxHelper;

	public void setUpSuite(String deviceName, String emailId, String apiKey, String appInfo, String duration,
			String newCommandTimeout, String launchTimeout) {
		try {
			WebDriverManager.chromedriver().setup();
			// DesiredCapabilities capabilities = new DesiredCapabilities();
			/*
			 * capabilities.setCapability("pCloudy_Username", emailId);
			 * capabilities.setCapability("pCloudy_ApiKey", apiKey);
			 * capabilities.setCapability("pCloudy_DurationInMinutes", 5);
			 * capabilities.setCapability("pCloudy_DeviceManafacturer", deviceName);
			 * capabilities.setBrowserName("Chrome");
			 * capabilities.setCapability("newCommandTimeout", 1699999999);
			 * capabilities.setCapability("launchTimeout", 1999999999);
			 * capabilities.setCapability("deviceName", deviceName);
			 * capabilities.setCapability("platformName", "Android");
			 * capabilities.setCapability("launchTimeout", 90000);
			 * capabilities.setCapability("pCloudy_DeviceFullName",
			 * "SAMSUNG_GalaxyM10_Android_10.0.0_a58e4");
			 */
			driver = new ChromeDriver();

			// driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

			driver.get("https://www.hobbs.com/");

			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			driver.manage().window().maximize();
			
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
					  
					  
				/*
				 * driver.findElement(By.xpath(
				 * "/html/body/div[2]/footer/div[2]/div/div[2]/div/div/div/a[1]/i") ).click();
				 * 
				 */
			  

		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			OLOG.error(exceptionAsString);
			Reporter.log(exceptionAsString);
		}
	}

	@org.testng.annotations.Test
	public void Test() throws Throwable {

		try {
			setUpSuite("Samsung", "Anjana.Prakash@g10x.com", "snscd7722yv3qynfrwqy5prd", "uiautomator2", "10", "6000",
					"90000");

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
		if (null != driver) {
			// driver.quit();
		}
	}

}
