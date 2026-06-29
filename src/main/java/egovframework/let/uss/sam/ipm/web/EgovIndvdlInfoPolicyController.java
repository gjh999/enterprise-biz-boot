package egovframework.let.uss.sam.ipm.web;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.let.uss.sam.ipm.service.EgovIndvdlInfoPolicyService;
import egovframework.let.uss.sam.ipm.service.IndvdlInfoPolicy;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

/**
 * 개인정보보호정책를 처리하는 Controller Class 구현
 * @author 공통서비스 장동한
 * @since 2009.07.03
 * @version 1.0
 * @see <pre>
 * &lt;&lt; 개정이력(Modification Information) &gt;&gt;
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.07.03  장동한          최초 생성
 *   2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성
 *  2026.06.17  구재호          Spring Boot + Thymeleaf 전환
 *
 * </pre>
 */
@Controller
public class EgovIndvdlInfoPolicyController {

    /** EgovMessageSource */
    @Resource(name = "egovMessageSource")
    EgovMessageSource egovMessageSource;

    /** egovOnlinePollService */
    @Resource(name = "egovIndvdlInfoPolicyService")
    private EgovIndvdlInfoPolicyService egovIndvdlInfoPolicyService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /**
     * 현재 인증 사용자가 ROLE_ADMIN 권한을 보유하는지 확인한다.
     * @return 관리자 여부
     */
    private boolean isAdmin() {
        List<String> authorities = EgovUserDetailsHelper.getAuthorities();
        return authorities != null && authorities.contains("ROLE_ADMIN");
    }

    /**
     * 개인정보보호정책 목록을 조회한다.
     * @param searchVO
     * @param commandMap
     * @param indvdlInfoPolicy
     * @param model
     * @return "/uss/sam/ipm/EgovOnlinePollList"
     * @throws Exception
     */
	@RequestMapping(value = "/uss/sam/ipm/listIndvdlInfoPolicy.do")
    public String EgovIndvdlInfoPolicyList(
            @ModelAttribute("searchVO") ComDefaultVO searchVO, @RequestParam Map <String, Object> commandMap,
            IndvdlInfoPolicy indvdlInfoPolicy, ModelMap model)
            throws Exception {
        // 약관/개인정보 통합 목록으로 일원화(항목7) — 옛 분리 목록 진입점은 통합 목록(유형=개인정보)으로 리다이렉트.
        return "redirect:/uss/sam/terms/list.do?termsType=ipm";
    }

    /**
     * 개인정보보호정책 목록을 상세조회 조회한다.
     * @param searchVO
     * @param indvdlInfoPolicy
     * @param commandMap
     * @param model
     * @return
     *         "/uss/sam/ipm/EgovOnlinePollDetail"
     * @throws Exception
     */
    @RequestMapping(value = "/uss/sam/ipm/detailIndvdlInfoPolicy.do")
    public String EgovIndvdlInfoPolicyDetail(
            @ModelAttribute("searchVO") ComDefaultVO searchVO,
            IndvdlInfoPolicy indvdlInfoPolicy, @RequestParam Map <String, Object> commandMap,
            ModelMap model) throws Exception {

        String sLocationUrl = "uss/sam/ipm/EgovIndvdlInfoPolicyDetail";

        String sCmd = commandMap.get("cmd") == null ? "" : (String) commandMap.get("cmd");

        if (sCmd.equals("del")) {
            // 삭제는 관리자(ROLE_ADMIN)만 가능
            if (!isAdmin()) {
                model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
                return "uat/uia/EgovLoginUsr";
            }
            egovIndvdlInfoPolicyService.deleteIndvdlInfoPolicy(indvdlInfoPolicy);
            sLocationUrl = "redirect:/uss/sam/terms/list.do?termsType=ipm";
        } else {
            IndvdlInfoPolicy indvdlInfoPolicyVO = egovIndvdlInfoPolicyService.selectIndvdlInfoPolicyDetail(indvdlInfoPolicy);
            model.addAttribute("indvdlInfoPolicy", indvdlInfoPolicyVO);
        }

        return sLocationUrl;
    }

