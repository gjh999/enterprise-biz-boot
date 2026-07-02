package egovframework.let.cmm.web;

import java.util.List;

import org.egovframe.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import egovframework.com.cmm.LoginVO;

/**
 * 사용자 구분(USR/GNR/ENT) 안내·분기 예시 페이지.
 * ① 3구분 의미·테이블·가입경로·권한 비교 ② 현재 로그인 사용자의 구분/권한 표시
 * ③ 구분별 분기 예시 + 분기 코드 패턴 안내. (공개 페이지)
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *  2026.06.23  Claude        사용자 구분 안내 페이지 신규
 *   2026.07.02  구재호        Spring Boot + Thymeleaf + KRDS + 다국어(i18n) 전환
 * </pre>
 */
@Controller
public class EgovUserTypesController {

	@GetMapping("/cmm/user-types")
	public String userTypes(ModelMap model) {
		boolean authenticated = Boolean.TRUE.equals(EgovUserDetailsHelper.isAuthenticated());
		model.addAttribute("authenticated", authenticated);
		if (authenticated) {
			LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			model.addAttribute("loginVO", loginVO);
			List<String> authorities = EgovUserDetailsHelper.getAuthorities();
			model.addAttribute("authorities", authorities);
		}
		return "cmm/EgovUserTypes";
	}
}
