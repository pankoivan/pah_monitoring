package org.pah_monitoring.main.services.main.examinations.cards.interfaces;

import org.pah_monitoring.main.entities.additional.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;

import java.util.List;

public interface IndicatorCardService {

    List<IndicatorCard> getAllIndicatorCardsFor(Patient patient);

    void checkAccessRightsForObtaining(Patient patient) throws NotEnoughRightsServiceException;

}
