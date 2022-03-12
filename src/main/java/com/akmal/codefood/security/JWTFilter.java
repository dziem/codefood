package com.akmal.codefood.security;

import com.akmal.codefood.api.CommonRs;
import com.akmal.codefood.exception.BadRequestException;
import com.akmal.codefood.util.JWTUtil;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired private UserDetailsService userDetailsService;
    @Autowired private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")){
            String jwt = authHeader.substring(7);
            if (jwt == null || jwt.isBlank()) {
                unauthorizedResponse(response);
            } else {
                try{
                    String username = jwtUtil.validateTokenAndRetrieveSubject(jwt);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
                    if(SecurityContextHolder.getContext().getAuthentication() == null){
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }catch(JWTVerificationException exc){
                    unauthorizedResponse(response);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private void unauthorizedResponse(HttpServletResponse response) throws ServletException, IOException {
        CommonRs responseObj = new CommonRs(false, "Unauthorized");
        String json = new ObjectMapper().writeValueAsString(responseObj);
        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().write(json);
        response.flushBuffer();
    }
}
