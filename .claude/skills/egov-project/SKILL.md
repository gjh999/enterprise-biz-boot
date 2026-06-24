---
name: egov-project
description: >-
  simple-home-boot(eGovFramework 5.0 + Spring Boot) 프로젝트의 표준 절차·규약으로 작업할 때 사용한다.
  표준 참조 순서, DB 스키마·테이블 명명(TB_)·감사컬럼·DBMS 7종 문법, 매퍼 XML, Java 패키지·클래스 명명,
  Thymeleaf/KRDS 화면, 빌드·실행(eGovCI 번들), 비밀번호 이중해시, 접근권한 매트릭스를 포함한다.
  신규 기능/테이블/매퍼/화면/빌드 작업을 시작하기 전에 참조한다.
---

# egov-project — simple-home-boot 프로젝트 표준 절차

> 본 프로젝트에서 반복 작업을 재현 가능하게 수행하기 위한 **컨텍스트·하네스 가이드**.
> 컴포넌트 스캐폴딩은 `egov-component`, 화면 KRDS 전환은 `krds-conversion` 스킬과 함께 사용한다.

## 1. 표준 참조 순서 (신규 작업 시)
```
① CLAUDE.md         → 전체 구조·아키텍처 파악
② Docs/단어규칙.md    → 영문약어 결정 (3284개 공통표준단어)
③ Docs/도메인규칙.md  → 타입·길이 결정 (129개 공통표준도메인)
④ Docs/용어규칙.md   → 컬럼명·테이블명 확인
⑤ 이 스킬(egov-project) → 구현 절차·검증 체크리스트
```
규칙 파일에 없는 단어/용어/도메인은 해당 파일 하단에 **직접 추가** 후 사용한다.

## 2. DB 스키마 표준
### 2-1. 테이블 명명 — `TB_ + 기능명`(대문자 스네이크). 예: `TB_BBS`, `TB_BBS_MASTER`, `TB_EMPLYR_INFO`.
### 2-2. 감사 컬럼 (모든 테이블 필수)
```sql
FRST_REGIST_PNTTM  TIMESTAMP    -- 등록일시
FRST_REGISTER_ID   VARCHAR(20)  -- 등록자ID
LAST_UPDT_PNTTM    TIMESTAMP    -- 수정일시
LAST_UPDUSR_ID     VARCHAR(20)  -- 수정자ID
```
### 2-3. DBMS별 문법 차이
| 구분 | HSQL | PostgreSQL | MySQL | Oracle/Tibero |
|------|------|-----------|-------|--------|
| 자동증가 | IDS 테이블 | SEQUENCE | AUTO_INCREMENT | SEQUENCE |
| 현재시각 | NOW() | NOW() | NOW() | SYSDATE |
| 문자열연결 | `\|\|` | `\|\|` | CONCAT() | `\|\|` |
| CLOB | LONGVARCHAR | TEXT | MEDIUMTEXT | CLOB |
| LIMIT | LIMIT n OFFSET m | LIMIT n OFFSET m | LIMIT n OFFSET m | ROWNUM / FETCH |
| IFNULL | IFNULL() | COALESCE() | IFNULL() | NVL() |
### 2-4. 신규 테이블 체크리스트
- [ ] TB_ 스네이크 명명 · PK · 감사컬럼 4 · FK 명명(`FK_대상_참조`)
- [ ] `src/main/resources/db/shtdb.sql`(HSQL) 업데이트
- [ ] `DATABASE/all_sht_ddl_*.sql` **7종 전 DBMS** 업데이트(컬럼 인라인 한글주석 포함)
- [ ] `Docs/db-schema-guide.md`·`Docs/db-컬럼-한글명-매핑.md` 갱신
### 2-5. 테이블명 매핑(구 LET*/COMVN* → 신 TB_/VW_)
상세표는 `Docs/db-name-mapping.md` 참조. 일괄 치환 스크립트는 §7.

## 3. 매퍼 XML 표준
### 3-1. 위치 `src/main/resources/egovframework/mapper/let/<group>/<module>/Egov<기능>_SQL_<db>.xml`
### 3-2. DB별 파일 목록 (**7종**)
`hsql`, `postgresql`, `mysql`, `oracle`, `tibero`, `cubrid`, `altibase` — 동일 namespace/id로 모두 작성(개발 기본 `hsql`).
### 3-3. namespace = **DAO 단순명**(예: `namespace="BBSManageDAO"`), 쿼리 호출 = `"<DAO>.<id>"`.
### 3-4. HSQL 주의
```xml
SELECT IFNULL(MAX(NTT_ID), 0) + 1     <!-- NVL 금지 -->
LIMIT #{recordCountPerPage} OFFSET #{firstIndex}
NOW()
```

