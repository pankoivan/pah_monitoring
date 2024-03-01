package org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common;

import org.pah_monitoring.main.entities.additional.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.Indicator;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataAddingValidationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InputIndicatorService<T extends Indicator, M> extends IndicatorService<T>, DataAddingValidationService<M> {

    IndicatorType getIndicatorType();

    Optional<LocalDateTime> getLastExaminationDateFor(Patient patient);

    Optional<String> getScheduleFor(Patient patient);

    IndicatorCard getIndicatorCardFor(Patient patient);

    List<T> findAllByPatientId(Integer patientId) throws DataSearchingServiceException;

    List<T> findAllByPatientId(Integer patientId, String period) throws DataSearchingServiceException;

    T add(M addingDto) throws DataSavingServiceException;

}
