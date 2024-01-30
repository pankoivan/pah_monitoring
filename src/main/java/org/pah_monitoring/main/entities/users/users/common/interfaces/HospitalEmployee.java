package org.pah_monitoring.main.entities.users.users.common.interfaces;

import org.pah_monitoring.main.entities.users.inactivity.SickLeave;
import org.pah_monitoring.main.entities.users.inactivity.Vacation;
import org.pah_monitoring.main.entities.users.info.EmployeeInformation;

import java.time.LocalDate;

public interface HospitalEmployee extends HospitalUser {

    EmployeeInformation getEmployeeInformation();

    default Vacation getCurrentVacation() {
        return getEmployeeInformation().getVacations()
                .stream()
                .filter(vacation -> vacation.getEndDate().isAfter(LocalDate.now()))
                .findFirst()
                .orElse(null);
    }

    default SickLeave getCurrentSickLeave() {
        return getEmployeeInformation().getSickLeaves()
                .stream()
                .filter(sickLeave -> sickLeave.getEndDate().isAfter(LocalDate.now()))
                .findFirst()
                .orElse(null);
    }

    default boolean isOnVacation() {
        return getCurrentVacation() != null;
    }

    default boolean isOnSickLeave() {
        return getCurrentSickLeave() != null;
    }

}
