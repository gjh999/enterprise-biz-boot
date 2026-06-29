# eGovFrame 5.0 기업업무 공통컴포넌트 (enterprise-biz-boot) — Claude 컨텍스트

## 프로젝트 개요

- **프레임워크**: 전자정부표준프레임워크(eGovFrame) 5.0 + **Spring Boot** + **Thymeleaf**
- **유래**: `enterprise-biz-jsp`(Spring + JSP + WAR) → Spring Boot + Thymeleaf(JAR) 전환본
- **참조 템플릿**: 동일 컨벤션의 Boot+Thymeleaf 레퍼런스(`portal-site-boot`)를 개발 시 참조했으며, 인프라/설정/보안/공통(com)/중복모듈은 **본 저장소에 모두 복사 완료**되어 단독으로 빌드·실행된다(외부 형제 프로젝트 불요).
- **성격**: 공공/기업 **업무 관리자 콘솔**(게시판·회원/권한·코드·메뉴·프로그램·로그·통계·기준정보 관리).
- **Java**: 17 / **빌드**: Maven 3.9.9 (eGovCI-5.0.0-Windows-64bit 내장)
- **DB**: 개발=내장 HSQL(`src/main/resources/db/shtdb.sql`) / 운영=6종 DB(`DATABASE/{altibase,cubrid,mysql,oracle,postgresql,tibero}/`)
- **포트**: **28080**, context-path: `/`, 패키지 루트: `egovframework`, artifactId `egovframe-boot-enterprise-biz`

## 빌드·실행

```bash
# JDK 17 + Maven 3.9.9 가 설치돼 있으면 시스템 mvn/java 사용(아래는 eGovCI 번들 경로 예시)
cd <project-root>
mvn clean compile                            # 컴파일 검증
mvn clean package -DskipTests                # 실행 JAR 생성(단위테스트 없음)
java -Dfile.encoding=UTF-8 -jar target/egovframe-boot-enterprise-biz-5.0.0.jar --server.port=28080
```
포트 충돌 종료(PowerShell): `Get-NetTCPConnection -LocalPort 28080 -State Listen | %{ if($_.OwningProcess -gt 0){ Stop-Process -Id $_.OwningProcess -Force } }`

> `spring-boot:run`은 `target/classes` 리소스 사용. 가동 중 템플릿 수정 시
> `cp -r src/main/resources/templates/. target/classes/templates/` 동기화 또는 재기동.

## 테스트 계정 (portal-site-boot와 동일 — 세션 기반, 단일 해시)

| 계정 | 구분 | ID | 비밀번호 | 권한 |
|------|------|-----|---------|------|
| 관리자 | 업무사용자(USR) | `admin` | `1` | ROLE_ADMIN |
| 사용자 | 업무사용자(USR) | `user` | `1` | ROLE_USER |

> 비밀번호 = `Base64(SHA-256(id + 평문))`. 로그인 `POST /uat/uia/actionSecurityLogin.do`(id,password,userSe).
> 권한(데모 단순화): `admin`만 ROLE_ADMIN, 그 외 ROLE_USER.

## 아키텍처 (JSP → Boot 전환 핵심)

- **설정**: `web.xml`/`context-*.xml`(스프링 XML) → `egovframework.com.config.*`(Java @Configuration).
- **컴포넌트 스캔**: `EgovConfigAppCommon`(@Service/@Repository), `EgovConfigWebDispatcherServlet`(@Controller),
  `@SpringBootApplication`(egovframework 전체).
- **보안**: 세션 기반 Spring Security(`com.security.SecurityConfig`). 미인증/권한부족 → `/uat/uia/egovLoginUsr.do`.
- **뷰**: 컨트롤러 반환 뷰명(앞 슬래시 없음) = `templates/<뷰명>.html`. JSP 미작성.
- **레이아웃**: `templates/layouts/default.html`(Thymeleaf Layout Dialect) + `templates/fragments/{header,nav,footer,pagination}.html`. 정적자원 `static/css|js|images`(**공식 KRDS** `krds/resources/cdn/krds.min.css`+호환 레이어 `krds-compat.css`+`krds.css`+다크 `theme.css`, 전부 로컬. **Bootstrap 프레임워크 제거**·Bootstrap Icons `bi-*`만 유지. GNB 드롭다운/모바일 collapse는 `data-bs-*` + `krds-compat.js` 바닐라 대체). 레거시 컴포넌트 클래스 잔존 0.

## 다국어 (i18n) — 전면 적용 완료

