package org.pah_monitoring.main.controllers.rest.examinations.schedules;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.dto.in.examinations.schedules.ExaminationScheduleAddingDto;
import org.pah_monitoring.main.dto.in.examinations.schedules.ExaminationScheduleEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.dto.out.examinations.schedules.ExaminationScheduleOutDto;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.schedules.ExaminationSchedule;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.UrlValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.forbidden.NotEnoughRightsRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataDeletionRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.mappers.examinations.schedules.SpecialExaminationScheduleToOutDtoMapper;
import org.pah_monitoring.main.services.main.examinations.schedules.interfaces.ExaminationScheduleService;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/schedules")
@PreAuthorize("isAuthenticated()")
public class ExaminationScheduleRestController {

    private final ExaminationScheduleService service;

    @Qualifier("patientService")
    private final HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    private final SpecialExaminationScheduleToOutDtoMapper examinationScheduleMapper;

    @GetMapping("/get/for/{patientId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public Map<IndicatorType, ExaminationScheduleOutDto> get(@PathVariable("patientId") String pathPatientId) {
        try {
            return examinationScheduleMapper.forPatient(patientService.findById(patientService.parsePathId(pathPatientId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('DOCTOR')")
    public ExaminationScheduleOutDto add(@RequestBody @Valid ExaminationScheduleAddingDto addingDto, BindingResult bindingResult) {
        try {
            service.checkAccessRightsForActions(patientService.findById(addingDto.getPatientId()));
            service.checkDataValidityForAdding(addingDto, bindingResult);
            return examinationScheduleMapper.map(service.add(addingDto));
        } catch (DataSearchingServiceException | DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/edit")
    @PreAuthorize("hasRole('DOCTOR')")
    public ExaminationScheduleOutDto edit(@RequestBody @Valid ExaminationScheduleEditingDto editingDto, BindingResult bindingResult) {
        try {
            ExaminationSchedule schedule = service.findById(editingDto.getId());
            service.checkAccessRightsForActions(schedule.getPatient());
            service.checkDataValidityForEditing(editingDto, bindingResult);
            return examinationScheduleMapper.map(service.edit(editingDto));
        } catch (DataSearchingServiceException | DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public void delete(@PathVariable("id") String pathId) {
        try {
            ExaminationSchedule schedule = service.findById(service.parsePathId(pathId));
            service.checkAccessRightsForActions(schedule.getPatient());
            service.deleteById(schedule.getId());
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataDeletionServiceException e) {
            throw new DataDeletionRestControllerException(e.getMessage(), e);
        }
    }

}
