#!/usr/bin/env bash
# KRDS 자체검증 1차 자동 점검 — 페이지별 HTTP/오류/레거시/KRDS 마크업 전수검사
# 사용법: bash krds-verify.sh [BASE_URL]   (기본 http://localhost:28080 — enterprise-biz-boot)
# 사전: 서버 기동 + admin/1 계정.
# ⚠️ 주의: 아래 로그인/페이지 URL은 simple-home-boot(JWT, REST URL) 기준이다.
#    enterprise-biz-boot는 세션 로그인(POST /uat/uia/actionSecurityLogin.do)·eGov *.do URL이므로
#    비즈 검증 시 로그인 절차·chk URL 목록을 비즈 URL로 교체해야 한다(다음 배치).
set -u
BASE="${1:-http://localhost:28080}"
JAR="$(mktemp)"; trap 'rm -f "$JAR"' EXIT

# 로그인(JWT 쿠키). 실패해도 공개 페이지는 점검 가능.
curl -s -c "$JAR" -b "$JAR" -o /dev/null -X POST -d "id=admin" -d "password=1" --max-time 15 "$BASE/login" || true

# 클래스 경계 인식(krds-* 접두 오탐 방지). 랜딩/로그인의 의도적 커스텀 .btn/.login-*은 대상 아님.
LEGACY='egov-table|btn-outline|alert-dismissible|[" ]card[" ]|[" ]form-control[" ]|[" ]form-select[" ]|[" ]badge[" ]'
# 예외(E): JS 검증이 참조하는 invalid-feedback, 커스텀 로그인의 input-group — 하드 실패 아님
EXCEPT='invalid-feedback|input-group'
KRDS='krds-panel|krds-table-wrap|table class="tbl"|krds-input|krds-btn|krds-badge|krds-pagination|form-group'

pass=0; fail=0; exc=0
chk() { # url  label  [accept]
  local url="$1" label="$2" acc="${3:-text/html}"
  local body code legacy krds err except
  body="$(curl -s -b "$JAR" -H "Accept: $acc" --max-time 15 "$BASE$url")"
  code="$(curl -s -b "$JAR" -H "Accept: $acc" -o /dev/null -w '%{http_code}' --max-time 15 "$BASE$url")"
  err="$(printf '%s' "$body" | grep -cE 'TemplateInputException|TemplateProcessingException|Whitelabel Error|StackTrace')"
  legacy="$(printf '%s' "$body" | grep -cE "$LEGACY")"
  except="$(printf '%s' "$body" | grep -cE "$EXCEPT")"
  krds="$(printf '%s' "$body" | grep -cE "$KRDS")"
  local mark="OK"
  if [ "$code" != "200" ] || [ "$err" != "0" ] || [ "$legacy" != "0" ]; then mark="FAIL"; fail=$((fail+1));
  elif [ "$except" != "0" ]; then mark="E"; exc=$((exc+1));
  else pass=$((pass+1)); fi
  printf "[%-4s] %-26s HTTP=%s 오류=%s 레거시=%s 예외=%s KRDS=%s\n" "$mark" "$label" "$code" "$err" "$legacy" "$except" "$krds"
}

echo "== KRDS 자체검증 ($BASE) =="
# 공개
chk "/"            "랜딩(홈)"
chk "/krds-sample" "KRDS 예시"
chk "/login"       "로그인"
chk "/register"    "회원가입"
# 업무(로그인)
chk "/portal"      "포털 대시보드"
chk "/bbs/BBSMSTR_AAAAAAAAAAAA/list"   "게시판 목록"
chk "/bbs/BBSMSTR_AAAAAAAAAAAA/write"  "게시판 작성"
chk "/schedule"    "일정 목록"
chk "/schedule/write" "일정 작성"
chk "/member/list" "회원 목록"
chk "/bbs/master/list" "게시판 마스터"
chk "/bbs/use/list"    "게시판 사용정보"
chk "/admin/password"  "관리자 비밀번호"
chk "/mypage"      "마이페이지"
echo "== 결과: PASS=$pass  E(예외)=$exc  FAIL=$fail =="
[ "$fail" = "0" ]
