package stepdefs;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assume;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.PickleStepTestStep;
import reports.writejson;
import runner.RunTestNGTest;
import utility.WebDriverManagedef;
import utility.WebDriverManagerAppium;

import java.io.*;

@SuppressWarnings("serial")
public class reportdefinitions extends Exception {
	static String scenarioSteps;
	ArrayList<String> testcase = new ArrayList<String>();
	static ArrayList<String> getScenarioSteps = new ArrayList<String>();
	static ArrayList<String> getScenarioStepsrefer = new ArrayList<String>();

	public static String Scenarioname;
	Date date = new Date();
	Long currentTime = date.getTime();
	WebDriverManagedef def = new WebDriverManagedef();
	long initialstarttime;
	public static String featurefilename;
	public static String featuretagname;
	public static String description;
	public String platform;
	writejson writereport = new writejson();
	public long duration;
	public String ScenarioStatus;
	public String ScenarioTags;
	static JSONObject featuredetails;
	static JSONObject jsonfeaturedetails;
	public static String strStatus = "PASSED";
	// static JSONArray features;
	static JSONObject stepdetails;
	static JSONArray steps;
	ArrayList<String> scenarionamelist = new ArrayList<String>();
	public static WebDriverManagedef webdef = new WebDriverManagedef();
	DyasHomeStepDefinitions homepage = new DyasHomeStepDefinitions();
	long initialtime;
	public static String initialfeaturestarttime;
	public static String finalfeatureendtime;
	public static boolean terminationflag = false;
	public static boolean flag = false;
	public static int count = 1;
	public static String tagname;
	public static boolean browserquit = false;

