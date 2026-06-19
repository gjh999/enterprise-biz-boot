# enterprise-biz-boot 전용 스킬 (SKILL.md)

> eGovFrame 5.0 **기업업무 공통컴포넌트 관리자콘솔**(JSP→Spring Boot + Thymeleaf 전환본)에서
> Claude Code가 반복 작업을 재현 가능한 절차로 수행하기 위한 **컨텍스트 엔지니어링 + 하네스 엔지니어링** 가이드.
> 최종 현행화: 2026-06-17. (상세 아키텍처·전환 이력은 같은 폴더 `CLAUDE.md` 참조)
>
> ⚠️ 인프라/설정/보안/공통(com)·중복 업무모듈은 동일 컨벤션의 Boot 레퍼런스(`portal-site-boot`)를
> 참조해 작성했고 **본 저장소에 모두 포함**되어 단독 동작한다(외부 형제 프로젝트 불요).
> 인증은 **세션 기반 Spring Security**(JWT 아님).

---

## 0. 정체성 한눈에

| 항목 | 값 |
|------|-----|
| 유래 | `enterprise-biz-jsp`(Spring+JSP+WAR) → Boot+Thymeleaf(JAR) |
| 기능 유래 | `enterprise-biz-jsp`(JSP 원본)의 전 기능을 Boot+Thymeleaf로 전환(본 저장소가 정본) |
| 인증 | **세션 기반 Spring Security**(JWT 아님). `HttpSessionSecurityContextRepository` |
| URL 컨벤션 | eGov 원본 `*.do` 유지 |
| 뷰 매핑 | 컨트롤러 반환 뷰명(앞 슬래시 없음) = `templates/<뷰명>.html` (JSP 미작성) |
| 포트 / context | **28080** / `/` , 패키지 루트 `egovframework`, artifactId `egovframe-boot-enterprise-biz` |
| DB | 개발=내장 HSQL(`src/main/resources/db/shtdb.sql`) / 운영=6종(`DATABASE/*`) |
| Java / 빌드 | 17 / Maven 3.9.9 (eGovCI-5.0.0 내장) |

---

## 1. 표준 참조 순서 (신규 작업 시)
```
① CLAUDE.md                → 전체 구조·모듈맵·전환 핵심·함정
② SKILL.md                  → 본 파일(절차·함정·검증 체크리스트)
③ docs/                     → 단어/도메인/용어/DB스키마 규칙(본 저장소 내 포함)
④ src/main/java, mapper     → 컨트롤러·매퍼·템플릿 골든패턴(저장소 내 정본)
```
> 본 저장소는 단독으로 완결되어 있어 외부 형제 프로젝트(원본 JSP 등) 없이 작업 가능하다.

---

## 2. 빌드·실행 (검증된 절차)

### 2-1. 요구사항
```
JDK 17 + Maven 3.9.9 (시스템 설치 또는 eGovCI-5.0.0 번들)
PROJECT = <project-root> (본 저장소 루트)
```

### 2-2. 빌드 + 패키지(JAR) — Bash 도구
```bash
cd <project-root>
mvn clean compile                 # 컴파일 검증(단위테스트 없음)
mvn clean package -DskipTests     # 실행 JAR
```

### 2-3. 서버 구동 (JAR, 포트 28080) — 백그라운드
```bash
nohup java -Dfile.encoding=UTF-8 -jar target/egovframe-boot-enterprise-biz-5.0.0.jar \
  --server.port=28080 > target/bootrun.log 2>&1 & disown
```
> PowerShell로 JAR 구동 시 인자는 반드시 **배열**로(`$a=@('-Dfile.encoding=UTF-8','-jar',$jar)`).
> 문자열로 붙이면 `.encoding` 오파싱 → `ClassNotFoundException`.

### 2-4. 포트 충돌 종료 (PowerShell)
```powershell
$c = Get-NetTCPConnection -LocalPort 28080 -State Listen -ErrorAction SilentlyContinue
foreach($x in $c){ if($x.OwningProcess -gt 0){ try{ Stop-Process -Id $x.OwningProcess -Force }catch{} } }
```

### 2-5. 기동 확인 / 로그 (UTF-16 NUL 제거 후 grep)
```bash
log="target/bootrun.log"
tr -d '\000' < "$log" | grep -aE "Started EgovBootApplication|APPLICATION FAILED"
```

---

