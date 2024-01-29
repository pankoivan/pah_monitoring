package org.pah_monitoring.main.entities.auxiliary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class IndicatorCard {

    private String workingName;

    private String name;

    private String filename;

}
