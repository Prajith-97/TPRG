package reports;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class writejson {
	
	
	
	
	@SuppressWarnings("unchecked")
	public void writejasondetails(String featurename,String description,String platform, String featureendtime, String featurestarttime) throws JsonProcessingException {
			
		 JSONObject jsonObject1 = new JSONObject();	 
//		 JSONArray scenarios;
		 JSONArray steps;
		 JSONObject featuredetails = new JSONObject();
		 featuredetails.put("description",description);
		 featuredetails.put("name",featurename);
		 	
		 JSONArray features = new JSONArray();
		 featuredetails.put("features", features);
		 
		 
		 //feature lists
		  
		 JSONObject jsonfeaturedetails = new JSONObject();     
		 jsonfeaturedetails.put("starttime:", featurestarttime);
		 jsonfeaturedetails.put("endttime:", featureendtime);
		 jsonfeaturedetails.put("platform:", platform);
		 JSONArray scenarios = new JSONArray();
		 jsonfeaturedetails.put("scenarios", scenarios);
		 //scenario
		 
		 JSONObject scenariodetails = new JSONObject();			
		 scenariodetails.put("name:", "name");
		 scenariodetails.put("duration:", "duration");
		 scenariodetails.put("status:", "status");
		 scenariodetails.put("tags:","tags");
		 scenariodetails.put("severity:", "severity");
		 steps = new JSONArray();
		 scenariodetails.put("steps:", steps);
		 
		 
		 JSONObject stepdetails = new JSONObject();
		 stepdetails.put("name:", "name");
		 stepdetails.put("status:", "status");
		 stepdetails.put("screenshot:","screenshot");
		 stepdetails.put("errormessage:", "errormessage");

		 
	         JSONArray feature = new JSONArray();
			 feature.add(jsonfeaturedetails);
			 JSONObject featuelisting = new JSONObject();	
			 featuelisting.put("feature", feature);
			 features.add(featuelisting);
		 
			 JSONArray scenario = new JSONArray();
			 scenario.add(scenariodetails);
			 JSONObject scenariolisting = new JSONObject();	
			 scenariolisting.put("scenario", scenario);
			 scenarios.add(scenariolisting);
			 
			 JSONArray step = new JSONArray();
			 step.add(stepdetails);
			 JSONObject steplisting = new JSONObject();	
			 steplisting.put("step", step);
			 steps.add(steplisting);
		 	 		 		  
		  jsonObject1.putAll(featuredetails);
		  ObjectMapper mapper = new ObjectMapper();
	      
	      try {
	         FileWriter file = new FileWriter("C:\\Users\\VaishakRaju(_G10XIND\\Desktop\\output.json");
	         file.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject1));
	         file.close();
	      } catch (Exception e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	     
	      System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject1));
	   }
	
	@SuppressWarnings("unchecked")
	public void writefeatures(String featurename,String description,String platform, String featurestarttime) {
		JSONObject jsonObject1 = new JSONObject();	 
		 JSONArray scenarios;
		 JSONArray steps;
		 JSONObject featuredetails = new JSONObject();
		 featuredetails.put("description",description);
		 featuredetails.put("name",featurename);
		 	
		 JSONArray features = new JSONArray();
		 featuredetails.put("features", features);
		 
		 
		 //feature lists
		  
		 JSONObject jsonfeaturedetails = new JSONObject();     
		 jsonfeaturedetails.put("starttime:", featurestarttime);
		 jsonfeaturedetails.put("endttime:", "2021-7-10 06:07:31");
		 jsonfeaturedetails.put("platform:", platform);
		 
		 JSONArray feature = new JSONArray();
		 feature.add(jsonfeaturedetails);
		 JSONObject featuelisting = new JSONObject();	
		 featuelisting.put("feature", feature);
		 features.add(featuelisting);
		 
		 
		 jsonObject1.putAll(featuredetails);
		 ObjectMapper mapper = new ObjectMapper();
		 try {
	         FileWriter file = new FileWriter("C:\\Users\\VaishakRaju(_G10XIND\\Desktop\\output.json");
	         file.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject1));
	         file.close();
	      } catch (Exception e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	     
		
	}
}
