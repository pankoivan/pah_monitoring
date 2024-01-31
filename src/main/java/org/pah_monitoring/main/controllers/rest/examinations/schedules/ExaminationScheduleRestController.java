package org.pah_monitoring.main.controllers.rest.examinations.schedules;

import jakarta.validation.Valid;
import lombok.*;
import org.pah_monitoring.main.entities.dto.saving.examinations.schedules.ExaminationScheduleAddingDto;
import org.pah_monitoring.main.entities.dto.saving.examinations.schedules.ExaminationScheduleEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.universal.schedules.ExaminationScheduleUniversalDto;
import org.pah_monitoring.main.entities.enums.IndicatorType;
import org.pah_monitoring.main.entities.examinations.schedules.ExaminationSchedule;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.UrlValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.forbidden.NotEnoughRightsRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.examinations.schedules.interfaces.ExaminationScheduleService;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/schedules")
public class ExaminationScheduleRestController {

    private final ExaminationScheduleService service;

    @Qualifier("patientService")
    private final HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    /*@PostMapping("/check")
    public ScheduleCheckResponse check(@RequestBody ScheduleCheckRequest request) {
        try {
            Patient patient = patientService.findById(request.getPatientId());
            return ScheduleCheckResponse
                    .builder()
                    .indicatorTypeAlias(request.getIndicatorType().getAlias())
                    .schedule(service.findConcrete(request.getIndicatorType(), patient).map(ExaminationSchedule::getSchedule).orElse(null))
                    .build();
        } catch (DataSearchingServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        }
    }*/

    @PostMapping("/add")
    public ExaminationSchedule add(@RequestBody @Valid ExaminationScheduleAddingDto addingDto, BindingResult bindingResult) {
        try {
            service.checkAccessRightsForActions(patientService.findById(addingDto.getPatientId()));
            service.checkDataValidityForAdding(addingDto, bindingResult);
            return service.add(addingDto);
        } catch (DataSearchingServiceException | DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/edit")
    public ExaminationSchedule edit(@RequestBody @Valid ExaminationScheduleEditingDto editingDto, BindingResult bindingResult) {
        try {
            ExaminationSchedule schedule = service.findById(editingDto.getId());
            service.checkAccessRightsForActions(schedule.getPatient());
            service.checkDataValidityForEditing(editingDto, bindingResult);
            return service.edit(editingDto);
        } catch (DataSearchingServiceException | DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/delete/{id}")
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
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @NoArgsConstructor @AllArgsConstructor @Data
    public static class ScheduleCheckRequest {
        private Integer patientId;
        private IndicatorType indicatorType;
    }

    @NoArgsConstructor @AllArgsConstructor @Data @Builder
    public static class ScheduleCheckResponse {
        private String indicatorTypeAlias;
        private String schedule;
    }

    @NoArgsConstructor @AllArgsConstructor @Data @Builder
    public static class Temp {
        private Integer id;
        private Integer patientId;
        private IndicatorType indicatorType;
        private String indicatorTypeAlias;
        private String schedule;
    }

    @GetMapping("/get/for/{patientId}")
    public Map<IndicatorType, Temp> get(@PathVariable("patientId") String pathPatientId) {
        try {

            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            return Stream.of(IndicatorType.values())
                    .map(indicatorType -> {
                                Optional<ExaminationSchedule> schedule = service.findConcrete(indicatorType, patient);
                                return Temp
                                        .builder()
                                        .id(schedule.map(ExaminationSchedule::getId).orElse(null))
                                        .patientId(patient.getId())
                                        .indicatorType(indicatorType)
                                        .indicatorTypeAlias(indicatorType.getAlias())
                                        .schedule(schedule.map(ExaminationSchedule::getSchedule).orElse(null))
                                        .build();
                            }
                    )
                    .collect(Collectors.toMap(Temp::getIndicatorType, Function.identity()));

        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        }
    }

}
