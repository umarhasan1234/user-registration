package com.ss.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ss.constant.ConstantsMessage;
import com.ss.email.EmailSend;
import com.ss.entity.UserEntity;
import com.ss.exception.BadCredentialsExceptionHandle;
import com.ss.exception.EmptyResultDataAccessExceptionHandle;
import com.ss.exception.InternalException;
import com.ss.exception.NullValueException;
import com.ss.exception.InvalidProperty;
import com.ss.exception.NoSuchElementExceptionHandle;
import com.ss.helper.JwtUtil;
import com.ss.repository.UserRepository;
import com.ss.request.JwtRequest;
import com.ss.request.PaginationRequest;
import com.ss.request.PasswordUpdateRequest;
import com.ss.request.UserRequest;
import com.ss.response.GeneralResponse;
import com.ss.response.PaginationResponse;
import com.ss.security.config.CustomUserDetails;
import com.ss.security.config.CustomUserService;


@Service
public class UserServiceImpl implements UserService
{
	private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailSend emailSend;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;

	
	@Override
	public ResponseEntity<GeneralResponse> userRegister(UserRequest userRequest)
	{
		
	try 
	{
		Date date = Date.valueOf(new SimpleDateFormat(ConstantsMessage.DATE_FORMAT).format(Calendar.getInstance().getTime()));
		UserEntity userEntity=new UserEntity();
		userEntity.setEmail(userRequest.getRequestEmail());
		userEntity.setFirstName(userRequest.getRequestFirstName());
        userEntity.setLastName(userRequest.getRequestLastName());
        userEntity.setPassword(passwordEncoder.encode(userRequest.getRequestPassword()));
        userEntity.setPhoneNo(	userRequest.getRequestPhoneNo());
        userEntity.setCreatedAt(date);
		userEntity.setRole("ROLE_"+userRequest.getRequestRole().toUpperCase());
		userEntity.setPasswordUpdated(date);
		log.info("user register  service method called"+date);
		return  new ResponseEntity<GeneralResponse>(new GeneralResponse(ConstantsMessage.SAVE_SUCCESSFULLY,userRepository.save(userEntity)),HttpStatus.OK);
	}
	catch (Exception e)
	{
		log.info("error inside the user register method");
		log.error(e.getLocalizedMessage());
		
		throw new InternalException(ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE);
	}

}

