package com.springpractice.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springpractice.service.MyUserDetailsService;
import com.springpractice.utils.JwtTokenUtil;

/**
 * 
 * @author afnanrehman
 * 
 *  The JwtRequestFilter extends the Spring Web Filter
 *  OncePerRequestFilter class. For any incoming request, this Filter
 *  class gets executed. It checks if the request has a valid JWT token.
 *  If it has a valid JWT Token, then it sets the authentication in
 *  context to specify that the current user is authenticated.
 **/
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	private MyUserDetailsService userDetailsService;
	private JwtTokenUtil jwtTokenUtil;
	private final String AUTHENTICATION = "Authorization";
	private final String BEARER = "Bearer ";

	public JwtRequestFilter(MyUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader(AUTHENTICATION);
		String token = null;
		String username = null;

		/*
		 * JWT Token is in the form "Bearer token". Remove Bearer word and get only the
		 * Token.
		 */
		if (null != requestTokenHeader && requestTokenHeader.startsWith(BEARER)) {
			token = requestTokenHeader.substring(7);
			username = jwtTokenUtil.getUsernameFromToken(token);
		}

		// Once we get the token validate it.
		if (null != username && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			/*
			 * If token is valid configure Spring Security to manually set authentication
			 */
			if (jwtTokenUtil.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				/*
				 * After setting the Authentication in the context, we specify that the current
				 * user is authenticated. So it passes the Spring Security Configurations
				 * successfully.
				 */
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
