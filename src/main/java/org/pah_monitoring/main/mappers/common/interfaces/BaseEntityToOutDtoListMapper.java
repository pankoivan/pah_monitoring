package org.pah_monitoring.main.mappers.common.interfaces;

import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;
import org.pah_monitoring.main.entities.main.common.interfaces.BaseEntity;

import java.util.List;

public interface BaseEntityToOutDtoListMapper<T extends BaseEntity, M extends OutDto> extends BaseEntityToOutDtoMapper<T, M> {

    default List<M> mapList(List<T> entities) {
        return entities.stream()
                .map(this::map)
                .toList();
    }

}
