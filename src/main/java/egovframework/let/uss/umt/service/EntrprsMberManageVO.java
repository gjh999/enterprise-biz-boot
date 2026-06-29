package egovframework.let.uss.umt.service;

import org.egovframe.rte.ptl.reactive.validation.EgovNullCheck;
import org.egovframe.rte.ptl.reactive.validation.EgovEmailCheck;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 기업회원VO클래스로서 기업회원 가입/관리 비지니스로직 처리용 항목을 구성한다.
 * TB_ENTRPRS_MBER 실제 컬럼에 대응한다.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *  2026.06.29  Claude        기업회원 가입 기능 신규(포털 이식)
 * </pre>
 */
@Getter
@Setter
public class EntrprsMberManageVO extends UserDefaultVO {

	private static final long serialVersionUID = 1L;

	/** 사용자고유아이디(ESNTL_ID) */
	private String uniqId = "";

	/** 기업회원 ID */
	@EgovNullCheck
	@Size(max = 20)
	private String entrprsMberId;

	/** 비밀번호 */
	private String entrprsMberPassword;

	/** 비밀번호 힌트 */
	@EgovNullCheck
	@Size(max = 100)
	private String entrprsMberPasswordHint;

	/** 비밀번호 정답 */
	@EgovNullCheck
	@Size(max = 100)
	private String entrprsMberPasswordCnsr;

	/** 기업구분코드 */
	@Size(max = 15)
	private String entrprsSeCode;

	/** 사업자등록번호 */
	@Size(max = 10)
	private String bizrno;

	/** 법인등록번호 */
	@Size(max = 13)
	private String jurirno;

	/** 회사명 */
	@EgovNullCheck
	@Size(max = 60)
	private String cmpnyNm;

	/** 대표자명(대표이사) */
	@Size(max = 50)
	private String cxfc;

	/** 업종코드 */
	@Size(max = 15)
	private String indutyCode;

	/** 신청자명(담당자) */
	@EgovNullCheck
	@Size(max = 50)
	private String applcntNm;

	/** 신청자 주민등록번호 */
	@Size(max = 13)
	private String applcntIhidnum;

	/** 신청자 이메일주소 */
	@EgovEmailCheck
	private String applcntEmailAdres;

	/** 기업회원 상태 */
	@Size(max = 15)
	private String entrprsMberSttus;

	/** 전화번호(단일 키인값, 자동 하이픈) */
	@Size(max = 20)
	private String telno;

	/** 팩스번호 */
	@Size(max = 20)
	private String fxnum;

	/** 우편번호 */
	@Size(max = 6)
	private String zip;

	/** 주소 */
	@Size(max = 100)
	private String adres;

	/** 상세주소 */
	@Size(max = 100)
	private String detailAdres;

	/** 그룹 ID */
	@EgovNullCheck
	private String groupId;

	/** 가입일자 */
	private String sbscrbDe;
}
