package org.pah_monitoring.main.controllers.rest.main_admin_contacts;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.main_admin_contacts.MainAdminContactSavingDto;
import org.pah_monitoring.main.entities.main_admin_contacts.MainAdminContact;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.UrlValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataDeletionRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.*;
import org.pah_monitoring.main.services.main_admin_contacts.interfaces.MainAdminContactService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/contacts")
@PreAuthorize("permitAll()") // todo: only main admin
public class MainAdminContactRestController {

    private final MainAdminContactService service;

    @PostMapping("/save")
    public MainAdminContact save(@RequestBody @Valid MainAdminContactSavingDto savingDto, BindingResult bindingResult) {
        try {
            service.checkDataValidityForSaving(savingDto, bindingResult);
            return service.save(savingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable("id") String pathId) {
        try {
            service.deleteById(service.parsePathId(pathId));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (DataDeletionServiceException e) {
            throw new DataDeletionRestControllerException(e.getMessage(), e);
        }
    }

}
