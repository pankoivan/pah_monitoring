package org.pah_monitoring.main.entities.additional.hospitals;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PatientStatistics {

    private int total;

    private int activeCount;

    private int inactiveCount;

    private int unallocated;

}
