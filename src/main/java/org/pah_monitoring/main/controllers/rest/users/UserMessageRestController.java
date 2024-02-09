package org.pah_monitoring.main.controllers.rest.users;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pah_monitoring.main.dto.in.users.messages.UserMessageAddingDto;
import org.pah_monitoring.main.dto.in.users.messages.UserMessageEditingDto;
import org.pah_monitoring.main.dto.out.users.messages.UserMessageOutDto;
import org.pah_monitoring.main.entities.main.users.messages.UserMessage;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.UrlValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.forbidden.NotEnoughRightsRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataDeletionRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.pah_monitoring.main.services.additional.users.interfaces.UserSearchingService;
import org.pah_monitoring.main.services.main.users.messages.interfaces.UserMessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/messages")
@PreAuthorize("isAuthenticated()")
public class UserMessageRestController {

    private final UserMessageService service;

    private final UserSearchingService searchingService;

    @Qualifier("userMessageMapper")
    private final BaseEntityToOutDtoMapper<UserMessage, UserMessageOutDto> userMessageMapper;

    @PostMapping("/add")
    public UserMessageOutDto add(@RequestBody @Valid UserMessageAddingDto addingDto, BindingResult bindingResult) {
        try {
            service.checkAccessRightsForAdding(searchingService.findUserByUserInformationId(addingDto.getRecipientId()));
            service.checkDataValidityForAdding(addingDto, bindingResult);
            return userMessageMapper.map(service.add(addingDto));
        } catch (DataSearchingServiceException | DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/edit")
    public UserMessageOutDto edit(@RequestBody @Valid UserMessageEditingDto editingDto, BindingResult bindingResult) {
        try {
            UserMessage message = service.findById(editingDto.getId());
            service.checkAccessRightsForEditing(searchingService.findUserByUserInformationId(message.getAuthor().getId()));
            service.checkDataValidityForEditing(editingDto, bindingResult);
            return userMessageMapper.map(service.edit(editingDto));
        } catch (DataSearchingServiceException | DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable("id") String pathId) {
        try {
            UserMessage message = service.findById(service.parsePathId(pathId));
            service.checkAccessRightsForDeleting(searchingService.findUserByUserInformationId(message.getAuthor().getId()));
            service.deleteById(message.getId());
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataDeletionServiceException e) {
            throw new DataDeletionRestControllerException(e.getMessage(), e);
        }
    }

}
