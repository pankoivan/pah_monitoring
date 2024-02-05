package org.pah_monitoring.main.controllers.rest.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/messages")
@PreAuthorize("isAuthenticated()")
public class UserMessageRestController {

}
