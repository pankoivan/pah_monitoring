package org.pah_monitoring.main.services.main.examinations.schedules.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.examinations.schedules.ExaminationScheduleAddingDto;
import org.pah_monitoring.main.dto.in.examinations.schedules.ExaminationScheduleEditingDto;
import org.pah_monitoring.main.dto.in.examinations.schedules.ExaminationScheduleSavingDto;
import org.pah_monitoring.main.dto.in.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.schedules.ExaminationSchedule;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.main.examinations.schedules.ExaminationScheduleRepository;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.main.examinations.schedules.interfaces.ExaminationScheduleService;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
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

    private CurrentUserCheckService checkService;

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
    public ExaminationSchedule add(ExaminationScheduleAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    ExaminationSchedule
                            .builder()
                            .patient(patientService.findById(addingDto.getPatientId()))
                            .indicatorType(addingDto.getIndicatorType())
                            .schedule(addingDto.getSchedule())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    public ExaminationSchedule edit(ExaminationScheduleEditingDto editingDto) throws DataSavingServiceException {
        try {
            ExaminationSchedule schedule = findById(editingDto.getId());
            return repository.save(
                    ExaminationSchedule
                            .builder()
                            .id(schedule.getId())
                            .patient(schedule.getPatient())
                            .indicatorType(schedule.getIndicatorType())
                            .schedule(editingDto.getSchedule())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(editingDto), e);
        }
    }

    @Override
    public void deleteById(Integer id) throws DataDeletionServiceException {
        try {
            repository.deleteById(findById(id).getId());
        } catch (Exception e) {
            throw new DataDeletionServiceException("Сущность с id \"%s\" не была удалена".formatted(id), e);
        }
    }

    @Override
    public void checkDataValidityForAdding(ExaminationScheduleAddingDto addingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        checkDataValidityForSaving(addingDto, bindingResult);

    }

    @Override
    public void checkDataValidityForEditing(ExaminationScheduleEditingDto editingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        checkDataValidityForSaving(editingDto, bindingResult);

        try {
            findById(editingDto.getId());
        } catch (DataSearchingServiceException e) {
            throw new DataValidationServiceException(e.getMessage(), e);
        }

    }

    @Override
    public void checkDataValidityForSaving(ExaminationScheduleSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

    }

    @Override
    public void checkAccessRightsForActions(Patient patient) throws NotEnoughRightsServiceException {
        if (!checkService.isOwnDoctor(patient)) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
