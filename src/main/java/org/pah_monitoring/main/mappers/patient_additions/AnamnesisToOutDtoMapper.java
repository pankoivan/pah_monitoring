package org.pah_monitoring.main.mappers.patient_additions;

import org.pah_monitoring.auxiliary.utils.FormulaUtils;
import org.pah_monitoring.auxiliary.utils.NumberUtils;
import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.patient_additions.AnamnesisOutDto;
import org.pah_monitoring.main.entities.main.patient_additions.Anamnesis;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("anamnesisMapper")
public class AnamnesisToOutDtoMapper implements BaseEntityToOutDtoMapper<Anamnesis, AnamnesisOutDto> {

    @Override
    @NullWhenNull
    public AnamnesisOutDto map(Anamnesis anamnesis) {
        return AnamnesisOutDto
                .builder()
                .patientId(anamnesis.getPatient().getId())
                .heartDisease(hasNot(anamnesis.getHeartDisease()))
                .lungDisease(hasNot(anamnesis.getLungDisease()))
                .relativesDiseases(hasNot(anamnesis.getRelativesDiseases()))
                .bloodClotting(anamnesis.getBloodClotting().getAlias())
                .diabetes(hasNot(anamnesis.getDiabetes()))
                .height(anamnesis.getHeight())
                .weight(NumberUtils.round(anamnesis.getWeight(), 2))
                .bodyMassIndex(NumberUtils.round(FormulaUtils.bodyMassIndex(anamnesis.getWeight(), anamnesis.getHeight()), 2))
                .build();
    }

    public String hasNot(boolean bool) {
        return bool ? "Есть" : "Нет";
    }

}
