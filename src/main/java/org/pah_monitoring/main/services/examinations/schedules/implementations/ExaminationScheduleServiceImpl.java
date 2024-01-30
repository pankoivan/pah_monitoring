package org.pah_monitoring.main.services.examinations.schedules.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.examinations.schedules.ExaminationScheduleAddingDto;
import org.pah_monitoring.main.entities.dto.saving.examinations.schedules.ExaminationScheduleEditingDto;
import org.pah_monitoring.main.entities.dto.saving.examinations.schedules.ExaminationScheduleSavingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.enums.IndicatorType;
import org.pah_monitoring.main.entities.examinations.schedules.ExaminationSchedule;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
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

import java.util.List;
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
    public List<ExaminationSchedule> findAllByPatientId(Integer patientId) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(patientId).getId());
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
    public void checkDataValidityForAdding(ExaminationScheduleAddingDto addingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        checkDataValidityForSaving(addingDto, bindingResult);

    }

    @Override
    public void checkDataValidityForEditing(ExaminationScheduleEditingDto editingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        checkDataValidityForSaving(editingDto, bindingResult);

    }

    @Override
    public void checkDataValidityForSaving(ExaminationScheduleSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

    }

    @Override
    public void checkAccessRightsForObtainingAll(Patient patient) throws NotEnoughRightsServiceException {
        if (!(
                checkService.isSamePatient(patient) ||
                checkService.isOwnDoctor(patient)
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    @Override
    public void checkAccessRightsForAdding(Patient patient) throws NotEnoughRightsServiceException {
        if (!checkService.isOwnDoctor(patient)) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    @Override
    public void checkAccessRightsForEditing(Patient patient) throws NotEnoughRightsServiceException {
        if (!checkService.isOwnDoctor(patient)) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
