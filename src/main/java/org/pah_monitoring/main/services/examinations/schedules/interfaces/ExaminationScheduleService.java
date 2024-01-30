package org.pah_monitoring.main.services.examinations.schedules.interfaces;

import org.pah_monitoring.main.entities.dto.universal.schedules.ExaminationScheduleUniversalDto;
import org.pah_monitoring.main.entities.enums.IndicatorType;
import org.pah_monitoring.main.entities.examinations.schedules.ExaminationSchedule;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataSavingValidationService;

import java.util.Optional;

public interface ExaminationScheduleService extends DataSavingValidationService<ExaminationScheduleUniversalDto> {

    Optional<ExaminationSchedule> findConcrete(IndicatorType type, Patient patient);

    ExaminationSchedule findById(Integer id) throws DataSearchingServiceException;

    ExaminationSchedule save(ExaminationScheduleUniversalDto universalDto) throws DataSavingServiceException;

    void delete(ExaminationScheduleUniversalDto universalDto) throws DataDeletionServiceException;

    void checkAccessRightsForAnyAction(Patient patient) throws NotEnoughRightsServiceException;

}
