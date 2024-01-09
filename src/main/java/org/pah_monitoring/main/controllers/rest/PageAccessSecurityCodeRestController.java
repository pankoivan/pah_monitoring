package org.pah_monitoring.main.controllers.rest;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.security_codes.PageAccessSecurityCode;
import org.pah_monitoring.main.services.security_codes.interfaces.PageAccessSecurityCodeService;
import org.pah_monitoring.main.services.security_codes.interfaces.SecurityCodeGenerationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/code-generation")
@PreAuthorize("permitAll()")
public class PageAccessSecurityCodeRestController {

    private final PageAccessSecurityCodeService service;

    private final SecurityCodeGenerationService codeGenerationService;

    @PostMapping
    public PageAccessSecurityCode add(@RequestBody PageAccessSecurityCode code) {
        code = service.save(code, codeGenerationService.generate());
        System.out.println(code);
        return code;
    }

}
