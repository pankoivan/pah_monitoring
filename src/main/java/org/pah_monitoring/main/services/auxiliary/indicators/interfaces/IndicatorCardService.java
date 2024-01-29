package org.pah_monitoring.main.services.auxiliary.indicators.interfaces;

import org.pah_monitoring.main.entities.auxiliary.IndicatorCard;
import org.pah_monitoring.main.entities.enums.IndicatorType;

public interface IndicatorCardService {

    IndicatorCard getCardFor(IndicatorType indicatorType);

}
