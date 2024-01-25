package org.pah_monitoring.main.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.users.users.MainAdministrator;
import org.pah_monitoring.main.entities.users.users.common.interfaces.User;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.auxiliary.users.interfaces.UserSearchingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
@PreAuthorize("permitAll()")
public class UserRedirectController {

    private final UserSearchingService service;

    @GetMapping("/{id}")
    public String redirect(@PathVariable("id") String pathId) throws DataSearchingServiceException {
        User user = service.findUserByUserInformationId(Integer.parseInt(pathId));
        // todo:
    }

}
