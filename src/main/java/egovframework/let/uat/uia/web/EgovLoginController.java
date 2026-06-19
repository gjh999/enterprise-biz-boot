package egovframework.let.uat.uia.web;

import java.util.List;

import org.egovframe.rte.fdl.security.userdetails.EgovUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.let.uat.uia.service.EgovLoginService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 일반 로그인을 처리하는 컨트롤러 클래스 (Spring Boot + Thymeleaf, 세션 기반).
 *
 * <pre>
 * 인증 모델: 자격증명 확인(loginService) 후 EgovUserDetails 프린시플을 SecurityContext 에
 *           담아 세션에 저장한다. 이후 모든 컨트롤러는 RTE EgovUserDetailsHelper 로
 *           인증 사용자/권한을 조회한다.
 * 화면     : Thymeleaf 템플릿 templates/uat/uia/EgovLoginUsr.html
 * </pre>
 *
 * @author 공통서비스 개발팀 박지욱 / Boot 전환 egovframe
 * @since 2009.03.06
 */
@Slf4j
@Controller
public class EgovLoginController {

	@Resource(name = "loginService")
	private EgovLoginService loginService;

	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	private final SecurityContextRepository securityContextRepository;

	public EgovLoginController(SecurityContextRepository securityContextRepository) {
		this.securityContextRepository = securityContextRepository;
	}

	/**
	 * 로그인 화면으로 들어간다.
	 */
	@RequestMapping(value = "/uat/uia/egovLoginUsr.do")
	public String loginUsrView(@ModelAttribute("loginVO") LoginVO loginVO, ModelMap model) {
		return "uat/uia/EgovLoginUsr";
	}

	/**
	 * 일반(세션) 로그인을 처리한다.
	 */
	@RequestMapping(value = "/uat/uia/actionSecurityLogin.do")
	public String actionSecurityLogin(@ModelAttribute("loginVO") LoginVO loginVO,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		if (loginVO.getUserSe() == null || loginVO.getUserSe().isEmpty()) {
			loginVO.setUserSe("USR"); // 기본: 업무사용자(직원)
		}

		LoginVO resultVO = loginService.actionLogin(loginVO);

		if (resultVO != null && resultVO.getId() != null && !resultVO.getId().isEmpty()) {
			storeAuthentication(resultVO, request, response);
			return "redirect:/"; // 성공 시 메인 페이지
		}
		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		return "uat/uia/EgovLoginUsr";
	}

	/**
	 * 자격증명 확인 결과를 SecurityContext(EgovUserDetails)와 세션에 저장한다.
	 */
	private void storeAuthentication(LoginVO resultVO, HttpServletRequest request, HttpServletResponse response) {
		// 세션 보관(레거시 호환)
		request.getSession().setAttribute("LoginVO", resultVO);

		// 권한 결정: 데모 단순화 — admin 계정만 ROLE_ADMIN, 그 외 ROLE_USER
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(
				"admin".equalsIgnoreCase(resultVO.getId()) ? "ROLE_ADMIN" : "ROLE_USER"));

		EgovUserDetails principal = new EgovUserDetails(
				resultVO.getId(), resultVO.getPassword() == null ? "" : resultVO.getPassword(), true, resultVO);

		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), authorities);

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
		securityContextRepository.saveContext(context, request, response);

		log.debug("로그인 성공: {} / 권한 {}", resultVO.getId(), authorities);
	}

	/**
	 * 로그인 액션(원본 enterprise-biz-jsp 호환 별칭). 실제 인증은 actionSecurityLogin.do에서 처리.
	 */
	@RequestMapping(value = "/uat/uia/actionLogin.do")
	public String actionLogin(ModelMap model) {
		return "redirect:/uat/uia/actionMain.do";
	}

	/**
	 * 로그인 후 메인화면으로 들어간다.
	 */
	@RequestMapping(value = "/uat/uia/actionMain.do")
	public String actionMain(ModelMap model) {
		return "redirect:/";
	}

	/**
	 * 로그아웃한다. (세션 무효화 + SecurityContext 정리)
	 */
	@RequestMapping(value = "/uat/uia/actionLogout.do")
	public String actionLogout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		SecurityContextHolder.clearContext();
		return "redirect:/";
	}
}
