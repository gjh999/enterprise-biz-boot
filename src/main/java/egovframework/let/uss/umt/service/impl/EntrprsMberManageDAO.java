package egovframework.let.uss.umt.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.let.uss.umt.service.EntrprsMberManageVO;

/**
 * 기업회원 가입/관리 데이터 접근 클래스.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *  2026.06.29  Claude        기업회원 가입 기능 신규(포털 이식)
 * </pre>
 */
@Repository("entrprsMberManageDAO")
public class EntrprsMberManageDAO extends EgovAbstractMapper {

	/** 기업회원 등록 */
	public int insertEntrprsMber(EntrprsMberManageVO vo) {
		return insert("entrprsMberManageDAO.insertEntrprsMber_S", vo);
	}

	/** 기업회원 상세조회 */
	public EntrprsMberManageVO selectEntrprsMber(String uniqId) {
		return (EntrprsMberManageVO) selectOne("entrprsMberManageDAO.selectEntrprsMber_S", uniqId);
	}

	/** 기업회원 수정 */
	public void updateEntrprsMber(EntrprsMberManageVO vo) {
		update("entrprsMberManageDAO.updateEntrprsMber_S", vo);
	}

	/** 기업회원 약관확인 */
	public List<?> selectStplat(String stplatId) {
		return selectList("entrprsMberManageDAO.selectStplat_S", stplatId);
	}

	/** 아이디 중복확인 */
	public int checkIdDplct(String checkId) {
		return (Integer) selectOne("entrprsMberManageDAO.checkIdDplct_S", checkId);
	}
}
