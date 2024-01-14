package org.pah_monitoring.main.controllers.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.users.AdministratorSavingDto;
import org.pah_monitoring.main.entities.dto.saving.users.DoctorSavingDto;
import org.pah_monitoring.main.entities.dto.saving.users.PatientSavingDto;
import org.pah_monitoring.main.entities.users.Administrator;
import org.pah_monitoring.main.entities.users.Doctor;
import org.pah_monitoring.main.entities.users.Patient;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.services.users.users.interfaces.AdministratorService;
import org.pah_monitoring.main.services.users.users.interfaces.DoctorService;
import org.pah_monitoring.main.services.users.users.interfaces.PatientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/rest/users/save")
@PreAuthorize("permitAll()")
public class UserRestController {

    private final AdministratorService administratorService;

    private final DoctorService doctorService;

    private final PatientService patientService;

    @PostMapping("/administrator")
    public Administrator saveAdministrator(@RequestBody @Valid AdministratorSavingDto savingDto, BindingResult bindingResult) {
        try {
            administratorService.checkDataValidityForSaving(savingDto, bindingResult);
            return administratorService.save(savingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/doctor")
    public Doctor saveDoctor(@RequestBody @Valid DoctorSavingDto savingDto, BindingResult bindingResult) {
        try {
            doctorService.checkDataValidityForSaving(savingDto, bindingResult);
            return doctorService.save(savingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/patient")
    public Patient savePatient(@RequestBody @Valid PatientSavingDto savingDto, BindingResult bindingResult) {
        try {
            patientService.checkDataValidityForSaving(savingDto, bindingResult);
            return patientService.save(savingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

}
