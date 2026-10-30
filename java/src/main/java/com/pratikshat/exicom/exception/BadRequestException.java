package com.pratikshat.exicom.exception;

public class BadRequestException extends Exception{
	
	private static final long serialVersionUID = 7223835588340095223L;
	String message;
	public BadRequestException() {
		super();
	}
	public BadRequestException(String message) {
		super(message);
		this.message=message;
	}
	public BadRequestException(Exception ex) {
		super(ex);
		this.message=ex.getMessage();
	}
	public String toString(){
		return this.message;
	}
}