	@SuppressWarnings("unchecked")
	@Before
	public void appendbeforerjson(Scenario scenario) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException, FileNotFoundException, IOException, ParseException {

		reportdefinitions.flag=false;
		if (flag && count > 1) {
			return;
		}

		webdef.error = "";
		strStatus = "PASSED";

		terminationflag = true;

		RunTestNGTest objruntest = new RunTestNGTest();

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyy-M-dd HH:mm:ss");
		initialtime = System.currentTimeMillis();
		initialfeaturestarttime = formatter.format(date);
		Boolean newfeaturestatus = false;
		Boolean newplatformstatus = false;
		JSONParser parser = new JSONParser();
		Object obj = parser
				.parse(new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\logindetails.json"));
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray initialarray = new JSONArray();
		// platform = (String) jsonObject.get("Browser");
		platform = RunTestNGTest.browser;

		RunTestNGTest testng = new RunTestNGTest();
		scenarios(scenario);
		featurefilename = getFeatureFileNameFromScenarioId(scenario);

		featuretagname = tagname(scenario);
		System.out.println(tagname + "feature name..........");

		description = "Verifying empty basket";
		Scenarioname = scenario.getName();
		System.out.println(Scenarioname + "before scenario execution :- Scenarioname");
		initialstarttime = currentTime;
		String scenarionamefromJson = null;
		if ((platform.equalsIgnoreCase("chrome") || platform.equalsIgnoreCase("edge")
				|| platform.equalsIgnoreCase("firefox")) && tagname.contains("DESKTOP")
				|| (platform.equalsIgnoreCase("Android") && tagname.contains("MOBILE")))

		{
			Assume.assumeTrue(true);
		} else {
			Assume.assumeTrue(false);
		}
		String fileforjson = System.getProperty("user.dir") + "\\src\\test\\java\\reports\\output.json";
		if (Paths.get(fileforjson).toFile().isFile()) {
			// ...do somethinh
			System.out.println("file available in this location");

			JSONParser parser1 = new JSONParser();
			JSONArray a = (JSONArray) parser1
					.parse(new FileReader(System.getProperty("user.dir") + "\\src\\test\\java\\reports\\output.json"));
			// System.out.println(a);

			JSONObject jsonObject1 = (JSONObject) a.get(0);

			for (Object array : a) {

				JSONObject firstarray = (JSONObject) array;
				String featureFileNamefromjson = (String) firstarray.get("name");
				System.out.println(featureFileNamefromjson + "featurefilename from JSON file");
				System.out.println(featurefilename + "featurefilename execution");
				String description = (String) firstarray.get("description");
				if (featureFileNamefromjson.equals(featurefilename)) {
					newfeaturestatus = true;
					System.out.println("Name is equal");
					JSONArray features = (JSONArray) firstarray.get("features");
					for (Object array1 : features) {
						JSONObject secondarray = (JSONObject) array1;
						JSONArray feature = (JSONArray) secondarray.get("feature");
						for (Object array2 : feature) {
							JSONObject thirdarray = (JSONObject) array2;
							String platformjson = (String) thirdarray.get("platform");
							System.out.println(platform);
							JSONArray scenarios = (JSONArray) thirdarray.get("scenarios");
							if (platform.equals(platformjson)) {
								newplatformstatus = true;
								for (Object array3 : scenarios) {
									if (SkipFlag.skipFlag) {
										return;
									}
									JSONObject foutharray = (JSONObject) array3;

									JSONObject scenario1 = (JSONObject) foutharray.get("scenario");

									String scenarioname = (String) scenario1.get("name");

									String scenarionamefromJsontest = (String) scenario1.get("name");

									if (!Scenarioname.equals(scenarionamefromJsontest)) {
										JSONObject scenariodetails = new JSONObject();
										scenariodetails.put("name", Scenarioname);
										JSONArray steps = new JSONArray();
										scenariodetails.put("steps", steps);
										JSONObject scenariolisting = new JSONObject();
										scenariolisting.put("scenario", scenariodetails);
										scenarios.add(scenariolisting);
										ObjectMapper mapper = new ObjectMapper();
										// System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(a));
										try {
											FileWriter file = new FileWriter(System.getProperty("user.dir")
													+ "\\src\\test\\java\\reports\\output.json");
											file.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(a));
											file.close();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										break;
									}

								}

							}
						}

					}

				}
			}
			boolean flag = false;
			if (!newfeaturestatus) {
				flag = true;
				System.out.println("feature file name not found");
				JSONObject featuredetails = new JSONObject();
				featuredetails.put("name", featurefilename);

				JSONArray features1 = new JSONArray();
				featuredetails.put("features", features1);
				// feature lists
				JSONObject jsonfeaturedetails = new JSONObject();
				jsonfeaturedetails.put("starttime", initialfeaturestarttime);
				jsonfeaturedetails.put("description", description);
				jsonfeaturedetails.put("name", featurefilename);
				jsonfeaturedetails.put("platform", platform);
				jsonfeaturedetails.put("FeatureStatus", strStatus);
				JSONArray scenarios = new JSONArray();
				jsonfeaturedetails.put("scenarios", scenarios);
				JSONObject scenariodetails = new JSONObject();
				scenariodetails.put("name", Scenarioname);
				JSONArray steps = new JSONArray();
				scenariodetails.put("steps", steps);
				if (!Scenarioname.equals(scenarionamefromJson)) {
					JSONObject scenariolisting = new JSONObject();
					scenariolisting.put("scenario", scenariodetails);
					scenarios.add(scenariolisting);
					JSONArray feature = new JSONArray();
					feature.add(jsonfeaturedetails);
					JSONObject featuelisting = new JSONObject();
					featuelisting.put("feature", feature);
					features1.add(featuelisting);
					a.add(featuredetails);
					ObjectMapper mapper = new ObjectMapper();
					// System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(a));
					try {
						FileWriter file = new FileWriter(
								System.getProperty("user.dir") + "\\src\\test\\java\\reports\\output.json");
						file.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(a));
						file.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if (!newplatformstatus && !flag) {
				System.out.println("platform not found");
				JSONParser parser2 = new JSONParser();
				JSONArray array1 = (JSONArray) parser2.parse(
						new FileReader(System.getProperty("user.dir") + "\\src\\test\\java\\reports\\output.json"));
				// System.out.println(a);
				JSONObject jsonObjectplat = (JSONObject) array1.get(0);
				for (Object array : array1) {
					JSONObject firstarray = (JSONObject) array;

					String featureFileNamefromjson = (String) firstarray.get("name");
					System.out.println(featureFileNamefromjson + "featurefilename from JSON file");
					System.out.println(featurefilename + "featurefilename execution");
					// String description = (String) firstarray.get("description");
					if (featureFileNamefromjson.equals(featurefilename)) {
						JSONArray features = (JSONArray) firstarray.get("features");
						JSONObject featuredetails = new JSONObject();

						featuredetails.put("name", featurefilename);
						// JSONArray features1 = new JSONArray();
						// featuredetails.put("features", features1);
						// feature lists
						JSONObject jsonfeaturedetails = new JSONObject();
						jsonfeaturedetails.put("starttime", initialfeaturestarttime);
						jsonfeaturedetails.put("description", description);
						jsonfeaturedetails.put("name", featurefilename);
						jsonfeaturedetails.put("platform", platform);
						jsonfeaturedetails.put("FeatureStatus", strStatus);
						JSONArray scenarios = new JSONArray();
						jsonfeaturedetails.put("scenarios", scenarios);
						JSONObject scenariodetails = new JSONObject();
						scenariodetails.put("name", Scenarioname);
						JSONArray steps = new JSONArray();
						scenariodetails.put("steps", steps);
						if (!Scenarioname.equals(scenarionamefromJson)) {
							JSONObject scenariolisting = new JSONObject();
							scenariolisting.put("scenario", scenariodetails);
							scenarios.add(scenariolisting);
							JSONArray feature = new JSONArray();
							feature.add(jsonfeaturedetails);
							JSONObject featuelisting = new JSONObject();
							featuelisting.put("feature", feature);
							features.add(featuelisting);
							// a1.add(featuredetails);
							ObjectMapper mapper = new ObjectMapper();
							// System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(a));
							try {
								FileWriter file = new FileWriter(
										System.getProperty("user.dir") + "\\src\\test\\java\\reports\\output.json");
								file.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(array1));
								file.close();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}
				}
			}

		} else if (!Paths.get(fileforjson).toFile().isFile()) {
			JSONArray features12;
			JSONObject jsonObject3 = new JSONObject();
			JSONObject featuredetails = new JSONObject();
			featuredetails.put("name", featurefilename);

			features12 = new JSONArray();
			featuredetails.put("features", features12);
			// feature lists
			JSONObject jsonfeaturedetails = new JSONObject();
			jsonfeaturedetails.put("starttime", initialfeaturestarttime);
			jsonfeaturedetails.put("platform", platform);
			featuredetails.put("name", featurefilename);
			jsonfeaturedetails.put("name", featurefilename);
			jsonfeaturedetails.put("description", description);
			jsonfeaturedetails.put("FeatureStatus", strStatus);
			JSONArray scenarios = new JSONArray();
			jsonfeaturedetails.put("scenarios", scenarios);
			JSONObject scenariodetails = new JSONObject();
			scenariodetails.put("name", Scenarioname);
			JSONArray steps = new JSONArray();
			scenariodetails.put("steps", steps);
			if (!Scenarioname.equals(scenarionamefromJson)) {
				JSONObject scenariolisting = new JSONObject();
				scenariolisting.put("scenario", scenariodetails);
				scenarios.add(scenariolisting);
				JSONArray feature = new JSONArray();
				feature.add(jsonfeaturedetails);
				JSONObject featuelisting = new JSONObject();
				featuelisting.put("feature", feature);
				features12.add(featuelisting);
				initialarray.add(jsonObject3);
				jsonObject3.putAll(featuredetails);
				ObjectMapper mapper = new ObjectMapper();
				// System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(initialarray));
				try {
					FileWriter file = new FileWriter(
							System.getProperty("user.dir") + "\\src\\test\\java\\reports\\output.json");
					file.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(initialarray));
					file.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@After
	public void appendafterjson(Scenario scenario) throws Exception {

		/*
		 * if(!terminationflag) { return; }
		 * 
		 * 
		 * /* if(scenario.isFailed()) { WebDriverManagedef.driver.quit();}
		 */
		reportdefinitions.flag=false;
		if (flag && count > 1) {
			return;
		} else {
			count++;
		}

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyy-M-dd HH:mm:ss");
		finalfeatureendtime = formatter.format(date);
		long currenttime = System.currentTimeMillis();
		long scenarioduration = currenttime - initialtime;
		Collection<String> scenarioTagName = scenario.getSourceTagNames();
		String scenarioStatus = scenario.getStatus().toString();
		String featurename = "";
		String severity = "";
		JSONParser parser = new JSONParser();
		JSONObject feat = null;
		JSONArray a = (JSONArray) parser
				.parse(new FileReader(System.getProperty("user.dir") + "\\src\\test\\java\\reports\\output.json"));
		// System.out.println(a);
		JSONObject jsonObject1;
		jsonObject1 = (JSONObject) a.get(0);

		for (Object array : a) {
			JSONObject firstarray = (JSONObject) array;
			String featurefilenamejson = (String) firstarray.get("name");
			String description = (String) firstarray.get("description");
			JSONArray features = (JSONArray) firstarray.get("features");
			if (featurefilenamejson.equals(featurefilename)) {
				for (Object array1 : features) {
					JSONObject secondarray = (JSONObject) array1;
					JSONArray feature = (JSONArray) secondarray.get("feature");
					for (Object array2 : feature) {
						JSONObject thirdarray = (JSONObject) array2;
						String platformfromjson = (String) thirdarray.get("platform");
						JSONArray scenarios = (JSONArray) thirdarray.get("scenarios");
						thirdarray.put("endtime", finalfeatureendtime);
						if (platformfromjson.equals(platform)) {

							for (Object array3 : scenarios) {

								JSONObject foutharray = (JSONObject) array3;
								JSONObject scenario1 = (JSONObject) foutharray.get("scenario");
								String scenarioname = (String) scenario1.get("name");
								JSONArray steps = (JSONArray) scenario1.get("steps");
								if (scenarioname.equals(scenario.getName())) {
									JSONObject scenariodetails = new JSONObject();
									// scenariodetails.put("duration", millisToShortDHMS(scenarioduration));
									scenariodetails.put("duration", (scenarioduration));
									scenariodetails.put("tags", scenarioTagName);
									if (!strStatus.equalsIgnoreCase("Failed")
											&& !scenarioStatus.equalsIgnoreCase("PASSED")) {
										System.out.println("strStatus" + strStatus);
										strStatus = "FAILED";
									} // else {
										// strStatus = "PASSED";
										// }

									scenariodetails.put("status", scenarioStatus);
									scenariodetails.put("severity", severity);
									scenario1.putAll(scenariodetails);
									thirdarray.put("FeatureStatus", reportdefinitions.strStatus);
									ObjectMapper mapper = new ObjectMapper();
									// System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(a));
									try {
										FileWriter file = new FileWriter(System.getProperty("user.dir")
												+ "\\src\\test\\java\\reports\\output.json");
										file.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(a));
										file.flush();
										file.close();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}
							}
						}
					}
				}
			}
		}
		if (scenario.isFailed()) {

			if (WebDriverManagedef.driver != null) {
				System.out.println(WebDriverManagedef.driver);
				System.out.println("Executing aftertest");
				WebDriverManagedef.driver.quit();
			} else if (WebDriverManagerAppium.driver != null) {
				WebDriverManagerAppium.driver.quit();
			}
			Thread.currentThread().stop();
			System.out.println("Execution is stopped");

		}

	}

	public static String tagname(Scenario scenario) {

		Collection<String> tag = scenario.getSourceTagNames();
		ArrayList<String> tagarray = new ArrayList<>(tag);
		tagname = tagarray.get(0);
		return tagname;

	}

	@SuppressWarnings("deprecation")
	public void endOfTest(Scenario scenario) {
		if (scenario.getStatus() != null && scenario.isFailed()) {
			String filename = scenario.getName().replaceAll("\\s+", "_");
			final String featureError = scenario.getId().replaceAll("\\s+", "_").replaceAll(":", "_").split("\\.")[1];
			filename = filename + "_" + featureError;
			scenario.embed(filename.getBytes(StandardCharsets.UTF_8), "image/png", filename);
			System.out.println(filename + "filename with feature error");
		}
	}

	public static void scenarios(io.cucumber.java.Scenario scenario)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Map<String, String> scenarioStep = new HashMap<String, String>();
		Field f = scenario.getClass().getDeclaredField("delegate");
		f.setAccessible(true);
		io.cucumber.core.backend.TestCaseState sc = (io.cucumber.core.backend.TestCaseState) f.get(scenario);
		Field f1 = sc.getClass().getDeclaredField("testCase");
		f1.setAccessible(true);
		io.cucumber.plugin.event.TestCase testCase = (io.cucumber.plugin.event.TestCase) f1.get(sc);

		List<PickleStepTestStep> testSteps = testCase.getTestSteps().stream()
				.filter(x -> x instanceof PickleStepTestStep).map(x -> (PickleStepTestStep) x)
				.collect(Collectors.toList());

		for (PickleStepTestStep ts : testSteps) {
			String step = ts.getStep().getKeyWord() + ts.getStep().getText();
			getScenarioSteps.add(step);
			getScenarioStepsrefer.add(step);
		}
	}

	public class MyTestResultListener extends TestListenerAdapter {

		@Override
		public void onTestFailure(ITestResult result) {
			// do what you want to do
		}

		@Override
		public void onTestSuccess(ITestResult result) {
			// do what you want to do
			System.out.println("Step start time:" + result.getStartMillis());
			System.out.println("Step end time:" + result.getStartMillis());
			System.out.println("Step name:" + result.getName());
			System.out.println("Step getTestName():" + result.getTestName());
			System.out.println("getTestClass().getName:" + result.getTestClass().getName());
			System.out.println("getMethod().getMethodName()" + result.getMethod().getMethodName());
			System.out.println("Step status:" + "passed");
			// Do something here
			System.out.println("passed **********");
		}

		@Override
		public void onTestSkipped(ITestResult result) {
			// do what you want to do
		}
	}

	public static String getScenarioName(Scenario scenario) {
		String scenarioName = scenario.getName();
		return scenarioName;
	}

	public static Collection<String> getScenarioTagName(Scenario scenario) {
		Collection<String> scenarioTagName = scenario.getSourceTagNames();
		return scenarioTagName;
	}

	public static String getScenarioStatus(Scenario scenario) {
		String scenarioStatus = scenario.getStatus().toString();
		scenarioStatus = scenarioStatus;
		return scenarioStatus;
	}

	public String getFeatureFileNameFromScenarioId(Scenario scenario) {
		Object[] paramNames = Reporter.getCurrentTestResult().getParameters();
		String featureName = paramNames[1].toString().replaceAll("^\"+|\"+$", "");
		System.out.println(paramNames + "feature parameters");
		return featureName;

	}

	private int currentStepDefIndex = 0;
	private String refstepname;

	@BeforeStep
	public String doSomethingBeforeStep(Scenario scenario) throws Exception {

		Map<String, String> scenarioStep = new HashMap<String, String>();
		Field f = scenario.getClass().getDeclaredField("delegate");
		f.setAccessible(true);
		io.cucumber.core.backend.TestCaseState sc = (io.cucumber.core.backend.TestCaseState) f.get(scenario);
		Field f1 = sc.getClass().getDeclaredField("testCase");
		f1.setAccessible(true);
		io.cucumber.plugin.event.TestCase testCase = (io.cucumber.plugin.event.TestCase) f1.get(sc);

		List<PickleStepTestStep> testSteps = testCase.getTestSteps().stream()
				.filter(x -> x instanceof PickleStepTestStep).map(x -> (PickleStepTestStep) x)
				.collect(Collectors.toList());

		// This object now holds the information about the current step definition
		// If you are using pico container
		// just store it somewhere in your world state object
		// and to make it available in your step definitions.
		PickleStepTestStep currentStepDef = testSteps.get(currentStepDefIndex);
		String stepname = currentStepDef.getStep().getKeyWord() + currentStepDef.getStep().getText();
		refstepname = stepname;
		return stepname;
	}

	@SuppressWarnings({ "unchecked", "static-access", "deprecation" })
	@AfterStep
	public void doSomethingAfterStep(Scenario scenario) throws Exception {

		String screenshot = "";

		/* if (!terminationflag) { return; } */

		Date date = new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

		System.out.println("after step printing status" + webdef.stepstatus);
		System.out.println("after step printing errormessgae" + webdef.error);
		//////////////////////////////////////////////////////////////////////

		String stepname = doSomethingBeforeStep(scenario);
		TakesScreenshot scrShot = ((TakesScreenshot) homepage.driver);
		if (scrShot != null) {
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			File DestFile = new File(System.getProperty("user.dir") + "\\src\\test\\java\\reports\\screenshots\\"
					+ formatter.format(ts) + ".png");
			FileUtils.copyFile(SrcFile, DestFile);
			screenshot = DestFile.getAbsolutePath();
			System.out.println("screenshot path" + screenshot);
		}
		int size = getScenarioSteps.size();
		JSONParser parser = new JSONParser();
		JSONArray a = (JSONArray) parser
				.parse(new FileReader(System.getProperty("user.dir") + "\\src\\test\\java\\reports\\output.json"));
		// System.out.println(a);
		JSONObject jsonObject1 = (JSONObject) a.get(0);

		for (Object array : a) {
			JSONObject firstarray = (JSONObject) array;
			String featurefilenamejson = (String) firstarray.get("name");
			String description = (String) firstarray.get("description");
			JSONArray features = (JSONArray) firstarray.get("features");
			if (featurefilenamejson.equals(featurefilename)) {

				for (Object array1 : features) {
					JSONObject secondarray = (JSONObject) array1;
					JSONArray feature = (JSONArray) secondarray.get("feature");
					for (Object array2 : feature) {
						JSONObject thirdarray = (JSONObject) array2;
						String platformfromjson = (String) thirdarray.get("platform");
						System.out.println(platform + "from execution");
						System.out.println(platformfromjson + "platformfromjson from json");
						JSONArray scenarios = (JSONArray) thirdarray.get("scenarios");
						if (platformfromjson.equals(platform)) {
							for (Object array3 : scenarios) {
								JSONObject foutharray = (JSONObject) array3;
								JSONObject scenario1 = (JSONObject) foutharray.get("scenario");
								String scenarioname = (String) scenario1.get("name");
								System.out.println(scenarioname + "scenarioname from json");
								System.out.println(scenario.getName() + "scenarioname from execution");
								JSONArray steps = (JSONArray) scenario1.get("steps");
								if (scenarioname.equals(scenario.getName())) {
									JSONObject stepdetails = new JSONObject();
									stepdetails.put("name", stepname);
									stepdetails.put("status", webdef.stepstatus);

									stepdetails.put("screenshot", screenshot);
									stepdetails.put("errorMsg", webdef.error);
									JSONObject steplisting = new JSONObject();
									steplisting.put("step", stepdetails);
									System.out.println(stepdetails);
									steps.add(steplisting);
									ObjectMapper mapper = new ObjectMapper();
									// System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(a));
									try {
										FileWriter file = new FileWriter(System.getProperty("user.dir")
												+ "\\src\\test\\java\\reports\\output.json");
										file.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(a));
										file.flush();
										file.close();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									if (webdef.stepstatus.equalsIgnoreCase("failed")) {

										// terminationflag = false;

										SkipFlag.skipFlag = true;
									}

									else if (webdef.stepstatus.equalsIgnoreCase("passed")) {

										SkipFlag.skipFlag = false;

									}

								}

							}

						}
					}
				}
			}
		}

		currentStepDefIndex += 1;
		if (scenario.isFailed()) {

			if (WebDriverManagedef.driver != null) {
				System.out.println(WebDriverManagedef.driver);
				System.out.println("Executing aftertest");

				WebDriverManagedef.driver.quit();

			} else if (WebDriverManagerAppium.driver != null) {
				WebDriverManagerAppium.driver.quit();
				System.out.println("Appium Driver quit");
			}

			Thread.currentThread().stop();
			System.out.println("Execution not stopping");

			System.out.println("Execution stopped");
		}

		browserquit = true;

	}

	public void deleteexistingscreenshots() {

		String path = System.getProperty("user.dir") + "\\src\\test\\java\\reports\\screenshots";
		File file = new File(path);
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isFile() && f.exists()) {
				f.delete();
				System.out.println("successfully deleted");
			} else {
				System.out.println("cant delete a file due to open or error");

			}

		}
	}

	public static String millisToShortDHMS(long duration) {
		String res = ""; // java.util.concurrent.TimeUnit;
		long days = TimeUnit.MILLISECONDS.toDays(duration);
		long hours = TimeUnit.MILLISECONDS.toHours(duration)
				- TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
		long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
				- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
		long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
				- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
		long millis = TimeUnit.MILLISECONDS.toMillis(duration)
				- TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(duration));

		if (days == 0)
			res = String.format("%02d:%02d:%02d.%04d", hours, minutes, seconds, millis);
		else
			res = String.format("%dd %02d:%02d:%02d.%04d", days, hours, minutes, seconds, millis);
		return res;

	}

}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
