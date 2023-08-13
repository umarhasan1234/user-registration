package com.ss.exception;

public class InvalidProperty extends RuntimeException
{
	
	private static final long serialVersionUID = 2399982609080659024L;

	public InvalidProperty(String msg)
	{
		super(msg);
	}

}
