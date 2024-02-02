package org.pah_monitoring.main.mappers.common.interfaces;

import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;
import org.pah_monitoring.main.entities.main.common.interfaces.BaseEntity;

public interface BaseEntityToOutDtoMapper<T extends BaseEntity, M extends OutDto> {

    M map(T entity);

}
