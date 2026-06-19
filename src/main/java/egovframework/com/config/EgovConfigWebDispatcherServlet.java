package egovframework.com.config;

import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * @ClassName : EgovConfigWebDispatcherServlet.java
 * @Description : DispatcherServlet 설정(@Controller 컴포넌트 스캔)
 *
 * <pre>
 * 루트("/")는 EgovMainController 가 처리하므로 별도의 ViewController 매핑을 두지 않는다.
 * </pre>
 */
@Configuration
@ComponentScan(basePackages = "egovframework", excludeFilters = {
	@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Service.class),
	@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Repository.class),
	@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)
})
public class EgovConfigWebDispatcherServlet implements WebMvcConfigurer {

	/**
	 * JSP 원본(egov-com-servlet.xml)의 SimpleMappingExceptionResolver 대응.
	 * 컨트롤러에서 전파된 예외를 cmm/error/* Thymeleaf 뷰로 매핑한다.
	 */
	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
		SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
		Properties mappings = new Properties();
		mappings.setProperty("org.springframework.dao.DataAccessException", "cmm/error/dataAccessFailure");
		mappings.setProperty("org.springframework.transaction.TransactionException", "cmm/error/transactionFailure");
		mappings.setProperty("org.egovframe.rte.fdl.cmmn.exception.EgovBizException", "cmm/error/egovBizException");
		mappings.setProperty("org.springframework.security.access.AccessDeniedException", "cmm/error/accessDenied");
		resolver.setExceptionMappings(mappings);
		resolver.setDefaultErrorView("cmm/error/egovError");
		resolver.setOrder(2);
		resolvers.add(resolver);
	}
}
