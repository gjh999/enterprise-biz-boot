package egovframework.let.uss.sam.stp.web;

import java.util.List;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.let.uss.sam.ipm.service.EgovIndvdlInfoPolicyService;
import egovframework.let.uss.sam.ipm.service.IndvdlInfoPolicy;
import egovframework.let.uss.sam.stp.service.EgovStplatManageService;
import egovframework.let.uss.sam.stp.service.StplatManageDefaultVO;
import egovframework.let.uss.sam.stp.service.StplatManageVO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

/**
 *
 * 약관내용을 처리하는 비즈니스 구현 클래스
 * 
 * @author 공통서비스 개발팀 박정규
 * @since 2009.04.01
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.01  박정규          최초 생성
 *  2026.06.17  구재호          Spring Boot + Thymeleaf 전환
 *
 *      </pre>
 */
@Controller
public class EgovStplatManageController {

	@Resource(name = "StplatManageService")
	private EgovStplatManageService stplatManageService;

	/** 개인정보처리방침(ipm) 서비스 — 약관 등록폼에서 '개인정보처리방침' 유형 선택 시 위임 */
	@Resource(name = "egovIndvdlInfoPolicyService")
	private EgovIndvdlInfoPolicyService egovIndvdlInfoPolicyService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	/**
	 * 현재 인증 사용자가 ROLE_ADMIN 권한을 보유하는지 확인한다.
	 *
	 * @return 관리자 여부
	 */
	private boolean isAdmin() {
		List<String> authorities = EgovUserDetailsHelper.getAuthorities();
		return authorities != null && authorities.contains("ROLE_ADMIN");
	}

	/**
	 * 개별 배포시 메인메뉴를 조회한다.
	 * 
	 * @param model
	 * @return "/uss/sam/stp/EgovMain"
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/sam/stp/EgovMain.do")
	public String EgovMain(ModelMap model) throws Exception {
		return "uss/sam/stp/EgovMain";
	}

	/**
	 * 메뉴를 조회한다.
	 * 
	 * @param model
	 * @return "/uss/sam/stp/EgovLeft"
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/sam/stp/EgovLeft.do")
	public String EgovLeft(ModelMap model) throws Exception {
		return "uss/sam/stp/EgovLeft";
	}

	/**
	 * 약관정보 목록을 조회한다.
	 * 
	 * @param searchVO
	 * @param model
	 * @return "/uss/sam/stp/EgovStplatListInqire"
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/sam/stp/StplatListInqire.do")
	public String selectStplatList(@ModelAttribute("searchVO") StplatManageDefaultVO searchVO, ModelMap model)
			throws Exception {
		// 약관/개인정보 통합 목록으로 일원화(항목7) — 옛 분리 목록 진입점은 통합 목록(유형=이용약관)으로 리다이렉트.
		return "redirect:/uss/sam/terms/list.do?termsType=stplat";
	}

	/**
	 * 약관정보상세내용을 조회한다.
	 * 
	 * @param stplatManageVO
	 * @param searchVO
	 * @param model
	 * @return "/uss/sam/stp/EgovStplatDetailInqire"
	 * @throws Exception
	 */
	@RequestMapping("/uss/sam/stp/StplatDetailInqire.do")
	public String selectStplatDetail(StplatManageVO stplatManageVO,
			@ModelAttribute("searchVO") StplatManageDefaultVO searchVO, ModelMap model) throws Exception {

		StplatManageVO vo = stplatManageService.selectStplatDetail(stplatManageVO);

		model.addAttribute("result", vo);

		return "uss/sam/stp/EgovStplatDetailInqire";
	}

	/**
	 * 약관정보를 등록하기 위한 전 처리
	 * 
	 * @param searchVO
	 * @param model
	 * @return "/uss/sam/stp/EgovStplatCnRegist"
	 * @throws Exception
	 */
	@RequestMapping("/uss/sam/stp/StplatCnRegistView.do")
	public String insertStplatCnView(@ModelAttribute("searchVO") StplatManageDefaultVO searchVO, Model model)
			throws Exception {
		// 통합 등록(항목7)으로 일원화 — 옛 분리 등록 진입점은 통합 등록(유형=이용약관)으로 리다이렉트.
		return "redirect:/uss/sam/terms/registView.do?termsType=stplat";
	}

	/**
	 * 약관정보를 등록한다.
	 * 
	 * @param searchVO
	 * @param stplatManageVO
	 * @param bindingResult
	 * @return "forward:/uss/sam/stp/StplatListInqire.do"
	 * @throws Exception
	 */
	@RequestMapping("/uss/sam/stp/StplatCnRegist.do")
	public String insertStplatCn(@ModelAttribute("searchVO") StplatManageDefaultVO searchVO,
			@RequestParam(value = "stplatType", required = false, defaultValue = "stp") String stplatType,
			@Valid @ModelAttribute("stplatManageVO") StplatManageVO stplatManageVO,
			BindingResult bindingResult, ModelMap model) throws Exception {

		// 등록은 관리자(ROLE_ADMIN)만 가능
		if (!isAdmin()) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "uat/uia/EgovLoginUsr";
		}

		// 로그인VO에서 사용자 정보 가져오기
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		String frstRegisterId = loginVO.getUniqId();

