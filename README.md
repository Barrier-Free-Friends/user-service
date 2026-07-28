# User Service

Barrier Free Friends MSA의 유저 서비스입니다. 회원가입, 로그인(JWT 발급), 유저 정보 조회를 담당합니다.

전체 서비스 구성은 [조직 README](https://github.com/Barrier-Free-Friends)를 참고하세요.

## 기술 스택
- Java 21, Spring Boot 3.5.7 (JPA, QueryDSL)
- PostgreSQL, Spring Security, JJWT
- Resilience4j, Springdoc
- Eureka Client, Config Client

## 동작 방식
- 회원가입/로그인 시 Access/Refresh 토큰을 발급 (`JwtTokenProvider`)
- 발급된 JWT는 gateway-server가 검증 및 사용자 정보 헤더로 변환해 다른 서비스에 전달

## 주요 구조
- `application` — AuthenticationService, SignIn/SignUpRequest, TokenResponse, UserResponse
- `domain` — User, UserErrorCode, UserRepository
- `infrastructure/security/jwt` — JwtTokenProvider
- `presentation` — AuthController, UserInfoController

## 실행

```bash
./gradlew bootRun
```

기본 포트: `4040`

필요 환경변수: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`, `ACCESS_TOKEN_EXPIRATION`, `REFRESH_TOKEN_EXPIRATION`

## Docker

```bash
docker build -t user-service .
docker run -p 4040:4040 user-service
```
