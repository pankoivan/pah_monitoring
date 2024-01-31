package org.pah_monitoring.main.services.examinations.schedules.interfaces;

import org.pah_monitoring.main.entities.dto.saving.examinations.schedules.ExaminationScheduleAddingDto;
import org.pah_monitoring.main.entities.dto.saving.examinations.schedules.ExaminationScheduleEditingDto;
import org.pah_monitoring.main.entities.dto.saving.examinations.schedules.ExaminationScheduleSavingDto;
import org.pah_monitoring.main.entities.enums.IndicatorType;
import org.pah_monitoring.main.entities.examinations.schedules.ExaminationSchedule;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataAddingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataEditingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataSavingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.url.UrlValidationService;

import java.util.Optional;

public interface ExaminationScheduleService extends
        DataAddingValidationService<ExaminationScheduleAddingDto>, DataEditingValidationService<ExaminationScheduleEditingDto>,
        DataSavingValidationService<ExaminationScheduleSavingDto>, UrlValidationService {

    Optional<ExaminationSchedule> findConcrete(IndicatorType type, Patient patient);

    ExaminationSchedule findById(Integer id) throws DataSearchingServiceException;

    ExaminationSchedule add(ExaminationScheduleAddingDto addingDto) throws DataSavingServiceException;

    ExaminationSchedule edit(ExaminationScheduleEditingDto editingDto) throws DataSavingServiceException;

    void deleteById(Integer id) throws DataDeletionServiceException;

    void checkAccessRightsForActions(Patient patient) throws NotEnoughRightsServiceException;

}
