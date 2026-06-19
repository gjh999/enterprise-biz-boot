package egovframework.let.main.web;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.let.cop.bbs.service.BoardVO;
import egovframework.let.cop.bbs.service.EgovBBSManageService;
import egovframework.let.uss.olh.faq.service.EgovFaqManageService;
import egovframework.let.uss.olh.faq.service.FaqManageDefaultVO;
import egovframework.let.uss.olp.qri.service.EgovQustnrRespondInfoService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * 포털 메인 페이지 컨트롤러 (Spring Boot + Thymeleaf).
 *
 * <pre>
 * 메인 화면은 공지사항/자유게시판/FAQ 요약을 보여준다. 각 영역 조회는 모듈별 데이터
 * 계층 준비 여부와 무관하게 화면이 렌더링되도록 방어적으로 처리한다(실패 시 빈 목록).
 * 화면: templates/main/EgovMainView.html
 * </pre>
 *
 * @author 실행환경 개발팀 / Boot 전환 egovframe
 */
@Slf4j
@Controller
public class EgovMainController {

	@Resource(name = "EgovBBSManageService")
	private EgovBBSManageService bbsMngService;

	@Resource(name = "FaqManageService")
	private EgovFaqManageService faqManageService;

	@Resource(name = "egovQustnrRespondInfoService")
	private EgovQustnrRespondInfoService egovQustnrRespondInfoService;

	/**
	 * 포털 메인 페이지.
	 */
	@RequestMapping(value = {"/", "/cmm/main/mainPage.do"})
	public String mainPage(ModelMap model) {
		model.addAttribute("notiList", selectBoard("BBSMSTR_AAAAAAAAAAAA"));   // 공지사항
		model.addAttribute("bbsList", selectBoard("BBSMSTR_BBBBBBBBBBBB"));    // 자유게시판
		model.addAttribute("faqList", selectFaq());                            // FAQ
		model.addAttribute("qriList", selectQri());                            // 설문참여
		return "main/EgovMainView";
	}

	/**
	 * JSP 원본의 /cmm/forwardPage.do 호환 매핑. 메인으로 리다이렉트 처리한다.
	 */
	@RequestMapping(value = "/cmm/forwardPage.do")
	public String forwardPageWithMenuNo() {
		return "redirect:/";
	}

	@SuppressWarnings("unchecked")
	private List<?> selectBoard(String bbsId) {
		try {
			BoardVO boardVO = new BoardVO();
			boardVO.setUseAt("Y");
			boardVO.setPageUnit(5);
			boardVO.setPageSize(10);
			boardVO.setBbsId(bbsId);
			boardVO.setFirstIndex(0);
			boardVO.setLastIndex(5);
			boardVO.setRecordCountPerPage(5);
			Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, "BBSA02");
			Object result = map.get("resultList");
			return result instanceof List ? (List<Object>) result : List.of();
		} catch (Exception e) {
			log.warn("메인 게시판 조회 실패(bbsId={}): {}", bbsId, e.getMessage());
			return List.of();
		}
	}

	/**
	 * 메인 설문참여 요약 목록 조회. (설문 모듈 쿼리가 정비 중이므로 실패해도 메인은 렌더링되도록 방어)
	 */
	private List<?> selectQri() {
		try {
			ComDefaultVO qVO = new ComDefaultVO();
			qVO.setPageUnit(3);
			qVO.setPageSize(10);
			qVO.setFirstIndex(0);
			qVO.setLastIndex(3);
			qVO.setRecordCountPerPage(3);
			List<?> result = egovQustnrRespondInfoService.selectQustnrRespondInfoManageList(qVO);
			return result == null ? List.of() : result;
		} catch (Exception e) {
			log.warn("메인 설문참여 조회 실패: {}", e.getMessage());
			return List.of();
		}
	}

	private List<?> selectFaq() {
		try {
			FaqManageDefaultVO searchVO = new FaqManageDefaultVO();
			searchVO.setPageUnit(5);
			searchVO.setPageSize(10);
			searchVO.setFirstIndex(0);
			searchVO.setLastIndex(5);
			searchVO.setRecordCountPerPage(5);
			return faqManageService.selectFaqList(searchVO);
		} catch (Exception e) {
			log.warn("메인 FAQ 조회 실패: {}", e.getMessage());
			return List.of();
		}
	}
}