    /**
     * 개인정보보호정책를 수정한다.
     * @param searchVO
     * @param commandMap
     * @param indvdlInfoPolicy
     * @param bindingResult
     * @param model
     * @return
     *         "/uss/sam/ipm/EgovOnlinePollUpdt"
     * @throws Exception
     */
    @RequestMapping(value = "/uss/sam/ipm/updtIndvdlInfoPolicy.do")
    public String EgovIndvdlInfoPolicyModify(
            @ModelAttribute("searchVO") ComDefaultVO searchVO,
            @RequestParam Map <String, Object> commandMap,
            @Valid @ModelAttribute("indvdlInfoPolicy") IndvdlInfoPolicy indvdlInfoPolicy,
            BindingResult bindingResult, ModelMap model) throws Exception {
        // 0. Spring Security 사용자권한 처리
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if (!isAuthenticated) {
            model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
            return "uat/uia/EgovLoginUsr";
        }

        // 수정은 관리자(ROLE_ADMIN)만 가능
        if (!isAdmin()) {
            model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
            return "uat/uia/EgovLoginUsr";
        }

        // 로그인 객체 선언
        LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

        String sLocationUrl = "uss/sam/ipm/EgovIndvdlInfoPolicyUpdt";

        String sCmd = commandMap.get("cmd") == null ? "" : (String) commandMap.get("cmd");

        // 초기 페이지 로드 시에는 검증 에러 무시
        if (!"save".equals(sCmd)) {
            bindingResult = new BeanPropertyBindingResult(indvdlInfoPolicy, "indvdlInfoPolicy");
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "indvdlInfoPolicy", bindingResult);
        }

        if (sCmd.equals("save")) {

            if(bindingResult.hasErrors()){
                return sLocationUrl;
            }
            //아이디 설정
            indvdlInfoPolicy.setFrstRegisterId(loginVO.getUniqId());
            indvdlInfoPolicy.setLastUpdusrId(loginVO.getUniqId());

            egovIndvdlInfoPolicyService.updateIndvdlInfoPolicy(indvdlInfoPolicy);
            sLocationUrl = "redirect:/uss/sam/terms/list.do?termsType=ipm";
        } else {
            IndvdlInfoPolicy indvdlInfoPolicyVO = egovIndvdlInfoPolicyService.selectIndvdlInfoPolicyDetail(indvdlInfoPolicy);
            model.addAttribute("indvdlInfoPolicy", indvdlInfoPolicyVO);
            // th:field 가 조회된 VO 값을 렌더링하도록 BindingResult 대상도 조회 VO 로 교체
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "indvdlInfoPolicy",
                    new BeanPropertyBindingResult(indvdlInfoPolicyVO, "indvdlInfoPolicy"));
        }

        return sLocationUrl;
    }

    /**
     * 개인정보보호정책를 등록한다.
     * @param searchVO
     * @param commandMap
     * @param indvdlInfoPolicy
     * @param bindingResult
     * @param model
     * @return
     *         "/uss/sam/ipm/EgovOnlinePollRegist"
     * @throws Exception
     */
    @RequestMapping(value = "/uss/sam/ipm/registIndvdlInfoPolicy.do")
    public String EgovIndvdlInfoPolicyRegist(
            @ModelAttribute("searchVO") ComDefaultVO searchVO,
            @RequestParam Map <String, Object> commandMap,
            @Valid @ModelAttribute("indvdlInfoPolicy") IndvdlInfoPolicy indvdlInfoPolicy,
            BindingResult bindingResult, ModelMap model) throws Exception {
        // 0. Spring Security 사용자권한 처리
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if (!isAuthenticated) {
            model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
            return "uat/uia/EgovLoginUsr";
        }

        // 등록은 관리자(ROLE_ADMIN)만 가능
        if (!isAdmin()) {
            model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
            return "uat/uia/EgovLoginUsr";
        }

        // 로그인 객체 선언
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        String sCmd = commandMap.get("cmd") == null ? "" : (String) commandMap.get("cmd");

        // 통합 등록(항목7)으로 일원화 — 초기 등록폼 진입은 통합 등록(유형=개인정보)으로 리다이렉트.
        if (!"save".equals(sCmd)) {
            return "redirect:/uss/sam/terms/registView.do?termsType=ipm";
        }

        if (bindingResult.hasErrors()) {
            return "redirect:/uss/sam/terms/registView.do?termsType=ipm";
        }
        //아이디 설정
        indvdlInfoPolicy.setFrstRegisterId(loginVO.getUniqId());
        indvdlInfoPolicy.setLastUpdusrId(loginVO.getUniqId());
        //저장
        egovIndvdlInfoPolicyService.insertIndvdlInfoPolicy(indvdlInfoPolicy);
        return "redirect:/uss/sam/terms/list.do?termsType=ipm";
    }


}