- **전환 흐름**: 헤더 한국어/EN 토글 → `/cmm/lang?lang=ko|en` → `EgovLangController` 가 `LocaleResolver` 로 선택 언어를 **`LANG` 쿠키**(`CookieLocaleResolver "LANG"`, `WebMvcConfig`, 기본 `Locale.KOREAN`)에 저장 → Referer 로 PRG 리다이렉트(오픈 리다이렉트 방지·`?lang` 비잔류).
- **메시지 리소스**: `src/main/resources/egovframework/message/message-ui_{ko,en}.properties`(폴백 `message-ui.properties`). **키 집합 정합**(ko/en 각 ~1436키 동일 키). 템플릿 238개 ko/en 메시지키 전환, **하드코딩 한글 잔여 0**.
- **규약**: 신규 문구는 ko/en 양쪽에 **APPEND 만**(키 집합 항상 일치). 영어 의도적 빈값(단위 접미사·한국어 전용 부제 등)은 `key=` 빈값 유지(현재 ~17개). 부분 전환 시에도 양쪽 키 동시 추가.

## 모듈 구성

### portal-site-boot에서 그대로 가져온 인프라/중복 모듈
- 공통(`com/**`: config·security·cmm·파일관리), `cop/bbs`·`cop/com`(게시판), `main`,
  `sec/{gmt,ram,rgm,rmt}`(권한/그룹/롤), `sym/cal`(휴일), `sym/ccm/zip`(우편번호),
  `uat/uia`(로그인), `uss/umt`(사용자관리).
- portal 고유 공개모듈(`uss/olh` FAQ/Q&A, `uss/olp` 설문, `uss/sam` 약관/개인정보, `uss/ion/bnr` 배너)도
  베이스에 포함되어 동작은 하나, **enterprise-biz 본연 범위는 아님**(상단 nav에서는 노출 최소화). 필요 시 정리 가능.

### enterprise-biz-jsp에서 신규 전환한 업무관리자 모듈 (이번 전환의 핵심)
| 모듈 | 기능 | 대표 URL | 테이블(TB_) |
|------|------|----------|-------------|
| `sym/ccm/cca` | 공통코드관리 | `/sym/ccm/cca/EgovCcmCmmnCodeList.do` | TB_CMMN_CODE |
| `sym/ccm/ccc` | 공통분류코드관리 | `/sym/ccm/ccc/EgovCcmCmmnClCodeList.do` | TB_CMMN_CL_CODE |
| `sym/ccm/cde` | 공통상세코드관리 | `/sym/ccm/cde/EgovCcmCmmnDetailCodeList.do` | TB_CMMN_DETAIL_CODE |
| `sym/mnu/mpm` | 메뉴목록관리 | `/sym/mnu/mpm/EgovMenuManageSelect.do` | TB_MENU_INFO |
| `sym/mnu/mcm` | 메뉴생성관리 | `/sym/mnu/mcm/EgovMenuCreatManageSelect.do` | TB_MENU_CREAT_DTLS |
| `sym/prm` | 프로그램관리 | `/sym/prm/EgovProgramListManageSelect.do` | TB_PROGRM_LIST |
| `sym/log/clg` | 접속로그조회 | `/sym/log/clg/SelectLoginLogList.do` | TB_LOGIN_LOG |
| `sym/log/lgm` | 시스템로그조회 | `/sym/log/lgm/SelectSysLogList.do` | TB_SYS_LOG, TB_SYS_LOG_SUMMARY |
| `uat/uap` | 로그인정책관리 | `/uat/uap/selectLoginPolicyListView.do` | TB_LOGIN_POLICY |
| `uss/ion/uas` | 사용자부재관리 | `/uss/ion/uas/selectUserAbsnceListView.do` | TB_USER_ABSNCE |
| `sts/cst` | 접속통계 | `/sts/cst/selectConectStats.do` | TB_CONECT_LOG |

> **전환 시 의도적 생략(원본 충실 재현 보류)**: 로그 자동적재 AOP(`EgovLoginLogAspect`/`EgovSysLogAspect`),
> 로그 요약 스케줄러(`EgovSysLogScheduling`), 로그인정책 필터(`EgovLoginPolicyFilter`), 메뉴 엑셀 일괄등록(POI).
> 관리 화면(조회/CRUD)은 시드 데이터로 동작. 추후 필요 시 cross-cutting 컴포넌트 재이식.

## DB 명명 규칙 (현행화 기준)

- 테이블 접두어 `TB_` + **SNAKE_CASE 대문자**. 구 `LETT*/LETTC*/COMT*/COMVN*` 폐기.
- 사용자뷰는 `VW_`(예: `VW_USER_MASTER`).
- 모든 테이블 감사컬럼 4종: `FRST_REGIST_PNTTM`, `FRST_REGISTER_ID`, `LAST_UPDT_PNTTM`, `LAST_UPDUSR_ID`.
- **구→신 매핑(이번 전환에 사용)**: `LETTC/COMTCCMMNCODE→TB_CMMN_CODE`, `…CLCODE→TB_CMMN_CL_CODE`,
  `…DETAILCODE→TB_CMMN_DETAIL_CODE`, `…MENUINFO→TB_MENU_INFO`, `…MENUCREATDTLS→TB_MENU_CREAT_DTLS`,
  `…PROGRMLIST→TB_PROGRM_LIST`, `…LOGINLOG→TB_LOGIN_LOG`, `…SYSLOG→TB_SYS_LOG`,
  `…LOGINPOLICY→TB_LOGIN_POLICY`, `…USERABSNCE→TB_USER_ABSNCE`, 접속로그→`TB_CONECT_LOG`,
  `COMVNUSERMASTER(VIEW)→VW_USER_MASTER`.

