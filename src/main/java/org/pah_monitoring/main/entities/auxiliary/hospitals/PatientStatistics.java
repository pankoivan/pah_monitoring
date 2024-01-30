package org.pah_monitoring.main.entities.auxiliary.hospitals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientStatistics {

    private int activeCount;

    private int inActiveCount;

    private int unallocated;

}
