package com.example.controllers;

import static spark.Spark.exception;

import com.example.sparkapi.exceptions.InternalServerException;
import com.example.sparkapi.exceptions.NotValidDataException;
import com.example.sparkapi.exceptions.NotValidKeyException;
import com.example.sparkapi.exceptions.ResourceNotFoundException;
import com.example.sparkapi.utils.JsonTransformer;

public class ExceptionController {
	
	private static ExceptionController instance=null;
	private JsonTransformer transformer;
	
	private ExceptionController(JsonTransformer transformer)
	{
		this.transformer=transformer;
		exception(InternalServerException.class, (exception, request, response) -> {
    		response.status(501);
			response.body(transformer.renderError(exception.getMessage()));
    	});
    	
    	exception(ResourceNotFoundException.class, (exception, request, response) -> {
    		response.status(404);
			response.body(transformer.renderError(exception.getMessage()));
    	});
    	
    	exception(NotValidKeyException.class, (exception, request, response) -> {
    		response.status(400);
			response.body(transformer.renderError(exception.getMessage()));
    	});
    	
    	exception(NotValidDataException.class, (exception, request, response) -> {
    		response.status(400);
			response.body(transformer.renderError(exception.getMessage()));
    	});
	}
	
	public static ExceptionController activate(JsonTransformer transformer)
	{
		if (instance==null)
			instance=new ExceptionController(transformer);
		return instance;
	}
	
	

}
