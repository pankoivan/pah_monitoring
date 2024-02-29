package org.pah_monitoring.main.services.main.examinations.indicators.implementations.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.Indicator;
import org.pah_monitoring.main.entities.main.examinations.schedules.ExaminationSchedule;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.InputIndicatorService;
import org.pah_monitoring.main.services.main.examinations.schedules.interfaces.ExaminationScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
public abstract class AbstractInputIndicatorServiceImpl<T, M> extends AbstractIndicatorServiceImpl<T>
        implements InputIndicatorService<T, M> {

    private ExaminationScheduleService scheduleService;

    @Override
    public void checkDataValidityForAdding(M addingDto, BindingResult bindingResult) throws DataValidationServiceException {
        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        if (getExtractionService().patient().hasNoAnamnesis()) {
            throw new DataValidationServiceException(HAS_NO_ANAMNESIS);
        }
        if (getExtractionService().patient().hasNoDoctor()) {
            throw new DataValidationServiceException(HAS_NO_DOCTOR);
        }
    }

    @Override
    public Optional<LocalDateTime> getLastExaminationDateFor(Patient patient) {
        return findAllByPatient(patient)
                .stream()
                .map(Indicator::getDate)
                .max(Comparator.comparing(Function.identity()));
    }

    @Override
    public Optional<String> getScheduleFor(Patient patient) {
        return scheduleService.findConcrete(getIndicatorType(), patient)
                .map(ExaminationSchedule::getSchedule);
    }

    protected abstract List<Indicator> findAllByPatient(Patient patient);

}
