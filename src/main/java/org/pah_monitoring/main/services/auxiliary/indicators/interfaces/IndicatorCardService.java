package org.pah_monitoring.main.services.auxiliary.indicators.interfaces;

import org.pah_monitoring.main.entities.auxiliary.IndicatorCard;
import org.pah_monitoring.main.entities.users.users.Patient;

import java.util.List;

public interface IndicatorCardService {

    List<IndicatorCard> getAll(Patient patient);

}
