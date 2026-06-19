package egovframework.com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * 운영(prod) 프로파일에서 보안 시크릿이 안전하게 설정됐는지 기동 시점에 검증한다.
 *
 * <p>JWT 서명키(EGOV_JWT_SECRET)와 암복호화 키(EGOV_CRYPTO_KEY)가 공개된 기본 placeholder
 * 그대로이거나 권장 길이 미만이면 애플리케이션 기동을 즉시 중단(fail-fast)한다.
 * 기존에는 경고 로그만 남기고 기동되어, 운영 환경에서도 기본키로 동작할 위험이 있었다.</p>
 *
 * <p>dev/local 프로파일에서는 동작하지 않으므로 개발 편의에 영향을 주지 않는다.</p>
 */
@Slf4j
@Configuration
@Profile("prod")
public class ProductionSecretsGuard {

    /** application.properties 에 박혀 있는 기본 placeholder 값들 (운영에서 사용 금지) */
    private static final String DEFAULT_JWT_SECRET = "my-secret-jwt-key-for-egovframe-simple-homepage-project";
    private static final String DEFAULT_CRYPTO_KEY = "egovframe";

    private static final int MIN_JWT_SECRET_LENGTH = 32;
    private static final int MIN_CRYPTO_KEY_LENGTH = 16;

    @Value("${Globals.jwt.secret:}")
    private String jwtSecret;

    @Value("${Globals.crypto.algoritm:}")
    private String cryptoKey;

    @PostConstruct
    public void verify() {
        if (jwtSecret == null || jwtSecret.isBlank()
                || DEFAULT_JWT_SECRET.equals(jwtSecret) || jwtSecret.length() < MIN_JWT_SECRET_LENGTH) {
            throw new IllegalStateException(
                "[보안] 운영 프로파일에서 JWT 시크릿이 기본값이거나 너무 짧습니다. "
                + "환경변수 EGOV_JWT_SECRET 에 " + MIN_JWT_SECRET_LENGTH + "자 이상의 무작위 값을 설정하세요.");
        }
        if (cryptoKey == null || cryptoKey.isBlank()
                || DEFAULT_CRYPTO_KEY.equals(cryptoKey) || cryptoKey.length() < MIN_CRYPTO_KEY_LENGTH) {
            throw new IllegalStateException(
                "[보안] 운영 프로파일에서 암복호화 키가 기본값이거나 너무 짧습니다. "
                + "환경변수 EGOV_CRYPTO_KEY 에 " + MIN_CRYPTO_KEY_LENGTH + "자 이상의 무작위 값을 설정하세요.");
        }
        log.info("[보안] 운영 시크릿 검증 통과 (JWT/Crypto 키가 기본값이 아님).");
    }
}
