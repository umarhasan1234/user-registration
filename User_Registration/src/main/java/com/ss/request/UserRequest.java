package com.ss.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import com.ss.constant.ConstantsMessage;
import lombok.Data;

@Data
public class UserRequest 
{
	@Email(message =ConstantsMessage.VALID_EMAIL)
	private String requestEmail;

	@NotBlank(message =ConstantsMessage.VALID_FIRST_NAME)
	private String requestFirstName;

	@NotBlank(message=ConstantsMessage.VALID_LAST_NAME)
	private String requestLastName;

	@Pattern(regexp = ConstantsMessage.REGEXP_PASSWORD,message=ConstantsMessage.VALID_PASSWORD)
	private String requestPassword;

	@Pattern(regexp = ConstantsMessage.REGEXP_PHONE_NO,message=ConstantsMessage.VALID_PHONE_NO)
	private String requestPhoneNo;
	
	@NotBlank(message =ConstantsMessage.VALID_ROLE)
	private String requestRole;
	
	private long requestId;


}
