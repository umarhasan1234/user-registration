package com.ss.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponse
{

	private String message;
	
	private Object data;

	public GeneralResponse(String message) 
	{
		super();
		this.message = message;
	}
	
	

}
