package org.pah_monitoring.main.entities.additional.hospitals;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorStatistics {

    private int total;

    private int activeCount;

    private int vacationCount;

    private int sickLeaveCount;

    private int dismissalCount;

    private int withoutPatientsCount;

}
