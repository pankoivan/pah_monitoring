package org.pah_monitoring.main.mappers.examinations.indicators.tables;

import org.pah_monitoring.main.dto.out.examinations.indicators.tables.ChestPainTableDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.ChestPain;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.springframework.stereotype.Component;

@Component("chestPainTableMapper")
public class ChestPainToTableDtoMapper implements BaseEntityToOutDtoListMapper<ChestPain, ChestPainTableDto> {

    @Override
    public ChestPainTableDto map(ChestPain chestPain) {
        return ChestPainTableDto
                .builder()
                .formattedDate(chestPain.getFormattedDate())
                .type(chestPain.getType().getAlias())
                .duration(chestPain.getDuration().getAlias())
                .nitroglycerin(nitroglycerin(chestPain.getNitroglycerin()))
                .build();
    }

    private String nitroglycerin(ChestPain.Nitroglycerin nitroglycerin) {
        return switch (nitroglycerin) {
            case YES -> "Помог";
            case NO -> "Не помог";
            case DID_NOT_TAKE -> "Не принимал";
        };
    }

}
