---
name: egov-component
description: >-
  eGovFramework(전자정부표준프레임워크) 신규 업무 컴포넌트를 이 프로젝트(simple-home-boot) 관례로
  스캐폴딩하고, MyBatis 매퍼 SQL(7종 DBMS) 작성·설정 정합성 점검·빌드/실행까지 안내한다.
  Controller(MVC+REST)·Service/ServiceImpl·DAO(EgovAbstractMapper)·VO/DTO·SQL XML 일괄 생성,
  ID 채번·트랜잭션 AOP·Java Config(EgovConfigApp*)·**다국어(i18n)** 점검 포함. "새 기능/CRUD/매퍼/화면/eGov 컴포넌트/빌드" 요청 시 사용.
---

# egov-component — eGovFramework 컴포넌트 스캐폴딩 & 개발

이 프로젝트의 레이어 구조와 명명 규칙(`CLAUDE.md` 참조)에 맞춰 신규 컴포넌트를 만든다.
**핵심: 이 프로젝트는 DAO 클래스(`EgovAbstractMapper` 상속) + 단순명 namespace 패턴이다(인터페이스 Mapper 아님).**

## 0. 레이어 한눈에
```
Controller(@Controller, Thymeleaf/KRDS)  ─┐
Controller(@RestController, ApiController) ─┴→ Service(interface) → ServiceImpl(EgovAbstractServiceImpl)
                                                                       → DAO(@Repository, EgovAbstractMapper)
                                                                          → SQL XML(7종 DBMS)
```

## 1. 파일 위치·명명 (모듈 `let.<group>.<module>` 기준)

| 파일 | 위치 |
|---|---|
| MVC 컨트롤러 | `src/main/java/egovframework/let/<g>/<m>/controller/Egov<Name>Controller.java` |
| REST 컨트롤러 | `…/controller/Egov<Name>ApiController.java` |
| 서비스 IF | `…/<m>/service/Egov<Name>Service.java` |
| 서비스 Impl | `…/<m>/service/impl/Egov<Name>ServiceImpl.java` |
| DAO(매퍼) | `…/<m>/domain/repository/<Name>DAO.java` |
| VO/도메인 | `…/<m>/domain/model/<Name>VO.java` |
| 요청/응답 DTO | `…/<m>/dto/request/<Name>…RequestDTO.java`, `…/dto/response/…ResponseDTO.java` |
| SQL XML | `src/main/resources/egovframework/mapper/let/<g>/<m>/Egov<Name>_SQL_<db>.xml` (7종) |

- `<g>`=서비스그룹(cop/uss/uat/utl 등), `<m>`=모듈, `<Name>`=기능 파스칼표기.
- 컨트롤러 클래스는 `Egov` 접두, REST는 `…ApiController`.

## 2. 코드 패턴 (이 프로젝트 표준)

### Service 인터페이스
```java
public interface EgovSampleService {
    List<SampleVO> selectSampleList(SampleVO searchVO) throws Exception;
    int selectSampleListTotCnt(SampleVO searchVO) throws Exception;
    SampleVO selectSample(SampleVO vo) throws Exception;
    void insertSample(SampleVO vo) throws Exception;
    void updateSample(SampleVO vo) throws Exception;
    void deleteSample(SampleVO vo) throws Exception;
}
```

### ServiceImpl — `EgovAbstractServiceImpl` 상속, 빈 이름 명시
```java
@Service("EgovSampleService")
public class EgovSampleServiceImpl extends EgovAbstractServiceImpl implements EgovSampleService {
    @Resource(name = "SampleDAO")
    private SampleDAO sampleDAO;

    @Override
    public List<SampleVO> selectSampleList(SampleVO searchVO) throws Exception {
        return sampleDAO.selectSampleList(searchVO);
    }
    // insert 시 ID 채번이 필요하면 EgovIdGnrService 주입(§4)
}
```

### DAO — **클래스**(`@Repository`) + `EgovAbstractMapper` 상속
```java
@Repository("SampleDAO")
public class SampleDAO extends EgovAbstractMapper {
    public List<SampleVO> selectSampleList(SampleVO vo) { return selectList("SampleDAO.selectSampleList", vo); }
    public int selectSampleListTotCnt(SampleVO vo)      { return (Integer) selectOne("SampleDAO.selectSampleListTotCnt", vo); }
    public SampleVO selectSample(SampleVO vo)           { return selectOne("SampleDAO.selectSample", vo); }
    public void insertSample(SampleVO vo)               { insert("SampleDAO.insertSample", vo); }
    public void updateSample(SampleVO vo)               { update("SampleDAO.updateSample", vo); }
    public void deleteSample(SampleVO vo)               { delete("SampleDAO.deleteSample", vo); }
}
```
- 쿼리 ID = **`<namespace>.<id>`** 이고 namespace는 **DAO 단순명**(`SampleDAO`)이다.

