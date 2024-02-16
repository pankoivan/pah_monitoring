package org.pah_monitoring.main.entities.additional.hospitals;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientStatistics {

    private int total;

    private int activeCount;

    private int inactiveCount;

    private int unallocated;

}
