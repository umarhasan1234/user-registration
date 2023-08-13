package com.ss.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import com.ss.entity.UserEntity;
import com.ss.repository.UserRepository;


@Component
public class CustomUserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public CustomUserDetails loadUserByUsername(String username) 
	{

		UserEntity userEntity = userRepository.findByUserName(username);
		
		return new CustomUserDetails(userEntity);
	}

}
