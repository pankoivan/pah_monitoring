package org.pah_monitoring.main.mappers.examinations.indicators.tables;

import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.OverallHealthTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.OverallHealth;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("overallHealthTableMapper")
public class OverallHealthToTableDtoMapper implements BaseEntityToOutDtoListMapper<OverallHealth, OverallHealthTableDto> {

    @Override
    public OverallHealthTableDto map(OverallHealth overallHealth) {
        return OverallHealthTableDto
                .builder()
                .formattedDate(DateTimeFormatConstants.DAY_MONTH_YEAR_AT_HOUR_MINUTE_SECOND.format(overallHealth.getDate()))
                .breathlessness(overallHealth.getBreathlessness().getAlias())
                .fatigue(overallHealth.getFatigue().getAlias())
                .restFeeling(yesNo(overallHealth.getRestFeeling()))
                .drowsiness(yesNo(overallHealth.getDrowsiness()))
                .concentration(yesNo(overallHealth.getConcentration()))
                .weakness(overallHealth.getWeakness().getAlias())
                .appetite(yesNo(overallHealth.getAppetite()))
                .coldExtremities(overallHealth.getColdExtremities().getAlias())
                .build();
    }

    private String yesNo(Boolean bool) {
        return bool ? "Да" : "Нет";
    }

    // todo: additional view

}
