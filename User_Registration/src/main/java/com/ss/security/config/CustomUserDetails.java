package com.ss.security.config;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ss.entity.UserEntity;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class CustomUserDetails implements UserDetails
{

	private static final long serialVersionUID = 8015253558311061965L;
	
	private UserEntity userEntity ;

	public CustomUserDetails(UserEntity userEntity) 
	{
		this.userEntity=userEntity;

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{

		HashSet<SimpleGrantedAuthority> set = new HashSet<>();
		set.add(new SimpleGrantedAuthority(userEntity.getRole()));
		return set;

	}

	@Override
	public String getPassword()
	{
		return userEntity.getPassword();
	}

	@Override
	public String getUsername() 
	{
		return userEntity.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() 
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked() 
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() 
	{
		return true;
	}

	@Override
	public boolean isEnabled() 
	{
		return true;
	}

}