## 4. Java 코딩 표준
### 4-1. 패키지 구조
```
egovframework/
  com/cmm·config(EgovConfigApp*)·jwt·security
  let/<group>/<module>/
    controller/        → @Controller(MVC) / @RestController(API)
    domain/model/      → *VO, 도메인
    domain/repository/ → DAO (@Repository, EgovAbstractMapper 상속)
    dto/request·response/ → *RequestDTO, *ResponseDTO
    service/ · service/impl/ → 인터페이스 / *Impl(EgovAbstractServiceImpl)
```
### 4-2. 클래스 명명 (이 프로젝트 실제 패턴)
```java
@Controller                       public class Egov기능Controller { }      // Thymeleaf/KRDS
@RestController                    public class Egov기능ApiController { }    // JSON
public interface Egov기능Service { }
@Service("Egov기능Service")        public class Egov기능ServiceImpl extends EgovAbstractServiceImpl implements Egov기능Service { }
@Repository("기능DAO")             public class 기능DAO extends EgovAbstractMapper { }   // ※ 인터페이스 @Mapper 아님
```
### 4-3. 필수: `@RequiredArgsConstructor`(생성자 주입, 필드주입 금지), `@Slf4j`.
> 스캐폴딩 상세·예제는 `egov-component` 스킬 참조.

## 5. Thymeleaf / KRDS 화면 표준
### 5-1. 템플릿 `src/main/resources/templates/` — `layouts/`(default·login·public), `fragments/`(header·nav·footer·pagination), `let/<group>/<module>/<화면>.html`.
### 5-2. KRDS 적용 원칙 (※ **Bootstrap 프레임워크 미사용**)
- 로컬 자원만(CDN 금지). 로드 순서: `bootstrap-icons` → `krds.min.css`(공식) → `common.css` → `krds-compat.css` → `krds.css`.
- **KRDS 네이티브 클래스** 사용: 버튼 `krds-btn`(primary/secondary/tertiary), 표 `table.tbl`(+`krds-table-wrap`),
  폼 `form-group`+`krds-input`/`krds-form-select`, 배지 `krds-badge`, 패널 `krds-panel`, 페이지네이션 `krds-pagination`.
- 아이콘은 Bootstrap **Icons**(`bi-*`) 폰트만 유지(프레임워크 아님). 서체 Pretendard GOV.
- 공개 페이지(랜딩/`/krds-sample`)는 `layouts/public`, 업무 페이지는 `layouts/default`. 상세는 `krds-conversion` 스킬.
### 5-3. URL 패턴
| 기능 | MVC | REST |
|------|-----|------|
| 로그인 | GET/POST `/login` | POST `/auth/login-jwt` |
| 게시물 목록/상세 | `/bbs/{bbsId}/list` · `/bbs/{bbsId}/{nttId}/detail` | `/board` · `/board/{bbsId}/{nttId}` |
| 일정/회원 | `/schedule` · `/member/list` | `/schedule/daily` · `/etc/member/list` |

## 6. 빌드·실행 (eGovCI 번들 — PATH에 mvn/jdk 없음)
### 6-1. 경로
```
JAVA_HOME = C:\eGovCI-5.0.0-Windows-64bit\bin\jdk-17.0.17+10
Maven     = C:\eGovCI-5.0.0-Windows-64bit\bin\apache-maven-3.9.9\bin\mvn.cmd
```
### 6-2. bash 실행
```bash
export JAVA_HOME="/c/eGovCI-5.0.0-Windows-64bit/bin/jdk-17.0.17+10"; export PATH="$JAVA_HOME/bin:$PATH"
MVN="/c/eGovCI-5.0.0-Windows-64bit/bin/apache-maven-3.9.9/bin/mvn.cmd"
"$MVN" -f pom.xml -Dmaven.test.skip=true clean compile        # 컴파일(테스트 QueryDSL 이슈 회피)
"$MVN" -f pom.xml -Dmaven.test.skip=true -Dspring-boot.run.jvmArguments="-Dfile.encoding=UTF-8" spring-boot:run
```
### 6-3. 포트 8080 충돌 종료(PowerShell)
```powershell
Get-NetTCPConnection -LocalPort 8080 -EA SilentlyContinue | %{ Stop-Process -Id $_.OwningProcess -Force -EA SilentlyContinue }
```
### 6-4. 빌드 전 체크: 매퍼 TB_ 규칙 · shtdb.sql 문법 · `Globals.DbType=hsql` · 감사컬럼 INSERT/UPDATE 포함.
### 6-5. DB: **내장 HSQLDB**(별도 서버 불필요), 시드 `src/main/resources/db/shtdb.sql`. 테스트 계정 **`admin`/`1`**, **`user`/`user`**.
### 6-6. 비밀번호 = 이중 SHA-256(Base64)
```
1차 = Base64(SHA256(id + password)) ; 2차(DB저장) = Base64(SHA256(id + 1차))
생성 API: EgovFileScrty.encryptPasswordTwice(plaintext, id)
```
```powershell
$sha=[Security.Cryptography.SHA256]::Create()
function Get-EgovPwd($pw,$id){ $h1=[Convert]::ToBase64String($sha.ComputeHash([Text.Encoding]::UTF8.GetBytes($id+$pw))); [Convert]::ToBase64String($sha.ComputeHash([Text.Encoding]::UTF8.GetBytes($id+$h1))) }
Get-EgovPwd "1" "admin"      # admin 계정(비번 1)
Get-EgovPwd "user" "user"    # → rTozi2u1iWZ8AikX9o06EtgDVxXtwNV7Eb600ai5Amk=
```

