package egovframework.let.uss.sam.ipm.service;

import java.util.List;

import egovframework.com.cmm.ComDefaultVO;
/**
 * 개인정보보호정책를 처리하는 Service Class 구현
 * @author 공통서비스 장동한
 * @since 2009.07.03
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.07.03  장동한          최초 생성
 *   2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성
 *  2026.06.17  구재호          Spring Boot + Thymeleaf 전환
 *
 * </pre>
 */
public interface EgovIndvdlInfoPolicyService {


    /**
	 * 개인정보보호정책 목록을 조회한다.
	 * @param searchVO  조회할 정보가 담긴 VO
	 * @return List
	 * @throws Exception
	 */
	public List<?> selectIndvdlInfoPolicyList(ComDefaultVO searchVO) throws Exception;

    /**
     * 개인정보보호정책를(을) 목록 전체 건수를(을) 조회한다.
     * @param searchVO  조회할 정보가 담긴 VO
     * @return int
     * @throws Exception
     */
    public int selectIndvdlInfoPolicyListCnt(ComDefaultVO searchVO) throws Exception;

    /**
	 * 개인정보보호정책를(을) 상세조회 한다.
	 * @param indvdlInfoPolicy  개인정보보호정책 정보 담김 VO
	 * @return List
	 * @throws Exception
	 */
	public IndvdlInfoPolicy selectIndvdlInfoPolicyDetail(IndvdlInfoPolicy indvdlInfoPolicy) throws Exception;

	/**
	 * 대표(현행) 개인정보처리방침 1건을 조회한다. (모달 표출용 — 사용중 USE_AT='Y' + REPRSNT_AT='Y')
	 * @return 대표 개인정보처리방침 (없으면 null)
	 */
	public IndvdlInfoPolicy selectRepresentIndvdlInfoPolicy();

	/**
	 * 지정 개인정보처리방침을 대표로 설정한다(전체 대표 해제 후 단건 지정). 미사용은 대표 불가.
	 * @param indvdlInfoId 대표로 지정할 ID
	 */
	void setRepresentIndvdlInfoPolicy(String indvdlInfoId);

	/**
	 * 개인정보처리방침 사용여부(USE_AT)를 변경한다. 대표를 미사용 전환하면 대표도 함께 해제된다.
	 * @param indvdlInfoId 대상 ID
	 * @param useAt 'Y'/'N'
	 */
	void updateUseAtIndvdlInfoPolicy(String indvdlInfoId, String useAt);

	/**
	 * 사용중(USE_AT='Y')인 개인정보처리방침 건수.
	 */
	int selectActiveIndvdlInfoPolicyCnt();

        /**
	 * 개인정보보호정책를(을) 등록한다.
	 * @param indvdlInfoPolicy  개인정보보호정책 정보 담김 VO
	 * @throws Exception
	 */
	void  insertIndvdlInfoPolicy(IndvdlInfoPolicy indvdlInfoPolicy) throws Exception;

        /**
	 * 개인정보보호정책를(을) 수정한다.
	 * @param indvdlInfoPolicy  개인정보보호정책 정보 담김 VO
	 * @throws Exception
	 */
	void  updateIndvdlInfoPolicy(IndvdlInfoPolicy indvdlInfoPolicy) throws Exception;

	/**
	 * 개인정보보호정책를(을) 삭제한다.
	 * @param indvdlInfoPolicy  개인정보보호정책 정보 담김 VO
	 * @throws Exception
	 */
	void  deleteIndvdlInfoPolicy(IndvdlInfoPolicy indvdlInfoPolicy) throws Exception;

}
