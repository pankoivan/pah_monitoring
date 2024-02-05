package org.pah_monitoring.main.controllers.rest.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.dto.in.users.users.adding.AdministratorAddingDto;
import org.pah_monitoring.main.dto.in.users.users.adding.DoctorAddingDto;
import org.pah_monitoring.main.dto.in.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.editing.AdministratorEditingDto;
import org.pah_monitoring.main.dto.in.users.users.editing.DoctorEditingDto;
import org.pah_monitoring.main.dto.in.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.saving.AdministratorSavingDto;
import org.pah_monitoring.main.dto.in.users.users.saving.DoctorSavingDto;
import org.pah_monitoring.main.dto.in.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.main.users.users.Administrator;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataDeletionRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.main.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/registration")
@PreAuthorize("permitAll()")
public class UserRegistrationRestController {

    @Qualifier("administratorService")
    private final HospitalUserService<Administrator, AdministratorAddingDto, AdministratorEditingDto, AdministratorSavingDto> administratorService;

    @Qualifier("doctorService")
    private final HospitalUserService<Doctor, DoctorAddingDto, DoctorEditingDto, DoctorSavingDto> doctorService;

    @Qualifier("patientService")
    private final HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    private final RegistrationSecurityCodeService codeService;

    private final HospitalService hospitalService;

    @PostMapping("/admin")
    public Administrator addAdmin(@RequestBody @Valid AdministratorAddingDto addingDto, BindingResult bindingResult) {
        try {
            administratorService.checkDataValidityForAdding(addingDto, bindingResult);
            Administrator administrator = administratorService.add(addingDto);
            codeService.deleteByEmail(administrator.getUserSecurityInformation().getEmail());
            hospitalService.upgrade(administrator.getHospital());
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
    public Doctor addDoctor(@RequestBody @Valid DoctorAddingDto addingDto, BindingResult bindingResult) {
        try {
            doctorService.checkDataValidityForAdding(addingDto, bindingResult);
            Doctor doctor = doctorService.add(addingDto);
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
    public Patient addPatient(@RequestBody @Valid PatientAddingDto addingDto, BindingResult bindingResult) {
        try {
            patientService.checkDataValidityForAdding(addingDto, bindingResult);
            Patient patient = patientService.add(addingDto);
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
