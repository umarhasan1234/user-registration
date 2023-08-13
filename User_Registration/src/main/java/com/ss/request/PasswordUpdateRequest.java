package com.ss.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import com.ss.constant.ConstantsMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordUpdateRequest {
	
	@Email(message =ConstantsMessage.VALID_EMAIL)
	private String requestEmail;

	@Pattern(regexp = ConstantsMessage.REGEXP_PASSWORD,message=ConstantsMessage.VALID_PASSWORD)
	private String requestOldPassword;
	
	@Pattern(regexp = ConstantsMessage.REGEXP_PASSWORD,message=ConstantsMessage.VALID_PASSWORD)
	private String requestNewPassword;

}
