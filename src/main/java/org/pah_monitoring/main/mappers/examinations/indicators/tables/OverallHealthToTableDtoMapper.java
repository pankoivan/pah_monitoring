package org.pah_monitoring.main.mappers.examinations.indicators.tables;

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
                .id(String.valueOf(overallHealth.getId()))
                .formattedDate(overallHealth.getFormattedDate())
                .build();
    }

}
