package org.pah_monitoring.main.mappers.examinations.schedules;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.examinations.schedules.ExaminationScheduleOutDto;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.schedules.ExaminationSchedule;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.pah_monitoring.main.services.main.examinations.schedules.interfaces.ExaminationScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Component("examinationScheduleMapper")
public class SpecialExaminationScheduleToOutDtoMapper implements BaseEntityToOutDtoMapper<ExaminationSchedule, ExaminationScheduleOutDto> {

    private ExaminationScheduleService service;

    @Override
    @NullWhenNull
    public ExaminationScheduleOutDto map(ExaminationSchedule examinationSchedule) {
        return ExaminationScheduleOutDto
                .builder()
                .id(examinationSchedule.getId())
                .patientId(examinationSchedule.getPatient().getId())
                .indicatorType(examinationSchedule.getIndicatorType())
                .indicatorTypeAlias(examinationSchedule.getIndicatorType().getAlias())
                .schedule(examinationSchedule.getSchedule())
                .build();
    }

    public Map<IndicatorType, ExaminationScheduleOutDto> forPatient(Patient patient) {
        return Stream.of(IndicatorType.values())
                .map(indicatorType -> {
                            Optional<ExaminationSchedule> schedule = service.findConcrete(indicatorType, patient);
                            return ExaminationScheduleOutDto
                                    .builder()
                                    .id(schedule.map(ExaminationSchedule::getId).orElse(null))
                                    .patientId(patient.getId())
                                    .indicatorType(indicatorType)
                                    .indicatorTypeAlias(indicatorType.getAlias())
                                    .schedule(schedule.map(ExaminationSchedule::getSchedule).orElse(null))
                                    .build();
                        }
                )
                .collect(Collectors.toMap(ExaminationScheduleOutDto::getIndicatorType, Function.identity()));
    }

}
