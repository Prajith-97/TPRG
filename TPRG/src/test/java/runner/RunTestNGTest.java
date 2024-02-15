package runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;
import reports.PostCalljson;
import reports.readjson;
import reports.writejson;
import stepdefs.reportdefinitions;
import utility.WebDriverManagedef;
import utility.WebDriverManagerAppium;


@CucumberOptions(plugin = { "pretty", "io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm",
		"json:cucumber-reports/Cucumber.json" },
		features = {
		""},		
		glue = "stepdefs", strict = true)



//"java:C:\\Current\\HobbsTFG\\TFGHobbs\\src\\test\\java\\utility\\ListenerPlugin.java"	
public class RunTestNGTest extends AbstractTestNGCucumberTests {
	public static String browser;
		Date date = new Date();
	public static String featurestarttime;
	public static String featureendtime;
	SimpleDateFormat formatter = new SimpleDateFormat("yyy-M-dd HH:mm:ss");
	reportdefinitions reportdetails = new reportdefinitions();
	writejson writereport = new writejson();
	String startDate= formatter.format(date);
	TestNGCucumberRunner testNGCucumberRunner;
	  @BeforeTest
	  
	  @Parameters({"browser"}) 
	  
	  public void beforetest(String browser) 
	  {
		  
		  Class<?> testClass = this.getClass();
		  try {

			  File file= new File(System.getProperty("user.dir") +"\\src\\test\\resources\\journeyconfig.json");
			  if(!file.exists()){
				  System.out.println("Journey config File doesn't exists");
				  return;
			  }

			  JSONParser parser = new JSONParser();
			  JSONObject jsonObject=null;
			try {
				jsonObject = (JSONObject) parser
						  .parse(new FileReader(System.getProperty("user.dir") +"\\src\\test\\resources\\journeyconfig.json"));
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			  List <String>journeyList=new ArrayList<>();
			  JSONArray jsonArray= (JSONArray) jsonObject.get("journeys");
			  for(int x=0;x<jsonArray.size();x++){
				  JSONObject jsonObjectSingle= (JSONObject) jsonArray.get(x);
				  journeyList.add("src/test/resources/features/"+jsonObjectSingle.get("name"));
			  }

			  String[] journeyNameArray = Arrays.copyOf(
					  journeyList.toArray(), journeyList.size(), String[].class);


			  changeCucumberAnnotation(testClass, "features", journeyNameArray);
		  } catch (NoSuchFieldException e) {
			  e.printStackTrace();
		  } catch (IllegalAccessException e) {
			  e.printStackTrace();
		  }  catch (FileNotFoundException e) {
			  e.printStackTrace();
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
		  testNGCucumberRunner = new TestNGCucumberRunner(testClass);
		  
	  reportdefinitions.terminationflag = true; 
	  if(browser!= "")
	  {
		  	  RunTestNGTest.browser= browser;
	  }
	 
	  System.out.println(browser +" printing browser name");
	  
	  }
	  
	  private static void changeCucumberAnnotation(Class<?> clazz, String key, Object newValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
			Annotation options = clazz.getAnnotation(CucumberOptions.class);                   //get the CucumberOptions annotation
			InvocationHandler proxyHandler = Proxy.getInvocationHandler(options);              //setup handler so we can update Annotation using reflection. Basically creates a proxy for the Cucumber Options class
			Field f = proxyHandler.getClass().getDeclaredField("memberValues");                //the annotaton key/values are stored in the memberValues field
			f.setAccessible(true);                                                             //suppress any access issues when looking at f
			Map<String, Object> memberValues = (Map<String, Object>) f.get(proxyHandler);      //get the key-value map for the proxy
			memberValues.remove(key);                                                          //renove the key entry...don't worry, we'll add it back
			memberValues.put(key,newValue);                                                    //add the new key-value pair. The annotation is now updated.
		}	
	  
	  
	@AfterTest
	public void aftertest() {
		System.out.println("After Test method");
		System.out.println("Executing aftertest Scenario");
		if (WebDriverManagedef.driver != null) {
			System.out.println(WebDriverManagedef.driver);
			System.out.println("Executing aftertest");
			WebDriverManagedef.driver.quit();
			WebDriverManagedef.driver = null ;
		}
	 else if(WebDriverManagerAppium.driver != null) {
			WebDriverManagerAppium.driver.quit();
			WebDriverManagerAppium.driver = null ;
		}
		System.out.println("After Test completed");
	}
	@BeforeSuite
	public void beforesuite() {
		featurestarttime = formatter.format(date);
		File filedetails = new File(System.getProperty("user.dir") + "\\src\\test\\java\\reports\\output.json");

		if (filedetails.delete()) {
			System.out.println("File deleted successfully");
		} else {
			System.out.println("Failed to  the file");
		}
		reportdetails.deleteexistingscreenshots();
		System.out.println("previous screenshot deleted successfully");

	}

	@AfterSuite
	public void aftersuite() throws Throwable {
		System.out.println("After Suite method");
		Date date = new Date();
		String EndDate= formatter.format(date);
		String timediff=timeDiff(startDate,EndDate);
		System.out.println(timediff);
		 Logger logger=Logger.getLogger("Hobbs");
	     PropertyConfigurator.configure(System.getProperty("user.dir") + "\\src\\test\\resources\\log4j.properties");
	     logger.info("Execution Completed");
	     String sje_id =System.getProperty("sje_id");
	     logger.info("Execution ID generated : " +sje_id);
	     logger.info("Execution Start Time "+startDate);
	     logger.info("Execution End Time "+EndDate);
	     logger.info("Total Execution Time "+timediff);
		 readjson obj = new readjson();
		 logger.info("Post call Json api Calling :");
		PostCalljson postCalljson=new PostCalljson(); 
		try { 
			date = new Date();
			String Post_Starttime= formatter.format(date);
			logger.info("Post Call Start Time "+Post_Starttime);
			postCalljson.PostScreenshotjson(); 
			postCalljson.postCall(); 
			date = new Date();
			String Post_Endtime= formatter.format(date);
			logger.info("Post Call End Time "+Post_Endtime);
			System.out.println("PostCalljson api called successsfully"); 
			logger.info("PostCalljson api called successsfully and output json file created");
		 }
		catch(Exception e) 
		 {
			 e.printStackTrace();
			 System.out.println("PostCalljson api call failed");
			 logger.info("PostCalljson api call failed");
		 }

		 }
		
	public String timeDiff (String Startdate, String Enddate)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyy-M-dd HH:mm:ss");

    Date d1 = null;
    Date d2 = null;
    try {
        d1 = format.parse(Startdate);
        d2 = format.parse(Enddate);
    } 
    catch (ParseException e) {
        e.printStackTrace();
    }

    // Get msec from each, and subtract.
    long diff = d2.getTime() - d1.getTime();

    long days = TimeUnit.MILLISECONDS.toDays(diff);
    long remainingHoursInMillis = diff - TimeUnit.DAYS.toMillis(days);
    long hours = TimeUnit.MILLISECONDS.toHours(remainingHoursInMillis);
    long remainingMinutesInMillis = remainingHoursInMillis - TimeUnit.HOURS.toMillis(hours);
    long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMinutesInMillis);
    long remainingSecondsInMillis = remainingMinutesInMillis - TimeUnit.MINUTES.toMillis(minutes);
    long seconds = TimeUnit.MILLISECONDS.toSeconds(remainingSecondsInMillis);
    String timediff="Days: " + days + ", hours: " + hours + ", minutes: " + minutes + ", seconds: " + seconds;
     return timediff;         

	}
}