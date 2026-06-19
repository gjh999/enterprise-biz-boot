package egovframework.com.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.egovframe.rte.fdl.property.impl.EgovPropertyServiceImpl;

/**
 * @ClassName : EgovConfigAppProperties.java
 * @Description : Properties 설정
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
public class EgovConfigAppProperties {
	
	@Value("${Globals.fileStorePath}")
	private String fileStorePath;

	@Value("${Globals.addedOptions}")
	private String addedOptions;

	@Value("${Globals.pageUnit}")
	private String pageUnit;
	@Value("${Globals.pageSize}")
	private String pageSize;
	@Value("${Globals.posblAtchFileSize}")
	private String posblAtchFileSize;
	
	
	@Bean(destroyMethod = "destroy")
	public EgovPropertyServiceImpl propertiesService() {
		EgovPropertyServiceImpl egovPropertyServiceImpl = new EgovPropertyServiceImpl();

		Map<String, String> properties = new HashMap<String, String>();
		properties.put("Globals.pageUnit", pageUnit);
		properties.put("Globals.pageSize", pageSize);
		properties.put("Globals.posblAtchFileSize", posblAtchFileSize);
		properties.put("Globals.fileStorePath", fileStorePath);
		properties.put("Globals.addedOptions", addedOptions);
		// 포털 컨트롤러는 접두어 없는 키(pageUnit/pageSize 등)로도 조회하므로 함께 등록
		properties.put("pageUnit", pageUnit);
		properties.put("pageSize", pageSize);
		properties.put("posblAtchFileSize", posblAtchFileSize);
		properties.put("fileStorePath", fileStorePath);
		properties.put("addedOptions", addedOptions);

		egovPropertyServiceImpl.setProperties(properties);
		return egovPropertyServiceImpl;
	}
}
