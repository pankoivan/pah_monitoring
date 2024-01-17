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
    public HospitalRegistrationRequest add(@RequestBody @Valid HospitalRegistrationRequestSavingDto savingDto, BindingResult bindingResult) {
        try {
            service.checkDataValidityForSaving(savingDto, bindingResult);
            return service.add(savingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/delete/{id}") // todo: only for main admin
    public void delete(@PathVariable("id") String pathId) {
        try {
            int id = service.parsePathId(pathId);
            service.checkDataValidityForDeleting(service.findById(id));
            service.deleteById(id);
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataDeletionServiceException e) {
            throw new DataDeletionRestControllerException(e.getMessage(), e);
        }
    }

}
