package egovframework.com.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName : EgovConfigAppWhitelist.java
 * @Description : whiteList 설정
 *
 * @author : 윤주호
 * @since  : 2021. 7. 20
 * @version : 1.0
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일              수정자               수정내용
 *  -------------  ------------   ---------------------
 *   2021. 7. 20    윤주호               최초 생성
 *   2026.06.17  구재호          Spring Boot + Thymeleaf 전환
 * </pre>
 *
 */
@Configuration
public class EgovConfigAppWhitelist {

	/**
	 * /EgovPageLink.do?linkIndex=N 화이트리스트.
	 * 인덱스 순서는 portal-site-jsp 의 context-whitelist.xml 과 동일하게 유지한다.
	 * (0:민원발급, 1:민원신청, 2:민원결과확인, 3:사이트소개, 4:연혁, 5:조직소개, 6:찾아오시는길, 7:템플릿소개)
	 */
	@Bean
	public List<String> egovPageLinkWhitelist() {
		List<String> whiteList = new ArrayList<String>();
		whiteList.add("main/sample_menu/EgovServiceIssuance");
		whiteList.add("main/sample_menu/EgovServiceManage");
		whiteList.add("main/sample_menu/EgovServiceResult");
		whiteList.add("main/sample_menu/EgovAboutSite");
		whiteList.add("main/sample_menu/EgovHistory");
		whiteList.add("main/sample_menu/EgovOrganization");
		whiteList.add("main/sample_menu/EgovLocation");
		whiteList.add("main/sample_menu/Intro");
		return whiteList;
	}
}
