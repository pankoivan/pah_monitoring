package org.pah_monitoring.main.services.main.examinations.indicators.implementations.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
public abstract class AbstractIndicatorServiceImpl<T, M> implements IndicatorService<T, M> {

    private CurrentUserExtractionService extractionService;

    private CurrentUserCheckService checkService;

    @Override
    public void checkDataValidityForAdding(M addingDto, BindingResult bindingResult) throws DataValidationServiceException {
        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        if (extractionService.patient().getDoctor() == null) {
            throw new DataValidationServiceException("""
                    Вы не можете отправлять результаты наблюдений, так как на данный момент за вами не закреплён ни\
                     один врач. Ожидайте, пока администраторы назначат вам какого-нибудь врача, или обратитесь к ним\
                     посредством личных сообщений в случае долгого ожидания.
                    """
            );
        }
        if (extractionService.patient().hasNoAnamnesis()) {
            throw new DataValidationServiceException("Прежде чем отправлять результаты наблюдений, сначала отправьте анамнез");
        }
    }

    @Override
    public void checkAccessRightsForObtaining(Patient patient) throws NotEnoughRightsServiceException {
        if (!(
                patient.isActive() &&
                (checkService.isSelf(patient) ||
                checkService.isOwnDoctor(patient)) ||
                patient.isNotActive() &&
                checkService.isDoctorFromSameHospital(patient.getHospital())
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
