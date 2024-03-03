package org.pah_monitoring.main.mappers.examinations.indicators.plain;

import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.examinations.indicators.plain.PhysicalChangesPlainDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.PhysicalChanges;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("physicalChangesPlainMapper")
public class PhysicalChangesToPlainDtoMapper implements BaseEntityToOutDtoMapper<PhysicalChanges, PhysicalChangesPlainDto> {

    @Override
    @NullWhenNull
    public PhysicalChangesPlainDto map(PhysicalChanges physicalChanges) {
        return PhysicalChangesPlainDto
                .builder()
                .formattedDate(physicalChanges.getFormattedDate())
                .abdominalEnlargement(hasNot(physicalChanges.getAbdominalEnlargement()))
                .legsSwelling(physicalChanges.getLegsSwelling().getAlias())
                .vascularAsterisks(hasNot(physicalChanges.getVascularAsterisks()))
                .skinColor(physicalChanges.getSkinColor().getAlias())
                .fingersPhalanges(yesNo(physicalChanges.getFingersPhalanges()))
                .chest(yesNo(physicalChanges.getChest()))
                .neckVeins(hasNot(physicalChanges.getNeckVeins()))
                .build();
    }

    private String hasNot(boolean bool) {
        return bool ? "Есть" : "Нет";
    }

    private String yesNo(boolean bool) {
        return bool ? "Да" : "Нет";
    }

}
