package com.example.sparkapi.utils;

import java.util.HashMap;

import com.google.gson.Gson;

import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {

	private Gson gson;
	private static JsonTransformer instance;

    private JsonTransformer() {
		this.gson=new Gson();
	}
    
    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }
    
    public String renderError(String message) {
        HashMap<String, String>  error=new HashMap<String, String>();
    	error.put("error", message);
    	return render(error);
    }
    
    public synchronized static JsonTransformer getInstance()
    {
    	if (instance==null)
    		instance=new JsonTransformer();
    	return instance;
    }
    
    public Gson getGsonInstance()
    {
    	if (instance==null)
    		instance=new JsonTransformer();
    	return gson;
    }
    

}