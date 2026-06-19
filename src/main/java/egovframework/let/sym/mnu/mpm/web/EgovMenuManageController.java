package egovframework.let.sym.mnu.mpm.web;

import java.util.Map;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.let.sym.mnu.mpm.service.EgovMenuManageService;
import egovframework.let.sym.mnu.mpm.service.MenuManageVO;
import egovframework.let.sym.prm.service.EgovProgrmManageService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

/**
 * 메뉴목록 관리및 메뉴생성, 사이트맵 생성을 처리하는 비즈니스 구현 클래스
 *
 * @author 개발환경 개발팀 이용
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.20  이  용          최초 생성
 *	 2011.07.01	 서준식	   메뉴정보 삭제시 참조되고 있는 하위 메뉴가 있는지 체크하는 로직 추가
 *	 2011.07.27	 서준식	   deleteMenuManageList() 메서드에서 메뉴 멀티 삭제 버그 수정
 *   2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성
 *   2026.06.17  포팅            Boot 전환 - 엑셀 일괄등록(POI/멀티파트) 비활성화
 *   2026.06.17  구재호          Spring Boot + Thymeleaf 전환
 *      </pre>
 */
@Controller
public class EgovMenuManageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovMenuManageController.class);

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** EgovMenuManageService */
	@Resource(name = "menuManageService")
	private EgovMenuManageService menuManageService;

	/** EgovProgrmManageService */
	@Resource(name = "progrmManageService")
	private EgovProgrmManageService progrmManageService;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	/**
	 * 메뉴정보목록을 상세화면 호출 및 상세조회한다.
	 *
	 * @param req_menuNo String
	 * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuDetailSelectUpdt"
	 * @exception Exception
	 */
	@RequestMapping(value = "/sym/mnu/mpm/EgovMenuManageListDetailSelect.do")
	public String selectMenuManage(@RequestParam("req_menuNo") String req_menuNo,
			@ModelAttribute("searchVO") ComDefaultVO searchVO, ModelMap model) throws Exception {
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (isAuthenticated == null || !isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "uat/uia/EgovLoginUsr";
		}
		searchVO.setSearchKeyword(req_menuNo);

		MenuManageVO resultVO = menuManageService.selectMenuManage(searchVO);
		model.addAttribute("menuManageVO", resultVO);

		return "sym/mnu/mpm/EgovMenuDetailSelectUpdt";
	}

	/**
	 * 메뉴목록 리스트조회한다.
	 *
	 * @param searchVO ComDefaultVO
	 * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuManage"
	 * @exception Exception
	 */
	@RequestMapping(value = "/sym/mnu/mpm/EgovMenuManageSelect.do")
	public String selectMenuManageList(@ModelAttribute("searchVO") ComDefaultVO searchVO, ModelMap model)
			throws Exception {
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (isAuthenticated == null || !isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "uat/uia/EgovLoginUsr";
		}
		// 내역 조회
		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		model.addAttribute("list_menumanage", menuManageService.selectMenuManageList(searchVO));

		int totCnt = menuManageService.selectMenuManageListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "sym/mnu/mpm/EgovMenuManage";
	}

	/**
	 * 메뉴목록 멀티 삭제한다.
	 *
	 * @param checkedMenuNoForDel String
	 * @return 출력페이지정보 "forward:/sym/mnu/mpm/EgovMenuManageSelect.do"
	 * @exception Exception
	 */
	@RequestMapping("/sym/mnu/mpm/EgovMenuManageListDelete.do")
	public String deleteMenuManageList(@RequestParam("checkedMenuNoForDel") String checkedMenuNoForDel,
			@ModelAttribute("menuManageVO") MenuManageVO menuManageVO, ModelMap model)
			throws Exception {
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (isAuthenticated == null || !isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "uat/uia/EgovLoginUsr";
		}
		String sLocationUrl = null;
		String resultMsg = "";

		String[] delMenuNo = checkedMenuNoForDel != null ? checkedMenuNoForDel.split(",") : new String[0];
		if (delMenuNo.length == 0) {
			resultMsg = egovMessageSource.getMessage("fail.common.delete");
			sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuManageSelect.do";
		} else {
			menuManageVO.setMenuNo(Integer.parseInt(delMenuNo[0]));
			if (menuManageService.selectUpperMenuNoByPk(menuManageVO) != 0) {
				resultMsg = egovMessageSource.getMessage("fail.common.delete.upperMenuExist");
				sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuManageSelect.do";
			} else {
				menuManageService.deleteMenuManageList(checkedMenuNoForDel);
				resultMsg = egovMessageSource.getMessage("success.common.delete");
				sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuManageSelect.do";
			}
		}
		model.addAttribute("resultMsg", resultMsg);
		return sLocationUrl;
	}

	/**
	 * 메뉴정보 등록화면으로 이동 (GET)
	 *
	 * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuRegist"
	 * @exception Exception
	 */
	@GetMapping(value = "/sym/mnu/mpm/EgovMenuRegistInsert.do")
	public String insertMenuManageView(ModelMap model) throws Exception {
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (isAuthenticated == null || !isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "uat/uia/EgovLoginUsr";
		}

		model.addAttribute("menuManageVO", new MenuManageVO());
		return "sym/mnu/mpm/EgovMenuRegist";
	}

	/**
	 * 메뉴정보를 등록 한다 (POST)
	 *
	 * @param menuManageVO MenuManageVO
	 * @return 출력페이지정보 등록처리시 "forward:/sym/mnu/mpm/EgovMenuManageSelect.do"
	 * @exception Exception
	 */
	@PostMapping(value = "/sym/mnu/mpm/EgovMenuRegistInsert.do")
	public String insertMenuManage(@Valid @ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
			BindingResult bindingResult,
			ModelMap model) throws Exception {
		String sLocationUrl = null;
		String resultMsg = "";
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (isAuthenticated == null || !isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "uat/uia/EgovLoginUsr";
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("menuManageVO", menuManageVO);
			return "sym/mnu/mpm/EgovMenuRegist";
		}

		if (menuManageService.selectMenuNoByPk(menuManageVO) == 0) {
			ComDefaultVO searchVO = new ComDefaultVO();
			searchVO.setSearchKeyword(menuManageVO.getProgrmFileNm());
			if (progrmManageService.selectProgrmNMTotCnt(searchVO) == 0) {
				resultMsg = egovMessageSource.getMessage("fail.common.insert");
				sLocationUrl = "sym/mnu/mpm/EgovMenuRegist";
			} else {
				menuManageService.insertMenuManage(menuManageVO);
				resultMsg = egovMessageSource.getMessage("success.common.insert");
				sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuManageSelect.do";
			}
		} else {
			resultMsg = egovMessageSource.getMessage("common.isExist.msg");
			sLocationUrl = "sym/mnu/mpm/EgovMenuRegist";
		}
		model.addAttribute("resultMsg", resultMsg);

		return sLocationUrl;
	}

	/**
	 * 메뉴정보를 수정 한다.
	 *
	 * @param menuManageVO MenuManageVO
	 * @return 출력페이지정보 "forward:/sym/mnu/mpm/EgovMenuManageSelect.do"
	 * @exception Exception
	 */
	@PostMapping(value = "/sym/mnu/mpm/EgovMenuDetailSelectUpdt.do")
	public String updateMenuManage(@Valid @ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
			BindingResult bindingResult, ModelMap model) throws Exception {
		String sLocationUrl = null;
		String resultMsg = "";
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (isAuthenticated == null || !isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "uat/uia/EgovLoginUsr";
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("menuManageVO", menuManageVO);
			return "sym/mnu/mpm/EgovMenuDetailSelectUpdt";
		}
		ComDefaultVO searchVO = new ComDefaultVO();
		searchVO.setSearchKeyword(menuManageVO.getProgrmFileNm());
		if (progrmManageService.selectProgrmNMTotCnt(searchVO) == 0) {
			resultMsg = egovMessageSource.getMessage("fail.common.update");
			model.addAttribute("menuManageVO", menuManageVO);
			model.addAttribute("resultMsg", resultMsg);
			return "sym/mnu/mpm/EgovMenuDetailSelectUpdt";
		} else {
			menuManageService.updateMenuManage(menuManageVO);
			resultMsg = egovMessageSource.getMessage("success.common.update");
			sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuManageSelect.do";
		}
		model.addAttribute("resultMsg", resultMsg);

		return sLocationUrl;
	}

	/**
	 * 메뉴정보를 삭제 한다.
	 *
	 * @param menuManageVO MenuManageVO
	 * @return 출력페이지정보 "forward:/sym/mnu/mpm/EgovMenuManageSelect.do"
	 * @exception Exception
	 */
	@RequestMapping(value = "/sym/mnu/mpm/EgovMenuManageDelete.do")
	public String deleteMenuManage(@ModelAttribute("menuManageVO") MenuManageVO menuManageVO, ModelMap model)
			throws Exception {
		String resultMsg = "";
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (isAuthenticated == null || !isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "uat/uia/EgovLoginUsr";
		}
		if (menuManageService.selectUpperMenuNoByPk(menuManageVO) != 0) {
			resultMsg = egovMessageSource.getMessage("fail.common.delete.upperMenuExist");
			model.addAttribute("resultMsg", resultMsg);
			return "forward:/sym/mnu/mpm/EgovMenuManageSelect.do";
		}

		menuManageService.deleteMenuManage(menuManageVO);
		resultMsg = egovMessageSource.getMessage("success.common.delete");
		String _MenuNm = "%";
		menuManageVO.setMenuNm(_MenuNm);
		model.addAttribute("resultMsg", resultMsg);

		return "forward:/sym/mnu/mpm/EgovMenuManageSelect.do";
	}

	/* ### 일괄처리 프로세스 ### */

	/**
	 * 메뉴생성 일괄삭제프로세스
	 *
	 * @param menuManageVO MenuManageVO
	 * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuBndeRegist"
	 * @exception Exception
	 */
	@RequestMapping(value = "/sym/mnu/mpm/EgovMenuBndeAllDelete.do")
	public String menuBndeAllDelete(@ModelAttribute("menuManageVO") MenuManageVO menuManageVO, ModelMap model)
			throws Exception {
		String resultMsg = "";
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (isAuthenticated == null || !isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "uat/uia/EgovLoginUsr";
		}
		menuManageService.menuBndeAllDelete();
		resultMsg = egovMessageSource.getMessage("success.common.delete");
		model.addAttribute("resultMsg", resultMsg);

		return "sym/mnu/mpm/EgovMenuBndeRegist";
	}

	/**
	 * 메뉴일괄등록화면 호출 및 메뉴일괄등록처리 프로세스.
	 * Boot 전환에서 엑셀(POI)/멀티파트 일괄등록은 비활성화되어 화면 호출만 처리한다.
	 *
	 * @param commandMap   Map
	 * @param menuManageVO MenuManageVO
	 * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuBndeRegist"
	 * @exception Exception
	 */
	@RequestMapping(value = "/sym/mnu/mpm/EgovMenuBndeRegist.do")
	public String menuBndeRegist(@RequestParam Map<String, Object> commandMap,
			@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
			ModelMap model) throws Exception {
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (isAuthenticated == null || !isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "uat/uia/EgovLoginUsr";
		}
		String sCmd = commandMap.get("cmd") == null ? "" : (String) commandMap.get("cmd");
		if (sCmd.equals("bndeInsert")) {
			LOGGER.debug("menuBndeRegist: 엑셀 일괄등록 기능은 Boot 전환에서 미지원입니다.");
			model.addAttribute("resultMsg", "엑셀 일괄등록 기능은 현재 지원하지 않습니다.");
		}
		return "sym/mnu/mpm/EgovMenuBndeRegist";
	}

}
