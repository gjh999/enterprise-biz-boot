package egovframework.com.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.session.DisableEncodeUrlFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * fileName       : SecurityConfig
 * description    : 포털 사이트(Spring Boot + Thymeleaf) 세션 기반 Spring Security 설정.
 *                  - eGovFrame RTE 의 EgovUserDetailsHelper 가 SecurityContext 의
 *                    EgovUserDetails 프린시플을 읽어 인증 여부/사용자/권한을 판단한다.
 *                  - 로그인(EgovLoginController)이 자격증명 확인 후 SecurityContext 를
 *                    세션(HttpSessionSecurityContextRepository)에 저장한다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026/06/03        egovframe          최초 생성 (JSP→Boot 전환)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/** 인증 없이 접근 가능한 경로 */
	private static final String[] PERMIT_ALL = {
			"/",
			"/index.html",
			"/error",
			"/cmm/main/**",          // 메인 페이지
			"/cmm/forwardPage.do",
			"/sym/mms/**",           // 헤더/푸터/메뉴 프래그먼트
			"/uat/uia/**",           // 로그인/로그아웃 화면 및 처리
			"/cmm/lang",             // 다국어 전환
			"/EgovPageLink.do",      // 소개 페이지(whitelist 뷰 포워딩)
			"/validator.do",         // validation rule JS
			// 정적 리소스
			"/css/**", "/js/**", "/images/**", "/fonts/**", "/static/**",
			"/favicon.ico",
			// swagger
			"/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui.html",
			"/swagger-ui/**", "/webjars/**"
	};

	/** 관리자 전용 경로 (ROLE_ADMIN) */
	private static final String[] ADMIN_ONLY = {
			"/sec/**",                  // 권한/그룹/롤 관리
			"/cop/com/**",              // 게시판 사용정보/템플릿 관리
			"/uss/olh/**/admin/**",     // FAQ/Q&A 관리자
			"/uss/ion/bnr/**",          // 배너 관리
			// 설문: 관리(템플릿/설문/문항/항목/응답결과)는 관리자, 참여(qnn/qri)는 로그인 사용자
			"/uss/olp/qtm/**", "/uss/olp/qmc/**", "/uss/olp/qqm/**",
			"/uss/olp/qim/**", "/uss/olp/qrm/**",
			"/sts/**",                  // 접속통계 등 사용현황 통계
			"/uat/uap/**",              // 로그인정책관리
			"/uss/ion/uas/**",          // 사용자부재관리
			"/sym/**"                   // 시스템(코드/메뉴/프로그램/로그/일정/우편번호) 관리
	};

	/**
	 * 세션에 SecurityContext 를 저장/조회한다. 로그인 컨트롤러가 직접 saveContext 한다.
	 */
	@Bean
	public SecurityContextRepository securityContextRepository() {
		return new HttpSessionSecurityContextRepository();
	}

	@Bean
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		return filter;
	}

	/**
	 * 서블릿 컨테이너 최상위 우선순위로 UTF-8 인코딩 필터 등록 (한글 폼 입력 보존).
	 */
	@Bean
	public FilterRegistrationBean<CharacterEncodingFilter> characterEncodingFilterRegistration() {
		FilterRegistrationBean<CharacterEncodingFilter> registration =
				new FilterRegistrationBean<>(characterEncodingFilter());
		registration.addUrlPatterns("/*");
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registration;
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http,
			SecurityContextRepository securityContextRepository) throws Exception {
		return http
				// 서버 렌더링(Thymeleaf) + 세션 기반. 데모 단순화를 위해 CSRF 비활성화.
				.csrf(AbstractHttpConfigurer::disable)
				// 로그아웃은 EgovLoginController(/uat/uia/actionLogout.do)가 담당
				.logout(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(ADMIN_ONLY).hasRole("ADMIN")
						.requestMatchers(PERMIT_ALL).permitAll()
						// JSP 원본과 동일 정책: 목록/상세 등 조회는 공개,
						// 쓰기 동작은 각 컨트롤러의 EgovUserDetailsHelper.isAuthenticated() 검사가 보호한다.
						.anyRequest().permitAll())
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
				.securityContext(sc -> sc.securityContextRepository(securityContextRepository))
				// 미인증 접근 시 로그인 화면으로 이동(401), 권한 부족 시 로그인 화면(403)
				.exceptionHandling(eh -> eh
						.authenticationEntryPoint(
								new LoginUrlAuthenticationEntryPoint("/uat/uia/egovLoginUsr.do"))
						.accessDeniedPage("/uat/uia/egovLoginUsr.do"))
				.formLogin(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.addFilterBefore(characterEncodingFilter(), DisableEncodeUrlFilter.class)
				.build();
	}
}
