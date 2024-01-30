package org.pah_monitoring.main.entities.auxiliary;

import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class FileIndicatorCard extends IndicatorCard {

    private String filesRef;

}
