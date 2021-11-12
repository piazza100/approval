# 결재 요청/조회 페이지 및 API 구현
결재 문서 생성, 조회, 삭제 작업을 할 수 있는 프론트엔드 및 API 애플리케이션을 작성하는 과제입니다

## 개발 환경
- 기본 환경
  - IDE: Spring Tool Suite
  - OS: Window
  - GIT
- Backend
  - Java8
  - Spring Boot 2.4.3
  - mybatis
  - H2
  - Jwt
  - Maven
  - Junit5
- frontend
  - Vuejs


## 빌드 및 실행하기
### 사전작업
- H2 database 설치 및 실행하기
  - https://www.h2database.com/html/main.html
- db 관련 sql 실행하기
```
-- approval 스키마 생성
CREATE SCHEMA IF NOT EXISTS approval AUTHORIZATION sa;
-- user 테이블 생성
CREATE TABLE IF NOT EXISTS approval.user (
user_no int(11) NOT NULL AUTO_INCREMENT,
user_id varchar(45) NOT NULL,
password varchar(200) NOT NULL,
role varchar(45) DEFAULT NULL,
reg_time datetime NOT NULL,
mod_time datetime,
PRIMARY KEY (user_no)
);
-- approval 테이블 생성
CREATE TABLE IF NOT EXISTS approval.approval (
approval_no int(11) NOT NULL AUTO_INCREMENT,
user_no int(11) NOT NULL,
title varchar(45) NOT NULL,
content varchar(200) NOT NULL,
reg_time datetime NOT NULL,
mod_time datetime,
end_time datetime,
PRIMARY KEY (approval_no)
);
-- approval_line 테이블 생성
CREATE TABLE IF NOT EXISTS approval.approval_line (
approval_no int(11) NOT NULL,
user_no int(11) NOT NULL,
seq int(11) NOT NULL,
state varchar(10) NOT NULL,
reg_time datetime NOT NULL,
mod_time datetime,
PRIMARY KEY (approval_no, user_no)
);
-- 계정 생성
INSERT INTO approval.user (user_id, password, role, reg_time, mod_time) VALUES ('piazza101', '{yDuffW3B8hH1QvgOF2MhzViZT28QFJ9w1dnJyyEa+9w=}ea9cfb195f43865ffd6b2fa4dc80879f2def46aa200c19b0a1f950e67d7c12c9', 'USER', now(), now());
INSERT INTO approval.user (user_id, password, role, reg_time, mod_time) VALUES ('piazza100', '{yDuffW3B8hH1QvgOF2MhzViZT28QFJ9w1dnJyyEa+9w=}ea9cfb195f43865ffd6b2fa4dc80879f2def46aa200c19b0a1f950e67d7c12c9', 'USER', now(), now());
INSERT INTO approval.user (user_id, password, role, reg_time, mod_time) VALUES ('admin1', '{yDuffW3B8hH1QvgOF2MhzViZT28QFJ9w1dnJyyEa+9w=}ea9cfb195f43865ffd6b2fa4dc80879f2def46aa200c19b0a1f950e67d7c12c9', 'ADMIN', now(), now());
INSERT INTO approval.user (user_id, password, role, reg_time, mod_time) VALUES ('admin2', '{yDuffW3B8hH1QvgOF2MhzViZT28QFJ9w1dnJyyEa+9w=}ea9cfb195f43865ffd6b2fa4dc80879f2def46aa200c19b0a1f950e67d7c12c9', 'ADMIN', now(), now());
```



### git > 터미널 환경
```
git clone https://github.com/piazza100/approval.git
```

### Frontend > 터미널 환경
```
cd front
npm install
npm run dev
```

- 접속 Base URI: http://localhost

### Backend > 이클립스 환경
```
eclipse > File > Import > Git > Project from Git... 선택
approval 프로젝트 > configure > convert to maven project
approval 프로젝트 > Run As > maven install & build
ApprovalApplication.java 실행
```
- 접속 Base URI: http://localhost:8080




## 기능 요구사항
### 내용
1. 작성자가 결재 문서를 작성할 수 있습니다.
  - 결재 문서는 제목, 내용, 1명의 승인자, 결재 상태로 구성되어 있으며, 작성자는 제목, 내용을 작성하고 승인자를 지정하여 요청합니다.
  - 결재 상태는 '요청', '승인', '반려'로 나뉘어 집니다.
2. 본인이 작성한 결재 문서를 목록으로 조회할 수 있으며, 작성한 내용과 결재 상태를 확인하고 수정, 삭제를 할 수 있습니다.
3. 본인이 작성한 결재 문서는 '요청' 상태에서만 수정하거나 삭제할 수 있습니다.


## 문제 해결 전략
### 공통
1. 결재는 작성자 '요청'에 따른, 승인자 '승인/반려' 처리가 돼야 하므로, 로그인 계정별 권한에 따른 결재문서 상태 처리가 되도록 했습니다.
2. 사용자 인증처리는 Jwt 를 이용했습니다.

### Backend
1. spring security 를 통해, 관리자만 접근 가능한 API 를 별도로 뒀습니다. (결재 승인/반려, 결재 요청 목록)
2. 결재 승인/반려 후 수정요청이 있거나, 그 반대의 경우에 대비해, 요청 수행 전 검증을 추가했습니다.
3. @Valid 를 통해, 필수 파라미터 검증 처리를 했습니다.

