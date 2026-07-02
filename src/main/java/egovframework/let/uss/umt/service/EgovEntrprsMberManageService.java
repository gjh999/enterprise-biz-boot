package egovframework.let.uss.umt.service;

/**
 * 기업회원 가입/관리 비지니스 인터페이스.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *  2026.06.29  Claude        기업회원 가입 기능 신규(포털 이식)
 *   2026.07.02  구재호        Spring Boot + Thymeleaf + KRDS + 다국어(i18n) 전환
 * </pre>
 */
public interface EgovEntrprsMberManageService {

	/**
	 * 기업회원 가입정보를 데이터베이스에 저장한다.
	 * @param vo 기업회원 등록정보
	 * @return 등록결과
	 * @throws Exception
	 */
	int insertEntrprsMber(EntrprsMberManageVO vo) throws Exception;

	/**
	 * 기업회원 상세정보를 조회한다.
	 * @param uniqId 기업회원 고유아이디(ESNTL_ID)
	 * @return 기업회원 상세정보
	 */
	EntrprsMberManageVO selectEntrprsMber(String uniqId);

	/**
	 * 기업회원 정보를 수정한다.
	 * @param vo 기업회원 수정정보
	 * @throws Exception
	 */
	void updateEntrprsMber(EntrprsMberManageVO vo) throws Exception;

	/**
	 * 기업회원 약관확인
	 * @param stplatId 기업회원약관아이디
	 * @return 약관정보 목록
	 */
	java.util.List<?> selectStplat(String stplatId);

	/**
	 * 입력한 사용자아이디의 중복여부를 체크한다.
	 * @param checkId 중복여부 확인대상 아이디
	 * @return 사용회수
	 */
	int checkIdDplct(String checkId);
}
