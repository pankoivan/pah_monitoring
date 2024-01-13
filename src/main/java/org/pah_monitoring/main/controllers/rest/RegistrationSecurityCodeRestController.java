package org.pah_monitoring.main.controllers.rest;

import lombok.*;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/security-codes")
public class RegistrationSecurityCodeRestController {

    private final RegistrationSecurityCodeService service;

    @PostMapping("/check")
    public TrueFalseEntity isCodeExists(@RequestBody CheckCode checkCode) {
        System.out.println(checkCode);
        return new TrueFalseEntity(service.existsByStringCode(checkCode.code));
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class CheckCode {

        private String code;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class TrueFalseEntity {

        private boolean isTrue;

    }

}
