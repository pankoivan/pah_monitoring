package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.common.IndicatorRepository;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.interfaces.common.IndicatorService;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
public abstract class AbstractIndicatorServiceImpl<T, M> implements IndicatorService<T, M> {

    private IndicatorRepository<T> repository;

    private CurrentUserExtractionService extractionService;

    private AccessRightsCheckService checkService;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public List<T> findAllByPatientId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(id).getId());
    }

    @Override
    public void checkDataValidityForAdding(M addingDto, BindingResult bindingResult) throws DataValidationServiceException {
        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        if (extractionService.patient().getDoctor() == null) {
            throw new DataValidationServiceException("""
                        Вы не можете отправлять результаты наблюдений, так как на данный момент за вами не закреплён ни\
                         один врач. Ожидайте, пока администраторы назначат вам какого-нибудь врача, или обратитесь к ним\
                         посредством личных сообщений в случае долгого ожидания
                        """
            );
        }
    }

    @Override
    public void checkAccessRightsForObtainingAllByPatientId(Patient patient) throws NotEnoughRightsServiceException {
        if (!(
                checkService.isSamePatient(patient) ||
                checkService.isOwnDoctor(patient)
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    @Override
    public void checkAccessRightsForAdding(Patient patient) throws NotEnoughRightsServiceException {
        if (!checkService.isPatient()) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
