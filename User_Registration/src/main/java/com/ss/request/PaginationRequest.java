package com.ss.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import com.ss.constant.ConstantsMessage;
import lombok.Data;

@Data
public class PaginationRequest {

	@Min(0)
	private int pageNumber;
	
	@Min(1)
	private int pageSize;
	
	@NotBlank(message =ConstantsMessage.VALID_PROPERTY_SORT_BY)
	private String sortBy = "id";
	
	private String search;

}