## 7. 테이블명 일괄 치환(PowerShell) — 구명칭 잔존 정리 시
```powershell
$targetPath = "대상_디렉터리"
$replacements = [ordered]@{
  'LETTCCMMNCLCODE'='TB_CMMN_CL_CODE'; 'LETTCCMMNDETAILCODE'='TB_CMMN_DETAIL_CODE'; 'LETTCCMMNCODE'='TB_CMMN_CODE'
  'LETTHEMPLYRINFOCHANGEDTLS'='TB_EMPLYR_INFO_CHNG_DTLS'; 'LETTNAUTHORGROUPINFO'='TB_AUTHOR_GROUP_INFO'; 'LETTNAUTHORINFO'='TB_AUTHOR_INFO'
  'LETTNBBSMASTEROPTN'='TB_BBS_MASTER_OPTN'; 'LETTNBBSMASTER'='TB_BBS_MASTER'; 'LETTNBBSUSE'='TB_BBS_USE'; 'LETTNBBS'='TB_BBS'
  'LETTNEMPLYRSCRTYESTBS'='TB_EMPLYR_SCRTY_ESTBS'; 'LETTNEMPLYRINFO'='TB_EMPLYR_INFO'; 'LETTNENTRPRSMBER'='TB_ENTRPRS_MBER'
  'LETTNFILEDETAIL'='TB_FILE_DETAIL'; 'LETTNFILE'='TB_FILE'; 'LETTNGNRLMBER'='TB_GNRL_MBER'; 'LETTNORGNZTINFO'='TB_ORGNZT_INFO'
  'LETTNSCHDULINFO'='TB_SCHDUL_INFO'; 'COMVNUSERMASTER'='VW_USER_MASTER'
}
Get-ChildItem -Path $targetPath -Recurse -Include "*.xml","*.sql" | %{
  $c=Get-Content $_.FullName -Raw -Encoding UTF8; $o=$c
  foreach($k in $replacements.Keys){ $c=$c -replace $k,$replacements[$k] }
  if($c -ne $o){ Set-Content $_.FullName -Value $c -Encoding UTF8 -NoNewline; Write-Host "수정: $($_.Name)" }
}
```

## 8. Thymeleaf 주의 / 보안 / 권한
### 8-1. VO/DTO 필드명(자주 틀림): `searchKeyword`(≠searchWrd), `searchCondition`(≠searchCnd), `frstRegisterPnttm`(≠frstRegistPnttm), `inqireCo`(≠rdcnt), 상세는 `result.boardVO.*`. 페이징 베이스 `ComDefaultVO`.
### 8-2. 인증: JWT `ACCESS_TOKEN` HttpOnly 쿠키, CSRF off, `SessionCreationPolicy.STATELESS`, 로그인 `POST /login`(`id`,`password`).
### 8-3. 접근권한
| URL | 권한 |
|-----|------|
| `/`, `/login`, `/register`, `/error`, `/krds-sample`, `/css/**`, `/js/**`, `/krds/**` | 누구나 |
| `/bbs/{bbsId}/**`, `/schedule/**`, `/mypage` | 로그인 |
| `/bbs/master/**`, `/bbs/use/**`, `/member/**`, `/admin/**` | ROLE_ADMIN |

## 9. 비고
- 파괴적 DB 변경(DROP/TRUNCATE) 전엔 복구 가능한 SQL 덤프로 먼저 백업(전역 안전 규칙).
- 변경 이력은 `CLAUDE.md`의 'DB 스키마 변경 이력'을 단일 출처로 유지.
