package egovframework.com.cmm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * KRDS(디지털정부 표준 디자인시스템) 컴포넌트 사용 예시 페이지.
 * 표·탭·아코디언·버튼·배지·폼 등 KRDS 네이티브 마크업 참고용 정적 화면.
 * 인증 불필요(SecurityConfig anyRequest permitAll). 뷰명은 앞 슬래시 없이 반환.
 */
@Controller
public class EgovKrdsSampleController {

	@GetMapping("/cmm/krds/EgovKrdsSample.do")
	public String krdsSample() {
		return "cmm/krds/EgovKrdsSample";
	}
}
