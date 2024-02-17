package org.pah_monitoring.main.entities.additional.indicators;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public abstract class InputIndicatorCard extends IndicatorCard {

}
