# KRDS 적용 가이드 (Korea Design System)

> 디지털정부 표준 디자인 시스템(**KRDS**, 행정안전부 · https://www.krds.go.kr)을
> `simple-home-boot`에 적용한 내역과 **어디에 · 어떻게** 적용했는지 정리한 문서입니다.
> 작성: 2026-06-22

---

## 0. 최종 상태 (2026-06-22 갱신) — Bootstrap 완전 제거 + 공식 KRDS 전환

초기에는 *Bootstrap 그리드 + KRDS 시각 스킨* 하이브리드였으나, 이후 **Bootstrap 프레임워크를 완전히 제거**하고
**공식 KRDS HTML Component Kit**으로 전환했습니다.

- **삭제**: `static/css/bootstrap.min.css`, `static/js/bootstrap.bundle.min.js` (프레임워크). *Bootstrap Icons(`bi-*`) 폰트는 아이콘용으로 유지.*
- **도입(공식 패키지)**: `static/krds/`(KRDS HTML Component Kit). 레이아웃이 `/krds/resources/cdn/krds.min.css`(디자인 시스템, 1,835개 토큰)와 **Pretendard GOV** 폰트를 로드.
- **호환 레이어**: `static/css/krds-compat.css` — Bootstrap이 제공하던 **그리드(`row/col-*`)·유틸리티(여백/flex/text)·컴포넌트 구조(btn/card/table/form/breadcrumb/pagination/navbar/dropdown)**를 **KRDS 토큰으로 재구현** + `@font-face`(Pretendard GOV). 템플릿의 기존 클래스명을 그대로 두고 KRDS 룩으로 동작.
- **JS 동작 대체**: `static/js/krds-compat.js` — Bootstrap 번들이 하던 `data-bs-*`(드롭다운·모바일 메뉴 collapse·알림 닫기)를 바닐라 JS로 대체.
- **보안/서빙**: `SecurityConfig`·`WebMvcConfig`에 `/krds/**` 허용. krds.min.css의 **원격 아이콘 350개를 로컬(`/krds/resources/img/`)로 치환**(CDN 미사용 정책 준수).
- **KRDS 네이티브 컴포넌트**: `krds.min.js` **전역 로드**(탭·아코디언 등 동작). KRDS 네이티브 클래스(`krds-*`)만으로 구성한 **사용 예시 페이지 `/krds-sample`**(보드리스트·탭·아코디언·버튼·배지·폼·페이지네이션) 제공 → 다른 화면을 KRDS 네이티브로 전환할 때의 **표준 패턴**.
- **헤더 익명 안전화**: 공개(비로그인) 페이지에서 `principal.name` 접근 오류(500)를 막도록 헤더 사용자 영역을 `isAuthenticated()`로 가드, 익명 시 로그인 링크 노출.
- **검증**: Bootstrap 제거 후 `/`·`/portal`·게시판·게시판마스터·일정·회원·마이페이지·관리자·`/krds-sample` **전 페이지 HTTP 200**, 그리드·GNB 드롭다운·탭·아코디언·폼·테이블 정상.

> 로드 순서(레이아웃) — CSS: `bootstrap-icons` → `krds.min.css`(공식) → `common.css` → `krds-compat.css` → `krds.css`(스킨).
> JS: `krds.min.js`(공식) → `krds-compat.js` → `krds.js` → `common.js`.

아래 §1~9는 초기 하이브리드 적용 시점의 기록이며, 토큰·컴포넌트 매핑 원칙은 현재도 유효합니다.

---

## 1. 배경 — 적용 전 상태 (진단)

기존에는 "KRDS 적용"으로 표기되어 있었으나 **실제로는 KRDS가 아니라 Bootstrap + 다크 테마**였습니다.

| 파일 | 적용 전 실제 상태 |
| --- | --- |
| `static/css/krds.css` | 2.8KB **플레이스홀더** (구 파란색 `#0054A6` 변수 몇 개. 주석에 "공식 KRDS 파일 제공 시 교체" 명시) |
| `static/js/krds.js` | 284B **플레이스홀더** ("KRDS JS placeholder — 교체 시 삭제") |
| `static/css/theme.css` | 22KB **모던 다크 테마** (전역 오버라이드 — 사실상 사이트 외관을 지배) |
| `layouts/default.html` | 주석에 "현재: **Bootstrap 5 기반 임시 적용** — KRDS 파일 제공 시 교체" |

즉, 시각 디자인은 Bootstrap 기본 + 다크 테마였고 KRDS 디자인 토큰·컴포넌트는 적용돼 있지 않았습니다.

---

## 2. 적용 방식 (핵심 전략)

전 페이지가 **공유 레이아웃(`layouts/default.html`) + 공유 프래그먼트 + 전역 CSS**를 사용하므로,
**전역 CSS 한 곳(`krds.css`)을 실제 KRDS로 구현하고 로드 순서를 최종 권위로 두는 방식**으로
템플릿 30여 개를 일일이 고치지 않고 사이트 전체에 KRDS를 일괄 적용했습니다.

1. **`krds.css`를 KRDS 디자인 시스템으로 전면 재작성** — KRDS 디자인 토큰 정의 + Bootstrap `--bs-*` 변수 재매핑.
   → 템플릿의 기존 Bootstrap 클래스(`btn`·`card`·`table`·`form-control`…)가 **수정 없이 KRDS 톤으로 표시**.
2. **다크 `theme.css`를 레이아웃에서 제거**하고 **`krds.css`를 마지막에 로드**(최종 권위)하여 KRDS가 덮어쓰도록.
3. KWCAG(웹접근성) 보조 동작을 `krds.js`로 추가.

> **역할 분담 — Bootstrap(레이아웃) + KRDS(시각)**
> Bootstrap 5는 **그리드/레이아웃 전용**으로 유지한다 — `.container(-fluid)`·`.row`·`.col-*`(예: `col-lg-6`,
> `col-6 col-md-3`)·`.g-*`(거터)·`.d-flex` 등 **반응형 칸 배치·정렬·간격**을 담당. **시각 디자인(색·타이포·
> 컴포넌트 외관)은 `krds.css`** 가 담당한다. `krds.css` 에는 `.row`/`.col-`/`.container` 같은 레이아웃
> 규칙이 **없어** 그리드는 Bootstrap 그대로 동작한다 → 화면 구조를 다시 짜지 않고 KRDS 룩&필만 입힌다.

---

## 3. 변경 파일 — 어디에 무엇을

| 파일 | 변경 내용 |
| --- | --- |
| `static/css/krds.css` | **전면 재작성**. KRDS 디자인 토큰(Primary `#256EF4`, 그레이스케일, 시맨틱, 타이포·반경) + Bootstrap `--bs-*` 재매핑 + 컴포넌트(헤더/GNB/브레드크럼/버튼/카드/표/폼/배지/알림/페이지네이션/푸터) + 접근성(스킵네비·포커스 링). **추가**: 로그인 컴포넌트(`.login-lang`·`.login-divider`·`.sns-*`·`.login-back`), 포털 히어로(`.egov-hero` 도트패턴·글로우·배지·링아이콘), 페이지 타이틀 강조 바(`.page-title-bar h1`), 로고(`.logo-badge`) |
| `templates/layouts/default.html` | `theme.css`(다크) 링크 **제거**, `krds.css`를 **마지막에 로드**(common.css 다음). 주석을 KRDS 기준으로 갱신 |
| `templates/layouts/login.html` | 동일하게 `theme.css` 제거 + `krds.css` 최종 로드 |
| `static/css/common.css` | `--egov-primary` `#0054A6 → #256EF4`(KRDS Primary), `--egov-primary-dark → #0B50D0`로 통일 → 업무 커스텀 클래스(`egov-table`·`search-bar`·`bbs-content`·`egov-card`)도 KRDS 색으로 표시 |
| `templates/let/main/mainView.html` | 환영 배너를 **KRDS 히어로**(`.egov-hero`)로 보강(도트 패턴·글로우·배지·링 아이콘), 페이지 타이틀에 KRDS 강조 바 |
| `templates/let/main/landingView.html` | 다크 독립 랜딩 → **KRDS 라이트로 전면 재작성**(`krds.css` 토큰 로드, `#0a0e27` 제거, Primary `#256EF4`·Pretendard, 푸터 `gray-90`). 본문·i18n·JS 유지 |
| `static/js/krds.js` | 플레이스홀더 → KRDS 접근성 스크립트(본문 바로가기 시 `#content`에 실제 포커스 부여) |

> `theme.css` 파일 자체는 **삭제하지 않고 보존**(레이아웃에서 로드만 중단). 필요 시 되돌릴 수 있습니다.
>
> **전 페이지 적용 근거**: 모든 페이지가 ① 공유 레이아웃(`default`/`login`)의 KRDS 헤더·GNB·푸터, ② 전역 `krds.css`의 Bootstrap 클래스 재매핑, ③ 업무 커스텀 클래스를 담은 `common.css`(KRDS 색)를 공유하므로, 개별 템플릿 수정 없이 KRDS가 일괄 적용됩니다. theme.css 전용이던 로그인/타이틀/히어로 스타일은 모두 krds.css로 이관 완료.

---

## 4. KRDS 디자인 토큰

`krds.css` `:root`에 정의했고, 동시에 Bootstrap 변수로도 재매핑했습니다.

| 토큰 | 값 | 용도 |
| --- | --- | --- |
| `--krds-primary` | `#256EF4` | KRDS 대표 색(버튼·링크·GNB·강조) |
| `--krds-primary-hover` | `#0B50D0` | hover/active |
| `--krds-primary-light` / `-bg` | `#E8F0FE` / `#F0F6FF` | 연한 배경·hover row |
| `--krds-gray-0 ~ 90` | `#FFFFFF … #1E2124` | 배경·보더·본문 텍스트(gray-90) |
| `--krds-success/danger/warning/info` | `#228738 / #DE3412 / #FF9000 / #256EF4` | 시맨틱 |
| `--krds-radius(-sm/-lg)` | `6px / 4px / 12px` | 모서리 |
| `--krds-font` | `"Pretendard GOV", "Pretendard", …` 폴백 | KRDS 권장 서체 |

Bootstrap 재매핑: `--bs-primary`, `--bs-link-color`, `--bs-body-color`, `--bs-body-font-family`,
`--bs-border-color`, `--bs-border-radius` 등을 위 KRDS 값으로 덮어씀.

---

## 5. 컴포넌트별 적용

| 컴포넌트 | 적용 내용 |
| --- | --- |
| 헤더(`.egov-header`) | 흰 배경 + 하단 보더(KRDS 마스트헤드), 언어 토글 KRDS 스타일 |
| 전역 내비 GNB(`.egov-nav`) | KRDS Primary 배경, 드롭다운 KRDS 카드/hover |
| 브레드크럼 | 연회색 박스 + KRDS 보더/색 |
| 버튼(`.btn-primary`·`.btn-outline-*`) | KRDS Primary·hover·active, 반경 6px |
| 카드/표/폼 | KRDS 보더·헤더 배경, 표 헤더 강조, 폼 포커스 링(KRDS Primary) |
| 배지·알림·페이지네이션 | KRDS 시맨틱 색 매핑 |
| 푸터 | KRDS 정부 푸터(진회색 `gray-90`) |

---

## 6. 웹접근성 (KWCAG)

- **본문 바로가기(skip-nav)**: 레이아웃에 이미 존재하던 링크에 KRDS 스타일 + `krds.js`로 포커스 이동 보강.
- **선명한 포커스 링**: 모든 인터랙티브 요소 `:focus-visible`에 `3px` 외곽선(KRDS Primary 계열).
- **명도 대비**: 본문 텍스트 `gray-90(#1E2124)` / 흰 배경으로 충분한 대비 확보.

---

## 7. 적용 범위

| 대상 | KRDS 적용 |
| --- | --- |
| **공유 레이아웃(`default`/`login`) 사용 전 페이지** — 로그인·포털(mainView, `/portal`)·게시판·회원·일정·마이페이지·관리자 | ✅ 적용됨 |
| **독립 랜딩 페이지**(`landingView`, 라우트 `/`·`/landing`) | ✅ 적용됨 — 공유 `krds.css` 토큰을 로드하고 인라인 스타일을 **KRDS 라이트 테마로 전환**(다크 `#0a0e27` 제거, Primary `#256EF4`, Pretendard). 본문 마크업·i18n 메시지키·JS(스크롤 리빌·카운트업)는 유지 |

> 랜딩(`/`)은 공유 레이아웃을 쓰지 않는 독립 마케팅 페이지지만, 동일 KRDS 토큰을 공유하도록
> 재작성하여 사이트 전체와 디자인을 통일했습니다(푸터도 KRDS 진회색 `gray-90`으로 일치).

### 로그인 페이지 스타일 이관 (theme.css → krds.css)
다크 `theme.css`에만 있던 로그인 전용 스타일(`.login-lang` 우상단 고정, `.login-divider`,
`.sns-btn`/`.sns-naver`/`.sns-kakao`, `.login-signup-*`, `.login-back`)을 제거하면서 레이아웃이
깨졌던 문제를 해결하기 위해, 해당 컴포넌트들을 **KRDS 라이트 버전으로 `krds.css`에 이관**했습니다.
(`.egov-login-body`·`.login-card`·`.login-logo`는 기존부터 `common.css`에 존재)

---

## 8. 검증 결과 (2026-06-22)

`mvn spring-boot:run` (포트 8080) 구동 후 확인:

- `GET /login` → **HTTP 200**, `krds.css` 로드 ✅, 다크 `theme.css` 미로드 ✅, **Bootstrap 그리드(`bootstrap.min.css`) 유지** ✅
- `GET /css/krds.css` → **HTTP 200**, `#256EF4`·"Korea Design System"·`.egov-hero`·`.login-lang` 포함 ✅
- `GET /` (랜딩) → `krds.css` 로드 ✅, 다크색(`#0a0e27`) 제거 ✅, KRDS Primary(`#256EF4`) 적용 ✅
- `GET /portal` → 포털 히어로(`.egov-hero`)·타이틀 강조 바 적용 (JWT 인증 필요)

> **"Bootstrap 그리드 유지"의 의미**: `bootstrap.min.css` 를 계속 로드해 `.row`/`.col-*` 등 반응형 칸 배치가
> 그대로 동작함을 확인했다는 뜻. 레이아웃은 Bootstrap, 시각만 KRDS 로 덮어쓴 결과다(§2 역할 분담 참고).

---

## 9. 한계 및 후속 권장

1. **Pretendard GOV 웹폰트 로컬 추가**: 현재는 CDN 미사용 정책에 따라 시스템 폰트로 폴백. 픽셀 정합이
   필요하면 `static/css/fonts/`에 Pretendard GOV 웹폰트를 넣고 `@font-face` 등록.
2. **공식 KRDS 컴포넌트 패키지 교체**: 본 적용은 KRDS *디자인 토큰·가이드 기반 구현*이다. krds.go.kr의
   공식 CSS/컴포넌트 배포본을 도입하면 `krds.css`를 그것으로 교체하고 매핑만 정리하면 된다.
3. **업무 페이지 세부 폴리시**: 게시판·회원·일정 목록의 타이틀/카드/검색바 등 페이지별 미세 다듬기는 점진 적용.
4. **세부 컴포넌트 확장**: KRDS 탭·아코디언·모달·툴팁 등은 필요 페이지에서 점진 적용.
