package org.pah_monitoring.main.controllers.rest.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.users.info.editing.UserSecurityInformationEditingDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/user-info-editing")
@PreAuthorize("isAuthenticated()")
public class UserInfoEditingRestController {

    /*@PostMapping("/security-user-info")
    public String editSecurityUserInfo(@RequestBody @Valid UserSecurityInformationEditingDto editingDto) {

    }*/

    //@PostMapping("/common-user-info/{id}")

    //@PostMapping("/hospital-employee-user-info/{id}")

}
