package org.pah_monitoring.main.services.auxiliary.indicators.implementations;

import org.apache.logging.log4j.util.Supplier;
import org.pah_monitoring.main.entities.auxiliary.IndicatorCard;
import org.pah_monitoring.main.entities.enums.IndicatorType;
import org.pah_monitoring.main.services.auxiliary.indicators.interfaces.IndicatorCardService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IndicatorCardServiceImpl implements IndicatorCardService {

    private HashMap<IndicatorType, Supplier<IndicatorCard>> actions = Map.of(
            IndicatorType.SPIROMETRY, () -> spirometry()
    );

    @Override
    public IndicatorCard getCardFor(IndicatorType indicatorType) {
        return actions.get(indicatorType).get();
    }

    private IndicatorType spirometry() {
        return null;
    }

    private IndicatorType walkTest() {
        return null;
    }

    private IndicatorType pulseOximetry() {
        return null;
    }

    private IndicatorType cough() {
        return null;
    }

    private IndicatorType chestPain() {
        return null;
    }

    private IndicatorType fainting() {
        return null;
    }

    private IndicatorType physicalChanges() {
        return null;
    }

    private IndicatorType ascites() {
        return null;
    }

    private IndicatorType overallHealth() {
        return null;
    }

    private IndicatorType vertigo() {
        return null;
    }

    private IndicatorType pressure() {
        return null;
    }

    private IndicatorType liquidAndWeight() {
        return null;
    }

    private IndicatorType functionalClass() {
        return null;
    }

    private IndicatorType bloodTest() {
        return null;
    }

    private IndicatorType electrocardiography() {
        return null;
    }

    private IndicatorType radiography() {
        return null;
    }

    private IndicatorType echocardiography() {
        return null;
    }

    private IndicatorType computedTomography() {
        return null;
    }

    private IndicatorType catheterization() {
        return null;
    }

}
