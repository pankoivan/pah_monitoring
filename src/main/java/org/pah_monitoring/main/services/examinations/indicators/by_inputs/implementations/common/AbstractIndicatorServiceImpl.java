package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations.common;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.common.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
public abstract class AbstractIndicatorServiceImpl<T, M> implements IndicatorService<T, M> {

    private AccessRightsCheckService checkService;

    @Override
    public void checkDataValidityForAdding(M addingDto, BindingResult bindingResult) throws DataValidationServiceException {
        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
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
