package egovframework.let.uss.ion.bnr.service;

import egovframework.com.cmm.ComDefaultVO;
import org.egovframe.rte.ptl.reactive.validation.EgovNullCheck;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
/**
 * 배너에 대한 model 클래스를 정의한다.
 * 배너의 일련번호, 배너명, 링크URL, 배너설명, 반영여부 항목을 관리한다.
 * @author 공통서비스개발팀 lee.m.j
 * @since 2009.08.03
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.08.03  lee.m.j        최초 생성
 *   2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성 
 *  2026.06.17  구재호          Spring Boot + Thymeleaf 전환
 *
 * </pre>
 */
public class Banner extends ComDefaultVO {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 배너 ID
	 */
	private String bannerId;
	/**
	 * 배너 명
	 */
	@EgovNullCheck
	@Size(max=30)
	private String bannerNm;
	/**
	 * 링크 URL (선택사항 — 없으면 이미지/슬라이드만 표시)
	 */
	private String linkUrl;
	/**
	 * 배너 이미지
	 */
	private String bannerImage;
	/**
	 * 배너 이미지 파일
	 */
	private String bannerImageFile;	
	/**
	 * 배너 설명
	 */
	private String bannerDc;
	/**
	 * 정렬 순서
	 */
	@EgovNullCheck
	@Size(max=100)
	@Pattern(regexp = "^[0-9]*$", message = "{validation.integer.check}")
	private String sortOrdr;
	/**
	 * 반영여부
	 */
	private String reflctAt;
	/**
	 * 배너 유형 (MAIN: 메인, POPUP: 팝업, FOOTER: 푸터)
	 */
	private String bannerTy;
	/**
	 * 팝업 좌표 X(left, px)
	 */
	private Integer popupLeft;
	/**
	 * 팝업 좌표 Y(top, px)
	 */
	private Integer popupTop;
	/**
	 * 팝업 너비(px)
	 */
	private Integer popupWidth;
	/**
	 * 팝업 높이(px)
	 */
	private Integer popupHeight;
	/**
	 * 팝업 묶음 표출 여부 (Y: 여러 팝업을 한 팝업 캐러셀로 묶음, N: 각각 별도 팝업)
	 */
	private String popupGroupAt;
	/**
	 * 사용자 ID
	 */
	private String userId;
	/**
	 * 등록일자
	 */
	private String regDate;
	/**
	 * 수정일시 (LAST_UPDT_PNTTM)
	 */
	private String lastUpdtPnttm;
	/**
	 * 게시기간 시작일 (EXPSR_BGNDE, nullable — 미설정 시 상시 노출)
	 */
	private String expsrBgnde;
	/**
	 * 게시기간 종료일 (EXPSR_ENDDE, nullable — 미설정 시 상시 노출)
	 */
	private String expsrEndde;
	/**
	 * 파일첨부여부
	 */
	private boolean isAtchFile;
	
	/**
	 * @return the bannerId
	 */
	public String getBannerId() {
		return bannerId;
	}
	/**
	 * @param bannerId the bannerId to set
	 */
	public void setBannerId(String bannerId) {
		this.bannerId = bannerId;
	}
	/**
	 * @return the bannerNm
	 */
	public String getBannerNm() {
		return bannerNm;
	}
	/**
	 * @param bannerNm the bannerNm to set
	 */
	public void setBannerNm(String bannerNm) {
		this.bannerNm = bannerNm;
	}
	/**
	 * @return the linkUrl
	 */
	public String getLinkUrl() {
		return linkUrl;
	}
	/**
	 * @param linkUrl the linkUrl to set
	 */
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	/**
	 * @return the bannerImage
	 */
	public String getBannerImage() {
		return bannerImage;
	}
	/**
	 * @param bannerImage the bannerImage to set
	 */
	public void setBannerImage(String bannerImage) {
		this.bannerImage = bannerImage;
	}
	/**
	 * @return the bannerImageFile
	 */
	public String getBannerImageFile() {
		return bannerImageFile;
	}
	/**
	 * @param bannerImageFile the bannerImageFile to set
	 */
	public void setBannerImageFile(String bannerImageFile) {
		this.bannerImageFile = bannerImageFile;
	}
	/**
	 * @return the bannerDc
	 */
	public String getBannerDc() {
		return bannerDc;
	}
	/**
	 * @param bannerDc the bannerDc to set
	 */
	public void setBannerDc(String bannerDc) {
		this.bannerDc = bannerDc;
	}
	/**
	 * @return the sortOrdr
	 */
	public String getSortOrdr() {
		return sortOrdr;
	}
	/**
	 * @param sortOrdr the sortOrdr to set
	 */
	public void setSortOrdr(String sortOrdr) {
		this.sortOrdr = sortOrdr;
	}
	/**
	 * @return the reflctAt
	 */
	public String getReflctAt() {
		return reflctAt;
	}
	/**
	 * @param reflctAt the reflctAt to set
	 */
	public void setReflctAt(String reflctAt) {
		this.reflctAt = reflctAt;
	}
	/**
	 * @return the bannerTy
	 */
	public String getBannerTy() {
		return bannerTy;
	}
	/**
	 * @param bannerTy the bannerTy to set
	 */
	public void setBannerTy(String bannerTy) {
		this.bannerTy = bannerTy;
	}
	public Integer getPopupLeft() {
		return popupLeft;
	}
	public void setPopupLeft(Integer popupLeft) {
		this.popupLeft = popupLeft;
	}
	public Integer getPopupTop() {
		return popupTop;
	}
	public void setPopupTop(Integer popupTop) {
		this.popupTop = popupTop;
	}
	public Integer getPopupWidth() {
		return popupWidth;
	}
	public void setPopupWidth(Integer popupWidth) {
		this.popupWidth = popupWidth;
	}
	public Integer getPopupHeight() {
		return popupHeight;
	}
	public void setPopupHeight(Integer popupHeight) {
		this.popupHeight = popupHeight;
	}
	public String getPopupGroupAt() {
		return popupGroupAt;
	}
	public void setPopupGroupAt(String popupGroupAt) {
		this.popupGroupAt = popupGroupAt;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}
	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	/**
	 * @return the lastUpdtPnttm
	 */
	public String getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}
	/**
	 * @param lastUpdtPnttm the lastUpdtPnttm to set
	 */
	public void setLastUpdtPnttm(String lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}
	/**
	 * @return the expsrBgnde
	 */
	public String getExpsrBgnde() {
		return expsrBgnde;
	}
	/**
	 * @param expsrBgnde the expsrBgnde to set
	 */
	public void setExpsrBgnde(String expsrBgnde) {
		this.expsrBgnde = expsrBgnde;
	}
	/**
	 * @return the expsrEndde
	 */
	public String getExpsrEndde() {
		return expsrEndde;
	}
	/**
	 * @param expsrEndde the expsrEndde to set
	 */
	public void setExpsrEndde(String expsrEndde) {
		this.expsrEndde = expsrEndde;
	}
	/**
	 * @return the isAtchFile
	 */
	public boolean isAtchFile() {
		return isAtchFile;
	}
	/**
	 * @param isAtchFile the isAtchFile to set
	 */
	public void setAtchFile(boolean isAtchFile) {
		this.isAtchFile = isAtchFile;
	}

}
