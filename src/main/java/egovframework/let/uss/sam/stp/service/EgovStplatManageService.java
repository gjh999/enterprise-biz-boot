package egovframework.let.uss.sam.stp.service;

import java.util.List;

/**
 *
 * 약관내용을 처리하는 서비스 클래스
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
public interface EgovStplatManageService {

    /**
	 * 약관정보 글을 조회한다.
	 * @param vo
	 * @return 조회한 글
	 * @exception Exception
	 */
	StplatManageVO selectStplatDetail(StplatManageVO vo) throws Exception;

	/**
	 * 대표(현행) 이용약관 1건을 조회한다. (모달 표출용 — 사용중 USE_AT='Y' + REPRSNT_AT='Y')
	 * @return 대표 이용약관 (없으면 null)
	 */
	StplatManageVO selectRepresentStplat();

	/**
	 * 지정 이용약관을 대표로 설정한다(유형 전체 대표 해제 후 단건 지정).
	 * 미사용(USE_AT='N') 항목은 대표 지정 불가.
	 * @param useStplatId 대표로 지정할 약관 ID
	 */
	void setRepresentStplat(String useStplatId);

	/**
	 * 이용약관 사용여부(USE_AT)를 변경한다. 대표를 미사용 전환하면 대표도 함께 해제된다.
	 * @param useStplatId 대상 ID
	 * @param useAt 'Y'/'N'
	 */
	void updateUseAtStplat(String useStplatId, String useAt);

	/**
	 * 사용중(USE_AT='Y')인 이용약관 건수.
	 */
	int selectActiveStplatCnt();

    /**
	 * 약관정보 글 목록을 조회한다.
	 * @param searchVO
	 * @return 글 목록
	 * @exception Exception
	 */
	List<?> selectStplatList(StplatManageDefaultVO searchVO) throws Exception;

    /**
	 * 약관정보 글 총 갯수를 조회한다.
	 * @param searchVO
	 * @return 글 총 갯수
	 */
    int selectStplatListTotCnt(StplatManageDefaultVO searchVO);

	/**
	 * 약관정보 글을 등록한다.
	 * @param vo
	 * @exception Exception
	 */
    void insertStplatCn(StplatManageVO vo) throws Exception;


	/**
	 * 약관정보 글을 수정한다.
	 * @param vo
	 * @exception Exception
	 */
    void updateStplatCn(StplatManageVO vo) throws Exception;

	/**
	 * 약관정보 글을 삭제한다.
	 * @param vo
	 * @exception Exception
	 */
    void deleteStplatCn(StplatManageVO vo) throws Exception;


}
