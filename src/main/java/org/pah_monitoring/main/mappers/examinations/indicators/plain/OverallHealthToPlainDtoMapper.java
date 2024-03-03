package org.pah_monitoring.main.mappers.examinations.indicators.plain;

import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.examinations.indicators.plain.OverallHealthPlainDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.OverallHealth;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("overallHealthPlainMapper")
public class OverallHealthToPlainDtoMapper implements BaseEntityToOutDtoMapper<OverallHealth, OverallHealthPlainDto> {

    @Override
    @NullWhenNull
    public OverallHealthPlainDto map(OverallHealth overallHealth) {
        return OverallHealthPlainDto
                .builder()
                .formattedDate(overallHealth.getFormattedDate())
                .breathlessness(overallHealth.getBreathlessness().getAlias())
                .fatigue(overallHealth.getFatigue().getAlias())
                .restFeeling(hasNot(overallHealth.getRestFeeling()))
                .drowsiness(hasNot(overallHealth.getDrowsiness()))
                .concentration(hasNot(overallHealth.getConcentration()))
                .weakness(overallHealth.getWeakness().getAlias())
                .appetite(hasNot(overallHealth.getAppetite()))
                .coldExtremities(overallHealth.getColdExtremities().getAlias())
                .build();
    }

    private String hasNot(boolean bool) {
        return bool ? "Есть" : "Нет";
    }

}
