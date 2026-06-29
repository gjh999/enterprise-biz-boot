package egovframework.let.uss.sam.stp.service.impl;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import egovframework.let.uss.sam.stp.service.EgovStplatManageService;
import egovframework.let.uss.sam.stp.service.StplatManageDefaultVO;
import egovframework.let.uss.sam.stp.service.StplatManageVO;
import jakarta.annotation.Resource;



/**
 *
 * 약관내용을 처리하는 서비스 구현 클래스
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
@Service("StplatManageService")
public class EgovStplatManageServiceImpl extends EgovAbstractServiceImpl implements
        EgovStplatManageService {

    @Resource(name="StplatManageDAO")
    private StplatManageDAO stplatManageDAO;

    /** ID Generation */
	@Resource(name="egovStplatManageIdGnrService")
	private EgovIdGnrService idgenService;


    /**
	 * 글을 조회한다.
	 * @param vo
	 * @return 조회한 글
	 * @exception Exception
	 */
    @Override
	public StplatManageVO selectStplatDetail(StplatManageVO vo) throws Exception {
        StplatManageVO resultVO = stplatManageDAO.selectStplatDetail(vo);
        if (resultVO == null)
            throw processException("info.nodata.msg");
        return resultVO;
    }

    /**
	 * 대표(현행) 이용약관 1건을 조회한다. (모달 표출용)
	 */
    @Override
	public StplatManageVO selectRepresentStplat() {
        return stplatManageDAO.selectRepresentStplat();
    }

    /**
     * 지정 이용약관을 대표로 설정한다(전체 대표 해제 후 단건 지정).
     * 미사용(USE_AT='N') 항목은 대표 지정 불가 — 무시한다.
     */
    @Override
    public void setRepresentStplat(String useStplatId) {
        StplatManageVO vo = new StplatManageVO();
        vo.setUseStplatId(useStplatId);
        StplatManageVO cur;
        try {
            cur = stplatManageDAO.selectStplatDetail(vo);
        } catch (Exception e) {
            return;
        }
        // 미사용 항목은 대표 지정 금지
        if (cur == null || "N".equals(cur.getUseAt())) {
            return;
        }
        stplatManageDAO.clearRepresentStplat();
        stplatManageDAO.setRepresentStplat(useStplatId);
    }

    /**
     * 사용여부(USE_AT)를 변경한다. 대표를 미사용 전환하면 대표도 함께 해제(데이터 정합).
     */
    @Override
    public void updateUseAtStplat(String useStplatId, String useAt) {
        java.util.Map<String, String> param = new java.util.HashMap<>();
        param.put("useStplatId", useStplatId);
        param.put("useAt", "N".equals(useAt) ? "N" : "Y");
        // SQL 에서 USE_AT='N' 이면 REPRSNT_AT='N' 으로 함께 내려 대표 정합을 보장한다.
        stplatManageDAO.updateUseAtStplat(param);
    }

    @Override
    public int selectActiveStplatCnt() {
        return stplatManageDAO.selectActiveStplatCnt();
    }

    /**
	 * 약관정보 글 목록을 조회한다.
	 * @param searchVO
	 * @return 글 목록
	 * @exception Exception
	 */
	@Override
	public List<?> selectStplatList(StplatManageDefaultVO searchVO) throws Exception {
        return stplatManageDAO.selectStplatList(searchVO);
    }

    /**
	 * 약관정보 글 총 갯수를 조회한다.
	 * @param searchVO
	 * @return 글 총 갯수
	 */
    @Override
	public int selectStplatListTotCnt(StplatManageDefaultVO searchVO) {
		return stplatManageDAO.selectStplatListTotCnt(searchVO);
	}

	/**
	 * 약관정보 글을 등록한다.
	 * @param vo
	 * @exception Exception
	 */
    @Override
	public void insertStplatCn(StplatManageVO vo) throws Exception {

		String	useStplatId = idgenService.getNextStringId();

		vo.setUseStplatId(useStplatId);

    	stplatManageDAO.insertStplatCn(vo);
    }

	/**
	 * 약관정보 글을 수정한다.
	 * @param vo
	 * @exception Exception
	 */
    @Override
	public void updateStplatCn(StplatManageVO vo) throws Exception {

    	stplatManageDAO.updateStplatCn(vo);
    }

	/**
	 * 약관정보 글을 삭제한다.
	 * @param vo
	 * @exception Exception
	 */
    @Override
	public void deleteStplatCn(StplatManageVO vo) throws Exception {

    	stplatManageDAO.deleteStplatCn(vo);
    }

}
