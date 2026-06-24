/*
 * ============================================================
 * krds-compat.js — Bootstrap 번들 제거에 따른 동작 호환 레이어
 * 템플릿에 남아 있는 data-bs-* 동작을 바닐라 JS로 대체한다.
 *  - data-bs-toggle="dropdown"  : 드롭다운 메뉴 토글(.dropdown-menu.show)
 *  - data-bs-toggle="collapse"  : 모바일 메뉴 등 콜랩스 토글(.collapse.show)
 *  - data-bs-dismiss="alert"    : 알림 닫기
 * 공식 krds.min.js 와 함께 로드된다.
 * ============================================================ */
'use strict';
(function () {
  function closeAllDropdowns(except) {
    document.querySelectorAll('.dropdown-menu.show').forEach(function (m) {
      if (m !== except) m.classList.remove('show');
    });
  }

  document.addEventListener('click', function (e) {
    var toggle = e.target.closest('[data-bs-toggle="dropdown"]');
    if (toggle) {
      e.preventDefault();
      var menu = toggle.parentElement.querySelector('.dropdown-menu');
      var open = menu && menu.classList.contains('show');
      closeAllDropdowns(menu);
      if (menu) {
        menu.classList.toggle('show', !open);
        toggle.setAttribute('aria-expanded', String(!open));
      }
      return;
    }

    var col = e.target.closest('[data-bs-toggle="collapse"]');
    if (col) {
      e.preventDefault();
      var sel = col.getAttribute('data-bs-target') || col.getAttribute('href');
      if (sel) {
        var target = document.querySelector(sel);
        if (target) {
          var shown = target.classList.toggle('show');
          col.setAttribute('aria-expanded', String(shown));
        }
      }
      return;
    }

    var dismiss = e.target.closest('[data-bs-dismiss="alert"]');
    if (dismiss) {
      e.preventDefault();
      var alert = dismiss.closest('.alert');
      if (alert) alert.remove();
      return;
    }

    // 바깥 클릭 시 열린 드롭다운 닫기
    if (!e.target.closest('.dropdown-menu')) closeAllDropdowns(null);
  });

  // ESC 로 드롭다운 닫기(접근성)
  document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape') closeAllDropdowns(null);
  });
})();
