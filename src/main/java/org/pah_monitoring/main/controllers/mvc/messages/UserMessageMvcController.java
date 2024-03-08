package org.pah_monitoring.main.controllers.mvc.messages;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.main.users.info.UserInformation;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.additional.users.interfaces.UserSearchingService;
import org.pah_monitoring.main.services.main.users.info.interfaces.UserInformationService;
import org.pah_monitoring.main.services.main.users.messages.interfaces.UserMessageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
@RequestMapping("/messenger")
@PreAuthorize("isAuthenticated()")
public class UserMessageMvcController {

    private final UserMessageService service;

    private final UserInformationService userInformationService;

    private final UserSearchingService searchingService;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    public String getMessengerPage(Model model, @RequestParam(value = "recipientId", required = false) String pathRecipientId) {
        try {
            model.addAttribute("recipients", service.findAllRecipients());
            if (pathRecipientId != null) {
                UserInformation recipient = userInformationService.findById(userInformationService.parsePathId(pathRecipientId));
                service.checkAccessRightsForAdding(searchingService.findUserByUserInformationId(recipient.getId()));
                model.addAttribute("recipient", recipient);
                model.addAttribute("messages", service.findAllMessagesFor(recipient.getId()));
            } else {
                model.addAttribute("recipient", null);
                model.addAttribute("messages", null);
            }
            pageHeaderService.addHeader(model);
            return "messenger/messenger";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