### SQL XML — namespace는 DAO 단순명
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="SampleDAO">
  <select id="selectSampleList" parameterType="sampleVO" resultType="sampleVO">
    SELECT * FROM TB_SAMPLE WHERE USE_AT = 'Y'
    <if test="searchKeyword != null and searchKeyword != ''">
      AND SAMPLE_NM LIKE '%' || #{searchKeyword} || '%'   <!-- DBMS별 §3 -->
    </if>
    ORDER BY FRST_REGIST_PNTTM DESC
    <!-- 페이징: DBMS별 §3 -->
  </select>
</mapper>
```
- resultMap 자동 camelCase↔SNAKE 매핑(`mapper/config/mapper-config.xml`). `resultType`은 VO 단순명(소문자 시작 별칭) 사용.
- **7종 DBMS 모두**(`hsql/postgresql/mysql/oracle/altibase/tibero/cubrid`) 동일 namespace·id로 파일을 만든다(개발 기본 `hsql`).

### MVC 컨트롤러(Thymeleaf/KRDS) — 화면 전환은 `krds-conversion` 스킬 패턴
```java
@Controller
@RequiredArgsConstructor
public class EgovSampleController {
    private final EgovSampleService sampleService;

    @GetMapping("/sample/list")
    public String list(@ModelAttribute SampleVO searchVO, Model model) throws Exception {
        model.addAttribute("resultList", sampleService.selectSampleList(searchVO));
        return "let/<g>/<m>/sampleList";   // templates/let/<g>/<m>/sampleList.html (KRDS)
    }
}
```
- 페이징은 `ComDefaultVO`(searchKeyword/searchCondition/pageIndex…) 상속 + `PaginationInfo` 사용, 공유 프래그먼트 `fragments/pagination`.

## 3. DBMS별 SQL 차이 (7종 분리 작성)
| 항목 | hsql/postgresql | mysql | oracle/tibero |
|---|---|---|---|
| 문자열연결 | `'%' \|\| #{kw} \|\| '%'` | `CONCAT('%',#{kw},'%')` | `'%' \|\| #{kw} \|\| '%'` |
| 페이징 | `LIMIT #{recordCountPerPage} OFFSET #{firstIndex}` | 동일 | `ROWNUM`/`OFFSET … ROWS FETCH NEXT …` |
| 현재시각 | `NOW()`(hsql/pg/mysql) | `NOW()` | `SYSDATE` |
| NULL치환 | `COALESCE(c,'')` | `IFNULL`/`COALESCE` | `NVL(c,'')` |
- 감사컬럼 4종(`FRST_REGIST_PNTTM/FRST_REGISTER_ID/LAST_UPDT_PNTTM/LAST_UPDUSR_ID`) 전 INSERT/UPDATE에 포함.
- 삭제는 **논리삭제**(`UPDATE … SET USE_AT='N'`) 우선.

## 4. ID 채번 (문자열 PK가 필요할 때)
- `EgovConfigAppIdGen.java`에 `EgovIdGnrServiceImpl`(`EgovIdGnrBuilder`) Bean을 추가(접두어·자릿수·`strategy`·`tableName`).
- 채번 테이블(`IDS`/`COMTECOPSEQ`)에 행이 있어야 한다(`DATABASE/`·`db/shtdb.sql`).
- ServiceImpl에서 `@Resource(name="egovSampleIdGnrService") EgovIdGnrService` 주입 후 `getNextStringId()`.

