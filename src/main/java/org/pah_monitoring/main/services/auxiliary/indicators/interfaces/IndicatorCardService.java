package org.pah_monitoring.main.services.auxiliary.indicators.interfaces;

import org.pah_monitoring.main.entities.auxiliary.FileIndicatorCard;
import org.pah_monitoring.main.entities.auxiliary.IndicatorCard;
import org.pah_monitoring.main.entities.auxiliary.InputIndicatorCard;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;

import java.util.List;

public interface IndicatorCardService {

    List<InputIndicatorCard> getAllInputIndicatorCardsFor(Patient patient);

    List<FileIndicatorCard> getAllFileIndicatorCardsFor(Patient patient);

    List<IndicatorCard> getAllIndicatorCardsFor(Patient patient);

    void checkAccessRightsForObtainingAll(Patient patient) throws NotEnoughRightsServiceException;

}
