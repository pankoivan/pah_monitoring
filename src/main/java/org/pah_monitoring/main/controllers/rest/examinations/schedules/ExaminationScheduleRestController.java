package org.pah_monitoring.main.controllers.rest.examinations.schedules;

import lombok.*;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.universal.schedules.ExaminationScheduleUniversalDto;
import org.pah_monitoring.main.entities.enums.IndicatorType;
import org.pah_monitoring.main.entities.examinations.schedules.ExaminationSchedule;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.forbidden.NotEnoughRightsRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.examinations.schedules.interfaces.ExaminationScheduleService;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/schedules")
public class ExaminationScheduleRestController {

    private final ExaminationScheduleService service;

    @Qualifier("patientService")
    private final HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @PostMapping("/check")
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
    }

    @PostMapping("/save")
    public ExaminationSchedule save(@RequestBody ExaminationScheduleUniversalDto universalDto) {
        try {
            service.checkAccessRightsForAnyAction(patientService.findById(universalDto.getPatientId()));
            return service.save(universalDto);
        } catch (DataSearchingServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/delete")
    public void delete(@RequestBody ExaminationScheduleUniversalDto universalDto) {
        try {
            service.checkAccessRightsForAnyAction(patientService.findById(universalDto.getPatientId()));
            service.delete(universalDto);
        } catch (DataSearchingServiceException e) {
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

}