### Frontend 
1. 라우터를 이용한, 로그인이 필요한 URL 을 구분했습니다.
2. 로그인 계정 권한에 따른 메뉴 및 화면을 분기처리 했습니다.

### DB
테이블은 USER, APPROVAL, APPROVAL_LINE 총 3개이며, 결재문서와 결재선 테이블을 별도로 둬서, 추후 결재선이 늘어날 경우를 대비했습니다. (1:N 관계)
1. USER - 회원 ID, PW, 권한 정보가 들어 있습니다.
2. APPROVAL - 결재문서 제목, 내용, 작성자, 작성, 종료일시 정보가 들어 있습니다.
3. APPROVAL_LINE - 결재 승인자ID, 순번, 처리상태 정보가 들어 있습니다.



### 1. 결재 문서 작성 API

- Request

```
POST http://localhost:8080/api/approval/add
```

```json
{
   "content":"내용",
   "title":"제목",
   "approvalLineVOList":[
      {
         "state":"REQUEST",
         "userNo":"3"
      }
   ]
}
```

- Response

```json 성공 시
{
    "result": "Y"
}
```

### 2. 결재 문서 수정 API

- Request

```
POST http://localhost:8080/api/approval/update
```

```json
{
   "approvalNo":"138",
   "content":"내용1",
   "title":"제목1",
   "approvalLineVOList":[
      {
         "state":"REQUEST",
         "userNo":"4"
      }
   ]
}
```

- Response

```json 성공 시
{
    "result": "Y"
}
```



### 3. 결재 문서 삭제 API

- Request

```
POST http://localhost:8080/api/approval/delete
```

```json
{
   "approvalNo":"138"
}
```

- Response

```json 성공 시
{
    "result": "Y"
}
```

### 4. 결재 문서 목록(작성자) API

- Request

```
GET http://localhost:8080/api/approval/list
```

- Response

```json 성공 시
[
    {
        "approvalNo": 139,
        "userNo": 1,
        "title": "제목",
        "content": "내용",
        "regTime": "2021-03-15T13:32:11.319+00:00",
        "modTime": "2021-03-15T13:32:11.319+00:00",
        "endTime": null,
        "approvalLineVOList": [
            {
                "approvalNo": 139,
                "userNo": 3,
                "seq": 1,
                "state": "REQUEST",
                "regTime": "2021-03-15T13:32:11.319+00:00",
                "modTime": "2021-03-15T13:32:11.319+00:00"
            }
        ]
    },
    {
        "approvalNo": 138,
        "userNo": 1,
        "title": "제목1",
        "content": "내용1",
        "regTime": "2021-03-15T13:30:23.234+00:00",
        "modTime": "2021-03-15T13:40:52.844+00:00",
        "endTime": "2021-03-15T13:40:52.844+00:00",
        "approvalLineVOList": [
            {
                "approvalNo": 138,
                "userNo": 4,
                "seq": 1,
                "state": "REQUEST",
                "regTime": "2021-03-15T13:34:49.790+00:00",
                "modTime": "2021-03-15T13:34:49.790+00:00"
            }
        ]
    },
	...
]
```

### 5. 결재 문서 목록(승인자) API

- Request

```
GET http://localhost:8080/api/approval/admin/list
```
- Response

```json 성공 시
[
    {
        "approvalNo": 139,
        "userNo": 1,
        "title": "제목",
        "content": "내용",
        "regTime": "2021-03-15T13:32:11.319+00:00",
        "modTime": "2021-03-15T13:32:11.319+00:00",
        "endTime": null,
        "approvalLineVOList": [
            {
                "approvalNo": 139,
                "userNo": 3,
                "seq": 1,
                "state": "REQUEST",
                "regTime": "2021-03-15T13:32:11.319+00:00",
                "modTime": "2021-03-15T13:32:11.319+00:00"
            }
        ]
    },
    {
        "approvalNo": 137,
        "userNo": 1,
        "title": "제목",
        "content": "내용",
        "regTime": "2021-03-15T13:30:07.945+00:00",
        "modTime": "2021-03-15T13:30:07.945+00:00",
        "endTime": null,
        "approvalLineVOList": [
            {
                "approvalNo": 137,
                "userNo": 3,
                "seq": 1,
                "state": "REQUEST",
                "regTime": "2021-03-15T13:30:07.945+00:00",
                "modTime": "2021-03-15T13:30:07.945+00:00"
            }
        ]
    },
	...
]
```

### 6. 결재 문서 승인/반려 API

- Request

```
POST http://localhost:8080/api/approval/admin/update
```

```json
{
   "state":"REJECT",
   "approvalNo":"138"
}
```

- Response

```json 성공 시
{
    "result": "Y"
}
```

### 7. 공통 에러 코드

```json 파라미터 누락 시
{
    "code": "E1009",
    "message": "[approvalLineVOList[0].userNo](은)는 널이어서는 안됩니다 입력된 값: [null]"
}
```

```json 존재하지 않는 결재문서에 대해 수정/삭제 요청 시 
{
    "code": "E1006",
    "message": "수정할 수 없습니다."
}
```


