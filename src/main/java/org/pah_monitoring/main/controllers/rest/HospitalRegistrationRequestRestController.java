package org.pah_monitoring.main.controllers.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.HospitalRegistrationRequestSavingDto;
import org.pah_monitoring.main.entities.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalRegistrationRequestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/hospitals/registration-request")
@PreAuthorize("permitAll()")
public class HospitalRegistrationRequestRestController {

    private final HospitalRegistrationRequestService service;

    @PostMapping
    public HospitalRegistrationRequest save(@Valid HospitalRegistrationRequestSavingDto requestDto, BindingResult bindingResult) {
        try {
            service.checkDataValidityForSaving(requestDto, bindingResult);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        }
        try {
            return service.save(requestDto);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

}
