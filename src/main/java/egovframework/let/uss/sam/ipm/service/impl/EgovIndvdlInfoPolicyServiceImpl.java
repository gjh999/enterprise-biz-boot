package egovframework.let.uss.sam.ipm.service.impl;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.let.uss.sam.ipm.service.EgovIndvdlInfoPolicyService;
import egovframework.let.uss.sam.ipm.service.IndvdlInfoPolicy;
import jakarta.annotation.Resource;

/**
 * 개인정보보호정책를 처리하는 ServiceImpl Class 구현
 * @author 공통서비스 장동한
 * @since 2009.07.03
 * @version 1.0
 * @see <pre>
 * &lt;&lt; 개정이력(Modification Information) &gt;&gt;
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.07.03  장동한          최초 생성
 *   2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성
 *  2026.06.17  구재호          Spring Boot + Thymeleaf 전환
 *
 * </pre>
 */
@Service("egovIndvdlInfoPolicyService")
public class EgovIndvdlInfoPolicyServiceImpl extends EgovAbstractServiceImpl
        implements EgovIndvdlInfoPolicyService {

    @Resource(name = "onlineIndvdlInfoPolicyDao")
    private IndvdlInfoPolicyDao dao;

    @Resource(name = "egovIndvdlInfoPolicyIdGnrService")
    private EgovIdGnrService idgenService;

    /**
     * 개인정보보호정책를(을) 목록을 조회 한다.
     * @param OnlinePoll 회정정보가 담김 VO
     * @return List
     * @throws Exception
     */
	@Override
	public List<?> selectIndvdlInfoPolicyList(ComDefaultVO searchVO) throws Exception {
        return dao.selectIndvdlInfoPolicyList(searchVO);
    }

    /**
     * 개인정보보호정책를(을) 목록 전체 건수를(을) 조회한다.
     * @param searchVO  조회할 정보가 담긴 VO
     * @return int
     * @throws Exception
     */
    @Override
	public int selectIndvdlInfoPolicyListCnt(ComDefaultVO searchVO) throws Exception {
        return dao.selectIndvdlInfoPolicyListCnt(searchVO);
    }

    /**
     * 개인정보보호정책를(을) 상세조회 한다.
     * @param searchVO 조회할 정보가 담긴 VO
     * @return List
     * @throws Exception
     */
    @Override
	public IndvdlInfoPolicy selectIndvdlInfoPolicyDetail( IndvdlInfoPolicy indvdlInfoPolicy) throws Exception {
        return dao.selectIndvdlInfoPolicyDetail(indvdlInfoPolicy);
    }

    /**
     * 대표(현행) 개인정보처리방침 1건을 조회한다. (모달 표출용)
     */
    @Override
	public IndvdlInfoPolicy selectRepresentIndvdlInfoPolicy() {
        return dao.selectRepresentIndvdlInfoPolicy();
    }

    /**
     * 지정 개인정보처리방침을 대표로 설정한다(전체 대표 해제 후 단건 지정). 미사용은 대표 불가.
     */
    @Override
    public void setRepresentIndvdlInfoPolicy(String indvdlInfoId) {
        IndvdlInfoPolicy q = new IndvdlInfoPolicy();
        q.setIndvdlInfoId(indvdlInfoId);
        IndvdlInfoPolicy cur;
        try {
            cur = dao.selectIndvdlInfoPolicyDetail(q);
        } catch (Exception e) {
            return;
        }
        if (cur == null || "N".equals(cur.getUseAt())) {
            return;
        }
        dao.clearRepresentIndvdlInfoPolicy();
        dao.setRepresentIndvdlInfoPolicy(indvdlInfoId);
    }

    /**
     * 사용여부(USE_AT)를 변경한다. 대표를 미사용 전환하면 SQL 에서 대표도 함께 해제된다.
     */
    @Override
    public void updateUseAtIndvdlInfoPolicy(String indvdlInfoId, String useAt) {
        java.util.Map<String, String> param = new java.util.HashMap<>();
        param.put("indvdlInfoId", indvdlInfoId);
        param.put("useAt", "N".equals(useAt) ? "N" : "Y");
        dao.updateUseAtIndvdlInfoPolicy(param);
    }

    @Override
    public int selectActiveIndvdlInfoPolicyCnt() {
        return dao.selectActiveIndvdlInfoPolicyCnt();
    }

    /**
     * 개인정보보호정책를(을) 등록한다.
     * @param indvdlInfoPolicy 개인정보보호정책 정보가 담긴 VO
     * @throws Exception
     */
    @Override
	public void insertIndvdlInfoPolicy(IndvdlInfoPolicy indvdlInfoPolicy)throws Exception {
        String sMakeId = idgenService.getNextStringId();
        indvdlInfoPolicy.setIndvdlInfoId(sMakeId);
        dao.insertIndvdlInfoPolicy(indvdlInfoPolicy);
    }

    /**
     * 개인정보보호정책를(을) 수정한다.
     * @param indvdlInfoPolicy 개인정보보호정책 정보가 담긴 VO
     * @throws Exception
     */
    @Override
	public void updateIndvdlInfoPolicy(IndvdlInfoPolicy indvdlInfoPolicy) throws Exception {
        dao.updateIndvdlInfoPolicy(indvdlInfoPolicy);
    }

    /**
     * 개인정보보호정책를(을) 삭제한다.
     * @param indvdlInfoPolicy 개인정보보호정책 정보가 담긴 VO
     * @throws Exception
     */
    @Override
	public void deleteIndvdlInfoPolicy(IndvdlInfoPolicy indvdlInfoPolicy) throws Exception {
        dao.deleteIndvdlInfoPolicy(indvdlInfoPolicy);
    }

}
