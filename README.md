# 모두의상점 (AllForShop)

모두의상점은 다양한 상품을 판매하는 온라인 쇼핑몰 프로젝트입니다.

## 목차
1. [소개](#소개)
2. [프로젝트 설계](#프로젝트-설계)
3. [기능](#기능)
4. [설치 방법](#설치-방법)


## 소개

온라인 쇼핑몰의 기본 기능을 구현한 RestAPI 서버입니다. Java를 사용하여 개발되었으며, Gradle을 사용하여 빌드됩니다.

## 프로젝트 설계
### ERD
<div align="center">
<img src="https://github.com/wken5577/AllForShop/assets/88573971/7a7774fb-2c3a-4ec9-94e4-f8a95411b4e0" width="650" height="450"></img>
</div>

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
  host: localhost
  port: 6379

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



