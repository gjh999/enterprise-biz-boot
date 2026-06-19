package egovframework.let.main.web;

import org.egovframe.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.LoginVO;
import egovframework.let.sym.mnu.mpm.service.EgovMenuManageService;
import egovframework.let.sym.mnu.mpm.service.MenuManageVO;
import jakarta.annotation.Resource;

/**
 * 업무화면 프레임(헤더/푸터/좌측메뉴/상단메뉴) 조회 컨트롤러.
 * enterprise-biz-jsp 원본 EgovMainController 의 /sym/mms/* 엔드포인트를 Boot+Thymeleaf로 1:1 재현한다.
 * (정적자원/프래그먼트 경로이므로 SecurityConfig 의 PERMIT_ALL: /sym/mms/** 적용)
 */
@Controller
public class EgovMainFrameController {

	@Resource(name = "menuManageService")
	private EgovMenuManageService menuManageService;

	private void bindUser(MenuManageVO vo) throws Exception {
		LoginVO user = EgovUserDetailsHelper.isAuthenticated()
				? (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser() : null;
		if (user != null) {
			vo.setTmp_Id(user.getId());
			vo.setTmp_Name(user.getName());
			vo.setTmp_UserSe(user.getUserSe());
			vo.setTmp_Email(user.getEmail());
			vo.setTmp_OrgnztId(user.getOrgnztId());
			vo.setTmp_UniqId(user.getUniqId());
			model_lists(vo);
		} else {
			vo.setAuthorCode("ROLE_ANONYMOUS");
		}
	}

	private void model_lists(MenuManageVO vo) {
		// 메뉴 조회는 selectHeader/selectMainMenuHead 에서 model 에 담는다.
	}

	/** 헤더(업무화면 상단메뉴) */
	@RequestMapping(value = "/sym/mms/EgovHeader.do")
	public String selectHeader(@ModelAttribute("menuManageVO") MenuManageVO menuManageVO, ModelMap model) throws Exception {
		bindUser(menuManageVO);
		if (EgovUserDetailsHelper.isAuthenticated()) {
			model.addAttribute("list_headmenu", menuManageService.selectMainMenuHead(menuManageVO));
			model.addAttribute("list_menulist", menuManageService.selectMainMenuLeft(menuManageVO));
		}
		return "main/inc/EgovIncHeader";
	}

	/** 푸터 */
	@RequestMapping(value = "/sym/mms/EgovFooter.do")
	public String selectFooter(ModelMap model) throws Exception {
		return "main/inc/EgovIncFooter";
	}

	/** 좌측메뉴 */
	@RequestMapping(value = "/sym/mms/EgovMenuLeft.do")
	public String selectMenuLeft(ModelMap model) throws Exception {
		if (EgovUserDetailsHelper.isAuthenticated()) {
			model.addAttribute("lastLogoutDateTime", "");
		}
		return "main/inc/EgovIncLeftmenu";
	}

	/** 내부업무 상단메뉴 */
	@RequestMapping(value = "/sym/mms/EgovMainMenuHead.do")
	public String selectMainMenuHead(@ModelAttribute("menuManageVO") MenuManageVO menuManageVO, ModelMap model) throws Exception {
		bindUser(menuManageVO);
		if (EgovUserDetailsHelper.isAuthenticated()) {
			model.addAttribute("list_headmenu", menuManageService.selectMainMenuHead(menuManageVO));
			model.addAttribute("list_menulist", menuManageService.selectMainMenuLeft(menuManageVO));
		}
		return "main/inc/EgovIncTopnav";
	}

	/** 내부업무 좌측메뉴 */
	@RequestMapping(value = "/sym/mms/EgovMainMenuLeft.do")
	public String selectMainMenuLeft(ModelMap model) throws Exception {
		if (EgovUserDetailsHelper.isAuthenticated()) {
			model.addAttribute("lastLogoutDateTime", "");
		}
		return "main/inc/EgovIncLeftmenu";
	}
}
