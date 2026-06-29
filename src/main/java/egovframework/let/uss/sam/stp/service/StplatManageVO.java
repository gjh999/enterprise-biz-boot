package egovframework.let.uss.sam.stp.service;

import org.egovframe.rte.ptl.reactive.validation.EgovNullCheck;
import jakarta.validation.constraints.Size;

/**
 * 
 * 약관내용을 처리하는 VO 클래스
 * @author 공통서비스 개발팀 박정규
 * @since 2009.04.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.01  박정규          최초 생성
 *   2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성 
 *  2026.06.17  구재호          Spring Boot + Thymeleaf 전환
 *
 * </pre>
 */
public class StplatManageVO extends StplatManageDefaultVO {
	
    private static final long serialVersionUID = 1L;
    
    /** 이용약관 ID */
    private String useStplatId;
    
    /** 이용약관명 */
    @EgovNullCheck
    @Size(max=100)
    private String useStplatNm;    
    
    /** 이용약관내용 */
    @EgovNullCheck
    private String useStplatCn;
    
    /** 정보제공동의내용 */
    @EgovNullCheck
    private String infoProvdAgreCn;

    /**
     * 약관 유형 (stp=이용약관 / ipm=개인정보처리방침)
     * 등록폼의 유형 선택 값을 컨트롤러로 전달하기 위한 전용 필드(저장 안 함).
     */
    private String stplatType;

    /**
     * 개인정보처리방침 동의여부 (유형=ipm 일 때만 사용, INDVDL_INFO_POLICY_AGRE_AT 매핑).
     * 이용약관 저장 시에는 사용하지 않으며 검증 대상도 아니다(저장 안 함).
     */
    private String indvdlInfoYn;

    /** 최초등록시점 */
    private String frstRegisterPnttm;

    /** 최초등록자ID */
    private String frstRegisterId;

    /** 최종수정시점 */
    private String lastUpdusrPnttm;

    /** 최종수정자ID */
    private String lastUpdusrId;

    /** 버전 (예: '1.0') */
    private String ver;

    /** 적용일자 (YYYYMMDD) */
    private String aplcDe;

    /** 대표여부 ('Y'/'N') */
    private String reprsntAt;

    /** 사용여부 ('Y'=노출/'N'=미사용) */
    private String useAt;

    public String getUseAt() {
        return useAt;
    }

    public void setUseAt(String useAt) {
        this.useAt = useAt;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getAplcDe() {
        return aplcDe;
    }

    public void setAplcDe(String aplcDe) {
        this.aplcDe = aplcDe;
    }

    public String getReprsntAt() {
        return reprsntAt;
    }

    public void setReprsntAt(String reprsntAt) {
        this.reprsntAt = reprsntAt;
    }

	/**
	 * useStplatId attribute 를 리턴한다.
	 * @return the String
	 */
	public String getUseStplatId() {
		return useStplatId;
	}

	/**
	 * useStplatId attribute 값을 설정한다.
	 * @return useStplatId String
	 */
	public void setUseStplatId(String useStplatId) {
		this.useStplatId = useStplatId;
	}

	/**
	 * useStplatNm attribute 를 리턴한다.
	 * @return the String
	 */
	public String getUseStplatNm() {
		return useStplatNm;
	}

	/**
	 * useStplatNm attribute 값을 설정한다.
	 * @return useStplatNm String
	 */
	public void setUseStplatNm(String useStplatNm) {
		this.useStplatNm = useStplatNm;
	}

	/**
	 * useStplatCn attribute 를 리턴한다.
	 * @return the String
	 */
	public String getUseStplatCn() {
		return useStplatCn;
	}

	/**
	 * useStplatCn attribute 값을 설정한다.
	 * @return useStplatCn String
	 */
	public void setUseStplatCn(String useStplatCn) {
		this.useStplatCn = useStplatCn;
	}

	/**
	 * infoProvdAgreCn attribute 를 리턴한다.
	 * @return the String
	 */
	public String getInfoProvdAgreCn() {
		return infoProvdAgreCn;
	}

	/**
	 * infoProvdAgreCn attribute 값을 설정한다.
	 * @return infoProvdAgreCn String
	 */
	public void setInfoProvdAgreCn(String infoProvdAgreCn) {
		this.infoProvdAgreCn = infoProvdAgreCn;
	}

	/**
	 * stplatType attribute 를 리턴한다.
	 * @return the String
	 */
	public String getStplatType() {
		return stplatType;
	}

	/**
	 * stplatType attribute 값을 설정한다.
	 * @param stplatType String
	 */
	public void setStplatType(String stplatType) {
		this.stplatType = stplatType;
	}

	/**
	 * indvdlInfoYn attribute 를 리턴한다.
	 * @return the String
	 */
	public String getIndvdlInfoYn() {
		return indvdlInfoYn;
	}

	/**
	 * indvdlInfoYn attribute 값을 설정한다.
	 * @param indvdlInfoYn String
	 */
	public void setIndvdlInfoYn(String indvdlInfoYn) {
		this.indvdlInfoYn = indvdlInfoYn;
	}

	/**
	 * frstRegisterPnttm attribute 를 리턴한다.
	 * @return the String
	 */
	public String getFrstRegisterPnttm() {
		return frstRegisterPnttm;
	}

	/**
	 * frstRegisterPnttm attribute 값을 설정한다.
	 * @return frstRegisterPnttm String
	 */
	public void setFrstRegisterPnttm(String frstRegisterPnttm) {
		this.frstRegisterPnttm = frstRegisterPnttm;
	}

	/**
	 * frstRegisterId attribute 를 리턴한다.
	 * @return the String
	 */
	public String getFrstRegisterId() {
		return frstRegisterId;
	}

	/**
	 * frstRegisterId attribute 값을 설정한다.
	 * @return frstRegisterId String
	 */
	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}

	/**
	 * lastUpdusrPnttm attribute 를 리턴한다.
	 * @return the String
	 */
	public String getLastUpdusrPnttm() {
		return lastUpdusrPnttm;
	}

	/**
	 * lastUpdusrPnttm attribute 값을 설정한다.
	 * @return lastUpdusrPnttm String
	 */
	public void setLastUpdusrPnttm(String lastUpdusrPnttm) {
		this.lastUpdusrPnttm = lastUpdusrPnttm;
	}

	/**
	 * lastUpdusrId attribute 를 리턴한다.
	 * @return the String
	 */
	public String getLastUpdusrId() {
		return lastUpdusrId;
	}

	/**
	 * lastUpdusrId attribute 값을 설정한다.
	 * @return lastUpdusrId String
	 */
	public void setLastUpdusrId(String lastUpdusrId) {
		this.lastUpdusrId = lastUpdusrId;
	}

    
   
}
