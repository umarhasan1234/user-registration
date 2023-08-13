package com.ss.exception;

public class BadCredentialsExceptionHandle extends RuntimeException
{
	
	
	private static final long serialVersionUID = -8270863509023648778L;

	public BadCredentialsExceptionHandle(String msg)
	{
		super(msg);
	}

}

