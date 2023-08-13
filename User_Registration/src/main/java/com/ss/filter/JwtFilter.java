package com.ss.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ss.helper.JwtUtil;
import com.ss.security.config.CustomUserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserService customUserDetailsService;

	private final Logger log = LoggerFactory.getLogger(JwtFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		String requestTokenHeader = request.getHeader("Authorization");
		
		String userName = null;
		
		String jwtToken = null;
		
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				userName = this.jwtUtil.getUsernameFromToken(jwtToken);
			} catch (ExpiredJwtException e1) 
			{
				log.error("This token is Expired Please Generate new Token......");
				
			} catch (MalformedJwtException e2) 
			{
				log.error("Please Inter Valid Token....");
				
			} catch (RuntimeException e) 
			{
				log.error("Internal Error Please Try Again.....");
				
			}
		}

		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

			if (this.jwtUtil.validateToken(jwtToken, userDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}

		}
		
			filterChain.doFilter(request, response);

	}

}
