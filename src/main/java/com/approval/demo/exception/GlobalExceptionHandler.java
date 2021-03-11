package com.approval.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = { BindException.class})
	public Object processValidationError(BindException ex) {
		BindingResult bindingResult = ex.getBindingResult();

		StringBuilder builder = new StringBuilder();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			builder.append("[");
			builder.append(fieldError.getField());
			builder.append("](은)는 ");
			builder.append(fieldError.getDefaultMessage());
			builder.append(" 입력된 값: [");
			builder.append(fieldError.getRejectedValue());
			builder.append("]");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", ApprovalException.Code.NULL_PARAM_EXCEPTION.getKey());
		map.put("message", builder.toString());
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ApprovalException.class)
	public final ResponseEntity<Map<String, Object>> handleBaseException(ApprovalException ex, WebRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", ((ApprovalException.Code) ex.getCode()).getKey());
		map.put("message", ((ApprovalException.Code) ex.getCode()).getValue());
		if(ex.getErrorVO() != null) {
			map.put("errorVO", ex.getErrorVO());
		}
		this.systemLog(ex, false);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public final ResponseEntity<Map<String, String>> handleException(Exception ex, WebRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		if ("Bad credentials".equals(ex.getMessage())) {
			this.systemLog(ex, false);
			map.put("code", ApprovalException.Code.UNAUTHORIZED.getKey());
			map.put("message", ApprovalException.Code.UNAUTHORIZED.getValue());
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.UNAUTHORIZED);
		} else {
			this.systemLog(ex, true);
			map.put("code", ApprovalException.Code.EXCEPTION.getKey());
			map.put("message", ApprovalException.Code.EXCEPTION.getValue());
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public void systemLog(Exception cause, boolean isErrorLog) {
		StringBuffer temp = new StringBuffer();
		temp.append(cause.toString() + " (StackTrace : " + cause.getStackTrace().length + ")");

		if(isErrorLog) {
			for (int i = 0; i < cause.getStackTrace().length; i++) {
				temp.append("\n" + cause.getStackTrace()[i].toString());
			}
			logger.error(temp.toString());
		}else {
			logger.warn(temp.toString());
		}
	}

	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/403"));
		factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));

		return factory;
	}
}