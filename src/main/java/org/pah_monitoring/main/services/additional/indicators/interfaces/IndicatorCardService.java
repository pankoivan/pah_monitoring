package org.pah_monitoring.main.services.additional.indicators.interfaces;

import org.pah_monitoring.main.entities.additional.auxiliary.indicators.FileIndicatorCard;
import org.pah_monitoring.main.entities.additional.auxiliary.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.additional.auxiliary.indicators.InputIndicatorCard;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;

import java.util.List;

public interface IndicatorCardService {

    List<InputIndicatorCard> getAllInputIndicatorCardsFor(Patient patient);

    List<FileIndicatorCard> getAllFileIndicatorCardsFor(Patient patient);

    List<IndicatorCard> getAllIndicatorCardsFor(Patient patient);

    void checkAccessRightsForObtainingAll(Patient patient) throws NotEnoughRightsServiceException;

}
