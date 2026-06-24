---
name: krds-conversion
description: >-
  eGovFrame/Thymeleaf(또는 일반 HTML) 화면을 KRDS(디지털정부 표준 디자인시스템) 네이티브
  마크업으로 전환하고 자체 검증 체크리스트로 점검할 때 사용한다. Bootstrap/레거시 클래스를
  KRDS 클래스(krds-btn·table.tbl·form-group·krds-input·krds-badge·krds-panel 등)로
  바꾸는 매핑 규칙, 토큰 크기 정규화, 폰트 로딩, 접근성·체크리스트 전수검사 절차를 포함한다.
  "KRDS 적용/전환", "디자인시스템 점검", "UI 표준화", "krds" 요청 시 호출.
---

# KRDS 전환 & 자체검증 스킬

행정안전부 **KRDS(Korea Design System, https://www.krds.go.kr)** 로 화면을 전환·점검하는 절차.
근거 문서: `Docs/krds-uiux-가이드라인(2025.08)-요약.md`, `Docs/krds-uiux-자체검증-체크리스트-요약.md`
(전문: 동일 폴더 `krds-uiux-가이드라인(2025.08).md`, `krds-uiux-자체검증-체크리스트.md`),
적용 이력: `Docs/krds-적용-가이드.md`.

## 0. 핵심 원칙
1. **Bootstrap 프레임워크 미사용**. 공식 KRDS(`static/krds/`) + 호환 레이어(`krds-compat.css`)만 사용.
   - 호환 레이어는 **그리드(row/col-*)·유틸리티·GNB**의 기반으로 **유지**한다. 컴포넌트(card/btn/table/form/badge)는 KRDS 네이티브로 대체.
2. **로컬 자원만**(CDN 금지). `SecurityConfig`/`WebMvcConfig`에 `/krds/**` 허용 필수.
3. KRDS 토큰은 **root 10px(62.5%)** 기준 → 프로젝트 root 16px이면 1.6배 커짐 → `krds.css`에서 `!important` 정규화(아래 §4). **root는 16px 유지**(호환 페이지 보호).
4. 로직(`th:*`/`sec:*`/`layout:*`)·`name`/`id`/`action`/URL은 **절대 변경 금지**. **시각 클래스만** 교체.

## 0-1. Bootstrap Icons는 "의도적 유지" (잔재 아님)
- **Bootstrap 프레임워크**(`bootstrap.min.css`·`bootstrap.bundle.min.js`)는 **완전 제거**됨.
- **Bootstrap Icons**(`bootstrap-icons.min.css` + `fonts/bootstrap-icons.woff/woff2`)는 **프레임워크가 아니라 독립 아이콘 폰트**로, `bi-*` 아이콘(약 140곳 사용)의 소스로 **의도적으로 유지**한다.
- 이유: KRDS 자체 svg 아이콘(`ico-*`)이 본 환경에서 불안정(빈 박스) → 안정적인 `bi-*` 채택. 아이콘 추가 시 `bi-*` 사용.
- 즉 "Bootstrap 흔적 제거"는 **프레임워크 기준 달성**, 아이콘 폰트는 별개 선택. (CLAUDE.md에도 명시)

## 1. 자산 로드 순서 (레이아웃 `<head>`)
```
bootstrap-icons.min.css  (아이콘 폰트만; 프레임워크 아님)
/krds/resources/cdn/krds.min.css   (공식, 1,835 토큰)
common.css → krds-compat.css → krds.css   (krds.css 최종 권위)
```
JS: `krds.min.js`(탭·아코디언) → `krds-compat.js`(data-bs-* 대체) → `krds.js` → `common.js`.
폰트: **Pretendard GOV** woff2 **preload** + `@font-face{font-display:optional}`(클릭 후 글꼴 교체 깜빡임 제거).

## 2. 클래스 매핑 규칙 (전환 시 정확히 적용)
| 레거시(Bootstrap) | KRDS 네이티브 |
|---|---|
| `card` / `card-body` / `card-header` / `card-footer` | `krds-panel` / `krds-panel-body` / `krds-panel-head` / `krds-panel-body`(+상단 보더 인라인) |
| `table.egov-table`(목록) | `div.krds-table-wrap > table.tbl` + `colgroup`+`caption`+ `thead th[scope=col]` |
| 정의형 표(라벨-값) | `table.tbl.col` + `caption` + 라벨 `th[scope=row]` |
| `btn btn-primary` | `krds-btn primary` |
| `btn-outline-primary` | `krds-btn secondary` |
| `btn-outline-secondary`/`btn-secondary` | `krds-btn tertiary` |
| `btn-outline-danger`/`btn-danger` | `krds-btn danger` (보조 클래스, krds.css 정의) |
| `btn-sm`/`btn-lg` | 뒤에 `small`/`large` 추가 (예: `krds-btn tertiary small`) |
| 입력 묶음 `mb-3`/`col` | `form-group`(여러 개는 `fieldset`로 감쌈) |
| `label.form-label` | `div.form-tit > label[for]` |
| `input/textarea.form-control` | `krds-input` (`div.form-conts`로 감쌈) |
| `select.form-select` | `krds-form-select` |
| `div.form-text` | `p.form-hint` |
| 필수표시 `span.text-danger` | `span.frm-rq` |
| `div.invalid-feedback` | **삭제**(native validation), `<form novalidate>`의 `novalidate` 제거 |
| `span.badge.bg-*` | `span.krds-badge.bg-*` (`bg-secondary`→`bg-gray`; danger/warning/success 유지) |
| 페이지네이션 | `krds-pagination > .page-navi.prev + .page-links > a.page-link(.active) + .page-navi.next` |
| `alert alert-dismissible fade show` | `alert alert-success/danger`(닫기 `btn-close`+`data-bs-dismiss=alert`+`title=닫기`) |

- **그대로 두는 것**: `container-fluid`, `row`, `col-*`, `d-flex`, `justify-content-*`, `gap-*`, 여백(`mt-3`/`mb-2`/`px-4`), `text-center`, `text-muted`, `small`, `w-100`, 라디오 `form-check*`, `input-group`(호환 레이어 지원).
- 아이콘 `<i class="bi …">` 유지하되 마진 클래스(`me-1`)는 제거하고 텍스트와 공백 한 칸.

## 3. 페이지 유형별 패턴
- **목록**: page-title-bar(브레드크럼+h2) → 검색폼(`search-bar krds-search` + form-group + `krds-btn primary medium`) → 전체건수 바 → `krds-table-wrap`+`tbl` → `krds-pagination` → 하단 `krds-btn`.
- **상세**: `krds-panel`(head=제목/메타, body=본문, body=버튼바). 라벨-값은 `tbl.col`.
- **폼**: `krds-panel > krds-panel-body > form > fieldset > form-group*`. 하단 좌(취소 tertiary)/우(저장 primary).
- **공개 페이지(랜딩/샘플)**: `layouts/public.html` decorate(공통 헤더/푸터). **업무 페이지**: `layouts/default.html`(헤더+GNB+푸터 프래그먼트).

## 4. 토큰 크기 정규화 (krds.css, `!important`)
컴포넌트가 과대해지면 `krds.css`의 정규화 블록에서 조정: `body{font-size:1rem;line-height:1.6}`,
`.krds-btn`(+size 변형), `.krds-input/.krds-form-select`, `.tbl th/td`, `.krds-breadcrumb`, `.krds-pagination .page-link`,
`.btn-tab`, `.btn-accordion`/`.accordion-body`, `.page-title-bar`. 신규 컴포넌트도 동일 패턴으로 균형 맞춤.

## 5. 자체검증 전수검사
1. 서버 기동 후 `scripts/krds-verify.sh`(이 스킬 폴더) 실행 → 페이지별 **HTTP 200 / Thymeleaf 오류 0 / 레거시 클래스 잔존 0 / KRDS 마크업 존재**를 자동 점검.
2. 체크리스트 요약(`Docs/krds-uiux-자체검증-체크리스트-요약.md`)의 A~E 항목을 화면별 **P/F/E/N/A** 판정.
3. 접근성: skip-nav 존재, `:focus-visible` 링, 표 `caption/scope`, 폼 `label`, 명도 대비, 아이콘 텍스트 레이블.
4. 미준수(F)는 §2 매핑으로 수정 후 재검사. 결과를 표로 보고.

## 5-1. GNB(메인 메뉴) 설계 결정
- KRDS 공식 GNB(`krds-main-menu`)는 **대형 포털용 메가메뉴**(PC+모바일 다단/배너 패널 + KRDS 메가메뉴 JS + `svg-icon` 의존, 수백 줄)다.
- 본 프로젝트의 GNB는 항목 6개 수준의 **경량 단순 메뉴** → 메가메뉴는 과대적합·고위험(헤드리스 상호작용 검증 불가, svg-icon 불안정)이라 **미채택**.
- 대신 현 GNB(`egov-nav navbar`, **KRDS Primary 색·접근성 roles·반응형 collapse·드롭다운 compat JS**)를 유지하고
  KRDS 메인메뉴 관례(활성/호버 하단 인디케이터, `:focus-visible` 링, `aria-current="page"`)를 `krds.css`에 추가해 정합.
- 대형 포털로 확장 시에만 `static/krds/html/code/header.html`의 공식 메가메뉴 마크업+`krds.min.js`로 교체 검토.

## 6. 흔한 함정
- KRDS `ico-*` svg 아이콘 클래스는 불안정 → **Bootstrap Icons `bi-*`** 사용.
- 호환 `.collapse{display:none}` 같은 광역 규칙은 KRDS 아코디언(max-height)과 충돌 → 제거.
- 폰트는 로컬에 있어도 FOUT 발생 → **preload + font-display:optional**.
- S3 등 외부 이미지: `curl 200`만으로 정상 판단 금지(CORS 미설정 시 Canvas 렌더 실패) — 실제 화면 확인.
## ⭐ KRDS 최우선 원칙 (필수)
디자인/UI 적용·수정 시 **KRDS를 최우선으로 적용**한다. 부트스트랩식 마크업을 호환 레이어로 덧대 패치하기보다
**KRDS 네이티브 컴포넌트·클래스를 우선** 사용한다.
- 새 화면/수정은 먼저 KRDS 네이티브(`krds-*`, `table.tbl`, `form-group/form-conts`, `krds-input`, `krds-form-select`,
  `krds-btn`, `krds-badge`, `krds-panel`, `krds-pagination`)로 구현. 그리드·유틸·GNB 등 KRDS 미제공 구조만 `krds-compat.css` 유지.
- **KRDS가 이미 정의한 클래스명을 부트스트랩식 용도로 재사용 금지.** 예: `.input-group` 은 KRDS 옵션그룹(세로,
  `flex-direction:column`) 컴포넌트라, 부트스트랩식 가로 입력그룹(입력+버튼)에 그대로 쓰면 충돌해 세로로 깨진다.
  → 별도 클래스(`egov-input-group` 등)나 KRDS 네이티브 인라인 패턴(`form-conts` 내 배치)을 쓴다.
- 색·간격·타이포는 KRDS 토큰 우선. 부득이 호환 보정 시에도 KRDS 룩앤필을 따른다.
