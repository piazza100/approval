package com.approval.demo.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
public class ApprovalControllerTest {
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;
	
	private String USER_TOKEN;
	private String USER_TOKEN_2;
	
	private String ADMIN_TOKEN;
	private String ADMIN_TOKEN_2;
	
	@BeforeEach
	void init() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		
		// USER 로그인
		MvcResult userMvcResult = mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content("{\"userId\":\"piazza100\",\"password\":\"123qwe!@#\"}")).andReturn();
		this.USER_TOKEN = this.getToken(userMvcResult);

		// USER2 로그인
		MvcResult userMvcResult2 = mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content("{\"userId\":\"piazza101\",\"password\":\"123qwe!@#\"}")).andReturn();
		this.USER_TOKEN_2 = this.getToken(userMvcResult2);
		
		// ADMIN 로그인
		MvcResult adminMvcResult = mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content("{\"userId\":\"admin1\",\"password\":\"123qwe!@#\"}")).andReturn();
		this.ADMIN_TOKEN = this.getToken(adminMvcResult);
		
		// ADMIN 로그인
		MvcResult adminMvcResult2 = mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content("{\"userId\":\"admin2\",\"password\":\"123qwe!@#\"}")).andReturn();
		this.ADMIN_TOKEN_2 = this.getToken(adminMvcResult2);
		
		// 결재 등록 성공
		mvc.perform(post("/api/approval/add").header("Authorization", "Bearer " + this.USER_TOKEN).contentType(MediaType.APPLICATION_JSON)
		.content("{\"content\":\"내용\",\"title\":\"제목\",\"approvalLineVOList\":[{\"state\":\"REQUEST\",\"userNo\":\"3\"}]}"))
		.andExpect(status().isOk());
	}
	
	public String getToken(MvcResult result) throws UnsupportedEncodingException {
		return result.getResponse().getContentAsString().replace("{\"token\":\"", "").replace("\"}", "");
	}
	
	public Integer getApprovalNo(MvcResult result) throws UnsupportedEncodingException, JSONException {
		JSONArray json = new JSONArray(result.getResponse().getContentAsString());
		return (Integer) ((JSONObject) json.get(0)).get("approvalNo");
	}

	@Test
	@DisplayName("결재_등록")
	public void 결재_등록() throws Exception {
		// 결재 등록 실패 - 파라미터 content 누락
		mvc.perform(post("/api/approval/add").header("Authorization", "Bearer " + this.USER_TOKEN).contentType(MediaType.APPLICATION_JSON)
		.content("{\"content\":\"\",\"title\":\"제목\",\"approvalLineVOList\":[{\"state\":\"REQUEST\",\"userNo\":\"3\"}]}"))
		.andExpect(status().is4xxClientError());

		// 결재 등록 실패 - 파라미터 title 누락
		mvc.perform(post("/api/approval/add").header("Authorization", "Bearer " + this.USER_TOKEN).contentType(MediaType.APPLICATION_JSON)
		.content("{\"content\":\"내용\",\"title\":\"\",\"approvalLineVOList\":[{\"state\":\"REQUEST\",\"userNo\":\"3\"}]}"))
		.andExpect(status().is4xxClientError());

		// 결재 등록 실패 - 파라미터 userNo (결재자) 누락
		mvc.perform(post("/api/approval/add").header("Authorization", "Bearer " + this.USER_TOKEN).contentType(MediaType.APPLICATION_JSON)
		.content("{\"content\":\"내용\",\"title\":\"제목\",\"approvalLineVOList\":[{\"state\":\"REQUEST\",\"userNo\":\"\"}]}"))
		.andExpect(status().is4xxClientError());

		// 결재 등록 성공
		mvc.perform(post("/api/approval/add").header("Authorization", "Bearer " + this.USER_TOKEN).contentType(MediaType.APPLICATION_JSON)
		.content("{\"content\":\"내용\",\"title\":\"제목\",\"approvalLineVOList\":[{\"state\":\"REQUEST\",\"userNo\":\"3\"}]}"))
		.andExpect(status().isOk());
	}

	@Test
	@DisplayName("결재_수정")
	public void 결재_수정() throws Exception {
		// 사용자 > 결재 목록 조회
		MvcResult list = mvc.perform(get("/api/approval/list").header("Authorization", "Bearer " + this.USER_TOKEN)).andReturn();
		Integer approvalNo = this.getApprovalNo(list);

		// 결재 수정 실패 - 파라미터 content 누락
		mvc.perform(post("/api/approval/update").header("Authorization", "Bearer " + this.USER_TOKEN).contentType(MediaType.APPLICATION_JSON)
		.content("{\"approvalNo\":\"51\",\"content\":\"\",\"title\":\"제목1\",\"approvalLineVOList\":[{\"state\":\"REQUEST\",\"userNo\":\"3\"}]}"))
		.andExpect(status().is4xxClientError());

		// 결재 수정 실패 - 파라미터 title 누락
		mvc.perform(post("/api/approval/update").header("Authorization", "Bearer " + this.USER_TOKEN).contentType(MediaType.APPLICATION_JSON)
		.content("{\"approvalNo\":\"" + approvalNo + "\",\"content\":\"내용1\",\"title\":\"\",\"approvalLineVOList\":[{\"state\":\"REQUEST\",\"userNo\":\"3\"}]}"))
		.andExpect(status().is4xxClientError());

		// 결재 수정 실패 - 파라미터 userNo (결재자) 누락
		mvc.perform(post("/api/approval/update").header("Authorization", "Bearer " + this.USER_TOKEN).contentType(MediaType.APPLICATION_JSON)
		.content("{\"approvalNo\":\"" + approvalNo + "\",\"content\":\"내용1\",\"title\":\"제목1\",\"approvalLineVOList\":[{\"state\":\"REQUEST\",\"userNo\":\"\"}]}"))
		.andExpect(status().is4xxClientError());

		// 결재 수정 실패 - 파라미터 approvalNo (결재 번호) 누락
		mvc.perform(post("/api/approval/update").header("Authorization", "Bearer " + this.USER_TOKEN).contentType(MediaType.APPLICATION_JSON)
		.content("{\"approvalNo\":\"\",\"content\":\"내용1\",\"title\":\"제목1\",\"approvalLineVOList\":[{\"state\":\"REQUEST\",\"userNo\":\"3\"}]}"))
		.andExpect(status().is4xxClientError());

		// 결재 수정 실패 - 사용자 수정 권한 없음
		mvc.perform(post("/api/approval/update").header("Authorization", "Bearer " + this.USER_TOKEN_2).contentType(MediaType.APPLICATION_JSON)
		.content("{\"approvalNo\":\""+ approvalNo + "\",\"content\":\"내용1\",\"title\":\"제목1\",\"approvalLineVOList\":[{\"state\":\"REQUEST\",\"userNo\":\"3\"}]}"))
		.andExpect(status().is4xxClientError());

		// 결재 수정 성공
		mvc.perform(post("/api/approval/update").header("Authorization", "Bearer " + this.USER_TOKEN).contentType(MediaType.APPLICATION_JSON)
		.content("{\"approvalNo\":\""+ approvalNo + "\",\"content\":\"내용1\",\"title\":\"제목1\",\"approvalLineVOList\":[{\"state\":\"REQUEST\",\"userNo\":\"3\"}]}"))
		.andExpect(status().isOk());
	}

	@Test
	@DisplayName("결재_삭제")
	public void 결재_삭제() throws Exception {
		// 사용자 > 결재 목록 조회
		MvcResult list = mvc.perform(get("/api/approval/list").header("Authorization", "Bearer " + this.USER_TOKEN)).andReturn();
		Integer approvalNo = this.getApprovalNo(list);

		// 결재 삭제 실패 - 파라미터 approvalNo (결재 번호) 누락
		mvc.perform(post("/api/approval/delete").header("Authorization", "Bearer " + this.USER_TOKEN).contentType(MediaType.APPLICATION_JSON)
		.content("{\"approvalNo\":\"\"}"))
		.andExpect(status().is4xxClientError());

		// 결재 삭제 실패 - 사용자 삭제 권한 없음
		mvc.perform(post("/api/approval/delete").header("Authorization", "Bearer " + this.USER_TOKEN_2).contentType(MediaType.APPLICATION_JSON)
		.content("{\"approvalNo\":\"" + approvalNo + "\"}"))
		.andExpect(status().is4xxClientError());
		
		// 결재 삭제 성공
		mvc.perform(post("/api/approval/delete").header("Authorization", "Bearer " + this.USER_TOKEN).contentType(MediaType.APPLICATION_JSON)
		.content("{\"approvalNo\":\"" + approvalNo + "\"}"))
		.andExpect(status().isOk());

		// 결재 삭제 실패 - 삭제할 수 없는 상태
		mvc.perform(post("/api/approval/delete").header("Authorization", "Bearer " + this.USER_TOKEN).contentType(MediaType.APPLICATION_JSON)
		.content("{\"approvalNo\":\"" + approvalNo + "\"}"))
		.andExpect(status().is4xxClientError());
	}

	@Test
	@DisplayName("결재_상태_변경")
	public void 결재_상태_변경() throws Exception {
		// 사용자 > 결재 목록 조회
		MvcResult list = mvc.perform(get("/api/approval/list").header("Authorization", "Bearer " + this.USER_TOKEN)).andReturn();
		Integer approvalNo = this.getApprovalNo(list);

		// 결재 상태 변경 실패 - 파라미터 approvalNo (결재 번호) 누락
		mvc.perform(post("/api/approval/admin/update").header("Authorization", "Bearer " + this.ADMIN_TOKEN).contentType(MediaType.APPLICATION_JSON)
		.content("{\"state\":\"REJECT\",\"approvalNo\":\"\"}"))
		.andExpect(status().is4xxClientError());

		// 결재 상태 변경 실패 - 파라미터 state 누락
		mvc.perform(post("/api/approval/admin/update").header("Authorization", "Bearer " + this.ADMIN_TOKEN).contentType(MediaType.APPLICATION_JSON)
		.content("{\"state\":\"\",\"approvalNo\":\"69\"}"))
		.andExpect(status().is4xxClientError());

		// 결재 상태 변경 실패 - 수정할 수 없는 상태
		mvc.perform(post("/api/approval/admin/update").header("Authorization", "Bearer " + this.ADMIN_TOKEN).contentType(MediaType.APPLICATION_JSON)
		.content("{\"state\":\"REJECT\",\"approvalNo\":\"0\"}"))
		.andExpect(status().is4xxClientError());

		// 결재 상태 변경 실패 - 관리자 수정 권한 없음
		mvc.perform(post("/api/approval/admin/update").header("Authorization", "Bearer " + this.ADMIN_TOKEN_2).contentType(MediaType.APPLICATION_JSON)
		.content("{\"state\":\"REJECT\",\"approvalNo\":\"" + approvalNo + "\"}"))
		.andExpect(status().is4xxClientError());
		
		// 결재 상태 변경 성공
		mvc.perform(post("/api/approval/admin/update").header("Authorization", "Bearer " + this.ADMIN_TOKEN).contentType(MediaType.APPLICATION_JSON)
				.content("{\"state\":\"REJECT\",\"approvalNo\":\"" + approvalNo + "\"}"))
		.andExpect(status().isOk());
	}
}