package com.ss.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse 
{
	private Integer pageNumber;
	
	private Integer pageSize;
	
	private Integer totalPages;
	
	private long totalElements;
	
	private boolean lastPage;
	
	private boolean firstPage;
	
}
