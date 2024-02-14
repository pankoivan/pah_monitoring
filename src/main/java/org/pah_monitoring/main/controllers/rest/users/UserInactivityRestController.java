package org.pah_monitoring.main.controllers.rest.users;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pah_monitoring.main.dto.in.users.inactivity.DismissalAddingDto;
import org.pah_monitoring.main.dto.in.users.inactivity.PatientInactivityAddingDto;
import org.pah_monitoring.main.dto.in.users.inactivity.SickLeaveAddingDto;
import org.pah_monitoring.main.dto.in.users.inactivity.VacationAddingDto;
import org.pah_monitoring.main.dto.out.users.inactivity.common.InactivityOutDto;
import org.pah_monitoring.main.entities.main.users.inactivity.Dismissal;
import org.pah_monitoring.main.entities.main.users.inactivity.PatientInactivity;
import org.pah_monitoring.main.entities.main.users.inactivity.SickLeave;
import org.pah_monitoring.main.entities.main.users.inactivity.Vacation;
import org.pah_monitoring.main.entities.main.users.inactivity.common.Inactivity;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.entities.main.users.users.common.HospitalEmployee;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.forbidden.NotEnoughRightsRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.pah_monitoring.main.services.additional.users.interfaces.UserSearchingService;
import org.pah_monitoring.main.services.main.users.inactivity.interfaces.common.InactivityService;
import org.pah_monitoring.main.services.main.users.users.interfaces.PatientService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/inactivity/add")
@PreAuthorize("hasAnyRole('ADMINISTRATOR', 'DOCTOR')")
public class UserInactivityRestController {

    @Qualifier("vacationService")
    private final InactivityService<Vacation, VacationAddingDto, HospitalEmployee> vacationService;

    @Qualifier("sickLeaveService")
    private final InactivityService<SickLeave, SickLeaveAddingDto, HospitalEmployee> sickLeaveService;

    @Qualifier("dismissalService")
    private final InactivityService<Dismissal, DismissalAddingDto, HospitalEmployee> dismissalService;

    @Qualifier("patientInactivityService")
    private final InactivityService<PatientInactivity, PatientInactivityAddingDto, Patient> patientInactivityService;

    @Qualifier("inactivityMapper")
    private final BaseEntityToOutDtoMapper<Inactivity, InactivityOutDto> inactivityMapper;

    private final PatientService patientService;

    private final UserSearchingService searchingService;

    @PostMapping("/vacation")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public InactivityOutDto addVacation(@RequestBody @Valid VacationAddingDto addingDto, BindingResult bindingResult) {
        try {
            vacationService.checkAccessRightsForAdding(searchingService.findHospitalEmployeeByEmployeeInformationId(addingDto.getToWhomId()));
            vacationService.checkDataValidityForAdding(addingDto, bindingResult);
            return inactivityMapper.map(vacationService.add(addingDto));
        } catch (DataSearchingServiceException | DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/sick-leave")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public InactivityOutDto addSickLeave(@RequestBody @Valid SickLeaveAddingDto addingDto, BindingResult bindingResult) {
        try {
            sickLeaveService.checkAccessRightsForAdding(searchingService.findHospitalEmployeeByEmployeeInformationId(addingDto.getToWhomId()));
            sickLeaveService.checkDataValidityForAdding(addingDto, bindingResult);
            return inactivityMapper.map(sickLeaveService.add(addingDto));
        } catch (DataSearchingServiceException | DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/dismissal")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public InactivityOutDto addDismissal(@RequestBody @Valid DismissalAddingDto addingDto, BindingResult bindingResult) {
        try {
            dismissalService.checkAccessRightsForAdding(searchingService.findHospitalEmployeeByEmployeeInformationId(addingDto.getToWhomId()));
            dismissalService.checkDataValidityForAdding(addingDto, bindingResult);
            return inactivityMapper.map(dismissalService.add(addingDto));
        } catch (DataSearchingServiceException | DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/patient-inactivity")
    @PreAuthorize("hasRole('DOCTOR')")
    public InactivityOutDto addPatientInactivity(@RequestBody @Valid PatientInactivityAddingDto addingDto, BindingResult bindingResult) {
        try {
            Patient patient = patientService.findById(addingDto.getToWhomId());
            patientInactivityService.checkAccessRightsForAdding(patient);
            patientInactivityService.checkDataValidityForAdding(addingDto, bindingResult);
            patientService.removeFromDoctor(patient);
            return inactivityMapper.map(patientInactivityService.add(addingDto));
        } catch (DataSearchingServiceException | DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

}
