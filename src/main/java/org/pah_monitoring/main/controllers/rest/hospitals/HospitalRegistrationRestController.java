package org.pah_monitoring.main.controllers.rest.hospitals;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.hospitals.HospitalRegistrationRequestSavingDto;
import org.pah_monitoring.main.entities.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.UrlValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataDeletionRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.*;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalRegistrationRequestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/hospital-registration/requests")
@PreAuthorize("permitAll()") // todo: remove
public class HospitalRegistrationRestController {

    private final HospitalRegistrationRequestService service;

    @PostMapping("/add") // todo: for all
    public HospitalRegistrationRequest add(@RequestBody @Valid HospitalRegistrationRequestSavingDto requestDto, BindingResult bindingResult) {
        try {
            service.checkDataValidityForSaving(requestDto, bindingResult);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        }
        try {
            return service.add(requestDto);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/delete/{id}") // todo: only for main admin
    public void delete(@PathVariable("id") String pathId) {
        HospitalRegistrationRequest request;
        try {
            request = service.findById(service.parsePathId(pathId));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        }
        try {
            service.checkDataValidityForDeleting(request);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        }
        try {
            service.deleteById(request.getId());
        } catch (DataDeletionServiceException e) {
            throw new DataDeletionRestControllerException(e.getMessage(), e);
        }
    }

}
