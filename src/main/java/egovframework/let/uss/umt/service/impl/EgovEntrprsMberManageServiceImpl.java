package egovframework.let.uss.umt.service.impl;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import egovframework.let.uss.umt.service.EgovEntrprsMberManageService;
import egovframework.let.uss.umt.service.EntrprsMberManageVO;
import egovframework.let.utl.sim.service.EgovFileScrty;
import jakarta.annotation.Resource;

/**
 * 기업회원 가입/관리 비지니스 구현 클래스.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *  2026.06.29  Claude        기업회원 가입 기능 신규(포털 이식)
 *   2026.07.02  구재호        Spring Boot + Thymeleaf + KRDS + 다국어(i18n) 전환
 * </pre>
 */
@Service("entrprsMberManageService")
public class EgovEntrprsMberManageServiceImpl extends EgovAbstractServiceImpl implements EgovEntrprsMberManageService {

	@Resource(name = "entrprsMberManageDAO")
	private EntrprsMberManageDAO entrprsMberManageDAO;

	/** 회원 ESNTL_ID 채번 — 일반회원과 동일 채번기(USRCNFRM_) 사용 */
	@Resource(name = "egovUsrCnfrmIdGnrService")
	private EgovIdGnrService idgenService;

	@Override
	public int insertEntrprsMber(EntrprsMberManageVO vo) throws Exception {
		// 고유아이디 채번
		String uniqId = idgenService.getNextStringId();
		vo.setUniqId(uniqId);
		// 비밀번호 암호화 (일반회원 가입과 동일 규약: id 를 salt 로 SHA-256)
		String pass = EgovFileScrty.encryptPassword(vo.getEntrprsMberPassword(), vo.getEntrprsMberId());
		vo.setEntrprsMberPassword(pass);

		return entrprsMberManageDAO.insertEntrprsMber(vo);
	}

	@Override
	public EntrprsMberManageVO selectEntrprsMber(String uniqId) {
		return entrprsMberManageDAO.selectEntrprsMber(uniqId);
	}

	@Override
	public void updateEntrprsMber(EntrprsMberManageVO vo) throws Exception {
		entrprsMberManageDAO.updateEntrprsMber(vo);
	}

	@Override
	public List<?> selectStplat(String stplatId) {
		return entrprsMberManageDAO.selectStplat(stplatId);
	}

	@Override
	public int checkIdDplct(String checkId) {
		return entrprsMberManageDAO.checkIdDplct(checkId);
	}
}
