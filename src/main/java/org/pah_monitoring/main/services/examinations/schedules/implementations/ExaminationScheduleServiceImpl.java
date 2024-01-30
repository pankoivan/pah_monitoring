package org.pah_monitoring.main.services.examinations.schedules.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.universal.schedules.ExaminationScheduleUniversalDto;
import org.pah_monitoring.main.entities.enums.IndicatorType;
import org.pah_monitoring.main.entities.examinations.schedules.ExaminationSchedule;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.examinations.schedules.ExaminationScheduleRepository;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.examinations.schedules.interfaces.ExaminationScheduleService;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class ExaminationScheduleServiceImpl implements ExaminationScheduleService {

    private final ExaminationScheduleRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    private AccessRightsCheckService checkService;

    @Override
    public Optional<ExaminationSchedule> findConcrete(IndicatorType type, Patient patient) {
        return repository.findByIndicatorTypeAndPatientId(type, patient.getId());
    }

    @Override
    public ExaminationSchedule findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Расписание с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public ExaminationSchedule save(ExaminationScheduleUniversalDto universalDto) throws DataSavingServiceException {
        try {
            Optional<ExaminationSchedule> schedule = repository.findByIndicatorTypeAndPatientId(
                    universalDto.getIndicatorType(), universalDto.getPatientId()
            );
            return repository.save(
                    ExaminationSchedule
                            .builder()
                            .id(schedule.map(ExaminationSchedule::getId).orElse(null))
                            .patient(patientService.findById(universalDto.getPatientId()))
                            .indicatorType(universalDto.getIndicatorType())
                            .schedule(universalDto.getSchedule())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(universalDto), e);
        }
    }

    @Override
    public void delete(ExaminationScheduleUniversalDto universalDto) throws DataDeletionServiceException {
        Optional<ExaminationSchedule> schedule = repository.findByIndicatorTypeAndPatientId(
                universalDto.getIndicatorType(), universalDto.getPatientId()
        );
        try {
            repository.deleteById(schedule.get().getId());
        } catch (Exception e) {
            throw new DataDeletionServiceException("DTO-сущность \"%s\" не была удалена".formatted(schedule), e);
        }
    }

    @Override
    public void checkDataValidityForSaving(ExaminationScheduleUniversalDto universalDto, BindingResult bindingResult) throws DataValidationServiceException {
        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
    }

    @Override
    public void checkAccessRightsForAnyAction(Patient patient) throws NotEnoughRightsServiceException {
        if (!checkService.isOwnDoctor(patient)) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
