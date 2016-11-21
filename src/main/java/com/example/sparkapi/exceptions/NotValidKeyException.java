package com.example.sparkapi.exceptions;

public class NotValidKeyException extends RuntimeException{

	private String key;
	
	public NotValidKeyException(String key)
	{
		this.key=key;
	}
	
	@Override
	public String getMessage() {
		return key+" is not a valid key";
	}
	
	
}
