package com.ss.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.constant.ConstantsMessage;
import com.ss.request.JwtRequest;
import com.ss.response.GeneralResponse;
import com.ss.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RequestMapping("/jwt")
@RestController
public class GenerateTokenController {
	
	@Autowired
	private UserService userService;
	 
	private  Logger log=LoggerFactory.getLogger(GenerateTokenController.class);
	
	@Operation(summary = "Inter username and password then generate token", 
			
	           description = "Inter username and password then generate token all Role MANAGER APPRAISER PO")
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200",
	
	                      description = "ok response"),
			
	@ApiResponse(responseCode = "400", 
	
	             description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE,
	             
	             content = {@Content(examples = { @ExampleObject(value = "") }) }),
	
  @ApiResponse(responseCode = "404", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, 
   
               content = {@Content(examples = { @ExampleObject(value = "") }) }) })
	
	@PostMapping("/token")
	public ResponseEntity<GeneralResponse> generateToken(@RequestBody  @Valid JwtRequest jwtRequest) 
	{
		log.info("inside the generate token controller token generate api called");
		
		return userService.generateToken(jwtRequest);
	}

}
