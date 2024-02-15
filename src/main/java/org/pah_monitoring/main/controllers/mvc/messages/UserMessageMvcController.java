package org.pah_monitoring.main.controllers.mvc.messages;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.main.users.users.common.User;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.filtration.enums.messages.DialogueFiltrationProperty;
import org.pah_monitoring.main.filtration.enums.messages.DialogueSortingProperty;
import org.pah_monitoring.main.filtration.enums.messages.DialoguesFiltrationProperty;
import org.pah_monitoring.main.filtration.enums.messages.DialoguesSortingProperty;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.additional.users.interfaces.UserSearchingService;
import org.pah_monitoring.main.services.main.users.messages.interfaces.UserMessageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping("/messages")
@PreAuthorize("isAuthenticated()")
public class UserMessageMvcController {

    private final UserMessageService service;

    private final UserSearchingService searchingService;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    public String getDialoguesPage(Model model, @RequestParam Map<String, String> parameters) {
        EntityFilter.PageStat pageStat = new EntityFilter.PageStat();
        model.addAttribute("dialogues", service.findAllDialogues(parameters, pageStat));
        model.addAttribute("currentPage", pageStat.getCurrentPage());
        model.addAttribute("pagesCount", pageStat.getPagesCount());
        model.addAttribute("filtrationProperties", DialoguesFiltrationProperty.values());
        model.addAttribute("sortingProperties", DialoguesSortingProperty.values());
        pageHeaderService.addHeader(model);
        return "messages/dialogues";
    }

    @GetMapping("/{recipientId}")
    public String getDialoguePage(Model model, @PathVariable("recipientId") String pathRecipientId, @RequestParam Map<String, String> parameters) {
        try {
            User recipient = searchingService.findUserByUserInformationId(service.parsePathId(pathRecipientId));
            service.checkAccessRightsForAdding(recipient);
            model.addAttribute("recipient", recipient);
            EntityFilter.PageStat pageStat = new EntityFilter.PageStat();
            model.addAttribute("dialogue", service.findDialogue(recipient.getUserInformation().getId(), parameters, pageStat));
            model.addAttribute("currentPage", pageStat.getCurrentPage());
            model.addAttribute("pagesCount", pageStat.getPagesCount());
            model.addAttribute("filtrationProperties", DialogueFiltrationProperty.values());
            model.addAttribute("sortingProperties", DialogueSortingProperty.values());
            pageHeaderService.addHeader(model);
            return "messages/dialogue";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
