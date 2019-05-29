package com.example.ei1027.validation.excepcions;

public class ActivitatException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;
	String name;
	
	public ActivitatException(String message, String name)
	   {
	       this.message=message;
	       this.name=name;
	   }

	   public String getMessage() {
	       return message;
	   }

	   public void setMessage(String message) {
	       this.message = message;
	   }

	   public String getName() {
	       return name;
	   }
	   public void setName(String name) {
	       this.name = name;
	   }
}
