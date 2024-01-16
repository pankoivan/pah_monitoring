package org.pah_monitoring.main.controllers.rest.security_codes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/security-codes")
@PreAuthorize("permitAll()") // todo: remove
public class RegistrationSecurityCodeRestController {

    private final RegistrationSecurityCodeService service;

    @PostMapping("/check") // todo: for all
    public TrueFalseEntity isCodeExists(@RequestBody CheckCode checkCode) {
        return new TrueFalseEntity(service.existsByStringUuid(checkCode.code));
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CheckCode {

        private String code;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class TrueFalseEntity {

        private boolean isTrue;

    }

}
