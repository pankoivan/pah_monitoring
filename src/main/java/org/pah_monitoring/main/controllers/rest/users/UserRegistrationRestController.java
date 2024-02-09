package org.pah_monitoring.main.controllers.rest.users;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pah_monitoring.main.dto.in.users.users.administrator.AdministratorAddingDto;
import org.pah_monitoring.main.dto.in.users.users.administrator.AdministratorEditingDto;
import org.pah_monitoring.main.dto.in.users.users.administrator.AdministratorSavingDto;
import org.pah_monitoring.main.dto.in.users.users.doctor.DoctorAddingDto;
import org.pah_monitoring.main.dto.in.users.users.doctor.DoctorEditingDto;
import org.pah_monitoring.main.dto.in.users.users.doctor.DoctorSavingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.dto.out.users.users.common.UserOutDto;
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
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
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

@AllArgsConstructor
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

    @Qualifier("administratorMapper")
    private final BaseEntityToOutDtoMapper<Administrator, UserOutDto> administratorMapper;

    @Qualifier("doctorMapper")
    private final BaseEntityToOutDtoMapper<Doctor, UserOutDto> doctorMapper;

    @Qualifier("patientMapper")
    private final BaseEntityToOutDtoMapper<Patient, UserOutDto> patientMapper;

    private final RegistrationSecurityCodeService codeService;

    private final HospitalService hospitalService;

    @PostMapping("/admin")
    public UserOutDto addAdmin(@RequestBody @Valid AdministratorAddingDto addingDto, BindingResult bindingResult) {
        try {
            administratorService.checkDataValidityForAdding(addingDto, bindingResult);
            Administrator administrator = administratorService.add(addingDto);
            codeService.deleteByEmail(administrator.getUserSecurityInformation().getEmail());
            hospitalService.upgrade(administrator.getHospital());
            return administratorMapper.map(administrator);
        } catch (DataValidationServiceException | DataSearchingServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        } catch (DataDeletionServiceException e) {
            throw new DataDeletionRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/doctor")
    public UserOutDto addDoctor(@RequestBody @Valid DoctorAddingDto addingDto, BindingResult bindingResult) {
        try {
            doctorService.checkDataValidityForAdding(addingDto, bindingResult);
            Doctor doctor = doctorService.add(addingDto);
            codeService.deleteByEmail(doctor.getUserSecurityInformation().getEmail());
            return doctorMapper.map(doctor);
        } catch (DataValidationServiceException | DataSearchingServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        } catch (DataDeletionServiceException e) {
            throw new DataDeletionRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/patient")
    public UserOutDto addPatient(@RequestBody @Valid PatientAddingDto addingDto, BindingResult bindingResult) {
        try {
            patientService.checkDataValidityForAdding(addingDto, bindingResult);
            Patient patient = patientService.add(addingDto);
            codeService.deleteByEmail(patient.getUserSecurityInformation().getEmail());
            return patientMapper.map(patient);
        } catch (DataValidationServiceException | DataSearchingServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        } catch (DataDeletionServiceException e) {
            throw new DataDeletionRestControllerException(e.getMessage(), e);
        }
    }

}
