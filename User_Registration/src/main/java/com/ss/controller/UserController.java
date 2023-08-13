package com.ss.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ss.constant.ConstantsMessage;
import com.ss.request.PaginationRequest;
import com.ss.request.PasswordUpdateRequest;
import com.ss.request.UserRequest;
import com.ss.response.GeneralResponse;
import com.ss.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/user")
public class UserController {

	private final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;

	@Operation(summary = "User Registration", description = "User Registration all Role MANAGER APPRAISER PO")
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ConstantsMessage.SAVE_SUCCESSFULLY),
			
			@ApiResponse(responseCode = "400", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE,
			
			content = {@Content(examples = { @ExampleObject(value = "") }) }),
			
			@ApiResponse(responseCode = "404", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, 
			
			content = {@Content(examples = { @ExampleObject(value = "") }) }) })
	
	@PreAuthorize("hasRole('MANAGER')")
	@PostMapping("/register")
	public ResponseEntity<GeneralResponse> registerUserAll(@RequestBody  @Valid  UserRequest userRequest) 
	{
		log.info(" inside the usercontroller  all user register api called");
		return userService.userRegister(userRequest);
	}
	
	@Operation(summary = "User find by id", description = "User find  by id all Role MANAGER APPRAISER PO")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ConstantsMessage.FOUND_SUCCESSFULLY),
			@ApiResponse(responseCode = "400", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }),
			@ApiResponse(responseCode = "404", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }) })
	
	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping("/getbyid")
	public ResponseEntity<GeneralResponse> getSingleUserById( @RequestParam("id") long userId)
	{
		log.info(" inside the user controller get single user by id api called");
		return userService.userGetById(userId);
		
	}
	
	@Operation(summary = "User find all ", description = "User  all Role MANAGER APPRAISER PO")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ConstantsMessage.SAVE_SUCCESSFULLY),
			@ApiResponse(responseCode = "400", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }),
			@ApiResponse(responseCode = "404", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }) })
	
	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping("/get")
	public ResponseEntity<GeneralResponse> getAllUserBySerchingText(@RequestBody @Valid PaginationRequest paginationRequest)
	{
		log.info(" inside the user controller getAllUserBySerchingText api called");
		return userService.userGet(paginationRequest);
		
	}

	@Operation(summary = "User Update", description = "User Update all Role MANAGER APPRAISER PO")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ConstantsMessage.UPDATE_SUCCESSFULLY),
			@ApiResponse(responseCode = "400", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }),
			@ApiResponse(responseCode = "404", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }) })
	
	@PreAuthorize("hasRole('MANAGER')")
	@PutMapping("/update")
	public ResponseEntity<GeneralResponse> updateAllUser(@RequestBody @Valid UserRequest userRequest)
	{
		log.info(" inside the user controller updateAllUser api called");
		return userService.userUpdate(userRequest);
		
	}
	
	@Operation(summary = "User Delet", description = "User Delete all Role MANAGER APPRAISER PO")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ConstantsMessage.DELETE_SUCCESSFULLY),
			@ApiResponse(responseCode = "400", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }),
			@ApiResponse(responseCode = "404", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }) })
	
	@PreAuthorize("hasRole('MANAGER')")
	@DeleteMapping("/delete")
	public ResponseEntity<GeneralResponse> deletUserById(@RequestParam ("request_id") long userId)
	{
		log.info(" inside the user controller deleteUserById api called");
		return userService.userDelete(userId);
		
	}
	
	@Operation(summary = "APPRAISER get po ", description = "APPRAISER get  Role  APPRAISER PO")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ConstantsMessage.FOUND_SUCCESSFULLY),
			@ApiResponse(responseCode = "400", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }),
			@ApiResponse(responseCode = "404", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }) })
	
	@PreAuthorize("hasRole('APPRAISER')")
	@GetMapping("/get_po")
	public ResponseEntity<GeneralResponse> getAllPo()
	{
		log.info(" inside the user controller get all po  api called");
		return userService.getPo();
		
	}
	
	@Operation(summary = "APPRAISER Delete po ", description = "APPRAISER Delete  Role  APPRAISER PO")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ConstantsMessage.DELETE_SUCCESSFULLY),
			@ApiResponse(responseCode = "400", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }),
			@ApiResponse(responseCode = "404", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }) })
	
	
	@PreAuthorize("hasRole('APPRAISER')")
	@DeleteMapping("/delete_po")
	public ResponseEntity<GeneralResponse> deletePoById(@RequestParam ("id") long propertyOwnerId )
	{
		log.info(" inside the user controller deletepo by ById api called");
		return userService.deletePo(propertyOwnerId );
		
	}
	
	@Operation(summary = "APPRAISER Update po ", description = "APPRAISER Update  Role  APPRAISER PO")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ConstantsMessage.UPDATE_SUCCESSFULLY),
			@ApiResponse(responseCode = "400", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }),
			@ApiResponse(responseCode = "404", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }) })
	
	
	@PreAuthorize("hasRole('APPRAISER')")
	@PutMapping("/update_po")
	public ResponseEntity<GeneralResponse> updatePo(@RequestBody @Valid UserRequest userRequest)
	{
		log.info(" inside the user controller update po api called");
		return userService.updatePo(userRequest);
		
	}
	
	
	@Operation(summary = "APPRAISER Register po ", description = "APPRAISER Register  Role  APPRAISER PO")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ConstantsMessage.SAVE_SUCCESSFULLY),
			@ApiResponse(responseCode = "400", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }),
			@ApiResponse(responseCode = "404", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }) })
	
	
	@PreAuthorize("hasRole('APPRAISER')")
	@PostMapping("/register_po")
	public ResponseEntity<GeneralResponse> registerPo(@RequestBody  @Valid  UserRequest userRequest) 
	{
		log.info(" inside the user controller register po api called");
		return userService.registerPo(userRequest);
		
		
	}
	
	@Operation(summary = "User get person profile", description = "User get person profile")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ConstantsMessage.FOUND_SUCCESSFULLY),
			@ApiResponse(responseCode = "400", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }),
			@ApiResponse(responseCode = "404", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }) })
	
	@PreAuthorize("hasAnyRole('APPRAISER','MANAGER','PO')")
	@GetMapping("/profile")
	public ResponseEntity<GeneralResponse> getProfileOnlyPersional(@RequestParam("id") long userId) 
	{
		log.info(" inside the user controller get profile   api called");
		return userService.getProfile(userId);
	
	}
	
	@Operation(summary = "User Update person profile", description = "User Update person profile")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ConstantsMessage.UPDATE_SUCCESSFULLY),
			@ApiResponse(responseCode = "400", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }),
			@ApiResponse(responseCode = "404", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }) })
	
	@PreAuthorize("hasAnyRole('APPRAISER','MANAGER','PO')")
	@PutMapping("/profile_update")
	public ResponseEntity<GeneralResponse> updateProfileOnlyPersional(@RequestBody @Valid UserRequest userRequest) 
	{
		log.info(" inside the user controller update profile  api called");
		return userService.updateProfile(userRequest);
	
	}
	
	
	@Operation(summary = "password update Remainder ", description = "password update ")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ConstantsMessage.VALID_PASSWORD_SUCCESSFULLY_UPDATE),
			@ApiResponse(responseCode = "400", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }),
			@ApiResponse(responseCode = "404", description = ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE, content = {
					@Content(examples = { @ExampleObject(value = "") }) }) })
	
	@PreAuthorize("hasAnyRole('APPRAISER','MANAGER','PO')")
	@PutMapping("/passwordupdate")
	public ResponseEntity<GeneralResponse> passwordUpdate(@RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) 
	{
		log.info(" inside the user controller passwordUpdate  api called");
		return userService.passwordUpdate(passwordUpdateRequest);
	
	}
	
	
	
}
