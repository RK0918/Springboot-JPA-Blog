# SpringBoot JPA Blog
Spring Boot와 JPA를 사용해 만든 개인 블로그 프로젝트입니다.
게시글 CRUD와 회원 인증, 카카오 로그인을 구현하면서 Spring의 동작 방식을 공부하고 있습니다.

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=openjdk&logoColor=white" alt="Java 17" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.5.9-6DB33F?style=flat-square&logo=springboot&logoColor=white" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white" alt="Spring Security" />
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white" alt="MySQL" />
  <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white" alt="Gradle" />
</p>

프로젝트 소개
Spring Boot를 공부하면서 게시글과 회원 기능을 직접 구현한 블로그입니다. JSP로 화면을 구성하고, Spring Security로 로그인과 접근 권한을 처리했습니다. JPA를 이용한 데이터 저장과 수정 과정도 함께 학습했습니다.
- 프로젝트 유형: 개인 프로젝트
- 개발 환경: Java 17, Spring Boot 3.5.9, Gradle
- GitHub: RK0918/Springboot-JPA-Blog
기술 스택
구분	기술
Language	Java 17, JavaScript
Backend	Spring Boot 3.5.9, Spring MVC, Spring Data JPA
Security	Spring Security, OAuth 2.0 (Kakao 로그인 연동 학습)
Database	MySQL
View	JSP, JSTL, HTML, CSS, jQuery / AJAX
Build	Gradle
Development	IntelliJ IDEA, Git, GitHub


주요 기능
1. 회원 관리
- 회원가입 및 로그인
- Spring Security 기반 사용자 인증 및 접근 권한 관리
- 로그인 세션 유지 및 사용자 정보 수정
2. 게시글 관리
- 게시글 작성, 조회, 수정, 삭제(CRUD)
- AJAX를 이용한 비동기 HTTP 요청
- Spring Data JPA를 활용한 데이터베이스 연동
3. 소셜 로그인
- 카카오 OAuth 2.0 Authorization Code 흐름 학습 및 연동
- 인가 코드로 액세스 토큰 요청
- 카카오 사용자 정보 조회 및 애플리케이션 인증 처리
애플리케이션 구조
요청은 Spring Security 필터를 거쳐 컨트롤러로 전달되며, 서비스와 리포지토리를 통해 데이터베이스에 접근합니다.
1. Client (JSP / JavaScript / jQuery) — 화면 표시 및 HTTP 요청
2. Spring Security Filter Chain — 요청별 인증 및 접근 권한 확인
3. Spring MVC Controller — 요청 매핑과 응답 처리
4. Service — 비즈니스 로직 및 트랜잭션 처리
5. Repository (Spring Data JPA) — 데이터 조회 및 저장
6. MySQL — 사용자 및 게시글 데이터 관리
위 순서는 주요 요청 처리 흐름을 단순화한 것으로, 모든 요청이 데이터베이스까지 접근하는 것은 아닙니다.
인증 처리 흐름
일반 로그인
1. 사용자가 로그인 폼에서 아이디와 비밀번호를 입력합니다.
2. Spring Security가 로그인 요청을 받아 AuthenticationManager를 통해 인증을 수행합니다.
3. UserDetailsService에서 사용자 정보를 조회하고 비밀번호를 검증합니다.
4. 인증에 성공하면 SecurityContext에 인증 정보가 저장됩니다.
5. 세션을 통해 이후 요청에서도 로그인 상태를 유지합니다.
카카오 로그인
1. 사용자가 카카오 로그인을 요청합니다.
2. 카카오 인증 후 인가 코드를 전달받습니다.
3. 인가 코드를 사용해 액세스 토큰을 요청합니다.
4. 액세스 토큰으로 카카오 사용자 정보를 조회합니다.
5. 조회한 정보를 바탕으로 애플리케이션 사용자를 확인하고 Spring Security 인증을 처리합니다.
학습 및 구현 내용
Spring Security
- SecurityFilterChain을 활용한 URL별 접근 제어
- AuthenticationManager와 UserDetailsService의 역할 이해
- SecurityContext와 HTTP 세션의 관계 학습
- 일반 로그인과 OAuth 로그인 인증 흐름 비교
Spring Data JPA
- Entity와 Repository를 통한 데이터 영속화
- 영속성 컨텍스트와 트랜잭션의 동작 원리 학습
- 게시글 및 사용자 데이터의 CRUD 구현
REST API 및 AJAX
- @GetMapping, @PostMapping, @PutMapping, @DeleteMapping을 통한 요청 매핑
- @RequestBody, @PathVariable, @RequestParam의 역할 구분
- JSON 요청과 응답을 활용한 비동기 처리
로컬 실행 방법
1. 저장소 복제
git clone https://github.com/RK0918/Springboot-JPA-Blog.git
cd Springboot-JPA-Blog
2. 사전 준비
- JDK 17
- MySQL 서버
- 데이터베이스 및 접속 계정
- 카카오 로그인 테스트 시 Kakao Developers 앱 및 Redirect URI 설정
3. 애플리케이션 설정
로컬 환경에 맞게 application.yml의 데이터베이스 접속 정보와 필요한 OAuth 설정을 구성합니다.
server:
  port: 8000
  servlet:
    context-path: /

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/blog?serverTimezone=Asia/Seoul
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  mvc:
    view:
      prefix: /WEB-INF/views/
      suffix: .jsp
위 설정은 예시입니다. 실제 실행 시에는 프로젝트의 전체 설정값, 환경 변수 및 데이터베이스 스키마를 확인해야 합니다. API 키와 비밀번호 같은 민감한 정보는 별도로 관리해야 합니다.

4. 실행
Windows:
.\gradlew.bat bootRun
macOS / Linux:
./gradlew bootRun
접속 주소: http://localhost:8000
개선할 부분
- [ ] 핵심 기능에 대한 단위 및 통합 테스트 보강
- [ ] 예외 처리 및 API 응답 구조 표준화
- [ ] 인증 및 OAuth 관련 보안 설정 점검
- [ ] 페이징, 검색 및 성능 최적화
- [ ] 배포 환경 구성 및 운영 설정 분리
프로젝트를 진행하며
처음에는 게시글 CRUD 구현에 집중했지만, 로그인 기능을 추가하면서 Spring Security의 필터와 인증 객체, 세션이 어떻게 연결되는지 공부하게 됐습니다. 강의에서 사용한 예전 설정이 현재 버전에서는 그대로 동작하지 않는 부분이 있어 SecurityFilterChain, AuthenticationManager 설정도 직접 수정해 봤습니다.
Repository: https://github.com/RK0918/Springboot-JPA-Blog
