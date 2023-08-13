package com.ss.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.ss.request.JwtRequest;
import com.ss.request.PaginationRequest;
import com.ss.request.PasswordUpdateRequest;
import com.ss.request.UserRequest;
import com.ss.response.GeneralResponse;

@Component
public interface UserService 
{

	public ResponseEntity<GeneralResponse> userRegister(UserRequest userRequest);
	
	public ResponseEntity<GeneralResponse> userGet(PaginationRequest paginationRequest);
	
	public ResponseEntity<GeneralResponse> userUpdate(UserRequest userRequest);
	
	public ResponseEntity<GeneralResponse> userDelete(long requestid);
	
	public ResponseEntity<GeneralResponse> userGetById(long requestid);

	public ResponseEntity<GeneralResponse> getPo();

	public ResponseEntity<GeneralResponse> deletePo(long requestid);

	public ResponseEntity<GeneralResponse> updatePo(UserRequest userRequest);

	public ResponseEntity<GeneralResponse> getProfile(long requestid);

	public ResponseEntity<GeneralResponse> updateProfile(UserRequest userRequest);

	public ResponseEntity<GeneralResponse> generateToken(JwtRequest jwtRequest);
	
	public ResponseEntity<GeneralResponse> registerPo(UserRequest userRequest);

	public ResponseEntity<GeneralResponse> passwordUpdate(@Valid PasswordUpdateRequest passwordUpdateRequest);
	
	
	
}
