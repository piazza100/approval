package com.approval.demo.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@BeforeEach
	void init() {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}
	
	private String getToken(MvcResult result) throws UnsupportedEncodingException {
		return result.getResponse().getContentAsString().replace("{\"token\":\"", "").replace("\"}", "");
	}

	@Test
	@DisplayName("로그인_성공_실패")
	public void 로그인_성공_실패() throws Exception {
		// 로그인 성공
		mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content("{\"userId\":\"piazza100\",\"password\":\"123qwe!@#\"}"))
		.andExpect(status().isOk());

		// 로그인 실패
		mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content("{\"userId\":\"piazza100\",\"password\":\"123\"}"))
		.andExpect(status().is4xxClientError());
	}

	@Test
	@DisplayName("JWT_토큰_검증")
	public void JWT_토큰_검증() throws Exception {
		// 로그인 성공
		MvcResult result = mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content("{\"userId\":\"piazza100\",\"password\":\"123qwe!@#\"}"))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		// 토큰 검증
		mvc.perform(get("/api/approval/list").header("Authorization", "Bearer xxx" + this.getToken(result)))
		.andDo(print())
		.andExpect(status().is4xxClientError());
	}

	@Test
	@DisplayName("URL_호출_USER_ROLE_JWT_검증")
	public void URL_호출_USER_ROLE_JWT_검증() throws Exception {
		// 로그인 성공
		MvcResult result = mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content("{\"userId\":\"piazza100\",\"password\":\"123qwe!@#\"}"))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		// USER 권한일 경우, /api/approval/admin/** 접근 불가
		mvc.perform(get("/api/approval/admin/list").header("Authorization", "Bearer " + this.getToken(result)))
		.andDo(print())
		.andExpect(status().is4xxClientError());

		// USER 권한일 경우, /api/approval/list/** 접근 가능
		mvc.perform(get("/api/approval/list").header("Authorization", "Bearer " + this.getToken(result)))
		.andDo(print())
		.andExpect(status().isOk());
	}

	@Test
	@DisplayName("URL_호출_ADMIN_ROLE_JWT_검증")
	public void URL_호출_ADMIN_ROLE_JWT_검증() throws Exception {
		// 로그인 성공
		MvcResult result = mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content("{\"userId\":\"admin1\",\"password\":\"123qwe!@#\"}"))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		// ADMIN 권한일 경우, /api/approval/admin/** 접근 가능
		mvc.perform(get("/api/approval/admin/list").header("Authorization", "Bearer " + this.getToken(result)))
		.andDo(print())
		.andExpect(status().isOk());

		// ADMIN 권한일 경우, /api/approval/list/** 접근 가능
		mvc.perform(get("/api/approval/list").header("Authorization", "Bearer " + this.getToken(result)))
		.andDo(print())
		.andExpect(status().isOk());
	}

}