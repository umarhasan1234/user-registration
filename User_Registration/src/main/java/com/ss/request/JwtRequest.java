package com.ss.request;

import javax.validation.constraints.NotBlank;

import com.ss.constant.ConstantsMessage;

import lombok.Data;

@Data
public class JwtRequest {
	
	@NotBlank(message =ConstantsMessage.VALID_USER_NAME)
	private String userName;
	
	@NotBlank(message =ConstantsMessage.VALID_PASSWORD)
	private String userPassword;

}
