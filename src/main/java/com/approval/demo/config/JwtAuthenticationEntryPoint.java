package com.approval.demo.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.approval.demo.exception.ApprovalException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

    	Map<String, String> map = new HashMap<String, String>();
		map.put("code", ApprovalException.Code.UNAUTHORIZED.getKey());
		map.put("message", ApprovalException.Code.UNAUTHORIZED.getValue());
        String json = new ObjectMapper().writeValueAsString(map);

    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter out = response.getWriter();
        out.write(json);
    }
}