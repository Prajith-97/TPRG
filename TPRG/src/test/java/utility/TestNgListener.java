package utility;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.IAlterSuiteListener;
import org.testng.TestNG;
import org.testng.annotations.AfterTest;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class TestNgListener implements IAlterSuiteListener {
	String chromeStatus ;
	String firefoxStatus;
	String edgeStatus;
	String androidStatus;
	String iOSStatus;
	
	
	Map<String, String> params = new HashMap<>();

	@Override
	public void alter(List<XmlSuite> suites)

	{

		
		
		/*
		 * try { JSONParser parser = new JSONParser(); Object obj = parser.parse( new
		 * FileReader(System.getProperty("user.dir") +
		 * "\\src\\test\\resources\\logindetails.json")); JSONObject jsonObject =
		 * (JSONObject) obj; // Browser = (String) jsonObject.get("Browser");
		 * chromeStatus = (String) jsonObject.get("Chrome"); firefoxStatus =
		 * (String)jsonObject.get("Firefox"); edgeStatus = (String)
		 * jsonObject.get("Edge"); System.out.println("chromeStatus::" + chromeStatus);
		 * } catch (Exception e) { e.printStackTrace(); }
		 * 
		 */

		try {
			chromeStatus = System.getProperty("chromeStatus");
			 firefoxStatus = System.getProperty("firefoxStatus");
			edgeStatus = System.getProperty("edgeStatus");
			androidStatus = System.getProperty("androidStatus");
			iOSStatus = System.getProperty("iOSStatus");
			System.out.println("chromeStatus::" + chromeStatus);
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		if (chromeStatus.equalsIgnoreCase("true")) {
			XmlSuite suite = suites.get(0);
			XmlTest test = new XmlTest(suite);
			test.setName("Chrome Browser Execution");
			test.addParameter("browser", "chrome");
			List<XmlClass> classes = new ArrayList<XmlClass>();
			classes.add(new XmlClass("runner.RunTestNGTest"));
			test.setXmlClasses(classes);
		}

		
		  if (firefoxStatus.equalsIgnoreCase("true")) 
		  { XmlSuite suite = suites.get(0);
		  XmlTest test = new XmlTest(suite);
		  test.setName("FireFox Browser Execution");
		  test.addParameter("browser", "firefox");
		  Map<String, String> params2 = new HashMap<>();
		  params2.put("browser", "firefox");
		  
		  suite.setParameters(params2);
		   List<XmlClass> classes = new ArrayList<XmlClass>(); 
		  classes.add(new XmlClass("runner.RunTestNGTest"));
		  test.setXmlClasses(classes); }
		  
		  if (edgeStatus.equalsIgnoreCase("true"))
		  { XmlSuite suite = suites.get(0);
		  XmlTest test = new XmlTest(suite); 
		  test.setName("Edge Browser Execution");
		  test.addParameter("browser", "edge");
		  List<XmlClass> classes = new ArrayList<XmlClass>(); 
		  classes.add(new XmlClass("runner.RunTestNGTest"));
		  test.setXmlClasses(classes);
		  }
		 if (androidStatus.equalsIgnoreCase("true"))
		 {
		 
		  XmlSuite suite = suites.get(0);
		  XmlTest test = new XmlTest(suite); 
		  test.setName("Android Execution");
		  test.addParameter("browser", "Android");
		  List<XmlClass> classes = new ArrayList<XmlClass>(); 
		  classes.add(new XmlClass("runner.RunTestNGTest"));
		  test.setXmlClasses(classes);
		  }
		  
			
			  if(iOSStatus.equalsIgnoreCase("true")) 
			  { XmlSuite suite = suites.get(0);
			  XmlTest test = new XmlTest(suite); 
			  test.setName("iOS Execution");
			  test.addParameter("browser", "iOS"); 
			  List<XmlClass> classes = new
			  ArrayList<XmlClass>();
classes.add(new XmlClass("runner.RunTestNGTest"));
			  test.setXmlClasses(classes); }
			 
		  
		  
		
		  
	}
}
	
	
	  
