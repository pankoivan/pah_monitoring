package org.pah_monitoring.main.controllers.rest.registration;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.users.AdministratorSavingDto;
import org.pah_monitoring.main.entities.dto.saving.users.DoctorSavingDto;
import org.pah_monitoring.main.entities.dto.saving.users.PatientSavingDto;
import org.pah_monitoring.main.entities.users.Administrator;
import org.pah_monitoring.main.entities.users.Doctor;
import org.pah_monitoring.main.entities.users.Patient;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataDeletionRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.users.users.interfaces.AdministratorService;
import org.pah_monitoring.main.services.users.users.interfaces.DoctorService;
import org.pah_monitoring.main.services.users.users.interfaces.PatientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/registration")
@PreAuthorize("permitAll()")
public class RegistrationRestController {

    private final AdministratorService administratorService;

    private final DoctorService doctorService;

    private final PatientService patientService;

    private final RegistrationSecurityCodeService codeService;

    private final HospitalService hospitalService;

    @PostMapping("/admin")
    public Administrator addAdmin(@RequestBody @Valid AdministratorSavingDto savingDto, BindingResult bindingResult) {
        try {
            administratorService.checkDataValidityForSaving(savingDto, bindingResult);
            Administrator administrator = administratorService.add(savingDto);
            codeService.deleteByEmail(administrator.getUserSecurityInformation().getEmail());
            hospitalService.upgrade(administrator.getEmployeeInformation().getHospital());
            return administrator;
        } catch (DataValidationServiceException | DataSearchingServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        } catch (DataDeletionServiceException e) {
            throw new DataDeletionRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/doctor")
    public Doctor addDoctor(@RequestBody @Valid DoctorSavingDto savingDto, BindingResult bindingResult) {
        try {
            doctorService.checkDataValidityForSaving(savingDto, bindingResult);
            Doctor doctor = doctorService.add(savingDto);
            codeService.deleteByEmail(doctor.getUserSecurityInformation().getEmail());
            return doctor;
        } catch (DataValidationServiceException | DataSearchingServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        } catch (DataDeletionServiceException e) {
            throw new DataDeletionRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/patient")
    public Patient addPatient(@RequestBody @Valid PatientSavingDto savingDto, BindingResult bindingResult) {
        try {
            patientService.checkDataValidityForSaving(savingDto, bindingResult);
            Patient patient = patientService.add(savingDto);
            codeService.deleteByEmail(patient.getUserSecurityInformation().getEmail());
            return patient;
        } catch (DataValidationServiceException | DataSearchingServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        } catch (DataDeletionServiceException e) {
            throw new DataDeletionRestControllerException(e.getMessage(), e);
        }
    }

}
