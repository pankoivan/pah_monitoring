package org.pah_monitoring.main.services.main.examinations.indicators.implementations.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
public abstract class AbstractIndicatorServiceImpl<T> implements IndicatorService<T> {

    private CurrentUserExtractionService extractionService;

    private CurrentUserCheckService checkService;

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
