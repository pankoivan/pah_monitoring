package org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.main.validation.interfaces.url.UrlValidationService;

public interface IndicatorService<T> extends UrlValidationService {

    T findById(Integer id) throws DataSearchingServiceException;

    void checkAccessRightsForObtaining(Patient patient) throws NotEnoughRightsServiceException;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    enum Period {

        DAY("За последний день", 1),

        THREE_DAYS("За последние три дня", 3),

        WEEK("За последнюю неделю", 7),

        TWO_WEEKS("За последние две недели", 14),

        MONTH("За последний месяц", 30),

        THREE_MONTHS("За последние три месяца", 90),

        HALF_YEAR("За последние полгода", 180),

        YEAR("За последний год", 365),

        WHOLE_TIME("За всё время", -1);

        private final String alias;

        private final int days;

    }

}