## MyBatis 매퍼 표준

- 위치: `src/main/resources/egovframework/mapper/let/{모듈}/Egov{기능}_SQL_{dbType}.xml`.
- **7종 DB 변형 전부 유지**: `altibase, cubrid, hsql, mysql, oracle, postgresql, tibero`.
  런타임은 `Globals.DbType`(기본 **hsql**) → `*_hsql.xml` 로드. 나머지 6종은 운영 이식용으로 현행화 동기화.
- `mapper-config.xml`: `mapUnderscoreToCamelCase=true`, `jdbcTypeForNull=VARCHAR`, **`callSettersOnNulls=true`**(egovMap NULL키 보존).
- MySQL→HSQL 변환: `DATE_FORMAT(c,'%Y%m%d')`→`TO_CHAR(c,'YYYYMMDD')`, `IFNULL`→`COALESCE`,
  `NOW()/SYSDATE`→`CURRENT_TIMESTAMP`, 백틱 제거, `SUBSTR`→`SUBSTRING`, `LIMIT n OFFSET m` 사용.
- **테이블명 현행화 완료**: 매퍼 전수 `LETT*/COMT*/COMVN*` 잔존 0건(전부 `TB_*`/`VW_*`).

## 뷰(Thymeleaf) 규칙 ★ 배포 핵심
- 뷰 이름은 **앞 슬래시 없이**(`return "sym/ccm/cca/EgovCcmCmmnCodeList"`). `redirect:`/`forward:`/`.do` URL만 `/`로 시작.
  (이유: classpath prefix + `/` = 이중 슬래시 → 패키지 JAR에서 Whitelabel 500. portal-site-boot 전환 핵심 교훈.)
- EgovMap 키 케이싱: 별칭에 언더스코어 있으면 카멜케이스, 없으면 HSQL이 전부 소문자화. NULL은 `callSettersOnNulls`로 키 보존.

## 작업 규칙 (자율 진행)
1. 진행상태·요약은 **한글**. 2. 묻지 않고 끝까지 자율 진행, 막히면 합리적 판단. 3. 완료 후 결과만 요약.
4. **신중 수정**: `pom.xml`, `SecurityConfig.java`, `application.properties`, `db/shtdb.sql`. 파괴적 DB변경은 `db-safe-migrate` 백업 선행.
5. 빌드 컴파일→패키지→구동(28080) 후 스모크 확인, 임시파일 정리.

## 변경 이력
| 날짜 | 작업 |
|------|------|
| 2026-06-17 | enterprise-biz-jsp(JSP/WAR) → enterprise-biz-boot(Boot/Thymeleaf/JAR) 전환. portal-site-boot 인프라 재사용 + 업무관리자 11개 모듈(코드/메뉴/프로그램/로그/통계/정책/부재) 신규 전환. 매퍼 7종 DB 현행화(TB_ 명명, 잔존 0). 포트 28080. 패키지 빌드·구동 성공 |
| 2026-06-24 | **KRDS 전면 적용 완료**: 전 모듈(uss·cmm·sym·cop·sec·main·로그인) 템플릿 KRDS 네이티브 마크업 전환, 공식 자산(`krds.min.css`)+호환 레이어(`krds-compat`)+다크 `theme.css` 로드순서 정비. **Bootstrap 프레임워크 자산 제거**(참조 0 확인), `bi-*` 아이콘만 유지. GNB는 `data-bs-*`+`krds-compat.js` 바닐라 대체 |
| 2026-06-28 | **다국어(i18n) 전면 적용 완료**: 인프라 이식(`EgovLangController`·`CookieLocaleResolver "LANG"`) + 전 템플릿 238개 ko/en 메시지키 전환(message-ui_{ko,en}, 키 정합 ~1436, 하드코딩 잔여 0). 멀티파트/요청 UTF-8 인코딩 방어 강화. 메뉴 등록 반려 버그·메뉴목록 렌더 오류 수정 |
| 2026-06-28 | **기능 점검 수정**: 설문응답 `OCCP_TY_CODE` varchar(1)→(10)(shtdb+6 DBMS), `TB_EMPLYR_INFO_CHNG_DTLS` 불필요 NOT NULL 완화(사용자 수정 시 변경이력 INSERT 실패 해소), 사용자부재(uas) 조회 null 가드, Q&A 관리자(ROLE_ADMIN) 권한 판정 추가, 파일저장 경로 절대경로화·템플릿 매퍼 7종 보정. 접속로그/시스템로그 조회 정상 동작(목록·검색·상세) |
