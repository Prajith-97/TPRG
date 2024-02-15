package reports;

import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;


public class PostCalljson   {
	

	
		
	
		  
	 public void postCall() 
			   {
			    CloseableHttpClient client = HttpClients.createDefault();
			    HttpPost httpPost = new HttpPost("https://lcxicrka9a.execute-api.eu-west-2.amazonaws.com/tfg/result");
			    try {
			    	String sje_id =System.getProperty("sje_id");

				    System.out.println("Execution ID : "+sje_id);
				    
			    	JSONParser parser = new JSONParser();
			    JSONArray a = (JSONArray) parser .parse(new FileReader(System.getProperty("user.dir") +"\\src\\test\\java\\reports\\output.json"));
			    //String request = a.toJSONString(); //converting string to array
			    JSONObject ob=new JSONObject();
			    ob.put("sje_id",Integer.parseInt(sje_id));
			    ob.put("result",a);
			    StringEntity entity = new StringEntity(ob.toJSONString());
			    httpPost.setEntity(entity);
			    httpPost.setHeader("Accept", "application/json");
			    httpPost.setHeader("Content-type", "application/json");
			    CloseableHttpResponse response = client.execute(httpPost);
			    BufferedReader br = new BufferedReader(new     
			    		InputStreamReader((response.getEntity().getContent())));
			    StringBuffer result = new StringBuffer();
			    String line = "";
			    while ((line = br.readLine()) != null) {
			    System.out.println("Response : \n"+result.append(line));
			    }	
			    }
  			    catch (Exception e) { 
			    	// TODO Auto-generated catch block
			   	 e.printStackTrace();
			   	 }
			    
			   }
			   

	
	public void  PostScreenshotjson() throws IOException
	
	{
		File folder = new File(System.getProperty("user.dir") + "\\src\\test\\java\\reports\\screenshots");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile()) {
		    System.out.println("File " + listOfFiles[i].getAbsolutePath());
		    
		    
		    		
			  FileBody fileBody = new FileBody(listOfFiles[i], ContentType.IMAGE_PNG);
			  
			  MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			  builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			  
					/*
				 * builder.addBinaryBody ("file", listOfFiles[i], ContentType.IMAGE_PNG,
				 * listOfFiles[i].getName() );
				 */
			  
			  builder.addPart("file", fileBody); 
			  builder.addTextBody("project_code", "TFGHOBBS001");
			  
			  System.out.println(fileBody);
			  HttpEntity entity = builder.build();
			  //HttpPost request = new HttpPost("https://33v4rx8sce.execute-api.eu-west-2.amazonaws.com/development/upload");
			  HttpPost request = new HttpPost("https://5j3vga9j26.execute-api.eu-west-2.amazonaws.com/tfg/upload");
			  request.setEntity(entity);
			 // request.setHeader("Content-Type", ContentType.IMAGE_PNG.toString());
			 //setHeader("Content-Disposition", "inline");
			  
			  
			  HttpClient client = HttpClientBuilder.create().build(); try {
				  HttpResponse httpresponse = client.execute(request);
				  System.out.println(EntityUtils.toString(httpresponse.getEntity()));
			      System.out.println(httpresponse.getStatusLine());
			  
	
			      
			  } catch (IOException e)
			  { 
				  e.printStackTrace();
				  }
			  }
			 
		}
		
		
	}
	
			   
}
	    
		  
	
		
			   
		  