	@Override
	public ResponseEntity<GeneralResponse> userGet(PaginationRequest paginationRequest) {
		
		if (!paginationRequest.getSortBy().equals("email") && !paginationRequest.getSortBy().equals("role")
				&& !paginationRequest.getSortBy().equals("id")
				&& !paginationRequest.getSortBy().equals("first_name")
				&& !paginationRequest.getSortBy().equals("last_name")) {
			throw new InvalidProperty(ConstantsMessage.VALID_PROPERTY_SORT_BY);
		}
		
		try 
		{
			Page<UserEntity> pageUser =null;
			
			Pageable p = PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize(),Sort.by(paginationRequest.getSortBy()));
			
			if(paginationRequest.getSearch()==null)		
			{
				pageUser = userRepository.findAll(p);	
			}
			else
			{
			pageUser = userRepository.search(paginationRequest.getSearch(), p); 
			}
			
			List<UserEntity> userEntityList = pageUser.getContent();
			
			PaginationResponse paginationResponse = new PaginationResponse(pageUser.getNumber(), pageUser.getSize(),pageUser.getTotalPages(), pageUser.getTotalElements(), pageUser.isLast(), pageUser.isFirst());
			
				log.info("user get service  method called" );
				
				List list=new ArrayList<>();
				list.add(paginationResponse);
				list.add(userEntityList);
				
			return new ResponseEntity<GeneralResponse>(new GeneralResponse(ConstantsMessage.FOUND_SUCCESSFULLY,list),HttpStatus.FOUND);
		}
		catch (Exception e)
		{
			log.info(" error in side the user get service method ");
			log.error(e.getLocalizedMessage());
			
			throw new InternalException(ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE);	
		}
		
		}

	@Override
	public ResponseEntity<GeneralResponse> userUpdate(UserRequest userRequest) 
	{
		
		try 
		{
			UserEntity user = userRepository.findById(userRequest.getRequestId()).get();
			
			if(!passwordEncoder.matches(userRequest.getRequestPassword(),user.getPassword()))
			{
				throw new BadCredentialsException(ConstantsMessage.PASSWORD_NOT_CHANGE);
			}
			
			Date date = Date.valueOf(new SimpleDateFormat(ConstantsMessage.DATE_FORMAT).format(Calendar.getInstance().getTime()));
			
			UserEntity userEntity=new UserEntity();
			userEntity.setId(userRequest.getRequestId());
			userEntity.setEmail(userRequest.getRequestEmail());
			userEntity.setFirstName(userRequest.getRequestFirstName());
	        userEntity.setLastName(userRequest.getRequestLastName());
	        userEntity.setPassword(passwordEncoder.encode(userRequest.getRequestPassword()));
	        userEntity.setPhoneNo(	userRequest.getRequestPhoneNo());
	        userEntity.setCreatedAt(user.getCreatedAt());
	        userEntity.setUpdatedAt(date);
			userEntity.setRole("ROLE_"+userRequest.getRequestRole().toUpperCase());
			log.info("user update service method called ");
			
			return  new ResponseEntity<GeneralResponse>(new GeneralResponse(ConstantsMessage.UPDATE_SUCCESSFULLY,userRepository.save(userEntity)),HttpStatus.OK);
		}
		
		catch (NoSuchElementException e)
		{
			log.error(" user UPDATE by id service method but id not found");
			log.error(e.getMessage());
			throw new NoSuchElementException(ConstantsMessage.ID_NOT_FOUND);
		}
		
		catch (BadCredentialsException e)
		{
		
			log.info("error inside user update service method");
			log.error(e.getLocalizedMessage());
			throw new BadCredentialsExceptionHandle(ConstantsMessage.PASSWORD_NOT_CHANGE);
		}
		catch (Exception e)
		{
			log.info("error inside user update service method");
			log.error(e.getLocalizedMessage());
			
			throw new InternalException(ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE);
		}
	}

	@Override
	public ResponseEntity<GeneralResponse> userDelete(long userId) {
		try
		{
			if(!userRepository.existsById(userId))
			{
			throw new NoSuchElementException(ConstantsMessage.ID_NOT_FOUND);
			}
		userRepository.deleteById(userId);
		log.info("user delete service method called");
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(ConstantsMessage.DELETE_SUCCESSFULLY),HttpStatus.OK);
		}
		catch (EmptyResultDataAccessException e) 
		{
			log.error(" user delete service method called but id not found in database");
			throw new EmptyResultDataAccessExceptionHandle(ConstantsMessage.DELETE_ID_NOT_FOUND);
		}
		catch (Exception e)
		{
			log.error("error inside user delete service method");
			log.error(e.getLocalizedMessage());
			
			throw new InternalException(ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE);
		}
	}

	@Override
	public ResponseEntity<GeneralResponse> userGetById(long userId) {
		try
		{
			if(!userRepository.existsById(userId))
			{
				throw new NoSuchElementException(ConstantsMessage.ID_NOT_FOUND);
			}
			
		UserEntity userEntity = userRepository.findById(userId).get();
		log.info("user get by id service method called ");
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(ConstantsMessage.FOUND_SUCCESSFULLY,userEntity),HttpStatus.OK);
		}
		catch (NoSuchElementException e)
		{
			log.error("error inside the user get by id   service method ");
			log.error(e.getMessage());
			throw new NoSuchElementException(ConstantsMessage.ID_NOT_FOUND);
		}
		
		catch (Exception e)
		{
			log.error("error inside the user get by id service method");
			log.error(e.getLocalizedMessage());
			
			throw new InternalException(ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE);
		}
	}

	@Override
	public ResponseEntity<GeneralResponse> getPo() {
		try
		{
		List<UserEntity> userEntity = userRepository.findByRole("ROLE_PO");
				
		log.info("get po list service method callled");
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(ConstantsMessage.FOUND_SUCCESSFULLY,userEntity),HttpStatus.OK);
		}	
		catch (Exception e)
		{
			log.error("error inside the get po service method ");
			log.error(e.getLocalizedMessage());
			throw new InternalException(ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE);
		}
		
	}

	@Override
	public ResponseEntity<GeneralResponse> deletePo(long propertyOwnerId ) {
		try
		{
		UserEntity userEntity = userRepository.findById(propertyOwnerId ).get();
		
		if(userEntity.getRole().equals("ROLE_PO"))
		{
			userRepository.deleteById(propertyOwnerId );
			return new ResponseEntity<GeneralResponse>(new GeneralResponse(ConstantsMessage.DELETE_SUCCESSFULLY),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<GeneralResponse>(new GeneralResponse(ConstantsMessage.DELETE_ID_NOT_FOUND),HttpStatus.BAD_REQUEST);
		}
		}
		catch (NoSuchElementException e)
		{
			log.error("error inside the delete po service method ");
			log.error(e.getMessage());
			throw new NoSuchElementException(ConstantsMessage.ID_NOT_FOUND);
		}
		
		catch (Exception e)
		{
			log.error(e.getLocalizedMessage());
			log.error("error inside the delete po service method ");
			throw new InternalException(ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE);
		}
		
	}

	@Override
	public ResponseEntity<GeneralResponse> updatePo(UserRequest userRequest)
	{
		try
		{
			UserEntity userEntity = userRepository.findById(userRequest.getRequestId()).get();
			
			if(!passwordEncoder.matches(userRequest.getRequestPassword(),userEntity.getPassword()))
			{
				throw new BadCredentialsException(ConstantsMessage.PASSWORD_NOT_CHANGE);
			}
			
		
		if(userEntity.getRole().equals("ROLE_PO")&&("ROLE_"+userRequest.getRequestRole().toUpperCase()).equals("ROLE_PO"))
		{
			Date date = Date.valueOf(new SimpleDateFormat(ConstantsMessage.DATE_FORMAT).format(Calendar.getInstance().getTime()));
			
			UserEntity user= new UserEntity();
			
			 user.setEmail(userRequest.getRequestEmail());
			 user.setFirstName( (userRequest.getRequestFirstName()));             ;
			 user.setLastName(userRequest.getRequestLastName());
			 user.setPhoneNo(	userRequest.getRequestPhoneNo());
			 user.setUpdatedAt(date);
			 user.setCreatedAt(userEntity.getCreatedAt());
			 user.setId(userEntity.getId());
			 user.setRole(userEntity.getRole());
			 user.setPassword(userEntity.getPassword());
			 log.info("update po service method called");
			 return new ResponseEntity<GeneralResponse>(new GeneralResponse(ConstantsMessage.UPDATE_SUCCESSFULLY,userRepository.save(user)),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<GeneralResponse>(new GeneralResponse(ConstantsMessage.VALID_ROLE_AND_ID),HttpStatus.BAD_REQUEST);
		}
		}
		catch (BadCredentialsException e)
		{
		
			log.info("error inside user update service method");
			log.error(e.getLocalizedMessage());
			throw new BadCredentialsExceptionHandle(ConstantsMessage.PASSWORD_NOT_CHANGE);
		}
		
		catch (NoSuchElementException e)
		{
			log.error(e.getMessage());
			throw new NoSuchElementExceptionHandle(ConstantsMessage.ID_NOT_FOUND);
		}
		catch (Exception e)
		{
			log.error("error inside the update po service method");
			
			throw new InternalException(ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE);
		}
		
		
	}

	@Override
	public ResponseEntity<GeneralResponse> getProfile(long usertId)
	
	{
		Authentication authentication = SecurityContextHolder. getContext(). getAuthentication(); 
		try 
		{		
			UserEntity userEntity = userRepository.findById(usertId).get();
		if(authentication.getName().equals(userEntity.getEmail()))
		{	
			return new ResponseEntity<GeneralResponse>(new GeneralResponse(ConstantsMessage.FOUND_SUCCESSFULLY,userEntity),HttpStatus.OK);
		}
		else
		{
			throw new EmptyResultDataAccessExceptionHandle(ConstantsMessage.ID_NOT_FOUND);
		}
		
		}
		
		catch (EmptyResultDataAccessException e) {

			throw new EmptyResultDataAccessExceptionHandle(ConstantsMessage.ID_NOT_FOUND);

		}	
		catch (Exception e) {

			throw new InternalException(ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE);

		}		
	}

	@Override
	public ResponseEntity<GeneralResponse> updateProfile(UserRequest userRequest) {
		
		{
			
			try 
			{
				Authentication authentication = SecurityContextHolder. getContext(). getAuthentication(); 
				
				UserEntity	userEntity = userRepository.findById(userRequest.getRequestId()).get();
				if(authentication.getName().equals(userEntity.getEmail()))
				{
				Date date = Date.valueOf(new SimpleDateFormat(ConstantsMessage.DATE_FORMAT).format(Calendar.getInstance().getTime()));
					
					UserEntity user=new UserEntity();
					user.setId(userRequest.getRequestId());
					user.setEmail(userRequest.getRequestEmail());
					user.setFirstName(userRequest.getRequestFirstName());
					user.setLastName(userRequest.getRequestLastName());
					user.setPassword(passwordEncoder.encode(userRequest.getRequestPassword()));
					user.setPhoneNo(userRequest.getRequestPhoneNo());
			        user.setUpdatedAt(date);
					user.setRole(userEntity.getRole());
					user.setCreatedAt(userEntity.getCreatedAt());
					if(!passwordEncoder.matches(userRequest.getRequestPassword(),userEntity.getPassword()))
					{
						user.setPasswordUpdated(date);
                       emailSend.send(userEntity.getEmail(),ConstantsMessage.EMAIL_PASSWORD_CHANGE +" "+userEntity.getFirstName()+" "+ userEntity.getLastName());					
					}
					return new ResponseEntity<GeneralResponse>(new GeneralResponse(ConstantsMessage.UPDATE_SUCCESSFULLY,userRepository.save(user)),HttpStatus.OK);
				}
				else
				{
					throw new EmptyResultDataAccessExceptionHandle(ConstantsMessage.ID_NOT_FOUND);
				}
				
			}
			catch (Exception e) {

				throw new InternalException(ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE);

			}			
			
		}
	}

	@Override
	public ResponseEntity<GeneralResponse> generateToken(JwtRequest jwtRequest) {
		try {
			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getUserPassword()));
		} 
		
		catch(NullPointerException e)
		{
			throw new NullValueException(ConstantsMessage.VALID_USER_NAME);
		}
		catch(BadCredentialsException e)
		{
			throw new BadCredentialsExceptionHandle(ConstantsMessage.VALID_USER_PASSWORD);
		}
		
		catch (Exception e) 
		{
			throw new InternalException(ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE);
		}

		try 
		{
		CustomUserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUserName());
		
		String token = jwtUtil.generateToken(userDetails);
		
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(token), HttpStatus.OK);
		}
		catch(Exception e)
		{
			throw new InternalException(ConstantsMessage.VALID_USER_PASSWORD_AND_NAME);
		}
		
		
	}

	@Override
	public ResponseEntity<GeneralResponse> registerPo(UserRequest userRequest) {
		int flag = userRequest.getRequestRole().compareToIgnoreCase("po");
		if(flag==0)
		{
			return userRegister(userRequest);
		}
		else
		{
			return new ResponseEntity<GeneralResponse>(new GeneralResponse(ConstantsMessage.VALID_ROLE),HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<GeneralResponse> passwordUpdate(@Valid PasswordUpdateRequest passwordUpdateRequest) {
		
		try
		{
			
			UserEntity userEntity = userRepository.findByEmail(passwordUpdateRequest.getRequestEmail());
			
			Authentication authentication = SecurityContextHolder. getContext(). getAuthentication(); 
			
			
			if(authentication.getName().equals(userEntity.getEmail()))
			{
		
		if(passwordUpdateRequest.getRequestOldPassword().equals(passwordUpdateRequest.getRequestNewPassword()))
		{
			return new ResponseEntity<GeneralResponse>(new GeneralResponse(ConstantsMessage.VALID_NEW_PASSWORD),HttpStatus.BAD_REQUEST);
		}

		if(!passwordEncoder.matches(passwordUpdateRequest.getRequestOldPassword(),userEntity.getPassword()))
		{
			return new ResponseEntity<GeneralResponse>(new GeneralResponse(ConstantsMessage.VALID_OLD_PASSWORD),HttpStatus.BAD_REQUEST);
		}
			}
			else
			{
				return new ResponseEntity<GeneralResponse>(new GeneralResponse(ConstantsMessage.EMAIL_PASSWORD_CHANGE),HttpStatus.BAD_REQUEST);
			}
		userEntity.setPassword(passwordEncoder.encode(passwordUpdateRequest.getRequestNewPassword()));
		
		userRepository.save(userEntity);
		
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(ConstantsMessage.VALID_PASSWORD_SUCCESSFULLY_UPDATE),HttpStatus.OK);
		
		}
		catch (Exception e) {

			throw new InternalException(ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE);

		}	
		
	}
}

