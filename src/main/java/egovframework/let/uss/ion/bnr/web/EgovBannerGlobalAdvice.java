package egovframework.let.uss.ion.bnr.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import egovframework.let.uss.ion.bnr.service.BannerVO;
import egovframework.let.uss.ion.bnr.service.EgovBannerService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 모든 화면(레이아웃 fragment 포함)에서 사용할 배너 정보를 모델에 주입하는 ControllerAdvice.
 *
 * <pre>
 * - footerBannerList : 푸터 슬라이드 배너(BANNER_TY='FOOTER', REFLCT_AT='Y')
 * - popupBannerList  : 페이지 진입 팝업 배너(BANNER_TY='POPUP', REFLCT_AT='Y')
 * - bannerInterval   : 슬라이드 자동 전환 주기(ms). portal.banner.interval 프로퍼티(기본 5000)
 * 메인 배너(MAIN)는 홈 화면 전용이므로 EgovMainController 에서 별도로 모델에 담는다.
 * 데이터 계층 오류가 나도 화면이 렌더링되도록 방어적으로 빈 목록을 제공한다.
 * </pre>
 *
 * @author Boot 전환
 */
@Slf4j
@ControllerAdvice
public class EgovBannerGlobalAdvice {

	@Resource(name = "egovBannerService")
	private EgovBannerService egovBannerService;

	@Value("${portal.banner.interval:5000}")
	private long bannerInterval;

	/**
	 * 슬라이드 자동 전환 주기(ms)를 모든 모델에 주입한다.
	 */
	@ModelAttribute("bannerInterval")
	public long bannerInterval() {
		return bannerInterval;
	}

	/**
	 * 푸터 배너 목록을 모든 모델에 주입한다.
	 */
	@ModelAttribute("footerBannerList")
	public List<BannerVO> footerBannerList() {
		return selectByType("FOOTER");
	}

	/**
	 * 팝업 배너 목록을 모든 모델에 주입한다.
	 */
	@ModelAttribute("popupBannerList")
	public List<BannerVO> popupBannerList() {
		return selectByType("POPUP");
	}

	/**
	 * 현재 요청이 로그인/공개 인증 화면 등 팝업을 띄우지 말아야 하는 경로인지 여부를 모델에 주입한다.
	 * default 레이아웃은 이 값이 true 이면 팝업 fragment 자체를 렌더링하지 않는다.
	 *
	 * <pre>
	 * 제외 경로:
	 *  - /uat/uia/  : 로그인/로그아웃/인증 화면(egovLoginUsr.do, actionSecurityLogin.do 등)
	 *  - /error     : 에러 화면
	 * </pre>
	 */
	@ModelAttribute("bannerPopupHidden")
	public boolean bannerPopupHidden(HttpServletRequest request) {
		String uri = request.getRequestURI();
		if (uri == null) {
			return false;
		}
		String ctx = request.getContextPath();
		if (ctx != null && !ctx.isEmpty() && uri.startsWith(ctx)) {
			uri = uri.substring(ctx.length());
		}
		return uri.startsWith("/uat/uia/") || uri.startsWith("/error");
	}

	/**
	 * 현재 요청이 홈(메인) 화면인지 여부를 모델에 주입한다.
	 * 팝업 배너는 홈에서만 노출되어야 하므로 default 레이아웃은 이 값이 true 일 때만 팝업 fragment 를 렌더링한다.
	 *
	 * <pre>
	 * 홈 경로: "/" (EgovMainController), "/cmm/main/mainPage.do"(메인 호환 매핑)
	 * </pre>
	 */
	@ModelAttribute("isHome")
	public boolean isHome(HttpServletRequest request) {
		String uri = request.getRequestURI();
		if (uri == null) {
			return false;
		}
		String ctx = request.getContextPath();
		if (ctx != null && !ctx.isEmpty() && uri.startsWith(ctx)) {
			uri = uri.substring(ctx.length());
		}
		return "/".equals(uri) || "/cmm/main/mainPage.do".equals(uri);
	}

	private List<BannerVO> selectByType(String bannerTy) {
		try {
			BannerVO bannerVO = new BannerVO();
			bannerVO.setBannerTy(bannerTy);
			List<BannerVO> list = egovBannerService.selectBannerListByType(bannerVO);
			return list == null ? List.of() : list;
		} catch (Exception e) {
			log.warn("배너 조회 실패(type={}): {}", bannerTy, e.getMessage());
			return List.of();
		}
	}
}