## 3. 테스트 계정 / 비밀번호
| 계정 | ID | 비밀번호 | 권한 |
|------|-----|---------|------|
| 관리자 | `admin` | `1` | ROLE_ADMIN |
| 사용자 | `user` | `1` | ROLE_USER |
```
저장형식 = Base64(SHA-256(id + 평문))   (단일 해시). 생성: EgovFileScrty.encryptPassword(pw, id)
로그인: POST /uat/uia/actionSecurityLogin.do (id,password,userSe=USR)
```

---

## 4. 보안 / 접근 권한 (SecurityConfig.java — 세션 기반)
- 미인증/권한부족 → `/uat/uia/egovLoginUsr.do`.
- **ADMIN_ONLY(ROLE_ADMIN)**: `/sec/**`, `/cop/com/**`, `/sts/**`, `/uat/uap/**`, `/uss/ion/uas/**`, `/sym/**`
  (코드·메뉴·프로그램·로그·일정·우편번호·정책·부재·통계 = 전 시스템관리 모듈 보호).
- **PERMIT_ALL**: `/`, `/cmm/**`, `/sym/mms/**`, `/uat/uia/**`, 정적자원, swagger.
- 그 외 조회는 공개, **쓰기는 컨트롤러의 `EgovUserDetailsHelper.isAuthenticated()` 검사로 보호**(원본 정책).

---

## 5. 뷰(Thymeleaf) 규칙 ★ 배포 핵심
### 5-1. 뷰 이름은 앞 슬래시 없이
```java
return "sym/ccm/cca/EgovCcmCmmnCodeList";   // ✅
return "/sym/ccm/cca/EgovCcmCmmnCodeList";  // ❌ 패키지 JAR에서 Whitelabel 500
```
`redirect:`/`forward:`/`.do` URL만 `/`로 시작.

### 5-2. ★ EgovMap 키 케이싱 함정 (이번 전환에서 실제 발생·수정)
`resultType="egovMap"` SELECT의 **별칭에 언더스코어가 없으면** HSQL이 대문자화 → EgovMap 키가 **전부 소문자**가 된다.
```sql
SELECT AUTHOR_CODE AS authorCode ...   -- ❌ HSQL: 라벨 AUTHORCODE → 키 'authorcode' → ${result.authorCode} 500
SELECT AUTHOR_CODE AS AUTHOR_CODE ...  -- ✅ mapUnderscoreToCamelCase → 키 'authorCode'
```
- **규칙**: egovMap SELECT의 별칭은 **언더스코어 대문자**로(`AS CHK_YEO_BU`), 카멜케이스 별칭 금지.
  (mcm `EgovMenuCreatManage`에서 `result.authorCode` 500 → 7종 매퍼 별칭 전부 언더스코어화로 해소.)
- NULL 컬럼은 `mapper-config.xml`의 `callSettersOnNulls=true`로 키 보존, 값은 빈문자 렌더.

### 5-3. 레이아웃
```
templates/<뷰명>.html  +  layout:decorate="~{layouts/default}" + <... layout:fragment="content">
templates/fragments/{header,nav,footer,pagination}.html   (정적자원 static/css|js|images, 전부 로컬)
```

---

## 6. DB 스키마 / 매퍼 현행화 표준

### 6-1. 명명
```
테이블 = TB_ + 기능명(대문자 스네이크). 뷰 = VW_. 구 LETT*/COMT*/COMVN* 폐기.
감사컬럼 4종 필수: FRST_REGIST_PNTTM, FRST_REGISTER_ID, LAST_UPDT_PNTTM, LAST_UPDUSR_ID.
```
### 6-2. 매퍼 (7종 DB 전부 유지·현행화)
```
위치: src/main/resources/egovframework/mapper/let/{모듈}/Egov{기능}_SQL_{dbType}.xml
dbType ∈ {altibase,cubrid,hsql,mysql,oracle,postgresql,tibero}  (런타임=hsql)
mapper-config.xml: mapUnderscoreToCamelCase=true, jdbcTypeForNull=VARCHAR, callSettersOnNulls=true
```
- 신규/수정 시 **7종 변형 모두** 테이블명 `TB_*` 현행화(잔존 `LETT*/COMT*/COMVN*` 0 유지).
- MySQL→HSQL: `DATE_FORMAT(c,'%Y%m%d')`→`TO_CHAR(c,'YYYYMMDD')`, `IFNULL`→`COALESCE`,
  `NOW()/SYSDATE`→`CURRENT_TIMESTAMP`, 백틱 제거, `SUBSTR`→`SUBSTRING`, `LIMIT n OFFSET m`.
- egovMap SELECT 별칭은 §5-2 규칙(언더스코어 대문자).

