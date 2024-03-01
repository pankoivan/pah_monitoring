package org.pah_monitoring.main.services.main.examinations.indicators.implementations.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.Indicator;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
public abstract class AbstractIndicatorServiceImpl<T extends Indicator> implements IndicatorService<T> {

    private CurrentUserExtractionService extractionService;

    private CurrentUserCheckService checkService;

    @Override
    public void checkAccessRightsForObtaining(Patient patient) throws NotEnoughRightsServiceException {
        if (!(
                patient.isActive() && (checkService.isSelf(patient) || checkService.isOwnDoctor(patient)) ||
                patient.isNotActive() && checkService.isDoctorFromSameHospital(patient.getHospital())
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    protected void checkHasNoAnamnesis() throws DataValidationServiceException {
        if (extractionService.patient().hasNoAnamnesis()) {
            throw new DataValidationServiceException("""
                    Вы не можете отправлять результаты наблюдений, так как на данный момент у вас ещё не отправлен анамнез.\
                     Прежде чем отправлять результаты наблюдений, вам необходимо сначала отправить ваш анамнез.
                    """
            );
        }
    }

    protected void checkHasNoDoctor() throws DataValidationServiceException {
        if (extractionService.patient().hasNoAnamnesis()) {
            throw new DataValidationServiceException("""
                    Вы не можете отправлять результаты наблюдений, так как на данный момент за вами не закреплён ни\
                     один врач. Ожидайте, пока администраторы назначат вам какого-нибудь врача, или обратитесь к ним\
                     посредством личных сообщений в случае долгого ожидания.
                    """
            );
        }
    }

    protected List<T> overPeriod(List<T> indicators, String pathPeriod) {
        Period period = Period.myValueOf(pathPeriod);
        return period == Period.WHOLE_TIME
                ? indicators
                : indicators
                    .stream()
                    .filter(indicator -> indicator.getDate().isAfter(LocalDateTime.now().minusDays(period.getDays())))
                    .toList();
    }

}