## 5. 정합성 점검 (생성 후 필수)
- [ ] ServiceImpl 패키지가 트랜잭션 AOP 포인트컷 대상인가 (`EgovConfigAppTransaction` — `egovframework.let..impl.*Impl` 등).
- [ ] SQL XML `namespace`(DAO 단순명)와 `<select/insert/…>` `id`가 DAO 호출 `"<ns>.<id>"`와 정확히 일치.
- [ ] `EgovConfigAppMapper`가 `egovframework/mapper/let/**` 를 로딩 + 현재 `Globals.DbType`에 해당하는 `*_SQL_<db>.xml` 존재.
- [ ] `resultType`/`parameterType` 별칭이 실제 VO와 일치(또는 풀패키지명).
- [ ] 신규 보호 URL이면 `SecurityConfig` 권한 매트릭스에 반영.
- [ ] DB 스키마 변경 시 `Docs/db-schema-guide.md`·`Docs/db-컬럼-한글명-매핑.md` 갱신, DDL은 7종 모두.

## 6. 빌드·실행 (eGovCI 번들 — PATH에 mvn/jdk 없음)
```bash
export JAVA_HOME="/c/eGovCI-5.0.0-Windows-64bit/bin/jdk-17.0.17+10"
MVN="/c/eGovCI-5.0.0-Windows-64bit/bin/apache-maven-3.9.9/bin/mvn.cmd"
# 컴파일만(테스트 소스 QueryDSL 이슈 회피)
"$MVN" -f pom.xml -Dmaven.test.skip=true clean compile
# 실행(백그라운드, UTF-8) — 템플릿/정적리소스는 devtools 자동 반영, Java 변경은 재기동 필요
"$MVN" -f pom.xml -Dmaven.test.skip=true -Dspring-boot.run.jvmArguments="-Dfile.encoding=UTF-8" spring-boot:run
```
- 포트 8080. 검증은 `.claude/skills/krds-conversion/scripts/krds-verify.sh` 또는 `curl` HTTP 200.

## 7. (참고) XML Bean → Java Config 변환
이 프로젝트는 이미 전면 Java Config(`EgovConfigApp*`)다. 레거시 `context-*.xml`을 옮길 때는
`Docs/context-*-convert.md`·`Docs/java-config-convert.md` 참조, 신규 기능 Config는
`EgovConfigApp<기능>.java`를 만들고 `EgovConfigApp.java`의 `@Import`에 등록.

## 8. 다국어(i18n) — 신규 기능·화면 추가/변경 시 **필수 확인**
- **사용자 노출 텍스트는 하드코딩 금지.** Thymeleaf `th:text="#{message.key}"`(또는 `#{key}`)로 출력. `placeholder`/`title`/`alt`/버튼 라벨/검증 메시지 포함.
- 메시지 정의: `src/main/resources/egovframework/message/message-ui_{ko,en}.properties` 에 **ko·en 둘 다, 동일 키**로 추가.
  프레임워크 공통 메시지는 `message/com/message-common_{ko,en}` 사용. 설정은 `EgovConfigAppCommon`(MessageSource)·`EgovMessageSource`.
- 언어 전환: `/cmm/lang(lang='ko'|'en')`(쿠키 기반). 헤더/레이아웃에 KO/EN 스위치 존재.
- **신규/변경 작업 시 반드시 점검(체크리스트)**:
  - [ ] 추가·변경된 **모든 노출 문구**가 메시지 키로 처리됐는가(하드코딩 한글/영문 잔존 0)
  - [ ] `message-ui_ko`·`message-ui_en` 에 **양쪽 값 존재**(키 집합 일치), en 미번역으로 빈 값/한글 fallback 없음
  - [ ] 동적 문구는 파라미터 `#{key(${arg})}` 사용, 숫자/날짜 포맷 로캘 대응
- **검증 스크립트 예**:
  ```bash
  # ko/en 키 집합 차이(있으면 누락)
  comm -3 <(grep -oE '^[a-zA-Z0-9._-]+=' message-ui_ko.properties|sort) <(grep -oE '^[a-zA-Z0-9._-]+=' message-ui_en.properties|sort)
  # 변경 템플릿의 하드코딩 한글(>=2자) 스캔(th:text/속성 밖 텍스트)
  grep -nP '>[^<>{]*[가-힣]{2,}' 변경한.html | grep -v '#{'
  ```
- 기존 화면이 하드코딩 상태(예: 레거시 포팅분)라도, **그 화면을 건드리면(전환·수정) 함께 i18n 키로 전환**한다.

> 화면(Thymeleaf)을 KRDS로 만들 땐 **`krds-conversion` 스킬**과 함께 사용하고, 위 §8 i18n 점검을 **반드시** 동반한다.
> 파괴적 DB 변경(DROP/TRUNCATE) 전엔 반드시 복구 가능한 SQL 덤프로 먼저 백업한다(전역 안전 규칙).
