package org.pah_monitoring.main.entities.auxiliary.hospitals;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AdministratorStatistics {

    private int activeCount;

    private int vacationCount;

    private int sickLeaveCount;

    private int dismissalCount;

}
