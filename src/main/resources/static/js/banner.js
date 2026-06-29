/* 배너 슬라이드(캐러셀) + 팝업 — 순수 JS, 외부 라이브러리 없음 */
(function () {
    'use strict';

    /* ===== 캐러셀 ===== */
    function initCarousel(root) {
        var track = root.querySelector('.bnr-track');
        if (!track) return;
        var slides = track.querySelectorAll('.bnr-slide');
        var dots = root.querySelectorAll('.bnr-dot');
        var total = slides.length;
        if (total <= 1) {
            // 슬라이드가 1개 이하면 컨트롤 숨김
            root.querySelectorAll('.bnr-nav, .bnr-dots').forEach(function (el) { el.style.display = 'none'; });
            return;
        }
        var idx = 0;
        var interval = parseInt(root.getAttribute('data-interval'), 10);
        if (isNaN(interval) || interval < 1000) interval = 5000;
        var timer = null;

        function go(n) {
            idx = (n + total) % total;
            track.style.transform = 'translateX(' + (-idx * 100) + '%)';
            dots.forEach(function (d, i) { d.classList.toggle('active', i === idx); });
        }
        function next() { go(idx + 1); }
        function prev() { go(idx - 1); }
        function start() { stop(); timer = setInterval(next, interval); }
        function stop() { if (timer) { clearInterval(timer); timer = null; } }

        var btnNext = root.querySelector('.bnr-next');
        var btnPrev = root.querySelector('.bnr-prev');
        if (btnNext) btnNext.addEventListener('click', function () { next(); start(); });
        if (btnPrev) btnPrev.addEventListener('click', function () { prev(); start(); });
        dots.forEach(function (d, i) { d.addEventListener('click', function () { go(i); start(); }); });

        root.addEventListener('mouseenter', stop);
        root.addEventListener('mouseleave', start);

        go(0);
        start();
    }

    /* ===== 팝업: "오늘 하루 보지 않기"(쿠키 24h) ===== */
    function getCookie(name) {
        var m = document.cookie.match('(^|;)\\s*' + name + '\\s*=\\s*([^;]+)');
        return m ? m.pop() : '';
    }
    function setCookie(name, value, hours) {
        var d = new Date();
        d.setTime(d.getTime() + hours * 60 * 60 * 1000);
        document.cookie = name + '=' + value + ';expires=' + d.toUTCString() + ';path=/';
    }

    function num(v) { var n = parseInt(v, 10); return isNaN(n) ? null : n; }

    function initPopup(overlay) {
        var key = overlay.getAttribute('data-cookie-key') || 'bnrPopupHide';
        if (getCookie(key) === 'Y') {
            if (overlay.parentNode) overlay.parentNode.removeChild(overlay);
            return;
        }

        // 좌표/크기 지정(있으면 .bnr-popup 에 반영)
        var popup = overlay.querySelector('.bnr-popup');
        var left = num(overlay.getAttribute('data-left'));
        var top = num(overlay.getAttribute('data-top'));
        var width = num(overlay.getAttribute('data-width'));
        var height = num(overlay.getAttribute('data-height'));
        if (popup) {
            if (width !== null) popup.style.width = width + 'px';
            if (height !== null) popup.style.height = height + 'px';
            if (left !== null || top !== null) {
                overlay.classList.add('is-positioned');
                if (left !== null) popup.style.left = left + 'px';
                if (top !== null) popup.style.top = top + 'px';
            }
        }

        // 팝업 안에 캐러셀(여러 팝업 묶음)이 있으면 초기화
        var inner = overlay.querySelector('.bnr-carousel');
        if (inner) initCarousel(inner);

        overlay.classList.add('is-open');

        function close(remember) {
            if (remember) {
                var chk = overlay.querySelector('.bnr-popup-today');
                if (chk && chk.checked) setCookie(key, 'Y', 24);
            }
            overlay.classList.remove('is-open');
            if (overlay.parentNode) overlay.parentNode.removeChild(overlay);
        }
        overlay.querySelectorAll('.bnr-popup-close').forEach(function (b) {
            b.addEventListener('click', function () { close(true); });
        });
        // "오늘 하루 보지 않기" 체크 시 즉시 쿠키 저장 + 창 닫기
        overlay.querySelectorAll('.bnr-popup-today').forEach(function (chk) {
            chk.addEventListener('change', function () { if (chk.checked) close(true); });
        });
        // 오버레이 배경 클릭 시 닫기(체크박스 상태 반영). 위치지정(block) 모드에서는 팝업 외부 클릭만.
        overlay.addEventListener('click', function (e) { if (e.target === overlay) close(true); });
    }

    document.addEventListener('DOMContentLoaded', function () {
        // 팝업 내부 캐러셀은 initPopup 에서 별도 초기화하므로 팝업 밖 캐러셀만 우선 초기화
        document.querySelectorAll('.bnr-carousel').forEach(function (c) {
            if (!c.closest('.bnr-popup-overlay')) initCarousel(c);
        });
        document.querySelectorAll('.bnr-popup-overlay').forEach(initPopup);
    });
})();
