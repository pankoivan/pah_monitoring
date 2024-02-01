package org.pah_monitoring.main.controllers.rest.hospitals;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.additional.dto.saving.hospitals.HospitalRegistrationRequestAddingDto;
import org.pah_monitoring.main.entities.main.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.UrlValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataDeletionRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalRegistrationRequestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/hospital-registration/requests")
public class HospitalRegistrationRestController {

    private final HospitalRegistrationRequestService service;

    @PostMapping("/add")
    @PreAuthorize("permitAll()")
    public HospitalRegistrationRequest add(@RequestBody @Valid HospitalRegistrationRequestAddingDto addingDto, BindingResult bindingResult) {
        try {
            service.checkDataValidityForAdding(addingDto, bindingResult);
            return service.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('MAIN_ADMINISTRATOR')")
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
