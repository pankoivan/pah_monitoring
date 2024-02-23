package org.pah_monitoring.main.mappers.examinations.indicators.tables;

import org.pah_monitoring.main.dto.out.examinations.indicators.tables.PhysicalChangesTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.PhysicalChanges;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("physicalChangesTableMapper")
public class PhysicalChangesToTableDtoMapper implements BaseEntityToOutDtoListMapper<PhysicalChanges, PhysicalChangesTableDto> {

    @Override
    public PhysicalChangesTableDto map(PhysicalChanges physicalChanges) {
        return PhysicalChangesTableDto
                .builder()
                .id(String.valueOf(physicalChanges.getId()))
                .formattedDate(physicalChanges.getFormattedDate())
                .build();
    }

}
