package egovframework.let.uss.umt.web;

import org.egovframe.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.let.uss.umt.service.EgovEntrprsMberManageService;
import egovframework.let.uss.umt.service.EntrprsMberManageVO;
import jakarta.annotation.Resource;

/**
 * 기업회원 가입 관련 요청을 처리하는 Controller.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *  2026.06.29  Claude        기업회원 가입 기능 신규(포털 이식)
 *   2026.07.02  구재호        Spring Boot + Thymeleaf + KRDS + 다국어(i18n) 전환
 * </pre>
 */
@Controller
public class EgovEntrprsMberManageController {

	@Resource(name = "entrprsMberManageService")
	private EgovEntrprsMberManageService entrprsMberManageService;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	/**
	 * 회원가입 유형 선택 화면(일반회원 / 기업회원).
	 */
	@RequestMapping("/uss/umt/cmm/EgovMberSbscrbSelect.do")
	public String sbscrbSelect() {
		return "cmm/uss/umt/EgovMberSbscrbSelect";
	}

	/**
	 * 기업회원 약관 확인 화면(가입 진입점).
	 */
	@RequestMapping("/uss/umt/cmm/EgovStplatCnfirmEntrprs.do")
	public String stplatCnfirmEntrprs(Model model) throws Exception {
		// 기업회원용 약관(일반회원과 동일 약관 시드 사용)
		String stplatId = "STPLAT_0000000000001";
		model.addAttribute("stplatList", entrprsMberManageService.selectStplat(stplatId));
		model.addAttribute("sbscrbTy", "USR02"); // 기업회원
		return "cmm/uss/umt/EgovStplatCnfirmEntrprs";
	}

	/**
	 * 기업회원 가입 폼 화면으로 이동한다.
	 */
	@RequestMapping("/uss/umt/cmm/EgovEntrprsMberSbscrbView.do")
	public String entrprsMberSbscrbView(@ModelAttribute("entrprsMberManageVO") EntrprsMberManageVO vo, Model model) throws Exception {
		// @Valid 통과용 기본값(컨트롤러가 제출 시 재설정)
		vo.setGroupId("GROUP_00000000000001");
		vo.setEntrprsMberSttus("A");
		return "cmm/uss/umt/EgovEntrprsMberSbscrb";
	}

	/**
	 * 기업회원 가입처리 후 가입완료 안내 화면으로 이동한다.
	 */
	@RequestMapping("/uss/umt/cmm/EgovEntrprsMberSbscrb.do")
	public String entrprsMberSbscrb(@jakarta.validation.Valid @ModelAttribute("entrprsMberManageVO") EntrprsMberManageVO vo,
			BindingResult bindingResult, Model model) throws Exception {

		if (bindingResult.hasErrors()) {
			vo.setGroupId("GROUP_00000000000001");
			vo.setEntrprsMberSttus("A");
			return "cmm/uss/umt/EgovEntrprsMberSbscrb";
		}

		// 상태 'A'(승인대기): 셀프 가입은 일반회원과 동일하게 관리자 승인 후에만 로그인 가능
		vo.setEntrprsMberSttus("A");
		vo.setGroupId("GROUP_00000000000001");
		entrprsMberManageService.insertEntrprsMber(vo);
		// 가입 신청 완료 안내 화면으로 이동(관리자 승인 후 로그인 안내)
		model.addAttribute("sbscrbMberNm", vo.getApplcntNm());
		return "cmm/uss/umt/EgovMberSbscrbCmplt";
	}

	/**
	 * 기업회원 정보 수정 화면(회원관리 목록에서 기업회원 아이디 클릭 시 진입).
	 */
	@RequestMapping("/uss/umt/mber/EgovEntrprsMberSelectUpdtView.do")
	public String entrprsMberSelectUpdtView(@RequestParam("selectedId") String uniqId, Model model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		if (!Boolean.TRUE.equals(EgovUserDetailsHelper.isAuthenticated())) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "uat/uia/EgovLoginUsr";
		}

		EntrprsMberManageVO vo = entrprsMberManageService.selectEntrprsMber(uniqId);
		model.addAttribute("entrprsMberManageVO", vo);
		return "cmm/uss/umt/EgovEntrprsMberSelectUpdt";
	}

	/**
	 * 기업회원 정보 수정처리 후 회원관리 목록으로 이동한다.
	 */
	@RequestMapping("/uss/umt/mber/EgovEntrprsMberSelectUpdt.do")
	public String entrprsMberSelectUpdt(@ModelAttribute("entrprsMberManageVO") EntrprsMberManageVO vo,
			BindingResult bindingResult, Model model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		if (!Boolean.TRUE.equals(EgovUserDetailsHelper.isAuthenticated())) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "uat/uia/EgovLoginUsr";
		}

		entrprsMberManageService.updateEntrprsMber(vo);
		model.addAttribute("resultMsg", "success.common.update");
		return "forward:/uss/umt/mber/EgovMberManage.do";
	}
}
