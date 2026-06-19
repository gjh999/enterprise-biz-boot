package egovframework.com.security;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * fileName       : WebMvcConfig
 * description    : Thymeleaf 화면 렌더링용 MVC 설정(로케일/캐시 제어/인자 리졸버).
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026/06/03        egovframe          최초 생성 (JSP→Boot 전환)
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new CustomAuthenticationPrincipalResolver());
	}

	/**
	 * 다국어 로케일 해석기 — 쿠키(LANG)에 선택 언어를 저장한다. 기본 한국어.
	 */
	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver resolver = new CookieLocaleResolver("LANG");
		resolver.setDefaultLocale(Locale.KOREAN);
		resolver.setCookiePath("/");
		resolver.setCookieMaxAge(Duration.ofDays(365));
		return resolver;
	}

	/**
	 * 동적(컨트롤러) 응답은 브라우저 캐시를 막아 등록 후 목록 갱신/언어 전환 즉시 반영을 보장한다.
	 * 정적 리소스/바이너리 응답은 캐시 허용을 위해 제외한다.
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HandlerInterceptor() {
			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
				response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
				response.setHeader("Pragma", "no-cache");
				response.setDateHeader("Expires", 0);
				return true;
			}
		}).excludePathPatterns("/css/**", "/js/**", "/images/**", "/fonts/**",
				"/static/**", "/favicon.ico", "/webjars/**", "/swagger-ui/**", "/v3/api-docs/**",
				"/cmm/fms/**", "/sym/cmm/EgovImgView.do");
	}
}