### 6-3. ⚠️ 파괴적 변경 전 백업 (전역 필수 룰)
`DROP/TRUNCATE/DELETE` 등 데이터 손실 변경 **전에** `db-safe-migrate` 스킬로 복구 덤프 백업
(전역 `~/.claude/CLAUDE.md`). 비파괴(ADD COLUMN/CREATE TABLE)는 백업 불요.

---

## 7. 모듈 검증 체크리스트 (스모크)
```bash
B="http://localhost:28080"; J="target/cj.txt"; rm -f "$J"
curl.exe -s -c "$J" "$B/uat/uia/egovLoginUsr.do" -o /dev/null
curl.exe -s -b "$J" -c "$J" --data-urlencode id=admin --data-urlencode password=1 --data-urlencode userSe=USR \
  "$B/uat/uia/actionSecurityLogin.do" -o /dev/null         # 302 = 로그인 성공
# 각 화면 GET → 200. ★ 200이어도 bootrun.log에 SpelEvaluation/TemplateInput ERROR 0건 확인(부분렌더 함정)
```
> **함정**: 템플릿이 렌더 중간에 터지면 응답헤더는 이미 200으로 커밋되어 curl은 200을 받지만 화면은 깨진다.
> 반드시 `bootrun.log`의 `THYMELEAF/SpelEvaluation` ERROR 0건을 함께 확인할 것.

신규 전환 11개 관리자 모듈 대표 화면(전부 HTTP 200 + 에러 0건 검증됨):
`/sym/ccm/{cca,ccc,cde}/*List.do`, `/sym/mnu/{mpm,mcm}/*Select.do`, `/sym/prm/EgovProgramListManageSelect.do`,
`/sym/log/{clg,lgm}/Select*List.do`, `/uat/uap/selectLoginPolicyListView.do`,
`/uss/ion/uas/selectUserAbsnceListView.do`, `/sts/cst/selectConectStats.do`.

---

## 8. 컨트롤러 검증 함정 (앱 버그 아님, 원본 제약 재현)
- 멀티파트 컨트롤러(파일첨부)는 `multipart/form-data` 제출 필수(urlencoded → IllegalStateException).
- `@EgovNullCheck` 필드는 폼에 (hidden이라도) 값 제공해야 insert 도달.
- 상세/수정 화면을 ID 파라미터 없이 직접 호출하면 400/예외 → 정상(목록 링크로 진입).

---

## 9. 전환 시 의도적 생략(원본 충실 재현 보류 — 재이식 가능)
- 로그 자동적재 AOP(`EgovLoginLogAspect`/`EgovSysLogAspect`), 로그요약 스케줄러(`EgovSysLogScheduling`),
  로그인정책 필터(`EgovLoginPolicyFilter`), 메뉴 엑셀 일괄등록(POI), 프로그램 변경요청(원본 JSP 부재).
- 관리 화면(조회/CRUD)은 시드 데이터로 동작. cross-cutting 적재 로직은 후속 작업.

---

## 10. 사용 가능한 스킬 / 하네스
| 종류 | 항목 | 용도 |
|------|------|------|
| 스킬(전역) | `db-safe-migrate` | 파괴적 DB 변경 전 복구 덤프 백업 (§6-3) |
| 플러그인 | `andrej-karpathy-skills` | 코딩 가이드라인 |
| 훅(PreToolUse) | DROP·DELETE·TRUNCATE 감지 | 위험 SQL 실행 전 확인 |
| 전역 룰 | `~/.claude/CLAUDE.md` | DB 파괴변경 백업 필수 |

---

## 11. 작업 규칙 (자율 진행)
1. 진행상태·요약 **한글**. 2. 묻지 않고 끝까지 자율, 막히면 합리적 판단. 3. 완료 후 결과만 요약.
4. **신중 수정**: `pom.xml`, `SecurityConfig.java`, `application.properties`, `db/shtdb.sql`.
5. 빌드 컴파일→패키지→구동(28080)→스모크(§7, 로그 에러 0건)까지 확인하고 임시파일 정리.

## 12. 변경 이력
| 날짜 | 작업 |
|------|------|
| 2026-06-17 | JSP→Boot 전환 초판. portal-site-boot 인프라 재사용 + 업무관리자 11개 모듈 신규 전환, 매퍼 7종 DB 현행화(TB_, 잔존 0), EgovMap 키케이싱 함정 수정, 포트 28080, 패키지 빌드·구동·스모크(200/에러0) 검증 |
