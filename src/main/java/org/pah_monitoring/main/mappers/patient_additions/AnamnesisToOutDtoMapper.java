package org.pah_monitoring.main.mappers.patient_additions;

import org.pah_monitoring.auxiliary.utils.FormulaUtils;
import org.pah_monitoring.main.dto.out.patient_additions.AnamnesisOutDto;
import org.pah_monitoring.main.entities.main.patient_additions.Anamnesis;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("anamnesisMapper")
public class AnamnesisToOutDtoMapper implements BaseEntityToOutDtoMapper<Anamnesis, AnamnesisOutDto> {

    @Override
    public AnamnesisOutDto map(Anamnesis anamnesis) {
        return AnamnesisOutDto
                .builder()
                .heartDisease(anamnesis.getHeartDisease())
                .lungDisease(anamnesis.getLungDisease())
                .relativesDiseases(anamnesis.getRelativesDiseases())
                .bloodClotting(anamnesis.getBloodClotting())
                .diabetes(anamnesis.getDiabetes())
                .height(anamnesis.getHeight())
                .weight(anamnesis.getWeight())
                .bodyMassIndex(FormulaUtils.bodyMassIndex(anamnesis.getWeight(), anamnesis.getHeight()))
                .build();
    }

}
