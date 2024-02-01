package org.pah_monitoring.main.entities.users.users.common.interfaces;

import org.pah_monitoring.main.entities.users.inactivity.Dismissal;
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

    default boolean isDismissed() {
        return getCurrentDismissal() != null;
    }

    default boolean isNotDismissed() {
        return !isDismissed();
    }

    default boolean isActive() {
        return isNotDismissed() && !isOnVacation() && !isOnSickLeave();
    }

    default Dismissal getCurrentDismissal() {
        return getEmployeeInformation().getDismissal();
    }

    default String vacationMessage() {
        Vacation vacation = getCurrentVacation();
        if (vacation == null) {
            return "Не в отпуске";
        } else {
            return "В отпуске с %s по %s".formatted(vacation.getStartDate(), vacation.getEndDate());
        }
    }

    default String sickLeaveMessage() {
        SickLeave sickLeave = getCurrentSickLeave();
        if (sickLeave == null) {
            return "Не на больничном";
        } else {
            return "На больничном с %s по %s".formatted(sickLeave.getStartDate(), sickLeave.getEndDate());
        }
    }

    default String dismissalMessage() {
        Dismissal dismissal = getCurrentDismissal();
        if (dismissal == null) {
            return "Не уволен";
        } else {
            return "Уволен %s".formatted(dismissal.getDate());
        }
    }

    default String activityMessage() {
        if (isOnVacation()) {
            return vacationMessage();
        } else if (isOnSickLeave()) {
            return sickLeaveMessage();
        } else if (isDismissed()) {
            return dismissalMessage();
        } else {
            return "Активен";
        }
    }

}
