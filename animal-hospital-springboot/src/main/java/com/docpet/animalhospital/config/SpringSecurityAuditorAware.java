package com.docpet.animalhospital.config;

import com.docpet.animalhospital.security.SecurityUtils;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;

/**
 * Implementation of AuditorAware to get the current auditor (user).
 * Returns the current user login or "system" if no user is authenticated.
 * Đảm bảo luôn trả về giá trị hợp lệ, không bao giờ null.
 */
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    private static final Logger log = LoggerFactory.getLogger(SpringSecurityAuditorAware.class);

    @Override
    public Optional<String> getCurrentAuditor() {
        Optional<String> currentUser = SecurityUtils.getCurrentUserLogin();
        if (currentUser.isPresent()) {
            return currentUser;
        }
        // Luôn trả về "system" nếu không có user đăng nhập
        log.debug("No authenticated user found, using 'system' as auditor");
        return Optional.of("system");
    }
}

