package org.pah_monitoring.main.services.main.examinations.indicators.implementations.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.main.examinations.schedules.ExaminationSchedule;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.InputIndicatorService;
import org.pah_monitoring.main.services.main.examinations.schedules.interfaces.ExaminationScheduleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
public abstract class AbstractInputIndicatorServiceImpl<T, M, N, R> extends AbstractIndicatorServiceImpl<T, M>
        implements InputIndicatorService<T, M, N, R> {

    private ExaminationScheduleService scheduleService;

    @Override
    public Optional<LocalDateTime> getLastExaminationDateFor(Patient patient) {
        return findAllByPatient(patient)
                .stream()
                .map(InputIndicator::getDate)
                .max(Comparator.comparing(Function.identity()));
    }

    @Override
    public Optional<String> getScheduleFor(Patient patient) {
        return scheduleService.findConcrete(getIndicatorType(), patient)
                .map(ExaminationSchedule::getSchedule);
    }

    @Override
    public List<N> forTables(List<T> list) {
        return list
                .stream()
                .map(this::toTablesDto)
                .toList();
    }

    @Override
    public List<R> forGraphics(List<T> list) {
        return list
                .stream()
                .map(this::toGraphicsDto)
                .toList();
    }

    protected abstract List<InputIndicator> findAllByPatient(Patient patient);

    protected abstract N toTablesDto(T entity);

    protected abstract R toGraphicsDto(T entity);

}
