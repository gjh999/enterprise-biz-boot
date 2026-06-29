package egovframework.let.uss.umt.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.let.uss.umt.service.MberManageVO;
import egovframework.let.uss.umt.service.UserDefaultVO;

/**
 * 일반회원관리에 관한 데이터 접근 클래스를 정의한다.
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  JJY            최초 생성
 *   2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성
 *   2026.06.17  구재호          Spring Boot + Thymeleaf 전환
 *
 * </pre>
 */
@Repository("mberManageDAO")
public class MberManageDAO extends EgovAbstractMapper{

    /**
     * 기 등록된 특정 일반회원의 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param userSearchVO 검색조건
     * @return List<MberManageVO> 기업회원 목록정보
     */
	public List<MberManageVO> selectMberList(UserDefaultVO userSearchVO){
        return selectList("mberManageDAO.selectMberList", userSearchVO);
    }

    /**
     * 일반회원 총 갯수를 조회한다.
     * @param userSearchVO 검색조건
     * @return int 일반회원총갯수
     */
    public int selectMberListTotCnt(UserDefaultVO userSearchVO) {
        return (Integer)selectOne("mberManageDAO.selectMberListTotCnt", userSearchVO);
    }

    /**
     * 화면에 조회된 일반회원의 정보를 데이터베이스에서 삭제
     * @param delId 삭제 대상 일반회원아이디
     */
    public void deleteMber(String delId){
        delete("mberManageDAO.deleteMber_S", delId);
    }

    /**
     * 회원 삭제(가입삭제) - 회원구분(userTy)에 따라 처리
     * USR01=일반회원 물리삭제, USR02=기업회원 상태 'D', USR03=업무사용자 상태 'D'
     * @param uniqId 삭제대상 회원 고유ID(ESNTL_ID)
     * @param userTy 회원구분(USR01/USR02/USR03)
     */
    public void deleteMberByType(String uniqId, String userTy){
        java.util.Map<String, String> param = new java.util.HashMap<>();
        param.put("uniqId", uniqId);
        if ("USR02".equals(userTy)) {
            update("mberManageDAO.deleteEntrprsMber_S", param);
        } else if ("USR03".equals(userTy)) {
            update("mberManageDAO.deleteEmplyr_S", param);
        } else {
            delete("mberManageDAO.deleteMber_S", uniqId);
        }
    }

    /**
     * 회원 승인(A→P) - 회원구분(userTy)에 따라 해당 테이블의 상태컬럼을 'P'로 갱신
     * @param uniqId 승인대상 회원 고유ID(ESNTL_ID)
     * @param userTy 회원구분(USR01=일반, USR02=기업, USR03=업무사용자)
     */
    public void approveMber(String uniqId, String userTy){
        java.util.Map<String, String> param = new java.util.HashMap<>();
        param.put("uniqId", uniqId);
        if ("USR02".equals(userTy)) {
            update("mberManageDAO.approveEntrprsMber_S", param);
        } else if ("USR03".equals(userTy)) {
            update("mberManageDAO.approveEmplyr_S", param);
        } else {
            update("mberManageDAO.approveGnrlMber_S", param);
        }
    }

    /**
     * 일반회원의 기본정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param mberManageVO 일반회원 등록정보
     * @return String 등록결과
     */
    public int insertMber(MberManageVO mberManageVO){
        return insert("mberManageDAO.insertMber_S", mberManageVO);
    }

    /**
     * 기 등록된 사용자 중 검색조건에 맞는일반회원의 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param mberId 상세조회대상 일반회원아이디
     * @return MberManageVO 일반회원 상세정보
     */
    public MberManageVO selectMber(String mberId){
        return (MberManageVO) selectOne("mberManageDAO.selectMber_S", mberId);
    }

    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param mberManageVO 일반회원수정정보
     */
    public void updateMber(MberManageVO mberManageVO){
        update("mberManageDAO.updateMber_S",mberManageVO);
    }

    /**
     * 일반회원 약관확인
     * @param stplatId 일반회원약관아이디
     * @return List 일반회원약관정보
     */
	public List<?> selectStplat(String stplatId){
    	return selectList("mberManageDAO.selectStplat_S", stplatId);
    }

    /**
     * 일반회원 암호수정
     * @param passVO 기업회원수정정보(비밀번호)
     */
    public void updatePassword(MberManageVO passVO) {
        update("mberManageDAO.updatePassword_S", passVO);
    }

    /**
     * 일반회원이 비밀번호를 기억하지 못할 때 비밀번호를 찾을 수 있도록 함
     * @param mberManageVO 일반회원암호 조회조건정보
     * @return MberManageVO 일반회원 암호정보
     */
    public MberManageVO selectPassword(MberManageVO mberManageVO){
    	return (MberManageVO) selectOne("mberManageDAO.selectPassword_S", mberManageVO);
    }

    /**
     * 입력한 사용자아이디의 중복여부를 체크하여 사용가능여부를 확인
     * @param checkId 중복체크대상 아이디
     * @return int 사용가능여부(아이디 사용회수 )
     */
    public int checkIdDplct(String checkId){
        return (Integer)selectOne("mberManageDAO.checkIdDplct_S", checkId);
    }

}