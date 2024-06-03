# 모두의상점 (AllForShop)

모두의상점은 다양한 상품을 판매하는 온라인 쇼핑몰 프로젝트입니다.

## 목차
1. [소개](#소개)
2. [프로젝트 설계](#프로젝트-설계)
3. [기능 구현](#기능-구현)
4. [설치 방법](#설치-방법)


## 소개

온라인 쇼핑몰의 기본 기능을 구현한 RestAPI 서버입니다. Java를 사용하여 개발되었으며, Gradle을 사용하여 빌드됩니다.

## 프로젝트 설계
### ERD
<div style="text-align:center">
<img src="https://github.com/wken5577/AllForShop/assets/88573971/7a7774fb-2c3a-4ec9-94e4-f8a95411b4e0" width="500" height="350"></img>
</div>

### 기술 스택
<div style="text-align:center">
<img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white"></a>
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/spring_boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/spring_security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
<img src="https://img.shields.io/badge/spring_data_jpa-6DB33F?style=for-the-badge&logo=&logoColor=white">
</div>
<div style="text-align:center">
<img src="https://img.shields.io/badge/hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white"></a>
<img src="https://img.shields.io/badge/querydsl-04ACE6?style=for-the-badge&logo=&logoColor=white"></a>
<img src="https://img.shields.io/badge/redis-FF4438?style=for-the-badge&logo=redis&logoColor=white"></a>
<img src="https://img.shields.io/badge/h2database-FF7A00?style=for-the-badge&logo=&logoColor=white"></a>
</div>

## 기능 구현

### 주요 기능
- Spring Session기반의 로그인 기능
  - Naver, Google 소셜 로그인, username password 기반의 로그인 기능
  - CSRF Protection을 적용하여 보안 강화
- 상품 관리
  - 상품 등록, 삭제 기능
  - 상품 검색 기능
  - 상품 이미지 업로드 기능
- 주문 관리
  - 주문 생성, 취소
  - 주문 조회 기능
- 결제 기능
  - Toss Payments 결제 API를 사용한 결제 기능
  - 결제 승인, 취소 기능

### API Documentation
| Method | Endpoint                                 | Summary             |
| ------ |------------------------------------------|---------------------|
| POST | `/login`                                 | User login API      |
| POST | `/register`                              | User 등록 API         |
| POST | `/join`                                  | 회원 가입 API           |
| GET | `/oauth2/authorization/google`           | Google login API    |
| GET | `/oauth2/authorization/naver`            | Naver login API     |
| POST | `/payment/confirm`                       | 결제 승인 API           |
| POST | `/payment/cancel`                        | 결제 취소 API           |
| GET | `/order`                                 | 주문 조회 API           |
| POST | `/order`                                 | 주문 생성 API           |
| DELETE | `/order/{orderId}`                       | 주문 취소 API           |
| POST | `/category`                              | Category 등록 API     |
| GET | `/categories`                            | Category 조회 API     |
| DELETE | `/category/{categoryId}`                 | Category 삭제 API     |
| GET | `/basket/item`                           | 장바구니 item 조회 API    |
| POST | `/basket/item`                           | 장바구니 item 추가 API    |
| DELETE | `/basket/item`                           | 장바구니 item 삭제 API    |
| POST | `/item`                                  | Item 등록 API         |
| GET | `/items`                                 | Item 목록 API         |
| GET | `/item/{itemId}`                         | Item 상세조회 API       |
| DELETE | `/item/{itemId}`                         | Item 삭제 API         |
| GET | `/item/image/{filename}`                 | Item image 조회 API   |
| GET | `/item/image/attach/{itemId}/{filename}` | Item image 다운로드 API |


## 설치 방법

프로젝트를 클론하고 필요한 의존성을 설치하는 방법은 다음과 같습니다:

1. 프로젝트를 클론합니다.
```bash
git clone https://github.com/wken5577/AllForShop.git
```

2. 다음 yml 파일을 src/main/resources 경로에 추가합니다.
```yaml
spring:
  security:
    oauth2.client:
      registration:
        naver:
          redirect-uri: "{baseUrl}/{action}/oauth2/code/{registrationId}"
          authorization-grant-type: authorization_code
          scope: name, email, profile_image
          client-id: 'your client id'
          client-secret: 'your client secret'
        google:
          redirect-uri: "{baseUrl}/{action}/oauth2/code/{registrationId}"
          authorization-grant-type: authorization_code
          scope:
            - profile
            - email
          client-id: 'your client id'
          client-secret: 'your client secret'

      provider:
        naver:
          authorization-uri: "https://nid.naver.com/oauth2.0/authorize"
          token-uri: "https://nid.naver.com/oauth2.0/token"
          user-info-uri: "https://openapi.naver.com/v1/nid/me"
          user-name-attribute: response
        google:
          authorization-uri: "https://accounts.google.com/o/oauth2/v2/auth"
          token-uri: "https://www.googleapis.com/oauth2/v4/token"
          user-info-uri: "https://www.googleapis.com/oauth2/v3/userinfo"
          user-name-attribute: sub


  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: 'database username'
    password: 'database password'

  jpa:
    hibernate:
      ddl-auto: create
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        default_batch_fetch_size: 1000

  h2:
    console:
      enabled: true

logging-level:
  org.hibernate.SQL: debug

redis:
  host: 'redis host'
  port: 'redis port'

file:
  upload:
    max-file-size: # file size byte
    max-file-count: # file count
    allowed-file-types: #jpeg,png,gif,jpg
    upload-dir: # upload-dir

payment:
  secret-key: 'your secret'
  url: "https://api.tosspayments.com/v1" # toss payment api url


springdoc:
  swagger-ui:
    path: /api-docs
  default-consumes-media-type: application/json
  default-produces-media-type: application/json

cors:
  allowed-origins: "http://localhost:3000"
  allowed-methods: GET,POST,PUT,DELETE,OPTIONS,PATCH
  allowed-headers: "*"
  max-age: 3600
```

3. 프로젝트를 빌드하고 실행합니다.
```bash
cd AllForShop
./gradlew build
./gradlew bootRun
```