		// 유형이 '개인정보처리방침(ipm)'이면 ipm 서비스로 위임 저장한다.
		// (이용약관 전용 필드 검증오류는 무시하고, 개인정보처리방침 필수값만 직접 검증)
		if ("ipm".equals(stplatType)) {
			IndvdlInfoPolicy policy = new IndvdlInfoPolicy();
			policy.setIndvdlInfoNm(stplatManageVO.getUseStplatNm()); // 명칭 → INDVDL_INFO_POLICY_NM
			policy.setIndvdlInfoDc(stplatManageVO.getUseStplatCn()); // 내용 → INDVDL_INFO_POLICY_CN
			policy.setIndvdlInfoYn(stplatManageVO.getIndvdlInfoYn()); // 동의여부 → INDVDL_INFO_POLICY_AGRE_AT

			// 개인정보처리방침 필수값 검증(명칭/내용/동의여부)
			if (isBlank(policy.getIndvdlInfoNm()) || isBlank(policy.getIndvdlInfoDc())
					|| isBlank(policy.getIndvdlInfoYn())) {
				model.addAttribute("stplatType", stplatType);
				model.addAttribute("ipmValidationError", true);
				return "uss/sam/stp/EgovStplatCnRegist";
			}

			policy.setFrstRegisterId(frstRegisterId);
			policy.setLastUpdusrId(frstRegisterId);
			egovIndvdlInfoPolicyService.insertIndvdlInfoPolicy(policy);

			return "forward:/uss/sam/ipm/listIndvdlInfoPolicy.do";
		}

		// 이용약관(stp) 저장
		if (bindingResult.hasErrors()) {
			model.addAttribute("stplatType", stplatType);
			return "uss/sam/stp/EgovStplatCnRegist";
		}

		stplatManageVO.setFrstRegisterId(frstRegisterId); // 최초등록자ID
		stplatManageVO.setLastUpdusrId(frstRegisterId); // 최종수정자ID

		stplatManageService.insertStplatCn(stplatManageVO);

		return "redirect:/uss/sam/terms/list.do?termsType=stplat";
	}

	/** 문자열 공백 여부 */
	private boolean isBlank(String s) {
		return s == null || s.trim().isEmpty();
	}

	/**
	 * 약관정보를 수정하기 위한 전 처리
	 * 
	 * @param useStplatId
	 * @param searchVO
	 * @param model
	 * @return "/uss/sam/stp/EgovStplatCnUpdt"
	 * @throws Exception
	 */
	@RequestMapping("/uss/sam/stp/StplatCnUpdtView.do")
	public String updateStplatCnView(@RequestParam("useStplatId") String useStplatId,
			@ModelAttribute("searchVO") StplatManageDefaultVO searchVO, ModelMap model) throws Exception {

		// 수정은 관리자(ROLE_ADMIN)만 가능
		if (!isAdmin()) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "uat/uia/EgovLoginUsr";
		}

		StplatManageVO stplatManageVO = new StplatManageVO();

		// Primary Key 값 세팅
		stplatManageVO.setUseStplatId(useStplatId);

		// 변수명은 CoC 에 따라
		model.addAttribute(selectStplatDetail(stplatManageVO, searchVO, model));

		// 변수명은 CoC 에 따라 JSTL사용을 위해
		model.addAttribute("stplatManageVO", stplatManageService.selectStplatDetail(stplatManageVO));

		return "uss/sam/stp/EgovStplatCnUpdt";
	}

	/**
	 * 약관정보를 수정 처리한다.
	 * 
	 * @param searchVO
	 * @param stplatManageVO
	 * @param bindingResult
	 * @return "forward:/uss/sam/stp/StplatListInqire.do"
	 * @throws Exception
	 */
	@RequestMapping("/uss/sam/stp/StplatCnUpdt.do")
	public String updateStplatCn(@ModelAttribute("searchVO") StplatManageDefaultVO searchVO,
			@Valid @ModelAttribute("stplatManageVO") StplatManageVO stplatManageVO,
			BindingResult bindingResult, ModelMap model) throws Exception {

		// 수정은 관리자(ROLE_ADMIN)만 가능
		if (!isAdmin()) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "uat/uia/EgovLoginUsr";
		}

		if (bindingResult.hasErrors()) {

			// 검증 실패 시 수정 화면을 다시 보여준다 (기존 EgovStplatCnRegist 반환 오류 수정)
			return "uss/sam/stp/EgovStplatCnUpdt";

		}

		// 로그인VO에서 사용자 정보 가져오기
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		String lastUpdusrId = loginVO.getUniqId();

		stplatManageVO.setLastUpdusrId(lastUpdusrId); // 최종수정자ID

		stplatManageService.updateStplatCn(stplatManageVO);

		return "redirect:/uss/sam/terms/list.do?termsType=stplat";
	}

	/**
	 * 약관정보를 삭제 처리한다.
	 * 
	 * @param stplatManageVO
	 * @param searchVO
	 * @return "forward:/uss/sam/stp/StplatListInqire.do"
	 * @throws Exception
	 */
	@RequestMapping("/uss/sam/stp/StplatCnDelete.do")
	public String deleteStplatCn(StplatManageVO stplatManageVO,
			@ModelAttribute("searchVO") StplatManageDefaultVO searchVO, ModelMap model) throws Exception {

		// 삭제는 관리자(ROLE_ADMIN)만 가능
		if (!isAdmin()) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "uat/uia/EgovLoginUsr";
		}

		stplatManageService.deleteStplatCn(stplatManageVO);

		return "redirect:/uss/sam/terms/list.do?termsType=stplat";
	}

}
