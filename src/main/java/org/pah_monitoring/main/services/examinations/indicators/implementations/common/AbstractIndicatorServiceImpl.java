package org.pah_monitoring.main.services.examinations.indicators.implementations.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.examinations.indicators.interfaces.common.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
public abstract class AbstractIndicatorServiceImpl<T, M> implements IndicatorService<T, M> {

    private CurrentUserExtractionService extractionService;

    private AccessRightsCheckService checkService;

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
        if (!checkService.isPatient()) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
