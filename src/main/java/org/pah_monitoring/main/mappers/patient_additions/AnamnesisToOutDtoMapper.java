package org.pah_monitoring.main.mappers.patient_additions;

import org.pah_monitoring.auxiliary.utils.FormulaUtils;
import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.patient_additions.AnamnesisOutDto;
import org.pah_monitoring.main.entities.main.patient_additions.Anamnesis;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("anamnesisMapper")
public class AnamnesisToOutDtoMapper implements BaseEntityToOutDtoMapper<Anamnesis, AnamnesisOutDto> {

    @Override
    @NullWhenNull
    public AnamnesisOutDto map(Anamnesis anamnesis) {
        return Optional.ofNullable(anamnesis).map(mappedAnamnesis -> AnamnesisOutDto
                        .builder()
                        .patientId(mappedAnamnesis.getPatient().getId())
                        .heartDisease(mappedAnamnesis.getHeartDisease())
                        .lungDisease(mappedAnamnesis.getLungDisease())
                        .relativesDiseases(mappedAnamnesis.getRelativesDiseases())
                        .bloodClotting(mappedAnamnesis.getBloodClotting())
                        .diabetes(mappedAnamnesis.getDiabetes())
                        .height(mappedAnamnesis.getHeight())
                        .weight(String.format("%.2f", mappedAnamnesis.getWeight()))
                        .bodyMassIndex(
                                String.format("%.2f", FormulaUtils.bodyMassIndex(mappedAnamnesis.getWeight(), mappedAnamnesis.getHeight()))
                        )
                        .build())
                .orElse(null);
    }

}
