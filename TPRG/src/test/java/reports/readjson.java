package reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.json.Json;

import com.google.common.io.Files;
import com.google.gson.stream.JsonReader;

import stepdefs.reportdefinitions;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class readjson {
	static JSONObject firstarray;
	static HashMap<String, String> featurelist = new HashMap<String, String>();
	static String featurename;
	static String featuredescription;
	static String featurestatus;
	static Set<String> passed = new HashSet<String>();
	static Set<String> failed = new HashSet<String>();
	static Set<String> skipped = new HashSet<String>();
	static ArrayList<String> featurepassed = new ArrayList<String>();
	static ArrayList<String> featurefailed = new ArrayList<String>();
	static ArrayList<String> featureskipped = new ArrayList<String>();
	static StringBuilder NavbarList = new StringBuilder();
	static StringBuilder DashboardJourneyList = new StringBuilder();
//	static Hashtable<String, Hashtable<String, String>> BarChartData = new Hashtable<String, Hashtable<String, String>>();
//	static Hashtable<String, String> Browserexecutiontime = new Hashtable<String, String>();

	public readjson() {
		// TODO Auto-generated method stub
		// Creating a JSONParser object

		try {

			JSONParser parser = new JSONParser();
			JSONArray a = (JSONArray) parser
					.parse(new FileReader(System.getProperty("user.dir") +"\\src\\test\\java\\reports\\output.json"));

			// System.out.println(a.get(0));
			StringBuilder FeatureList = new StringBuilder();
			int SerialNum = 0;

			Hashtable<String, Hashtable<String, String>> BarChartData = new Hashtable<String, Hashtable<String, String>>();
			Set<String> BrowserJourneyList = new HashSet<String>();
			for (Object array : a) {
				Hashtable<String, String> Browserexecutiontime = new Hashtable<String, String>();

				firstarray = (JSONObject) array;
				String name = (String) firstarray.get("name");
				String description = (String) firstarray.get("description");

				featurelist.put(name, name);
				featurelist.put(featuredescription, description);
				
				featurelist.put("FeatureStatus", reportdefinitions.strStatus);

				featurename = featurelist.get(name);
				featuredescription = featurelist.get(featuredescription);

				StringBuilder FeatureResult = new StringBuilder();
				StringBuilder ScenarioResult = new StringBuilder();
				String ChromeStatus = "";
				String EdgeStatus = "";
				String FirefoxStatus = "";
				String AndroidStatus = "";
				String IOSStatus = "";
				JSONArray features = (JSONArray) firstarray.get("features");
				for (Object array1 : features) {
					JSONObject secondarray = (JSONObject) array1;

					JSONArray feature = (JSONArray) secondarray.get("feature");

					StringBuilder ScenarioList = new StringBuilder();

					for (Object array2 : feature) {
						JSONObject thirdarray = (JSONObject) array2;
						String starttime = (String) thirdarray.get("starttime");
						String endtime = (String) thirdarray.get("endtime");
						description = (String) thirdarray.get("description");
						 name = (String) thirdarray.get("name");
						featurename = name;
						String platform = (String) thirdarray.get("platform");
						featurelist.put(starttime, starttime);
						featurelist.put(endtime, endtime);
						featurelist.put(platform, platform);

						System.out.println(featurelist.get(starttime) + ":-  feature start time");
						System.out.println(featurelist.get(endtime) + ":-  feature end time");
						System.out.println(featurelist.get(platform) + ":-  platform");
						
						String[] startgh= starttime.split(" ");						
						String[] startty= startgh[1].split(":");
						String[] endgh= endtime.split(" ");						
						String[] endty= endgh[1].split(":");
						 long starthour = Long.valueOf(startty[0]);
						 long startminutes = Long.valueOf(startty[1]);
						 long startseconds = Long.valueOf(startty[2]);
						 long endhour = Long.valueOf(endty[0]);
						 long endminutes = Long.valueOf(endty[1]);
						 long endseconds = Long.valueOf(endty[2]);
						 long initialtime= (starthour*60*60*1000) + (startminutes* 60 * 1000) + (startseconds *1000);
						 long endfeaturetime= (endhour*60*60*1000) + (endminutes* 60 * 1000) + (endseconds *1000);
						 long duration= endfeaturetime-initialtime;
						 long minutes= (duration/1000) / 60;
						String ExecutionTime = Long.toString(minutes);
						
						System.out.println("ExecutionTime noted from json"+ ExecutionTime);
						if (platform.toUpperCase().equals("CHROME") || platform.toUpperCase().equals("EDGE")
								|| platform.toUpperCase().equals("FIREFOX")) {
							BrowserJourneyList.add(featurename.toUpperCase());
							Browserexecutiontime.put(platform.toUpperCase(), ExecutionTime);
						}

						JSONArray scenarios = (JSONArray) thirdarray.get("scenarios");

						int scenarioserialnum = 0;

						for (Object array3 : scenarios) {
							JSONObject foutharray = (JSONObject) array3;
							JSONObject scenario = (JSONObject) foutharray.get("scenario");
							String scenarioname = (String) scenario.get("name");
							Long scenarioduration = (Long) scenario.get("duration");
							String scenariostatus = (String) scenario.get("status");
							String severity = (String) scenario.get("severity");
							String scenariotags = (String) scenario.get("tags").toString();
							if (scenariostatus.equalsIgnoreCase("passed")) {
								passed.add(scenarioname);
							}
							if (scenariostatus.equalsIgnoreCase("failed")) {
								failed.add(scenarioname);
							}
							if (scenariostatus.equalsIgnoreCase("skipped")) {
								skipped.add(scenarioname);
							}

							featurelist.put(scenarioname, scenarioname);
							featurelist.put(scenariostatus, scenariostatus);

							scenarioserialnum++;

							StringBuilder ScenarioResultSteps = new StringBuilder();
							JSONArray steps = (JSONArray) scenario.get("steps");

							for (Object array4 : steps) {
								JSONObject fiftharray = (JSONObject) array4;
								JSONObject step = (JSONObject) fiftharray.get("step");
								String stepname = (String) step.get("name");
								Long stepduration = (Long) step.get("duration");
								String stepstatus = (String) step.get("status");
								String screenshotlink = (String) step.get("screenshot");
								String errormsg = (String) step.get("errorMsg");

								ScenarioResultSteps.append(
										AddDetailedScenarioSteps(stepname, stepstatus, screenshotlink, errormsg));

							}
							String ScenarioID = scenarioname.replace(" ", "");
							
							System.out.println(ScenarioResult);

							ScenarioResult.append(AddScenarioSteps(featurename, platform, ScenarioID, scenarioname,
									scenariostatus, severity, description, scenariotags,
									String.valueOf(scenarioduration), String.valueOf(ScenarioResultSteps)));

							ScenarioList.append(AddScenario(featurename, platform, scenarioserialnum, ScenarioID,
									scenarioname, scenariostatus, String.valueOf(scenarioduration)));
						}

						if (failed.size() > 0) {
							featurestatus = "Failed";
							featurefailed.add(name);
						} else if (skipped.size() > 0) {
							featurestatus = "Skipped";
							featureskipped.add(name);
						} else {
							featurestatus = "Passed";
							featurepassed.add(name);
						}
						passed.clear();
						failed.clear();
						skipped.clear();

						FeatureResult.append(
								AddPlatform(featurename, platform, featurestatus, String.valueOf(ScenarioList)));

						switch (platform.toUpperCase()) {

						case "CHROME":
							ChromeStatus = featurestatus;
							break;
						case "EDGE":
							EdgeStatus = featurestatus;
							break;
						case "FIREFOX":
							FirefoxStatus = featurestatus;
							break;
						case "ANDROID":
							AndroidStatus = featurestatus;
							break;
						case "IOS":
							IOSStatus = featurestatus;
							break;
						}

					}
				}
				SerialNum++;
				DashboardJourneyList.append(AddDashboardJourney(String.valueOf(SerialNum), featurename, ChromeStatus,
						EdgeStatus, FirefoxStatus, AndroidStatus, IOSStatus));
				NavbarList.append(AddJourneyToNavbar(featurename));
				FeatureList
						.append(AddFeature(featurename, String.valueOf(FeatureResult), String.valueOf(ScenarioResult)));
				if (Browserexecutiontime.size() != 0)
					BarChartData.put(featurename.toUpperCase(), Browserexecutiontime);

			}

			int PassedCount = featurepassed.size();
			int FailedCount = featurefailed.size();
			int SkippedCount = featureskipped.size();

			String PieChart = GeneratePieGraph(String.valueOf(PassedCount), String.valueOf(FailedCount),
					String.valueOf(SkippedCount));
			String BarChart = GenerateBarGraph(BrowserJourneyList, BarChartData);
			String Dashboard = GenerateDashboard(String.valueOf(PassedCount), String.valueOf(FailedCount),
					String.valueOf(SkippedCount), String.valueOf(DashboardJourneyList));

			String FileContent = GenerateReport(String.valueOf(NavbarList), String.valueOf(FeatureList), Dashboard,
					PieChart, BarChart);

			SaveHTMLResultFile(FileContent);

		} catch (

		Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String GenerateReport(String NavbarList, String JourneyList, String Dashboard, String PieChart,
			String BarChart) {
		String FinalReport = "<!doctype html>\r\n"
				+ "<!--[if lt IE 7]>      <html class=~no-js lt-ie9 lt-ie8 lt-ie7~ lang=~~> <![endif]-->\r\n"
				+ "<!--[if IE 7]>         <html class=~no-js lt-ie9 lt-ie8~ lang=~~> <![endif]-->\r\n"
				+ "<!--[if IE 8]>         <html class=~no-js lt-ie9~ lang=~~> <![endif]-->\r\n"
				+ "<!--[if gt IE 8]><!-->\r\n" + "<html class=~no-js~ lang=~~ dir=~ltr~>\r\n" + "\r\n" + "\r\n"
				+ "<head>\r\n" + "    <meta charset=~utf-8~>\r\n"
				+ "    <meta http-equiv=~X-UA-Compatible~ content=~IE=edge~>\r\n"
				+ "    <title>Test Execution Report</title>\r\n"
				+ "    <meta name=~description~ content=~G10X Report Template~>\r\n"
				+ "    <meta name=~viewport~ content=~width=device-width, initial-scale=1~>\r\n" + "\r\n"
				+ "    <link rel=~stylesheet~ href=~https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css~>\r\n"
				+ "    <link rel=~stylesheet~ href=~https://cdn.jsdelivr.net/npm/font-awesome@4.7.0/css/font-awesome.min.css~>\r\n"
				+ "\r\n" + "    <!-- For the the chart -->\r\n"
				+ "    <script src=~https://cdn.jsdelivr.net/npm/apexcharts~></script>\r\n"
				+ "    <!-- custom css -->\r\n" + "    <link href=~assets/css/Global.css~ rel=~stylesheet~>\r\n"
				+ "\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n" + "    <div id=~content~>\r\n"
				+ "        <div class=~app~>\r\n" + "            <div class=~app__nav~>\r\n"
				+ "                <div class=~side-nav~>\r\n" + "                    <div class=~side-nav__head~>\r\n"
				+ "                        <div class=~row justify-content-center~ style=~padding:10px;~>\r\n"
				+ "                            <div class=~col~ style=~margin-left: 3px;~><img class=~logoimg~\r\n"
				+ "                                    src=~assets/images/g10x_Logo2.png~></div>\r\n"
				+ "                            <div class=~col~ style=~margin: 0~> <img class=~logoimg~ src=~assets/images/TFG.png~></div>\r\n"
				+ "                        </div>\r\n" + "                    </div>\r\n"
				+ "                    <div class=~side-nav__head~>\r\n"
				+ "                        Test Automation Report\r\n" + "                    </div>\r\n" + "\r\n"
				+ "                    <nav class=~navbar~>\r\n"
				+ "                        <div id=~main-menu~ class=~main-menu~>\r\n"
				+ "                            <ul class=~nav navbar-nav~>\r\n"
				+ "                                <li class=~active mainitem~ onclick=~ShowMainSection('dash')~>\r\n"
				+ "                                    <a><i class=~menu-icon fa fa-laptop~></i>Dashboard </a>\r\n"
				+ "                                </li>\r\n"
				+ "                                <li class=~menu-title mainitem~>Execution Details</li>\r\n"
				+ "                                <li class=~menu-item-has-children dropdown~>\r\n"
				+ "                                    <a class=~dropdown-toggle collapsible mainitem~> <i\r\n"
				+ "                                            class=~menu-icon fa fa-cogs~></i>Suites</a>\r\n"
				+ "                                    <ul class=~sub-menu children dropdown-menu~>\r\n"
				+ "                                      vNAVLIST\r\n" + "                                    </ul>\r\n"
				+ "                                </li>\r\n" + "                            </ul>\r\n"
				+ "                        </div>\r\n" + "                    </nav>\r\n" + "                </div>\r\n"
				+ "            </div>\r\n" + "\r\n" + "       vDASHBOARD\r\n" + "  VJOURNEYS\r\n" + "        </div>\r\n"
				+ "    </div>\r\n" + "\r\n" + "    <!-- Scripts -->\r\n"
				+ "    <script src=~https://cdn.jsdelivr.net/npm/jquery@2.2.4/dist/jquery.min.js~></script>\r\n"
				+ "    <script src=~https://cdn.jsdelivr.net/npm/popper.js@1.14.4/dist/umd/popper.min.js~></script>\r\n"
				+ "\r\n"
				+ "    <script src=~https://cdn.jsdelivr.net/npm/jquery-match-height@0.7.2/dist/jquery.matchHeight.min.js~></script>\r\n"
				+ "    <script src=~https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js~></script>\r\n"
				+ "\r\n" + "\r\n" + "    <script src=~assets/js/main.js~></script>\r\n" + "\r\n" + "\r\n"
				+ "    <!-- main section -->\r\n" + "\r\n" + "    <script>\r\n"
				+ "        function ShowMainSection(Divname) {\r\n"
				+ "            var coll = document.getElementsByClassName(~mainsection~);\r\n"
				+ "            var i;\r\n" + "            for (i = 0; i < coll.length; i++) {\r\n"
				+ "                coll[i].style.display = 'none';\r\n" + "            }\r\n"
				+ "            var x = document.getElementById(Divname);\r\n"
				+ "            x.style.display = ~block~;\r\n" + "        }\r\n" + "    </script>\r\n" + "\r\n" + "\r\n"
				+ "    <!-- collapsible -->\r\n" + "\r\n" + "    <script>\r\n" + "\r\n"
				+ "        var coll = document.getElementsByClassName(~collapsible~);\r\n" + "        var i;\r\n"
				+ "\r\n" + "        for (i = 0; i < coll.length; i++) {\r\n"
				+ "            coll[i].addEventListener(~click~, function () {\r\n"
				+ "                this.classList.toggle(~active~);\r\n"
				+ "                var details = this.nextElementSibling;\r\n"
				+ "                if (details.style.display === ~block~) {\r\n"
				+ "                    details.style.display = ~none~;\r\n" + "                } else {\r\n"
				+ "                    details.style.display = ~block~;\r\n" + "                }\r\n"
				+ "            });\r\n" + "        }\r\n" + "    </script>\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "    <!-- node node__leaf-->\r\n" + "    <script>\r\n"
				+ "        var coll = document.getElementsByClassName(~node node__leaf~);\r\n" + "        var i;\r\n"
				+ "\r\n" + "        for (i = 0; i < coll.length; i++) {\r\n"
				+ "            coll[i].addEventListener(~click~, function () {\r\n"
				+ "                var elements = document.getElementsByClassName(~node node__leaf active~);\r\n"
				+ "                var n;\r\n" + "                for (n = 0; n < elements.length; n++) {\r\n"
				+ "                    var el = elements[n];\r\n"
				+ "                    el.classList.remove(~active~);\r\n" + "                }\r\n"
				+ "                this.classList.toggle(~active~);\r\n" + "\r\n"
				+ "                var coll = document.getElementsByClassName(~TCID~);\r\n"
				+ "                var i;\r\n" + "                for (i = 0; i < coll.length; i++) {\r\n"
				+ "                    coll[i].style.display = ~none~;\r\n" + "                }\r\n" + "\r\n"
				+ "                var class_name = this.classList.toString();;\r\n"
				+ "                var new_class_name = class_name.replace(/node node__leaf/g, ~~).trim();\r\n"
				+ "                new_class_name = new_class_name.replace(/active/g, ~~).trim();\r\n"
				+ "                document.getElementById(new_class_name).style.display = ~block~;\r\n"
				+ "            });\r\n" + "        }\r\n" + "\r\n" + "    </script>\r\n" + "\r\n"
				+ "    <!-- caret -->\r\n" + "    <script>\r\n"
				+ "        var toggler = document.getElementsByClassName(~caret~);\r\n" + "        var i;\r\n" + "\r\n"
				+ "        for (i = 0; i < toggler.length; i++) {\r\n"
				+ "            toggler[i].addEventListener(~click~, function () {\r\n"
				+ "                this.parentElement.querySelector(~.nested~).classList.toggle(~active~);\r\n"
				+ "                this.classList.toggle(~caret-down~);\r\n" + "            });\r\n" + "        }\r\n"
				+ "    </script>\r\n" + "\r\n" + "    <!-- Column Chart -->\r\n" + "\r\n" + "vBARCHART\r\n" + "\r\n"
				+ "    <!-- semi pie -->\r\n" + "\r\n" + "vPIECHART\r\n" + "\r\n" + "\r\n" + "</body>\r\n" + "\r\n"
				+ "</html>";

		FinalReport = FinalReport.replace("vDASHBOARD", Dashboard);
		FinalReport = FinalReport.replace("VJOURNEYS", JourneyList);
		FinalReport = FinalReport.replace("vBARCHART", PieChart);
		FinalReport = FinalReport.replace("vPIECHART", BarChart);
		FinalReport = FinalReport.replace("vNAVLIST", NavbarList);
		FinalReport = FinalReport.replace('~', '"');
		// System.out.println(FinalReport);
		return FinalReport;
	}

	public static String GenerateDashboard(String PassedCount, String FailedCount, String SkippedCount,
			String JourneyTable) {
		String Dashboard = "     <div class=~app__content mainsection~ id=~dash~>\r\n"
				+ "                <div class=~side-by-side~>\r\n"
				+ "                    <div id=~dashboard~ class=~content~ style=~display: block;~>\r\n"
				+ "                        <div class=~animated fadeIn~>\r\n"
				+ "                            <div class=~row justify-content-center~>\r\n"
				+ "                                <div class=~col-lg-3 col-md-6~>\r\n"
				+ "                                    <div class=~card~>\r\n"
				+ "                                        <div class=~card-body~>\r\n"
				+ "                                            <div class=~stat-widget-five~>\r\n"
				+ "                                                <div class=~stat-icon dib passicon~>\r\n"
				+ "                                                    <i class=~fa fa-check~></i>\r\n"
				+ "                                                </div>\r\n"
				+ "                                                <div class=~stat-content~>\r\n"
				+ "                                                    <div class=~text-left dib~>\r\n"
				+ "                                                        <div class=~stat-text~><span class=~count~>vPASSEDCOUNT</span></div>\r\n"
				+ "                                                        <div class=~stat-heading~>Passed</div>\r\n"
				+ "                                                    </div>\r\n"
				+ "                                                </div>\r\n"
				+ "                                            </div>\r\n"
				+ "                                        </div>\r\n"
				+ "                                    </div>\r\n" + "                                </div>\r\n"
				+ "\r\n" + "                                <div class=~col-lg-3 col-md-6~>\r\n"
				+ "                                    <div class=~card~>\r\n"
				+ "                                        <div class=~card-body~>\r\n"
				+ "                                            <div class=~stat-widget-five~>\r\n"
				+ "                                                <div class=~stat-icon dib failicon~>\r\n"
				+ "                                                    <i class=~fa fa-times~></i>\r\n"
				+ "                                                </div>\r\n"
				+ "                                                <div class=~stat-content~>\r\n"
				+ "                                                    <div class=~text-left dib~>\r\n"
				+ "                                                        <div class=~stat-text~><span class=~count~>vFAILEDCOUNT</span></div>\r\n"
				+ "                                                        <div class=~stat-heading~>Failed</div>\r\n"
				+ "                                                    </div>\r\n"
				+ "                                                </div>\r\n"
				+ "                                            </div>\r\n"
				+ "                                        </div>\r\n"
				+ "                                    </div>\r\n" + "                                </div>\r\n"
				+ "\r\n" + "                                <div class=~col-lg-3 col-md-6~>\r\n"
				+ "                                    <div class=~card~>\r\n"
				+ "                                        <div class=~card-body~>\r\n"
				+ "                                            <div class=~stat-widget-five~>\r\n"
				+ "                                                <div class=~stat-icon dib skipicon~>\r\n"
				+ "                                                    <i class=~fa fa-ban~ aria-hidden=~true~></i>\r\n"
				+ "                                                </div>\r\n"
				+ "                                                <div class=~stat-content~>\r\n"
				+ "                                                    <div class=~text-left dib~>\r\n"
				+ "                                                        <div class=~stat-text~><span class=~count~>vSKIPPEDCOUNT</span></div>\r\n"
				+ "                                                        <div class=~stat-heading~>Skipped</div>\r\n"
				+ "                                                    </div>\r\n"
				+ "                                                </div>\r\n"
				+ "                                            </div>\r\n"
				+ "                                        </div>\r\n"
				+ "                                    </div>\r\n" + "                                </div>\r\n"
				+ "                            </div>\r\n"
				+ "                            <div class=~row justify-content-center~>\r\n"
				+ "                                <div class=~col-lg-6 col-md-6~>\r\n"
				+ "                                    <div class=~card~>\r\n"
				+ "                                        <div class=~card-body~>\r\n"
				+ "                                            <div id=~piechart~></div>\r\n"
				+ "                                        </div>\r\n"
				+ "                                    </div>\r\n" + "                                </div>\r\n"
				+ "                                <div class=~col-lg-6 col-md-6~>\r\n"
				+ "                                    <div class=~card~>\r\n"
				+ "                                        <div class=~card-body~>\r\n"
				+ "                                            <div id=~barchart~></div>\r\n"
				+ "                                        </div>\r\n"
				+ "                                    </div>\r\n" + "                                </div>\r\n"
				+ "                            </div>\r\n" + "\r\n"
				+ "                            <div class=~row justify-content-center journeytable~>\r\n" + "\r\n"
				+ "                                <div class=~status~>\r\n"
				+ "                                    <div class=~row justify-content-center~>\r\n"
				+ "                                        <div class=~card~>\r\n"
				+ "                                            <div class=~card-body box-title~>\r\n"
				+ "                                                <h4>Execution Details</h4>\r\n"
				+ "                                            </div>\r\n"
				+ "                                            <div class=~card-body--~>\r\n"
				+ "                                                <div class=~table-stats status-table ov-h~>\r\n"
				+ "                                                    <table class=~table~>\r\n"
				+ "                                                        <thead>\r\n"
				+ "                                                            <tr class=~tableheadertop~>\r\n"
				+ "                                                                <th class=~tableheader~ style=~width: 5%~ colspan=~1~>#\r\n"
				+ "                                                                </th>\r\n"
				+ "                                                                <th class=~tableheader~ style=~width: 5%;~ colspan=~1~>\r\n"
				+ "                                                                    Customer\r\n"
				+ "                                                                    Journey</th>\r\n"
				+ "                                                                <th class=~tableheader~ style=~width: 5%~ colspan=~3~>\r\n"
				+ "                                                                    Browsers</th>\r\n"
				+ "                                                                <th class=~tableheader~\r\n"
				+ "                                                                    style=~width: 3%;text-align: center;~ colspan=~2~>\r\n"
				+ "                                                                    Devices</th>\r\n"
				+ "                                                            </tr>\r\n"
				+ "                                                            <tr class=~tableheaderbottom~>\r\n"
				+ "                                                                <th class=~tableheader~></th>\r\n"
				+ "                                                                <th class=~tableheader~></th>\r\n"
				+ "                                                                <th class=~tableheader~>Chrome</th>\r\n"
				+ "                                                                <th class=~tableheader~>Edge</th>\r\n"
				+ "                                                                <th class=~tableheader~>Firefox</th>\r\n"
				+ "                                                                <th class=~tableheader~>Android</th>\r\n"
				+ "                                                                <th class=~tableheader~>IOS</th>\r\n"
				+ "                                                            </tr>\r\n"
				+ "                                                        </thead>\r\n"
				+ "                                                        <tbody>\r\n"
				+ "                                                           vDASHBOARDJOURNEY\r\n"
				+ "                                                        </tbody>\r\n"
				+ "                                                    </table>\r\n"
				+ "                                                </div>\r\n"
				+ "                                            </div>\r\n"
				+ "                                        </div>\r\n"
				+ "                                    </div>\r\n" + "                                </div>\r\n"
				+ "                            </div>\r\n" + "                        </div>\r\n"
				+ "                    </div>\r\n" + "                </div>\r\n" + "            </div>";

		Dashboard = Dashboard.replace("vPASSEDCOUNT", PassedCount);
		Dashboard = Dashboard.replace("vFAILEDCOUNT", FailedCount);
		Dashboard = Dashboard.replace("vSKIPPEDCOUNT", SkippedCount);
		Dashboard = Dashboard.replace("vDASHBOARDJOURNEY", JourneyTable);
		return Dashboard;
	}

	public static String AddDashboardJourney(String SerialNum, String JourneyName, String ChromeStatus,
			String EdgeStatus, String FirefoxStatus, String AndroidStatus, String IOSStatus) {

		String DashboardJourney = "<tr>\r\n" + "<td class=~serial~>vSERIALNUM.</td>\r\n"
				+ "<td class=~journeyname~><span  onclick=~ShowMainSection('vJOURNEYID')~>vJOURNEYNAME</span></td>\r\n"
				+ "<td><span>vCHROMESTATUS</span></td>\r\n" + "<td><span>vEDGESTATUS</span></td>\r\n"
				+ "<td><span>vFIREFOXSTATUS</span></td>\r\n" + "<td><span>vANDROIDSTATUS</span> </td>\r\n"
				+ "<td><span>vIOSSTATUS</span> </td>\r\n" + "</tr>";

		if (ChromeStatus != "") {
			DashboardJourney = DashboardJourney.replace("vCHROMESTATUS", GetDashboardStatus(ChromeStatus));
		} else {
			DashboardJourney = DashboardJourney.replace("vCHROMESTATUS", "");
		}
		if (EdgeStatus != "") {
			DashboardJourney = DashboardJourney.replace("vEDGESTATUS", GetDashboardStatus(EdgeStatus));
		} else {
			DashboardJourney = DashboardJourney.replace("vEDGESTATUS", "");

		}
		if (FirefoxStatus != "") {
			DashboardJourney = DashboardJourney.replace("vFIREFOXSTATUS", GetDashboardStatus(FirefoxStatus));
		} else {
			DashboardJourney = DashboardJourney.replace("vFIREFOXSTATUS", "");

		}
		if (AndroidStatus != "") {
			DashboardJourney = DashboardJourney.replace("vANDROIDSTATUS", GetDashboardStatus(AndroidStatus));
		} else {
			DashboardJourney = DashboardJourney.replace("vANDROIDSTATUS", "");
		}
		if (IOSStatus != "") {
			DashboardJourney = DashboardJourney.replace("vIOSSTATUS", GetDashboardStatus(IOSStatus));
		} else {
			DashboardJourney = DashboardJourney.replace("vIOSSTATUS", "");
		}
		// System.out.println(DashboardJourney);
		DashboardJourney = DashboardJourney.replace("vSERIALNUM", SerialNum);
		DashboardJourney = DashboardJourney.replace("vJOURNEYNAME", JourneyName);
		DashboardJourney = DashboardJourney.replace("vJOURNEYID", JourneyName.replace(" ", "_").toLowerCase());
		return DashboardJourney;

	}

	public static String GetDashboardStatus(String Status) {
		Status = Status.toUpperCase().trim();
		String Result = "<i class=~fa fa-check passicon extralarge~></i>";
		switch (Status) {
		case "PASSED":
			Result = "<i class=~fa fa-check passicon extralarge~></i>";
			break;
		case "FAILED":
			Result = "<i class=~fa fa-times failicon extralarge~></i>";
			break;
		case "SKIPPED":
			Result = "<i class=~fa fa-ban skipicon extralarge~></i>";
			break;
		default:
			Result = "<i class=~fa fa-check passicon extralarge~></i>";
			break;
		}
		return Result;
	}

	public static String AddJourneyToNavbar(String JourneyName) {

		String NavList = new String();
		NavList = " <li onclick=~ShowMainSection('vJOURNEYID')~>\r\n"
				+ " <i class=~fa fa-list~ aria-hidden=~true~ #></i>\r\n" + " <a>vJOURNEYNAME</a>\r\n" + " </li>";

		NavList = NavList.replace("vJOURNEYID", JourneyName.replace(" ", "_").toLowerCase());
		NavList = NavList.replace("vJOURNEYNAME", JourneyName);
		return NavList;
	}

	public static String GeneratePieGraph(String PassedCount, String FailedCount, String SkippedCount) {
		String PieGraph = "    <script>\r\n" + "        var StatusValues = [~Skipped~, ~Failed~, ~Passed~];\r\n"
				+ "        var CountValues = [vSKIPPEDCOUNT,vFAILEDCOUNT, vPASSEDCOUNT];\r\n"
				+ "        var PieColors = [~#DDDDDD~, ~#FF6961~, ~#00B978~];\r\n" + "\r\n"
				+ "        var options = {\r\n" + "            series: CountValues,\r\n"
				+ "            labels: StatusValues,\r\n" + "            chart: {\r\n"
				+ "                type: 'donut',\r\n" + "                height: 218,\r\n"
						+ "                fontFamily: 'proxima_nova_altbold',\r\n"
						+ "                fontSize: \"14px\",\r\n"
						+ "                fontWeight:\"normal\",\r\n" + "            },\r\n"
				+ "            colors: PieColors,\r\n" + "            title: {\r\n"
				+ "                text: 'EXECUTION STATUS',\r\n" + "                align: 'left',\r\n"
				+ "                color: '#DDDDDD'\r\n" + "            },\r\n" + "            plotOptions: {\r\n"
				+ "                pie: {\r\n" + "                    startAngle: -90,\r\n"
				+ "                    endAngle: 90,\r\n" + "                    offsetY: 10\r\n"
				+ "                }\r\n" + "            },\r\n" + "            grid: {\r\n"
				+ "                padding: {\r\n" + "                    bottom: -150,\r\n"
				+ "                    top: 30\r\n" + "                }\r\n" + "            },\r\n"
				+ "            responsive: [{\r\n" + "                breakpoint: 480,\r\n"
				+ "                options: {\r\n" + "                    chart: {\r\n"
				+ "                        width: 200\r\n" + "                    },\r\n"
				+ "                    legend: {\r\n" + "                        position: 'bottom'\r\n"
				+ "                    }\r\n" + "                }\r\n" + "            }]\r\n" + "        };\r\n"
				+ "\r\n" + "        var chart = new ApexCharts(document.querySelector(~#piechart~), options);\r\n"
				+ "        chart.render();\r\n" + "\r\n" + "\r\n" + "    </script>\r\n" + "";
		PieGraph = PieGraph.replace("vPASSEDCOUNT", PassedCount);
		PieGraph = PieGraph.replace("vFAILEDCOUNT", FailedCount);
		PieGraph = PieGraph.replace("vSKIPPEDCOUNT", SkippedCount);
		return PieGraph;
	}

	/*public static String GenerateBarGraph(Set<String> BrowserJourneyList,
			Hashtable<String, Hashtable<String, String>> BarChartData) {
		String JourneyValues = "";
		String ChromeValues = "";
		String EdgeValues = "";
		String FirefoxValues = "";
		String BarGraph = " <script>\r\n" + "\r\n" + "        var JourneyValues = [vJOURNEYVALUES];\r\n"
				+ "        var ChromeValues = [vCHROMEVALUES];\r\n" + "        var EdgeValues = [vEDGEVALUES];\r\n"
				+ "        var FirefoxValues = [vFIREFOXVALUS];\r\n"
				+ "        var barColors = [~#0062B2~, ~#9F5AB5~, ~#F05683~];\r\n" + "\r\n"
				+ "        // colors: PieColors,\r\n" + "\r\n" + "        var options = {\r\n"
				+ "            series: [{\r\n" + "                name: 'Chrome',\r\n"
				+ "                data: ChromeValues,\r\n" + "                color: ~#0062B2~\r\n"
				+ "            },\r\n" + "            {\r\n" + "                name: 'Edge',\r\n"
				+ "                data: EdgeValues,\r\n" + "                color: ~#9F5AB5~\r\n"
				+ "            },\r\n" + "            {\r\n" + "                name: 'Firefox',\r\n"
				+ "                data: FirefoxValues,\r\n" + "                color: ~#F05683~\r\n"
				+ "            }],\r\n" + "            chart: {\r\n" + "                type: 'bar',\r\n"
				+ "                height: 218,\r\n"
				+ "                fontFamily: 'proxima_nova_altbold',\r\n"
				+ "                fontSize: \"14px\",\r\n"
				+ "                fontWeight:\"normal\"\r\n" + "            },\r\n" + "            title: {\r\n"
				+ "                text: 'EXECUTION TIME',\r\n" + "                align: 'left',\r\n"
				+ "                color: '#DDDDDD'\r\n" + "            },\r\n" + "            plotOptions: {\r\n"
				+ "                bar: {\r\n" + "                    horizontal: false,\r\n"
				+ "                    columnWidth: '55%',\r\n" + "                    endingShape: 'rounded'\r\n"
				+ "                },\r\n" + "            },\r\n" + "            dataLabels: {\r\n"
				+ "                enabled: false\r\n" + "            },\r\n" + "            stroke: {\r\n"
				+ "                show: true,\r\n" + "                width: 2,\r\n"
				+ "                colors: ['transparent']\r\n" + "            },\r\n" + "            xaxis: {\r\n"
				+ "                categories: JourneyValues,\r\n" + "            },\r\n" + "            yaxis: {\r\n"
				+ "                title: {\r\n" + "                    text: 'Time'\r\n" + "                }\r\n"
				+ "            },\r\n" + "            fill: {\r\n" + "                opacity: 1\r\n"
				+ "            },\r\n" + "            tooltip: {\r\n" + "                y: {\r\n"
				+ "                    formatter: function (val) {\r\n"
				+ "                        return val + ~ seconds~\r\n" + "                    }\r\n"
				+ "                }\r\n" + "            }\r\n" + "        };\r\n" + "\r\n"
				+ "        var chart = new ApexCharts(document.querySelector(~#barchart~), options);\r\n"
				+ "        chart.render();\r\n" + "\r\n" + "\r\n" + "    </script>\r\n" + "";

		if (BarChartData.size() != 0) {

			String value = "";
			Hashtable<String, String> Executiontime = new Hashtable<String, String>();

			for (Object journey : BrowserJourneyList) {
				if (BarChartData.containsKey(journey)) {
					JourneyValues = JourneyValues + "'" + journey + "',";
					Executiontime = BarChartData.get(journey);
					if (Executiontime.containsKey("CHROME")) {
						value = Executiontime.get("CHROME");
						ChromeValues = ChromeValues + "'" + value + "' ,";
					} else {
						ChromeValues = ChromeValues + ",";
					}
					if (Executiontime.containsKey("EDGE")) {
						value = Executiontime.get("EDGE");
						EdgeValues = EdgeValues + "'" + value + "' ,";
					} else {
						EdgeValues = EdgeValues + ",";
					}
					if (Executiontime.containsKey("FIREFOX")) {
						value = Executiontime.get("FIREFOX");
						FirefoxValues = FirefoxValues + "'" + value + "' ,";
					} else {
						FirefoxValues = FirefoxValues + ",";
					}
				}

			}
			// Remove the last ,
			JourneyValues = removeLastChar(JourneyValues);
			ChromeValues = removeLastChar(ChromeValues);
			EdgeValues = removeLastChar(EdgeValues);
			FirefoxValues = removeLastChar(FirefoxValues);

		}

		BarGraph = BarGraph.replace("vJOURNEYVALUES", JourneyValues);
		BarGraph = BarGraph.replace("vCHROMEVALUES", ChromeValues);
		BarGraph = BarGraph.replace("vEDGEVALUES", EdgeValues);
		BarGraph = BarGraph.replace("vFIREFOXVALUS", FirefoxValues);

		return BarGraph;
	}
*/
	public static String GenerateBarGraph(Set<String> BrowserJourneyList,
			Hashtable<String, Hashtable<String, String>> BarChartData) {
				String SerialNums = "";
		String JourneyValues = "";
		String ChromeValues = "";
		String EdgeValues = "";
		String FirefoxValues = "";
		String BarGraph = " <script>\r\n" + "\r\n" + "        var SerialNums = [vSERIALNUMS];\r\n"
				+ "        var ChromeValues = [vCHROMEVALUES];\r\n" + "        var EdgeValues = [vEDGEVALUES];\r\n"
				+ "        var FirefoxValues = [vFIREFOXVALUS];\r\n"
				+ "        var JourneyValues = [vJOURNEYVALUES];\r\n"
				+ "        var barColors = [~#0062B2~, ~#9F5AB5~, ~#F05683~];\r\n" + "\r\n"
				+ "        // colors: PieColors,\r\n" + "\r\n" + "        var options = {\r\n"
				+ "            series: [{\r\n" + "                name: 'Chrome',\r\n"
				+ "                data: ChromeValues,\r\n" + "                color: ~#0062B2~\r\n"
				+ "            },\r\n" + "            {\r\n" + "                name: 'Edge',\r\n"
				+ "                data: EdgeValues,\r\n" + "                color: ~#9F5AB5~\r\n"
				+ "            },\r\n" + "            {\r\n" + "                name: 'Firefox',\r\n"
				+ "                data: FirefoxValues,\r\n" + "                color: ~#F05683~\r\n"
				+ "            }],\r\n" + "            chart: {\r\n" + "                type: 'bar',\r\n"
				+ "                height: 218,\r\n"
				+ "                fontFamily: 'proxima_nova_altbold',\r\n"
				+ "                fontSize: \"14px\",\r\n"
				+ "                fontWeight:\"normal\"\r\n" + "            },\r\n" + "            title: {\r\n"
				+ "                text: 'EXECUTION TIME',\r\n" + "                align: 'left',\r\n"
				+ "                color: '#DDDDDD'\r\n" + "            },\r\n" + "            plotOptions: {\r\n"
				+ "                bar: {\r\n" + "                    horizontal: false,\r\n"
				+ "                    columnWidth: '55%',\r\n" + "                    endingShape: 'rounded'\r\n"
				+ "                },\r\n" + "            },\r\n" + "            dataLabels: {\r\n"
				+ "                enabled: false\r\n" + "            },\r\n" + "            stroke: {\r\n"
				+ "                show: true,\r\n" + "                width: 2,\r\n"
				+ "                colors: ['transparent']\r\n" + "            },\r\n" + "            xaxis: {\r\n"
				+ "                categories: SerialNums,\r\n" + "            },\r\n" + "            yaxis: {\r\n"
				+ "                title: {\r\n" + "                    text: 'Time'\r\n" + "                }\r\n"
				+ "            },\r\n" + "            fill: {\r\n" + "                opacity: 1\r\n"
				+ "            },\r\n" + "            tooltip: {\r\n" + "                y: {\r\n"
				+ "                    formatter: function (val) {\r\n"
				+ "                        return val + ~ seconds~\r\n" + "                    }\r\n"
				+ "                },\r\n" 
				+ "				   x: { formatter: function (val) { return JourneyValues[val-1] }}"
				+ "            }\r\n" + "        };\r\n" + "\r\n"
				+ "        var chart = new ApexCharts(document.querySelector(~#barchart~), options);\r\n"
				+ "        chart.render();\r\n" + "\r\n" + "\r\n" + "    </script>\r\n" + "";

		if (BarChartData.size() != 0) {

			String value = "";
			Hashtable<String, String> Executiontime = new Hashtable<String, String>();
int i=0;
			for (Object journey : BrowserJourneyList) {
				if (BarChartData.containsKey(journey)) {
					i++;
					SerialNums=SerialNums+ "'" + i + "',";
					JourneyValues = JourneyValues + "'" + journey + "',";
					Executiontime = BarChartData.get(journey);
					if (Executiontime.containsKey("CHROME")) {
						value = Executiontime.get("CHROME");
						ChromeValues = ChromeValues + "'" + value + "' ,";
					} else {
						ChromeValues = ChromeValues + ",";
					}
					if (Executiontime.containsKey("EDGE")) {
						value = Executiontime.get("EDGE");
						EdgeValues = EdgeValues + "'" + value + "' ,";
					} else {
						EdgeValues = EdgeValues + ",";
					}
					if (Executiontime.containsKey("FIREFOX")) {
						value = Executiontime.get("FIREFOX");
						FirefoxValues = FirefoxValues + "'" + value + "' ,";
					} else {
						FirefoxValues = FirefoxValues + ",";
					}
				}

			}
			// Remove the last ,
			SerialNums = removeLastChar(SerialNums);
			JourneyValues = removeLastChar(JourneyValues);
			ChromeValues = removeLastChar(ChromeValues);
			EdgeValues = removeLastChar(EdgeValues);
			FirefoxValues = removeLastChar(FirefoxValues);

		}
        BarGraph = BarGraph.replace("vSERIALNUMS", SerialNums);
		BarGraph = BarGraph.replace("vJOURNEYVALUES", JourneyValues);
		BarGraph = BarGraph.replace("vCHROMEVALUES", ChromeValues);
		BarGraph = BarGraph.replace("vEDGEVALUES", EdgeValues);
		BarGraph = BarGraph.replace("vFIREFOXVALUS", FirefoxValues);

		return BarGraph;
	}
	public static String removeLastChar(String s) {
		return (s == null || s.length() == 0) ? null : (s.substring(0, s.length() - 1));
	}

	public static String AddJourney(String FeatureName) {
		String Journey = new String();
		Journey = "";

		Journey = Journey.replace("vJOURNEYNAME", FeatureName);
//	System.out.println(Journey);
		return Journey;

	}

	public static String AddFeature(String JourneyName, String PlatformList, String DetailedResults) {
		String Feature = new String();
		Feature = "<div class=~app__content mainsection~ id=~vJOURNEYID~ style=~display: none;~>\r\n"
				+ "    <div class=~side-by-side~>\r\n"
				+ "        <div class=~side-by-side__left~ style=~width: calc(49.8677% - 3.5px);~>\r\n"
				+ "            <div class=~journeylist~>\r\n"
				+ "                <h2 class=~leftheading~>Suites - <em>vJOURNEYNAME</em></h2>\r\n"
				+ "                vPLATFORMFEATURES\r\n" + "            </div>\r\n" + "\r\n" + "        </div>\r\n"
				+ "        <div class=~gutter gutter-horizontal~></div>\r\n"
				+ "        <div class=~side-by-side__right~ style=~width: calc(50.1323% - 3.5px);~>\r\n"
				+ "            <div class=~journeydetails~>\r\n" + "                <div class=~vJOURNEYNAME~>\r\n"
				+ "                    vDETAILEDRESULTS\r\n" + "                </div>\r\n" + "            </div>\r\n"
				+ "        </div>\r\n" + "    </div>\r\n" + "</div>";
		Feature = Feature.replace("vJOURNEYNAME", JourneyName);
		Feature = Feature.replace("vPLATFORMFEATURES", PlatformList);
		Feature = Feature.replace("vDETAILEDRESULTS", DetailedResults);
		Feature = Feature.replace("vJOURNEYID", JourneyName.replace(" ", "_").toLowerCase());

		return Feature;
	}

	public static String AddPlatform(String Journey, String Platform, String Status, String ScenarioList) {
		String NewPlatform = new String();
		NewPlatform = "  <div class=~vPLATFORM~>\r\n"
				+ "                                <button class=~collapsible journeytitle~><i class=~journeyarrow~></i>\r\n"
				+ "                                    vJOURNEYNAME - vPLATFORM\r\n"
				+ "                                    VPLATFORMSTATUS"
				+ "                                </button>\r\n" + "\r\n"
				+ "                                <ul class=~node__children~>\r\n"
				+ "                                  vSCENARIOS\r\n" + "                                </ul>\r\n"
				+ "                            </div>";

		String PlatformStatus = "";
		switch (Status.toUpperCase()) {
		case "PASSED":
			PlatformStatus = "  <div class=~stat-icon dib passicon~>\r\n"
					+ "                                        <i class=~fa fa-check~></i>\r\n"
					+ "                                    </div>";
			break;
		case "FAILED":
			PlatformStatus = "  <div class=~stat-icon dib failicon~>\r\n"
					+ "                                        <i class=~fa fa-times~></i>\r\n"
					+ "                                    </div>";
			break;
		case "SKIPPED":
			PlatformStatus = "  <div class=~stat-icon dib skipicon~>\r\n"
					+ "                                        <i class=~fa fa-ban~></i>\r\n"
					+ "                                    </div>";
			break;
		default:
			PlatformStatus = "  <div class=~stat-icon dib passicon~>\r\n"
					+ "                                        <i class=~fa fa-check~></i>\r\n"
					+ "                                    </div>";
			break;
		}
		NewPlatform = NewPlatform.replace("vJOURNEYNAME", Journey);
		NewPlatform = NewPlatform.replace("vPLATFORM", Platform);
		NewPlatform = NewPlatform.replace("VPLATFORMSTATUS", PlatformStatus);
		NewPlatform = NewPlatform.replace("vSCENARIOS", ScenarioList);
		return NewPlatform;
	}

	public static String AddScenario(String JourneyName, String Platform, int SerialNum, String ScenarioID,
			String ScenarioTitle, String ScenarioStatus, String Duration) {
		String Scenario = new String();
		Scenario = "   <li class=~node node__leaf vJOURNEYID_vPLATFORM_vSCENARIOID~ style=~cursor: pointer;~>\r\n"
				+ "                                        <div class=~node__title~>\r\n"
				+ "                                            <div class=~node__anchor~>\r\n"
				+ "                                               vSCENARIOSTATUS\r\n"
				+ "                                            </div>\r\n"
				+ "                                            <div class=~node__order~>#vSERIALNUMBER</div>\r\n"
				+ "                                            <div class=~node__name~>vSCENARIOTITLE</div>\r\n"
				+ "                                            <div class=~tree__strut~>&nbsp;</div>\r\n"
				+ "                                            <div class=~node__stats~>vEXECUTIONDURATION</div></div>\r\n"
				+ "                                    </li>";
		String SlNum = String.valueOf(SerialNum);
		Scenario = Scenario.replace("vJOURNEYID", JourneyName.replace(" ", ""));
		Scenario = Scenario.replace("vPLATFORM", Platform);
		Scenario = Scenario.replace("vSCENARIOID", ScenarioID);
		Scenario = Scenario.replace("vSERIALNUMBER", SlNum);
		Scenario = Scenario.replace("vSCENARIOTITLE", ScenarioTitle);
		Scenario = Scenario.replace("vSCENARIOSTATUS", GetScenarioStatus(ScenarioStatus));

		Scenario = Scenario.replace("vEXECUTIONDURATION", Duration);

		// System.out.println(Scenario);

		return Scenario;
	}

	public static String GetScenarioStatus(String Status) {
		Status = Status.toUpperCase().trim();
		String Result = "<span class= ~fa fa-fw fa-check-circle ~></span>";
		switch (Status) {
		case "PASSED":
			Result = "  <span class=~fa fa-fw fa-check-circle ~></span>";
			break;
		case "FAILED":
			Result = " <span class=~fa fa-fw fa-times-circle~></span>";
			break;
		case "SKIPPED":
			Result = "<span class=~fa fa-fw fa-ban~></span>";
			break;
		default:
			Result = "<span class= ~fa fa-fw fa-check-circle ~></span>";
			break;
		}
		return Result;
	}

	public static String AddScenarioSteps(String JourneyName, String Platform, String ScenarioID, String ScenarioTitle,
			String ScenarioStatus, String Severity, String Description, String Tags, String Duration,
			String ScenarioResultSteps) {
		String Steps = new String();
		Steps = "    <div id=~vJOURNEYID_vPLATFORM_vSCENARIOID~ class=~TCID~ style=~display: none;~>\r\n"
				+ "        <div class=~subtitle long-line line-ellipsis~>\r\n"
				+ "            <span> vJOURNEYNAME - vPLATFORM: vSCENARIOTITLE</span>\r\n" + "        </div>\r\n"
				+ "        <h2 class=~pane_title~>\r\n" + "            <div class=~test-result_status~>\r\n"
				+ "                <span class=~status_label status_vSCENARIOSTATUS~>vSCENARIOSTATUS</span>\r\n"
				+ "            </div>\r\n" + "            <div class=~test-result_name~>\r\n"
				+ "                <span class=~long-line~>vSCENARIOTITLE</span>\r\n" + "            </div>\r\n"
				+ "        </h2>\r\n" + "\r\n" + "        <div class=~test-result_content~>\r\n"
				+ "            <div class=~pane_section~>\r\n"
				+ "                <div class=~test-result-overview__tags~><br>\r\n"
				+ "                    vTAGS</div>\r\n" + "                    <div class=~pane__section~></div>\r\n"
				+ "                    <div class=~pane__section~>Severity: vSEVERITY</div>\r\n"
				+ "                    <div class=~pane__section~> <span>\r\n"
				+ "                            Duration:\r\n"
				+ "                            <span class=~fa fa-clock-o~></span>\r\n"
				+ "                            vEXECUTIONDURATION\r\n" + "                        </span>\r\n"
				+ "                    </div>\r\n" + "                </div>\r\n" + "\r\n"
				+ "                <div class=~pane__section~>\r\n"
				+ "                    <h3 class=~result_section-title~>Description</h3>\r\n"
				+ "                    <div class=~description__text~>\r\n"
				+ "                        vTESTDESCRIPTION\r\n" + "                    </div>\r\n"
				+ "                </div>\r\n" + "                <div class=~pane__section~>\r\n"
				+ "                    <h3 class=~result_section-title~>Execution</h3>\r\n"
				+ "                    <div class=~panel-heading collapsed~ data-toggle=~collapse~\r\n"
				+ "                        data-target=~#bar~>\r\n"
				+ "                        <i class=~fa fa-fw fa-chevron-down~></i>\r\n"
				+ "                        <i class=~fa fa-fw fa-chevron-right~></i>Test Body\r\n"
				+ "                    </div>\r\n"
				+ "                    <div class=~panel-body collapse show~ id=~bar~>\r\n"
				+ "                        <ul id=~stepsUL~>\r\n" + "                            vDETAILEDSTEPS\r\n"
				+ "                        </ul>\r\n" + "                    </div>\r\n" + "                </div>\r\n"
				+ "            </div>\r\n" + "        </div>\r\n";

		Steps = Steps.replace("vJOURNEYID", JourneyName.replace(" ", ""));
		Steps = Steps.replace("vJOURNEYNAME", JourneyName);
		Steps = Steps.replace("vPLATFORM", Platform);
		Steps = Steps.replace("vSCENARIOTITLE", ScenarioTitle);
		Steps = Steps.replace("vSCENARIOSTATUS", ScenarioStatus.toUpperCase());
		Steps = Steps.replace("vSEVERITY", Severity);
		Steps = Steps.replace("vTESTDESCRIPTION", Description);
		Steps = Steps.replace("vEXECUTIONDURATION", Duration);
		Steps = Steps.replace("vSCENARIOID", ScenarioID);
		Steps = Steps.replace("vDETAILEDSTEPS", ScenarioResultSteps);
		Steps = Steps.replace("vTAGS", String.valueOf(AddScenarioTags(Tags)));

		// Steps=Steps.replace('~','"');
		// System.out.println(Steps);
		return Steps;

	}

	public static String AddDetailedScenarioSteps(String Title, String StepStatus, String ScreenshotLink,
			String ErrorMsg) {
		String DetialedSteps = new String();
		DetialedSteps = "   <li class=\"vSTEPSTATUS\">\r\n"
				+ "   <span class=\"caret\"> vSTEPTITLE</span>    <ul class=~nested~>\r\n"
				+ "        <li><span class=~caret screenshot~>Screenshot</span>\r\n"
				+ "            <ul class=~nested~>\r\n"
				+ "                <a class=~downloadimg~ href=~vSCREENSHOTLINK~ target=~blank~\r\n"
				+ "                    download>\r\n"
				+ "                    <img src=~vSCREENSHOTLINK~ width=~250~ height=~150~><br>\r\n"
				+ "                </a>\r\n" + "            </ul>\r\n" + "        </li>\r\n" + "        vERRORMSG\r\n"
				+ "    </ul></li>";

		DetialedSteps = DetialedSteps.replace("vSTEPTITLE", Title);
		DetialedSteps = DetialedSteps.replace("vSCREENSHOTLINK", ScreenshotLink);
		DetialedSteps = DetialedSteps.replace("vSTEPSTATUS", StepStatus.toLowerCase());

		if (ErrorMsg != "") {
			String ErrMsg = " <li><span class=~caret error~>Error</span>\r\n"
					+ "                                                                    <ul class=~nested~>\r\n"
					+ "                                                                        <p>vERROR</p>\r\n"
					+ "                                                                    </ul>\r\n"
					+ "                                                                </li>";
			ErrMsg = ErrMsg.replace("vERROR", ErrorMsg);
			DetialedSteps = DetialedSteps.replace("vERRORMSG", ErrMsg);
		} else {
			DetialedSteps = DetialedSteps.replace("vERRORMSG", "");
		}

		// DetialedSteps=DetialedSteps.replace("~",~);
		// System.out.println(DetialedSteps);

		return DetialedSteps;

	}

	public static StringBuilder AddScenarioTags(String TagNames) {
		String[] arrOfStr = TagNames.split(",");
		StringBuilder Tags = new StringBuilder();
		for (String TagName : arrOfStr) {
			Tags.append("<span class=~label label__info~>" + TagName.toUpperCase()
					+ "</span><span style=~margin-right:2px;background:transparent~></span>");
		}
		return Tags;
	}

	public static void SaveHTMLResultFile(String FileContent) {

		try {

			Date date = new Date();
			long time = date.getTime();
			Timestamp ts = new Timestamp(time);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String FilePath = System.getProperty("user.dir") + "\\Reports\\";
			String Filename = FilePath + "ExecutionReport_" + formatter.format(ts) + ".html";
			File myObj = new File(Filename);
			if (myObj.createNewFile()) {
				FileWriter myWriter = new FileWriter(Filename);
				myWriter.write(FileContent);
				myWriter.close();
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

};