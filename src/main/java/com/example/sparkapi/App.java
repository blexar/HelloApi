package com.example.sparkapi;

import static spark.Spark.get;
import static spark.Spark.stop;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.port;

import com.example.controllers.ExceptionController;
import com.example.sparkapi.utils.JsonTransformer;
import com.google.gson.Gson;

public class App {
	
	private static GregorianCalendar startTime=new GregorianCalendar();
	private static int BASE_PORT=1121;
	private static String BASE_URL="/hello/v1";
	private static int currentPort=BASE_PORT;
	
    public static void main( String[] args) {
    	
    	if (args.length>1)
    	{
    		if (args[0].equals("-P"))
    		{
    			try
    			{
    				int port=Integer.parseInt(args[1]);
    				currentPort=port;
    			}
    			catch(NumberFormatException nfe)
    			{
    				System.out.println(args[1]+" is not a valid TCP port number");
    			}
    		}
    	}
    	
    	port(currentPort);
    	
    	JsonTransformer transformer=JsonTransformer.getInstance();
    	
    	ExceptionController.activate(transformer);
    	
    	Logger logger=LoggerFactory.getLogger(App.class);
    	
    	
    	get(BASE_URL+"/greetings/:name", "application/json", (request, response) -> {
    		Map<String, String> ss=request.params();
    		String host=request.host();
    		String name=request.params("name");
    		logger.info("chiamata da "+host);
    		if (name.equals("stopthisserviceplease"))
    			stop();
    		HashMap<String, String> resData=new HashMap<String, String>();
    		resData.put("greeting", "Hello "+name+"!");
    		GregorianCalendar now=new GregorianCalendar();
    		long uptime=(now.getTimeInMillis()-startTime.getTimeInMillis())/1000;
    		resData.put("timestamp", now.getTime().toString());
    		resData.put("start time", startTime.getTime().toString());
    		resData.put("uptime", uptime+" seconds");
    	    return resData;
    	}, transformer);
    	
    	System.out.println("Server running on "+currentPort+" port...");
    	
    	
    	
    }
}