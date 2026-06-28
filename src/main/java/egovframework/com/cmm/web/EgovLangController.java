package egovframework.com.cmm.web;

import java.net.URI;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 다국어 전환 컨트롤러.
 *
 * <p>언어 버튼은 {@code /cmm/lang?lang=ko|en} 로 이동만 한다.
 * 이 컨트롤러가 LocaleResolver 로 선택 언어를 LANG 쿠키에 저장한 뒤,
 * 직전 페이지(Referer)로 다시 리다이렉트(PRG)한다.</p>
 *
 * <p>결과적으로 (1) 최종 URL 에 {@code ?lang} 파라미터가 남지 않고,
 * (2) 한 번의 클릭으로 즉시 언어가 전환된다.</p>
 */
@Controller
public class EgovLangController {

	private final LocaleResolver localeResolver;

	public EgovLangController(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}

	@GetMapping("/cmm/lang")
	public String changeLanguage(@RequestParam("lang") String lang,
								 HttpServletRequest request,
								 HttpServletResponse response) {
		Locale locale = "en".equalsIgnoreCase(lang) ? Locale.ENGLISH : Locale.KOREAN;
		localeResolver.setLocale(request, response, locale);
		return "redirect:" + safeReturnPath(request);
	}

	/**
	 * Referer 헤더에서 같은 호스트의 경로만 추출한다. (오픈 리다이렉트 방지)
	 * 유효한 Referer 가 없으면 홈("/")으로 보낸다.
	 */
	private String safeReturnPath(HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		if (referer != null && !referer.isBlank()) {
			try {
				URI uri = URI.create(referer);
				String path = uri.getRawPath();
				String host = uri.getHost();
				boolean sameHost = (host == null) || host.equalsIgnoreCase(request.getServerName());
				if (sameHost && path != null && path.startsWith("/")) {
					String query = uri.getRawQuery();
					return (query != null && !query.isBlank()) ? path + "?" + query : path;
				}
			} catch (IllegalArgumentException ignore) {
				// 파싱 불가한 Referer 는 무시하고 홈으로
			}
		}
		return "/";
	}
}
