package org.pah_monitoring.main.entities.main.users.users.common.interfaces;

import org.pah_monitoring.main.entities.main.users.inactivity.Dismissal;
import org.pah_monitoring.main.entities.main.users.info.EmployeeInformation;
import org.pah_monitoring.main.entities.main.users.inactivity.SickLeave;
import org.pah_monitoring.main.entities.main.users.inactivity.Vacation;

import java.time.LocalDate;
import java.util.Optional;

public abstract class HospitalEmployee extends HospitalUser {

    public abstract EmployeeInformation getEmployeeInformation();

    public Optional<Vacation> getCurrentVacation() {
        return getEmployeeInformation().getVacations()
                .stream()
                .filter(vacation -> vacation.getEndDate().isAfter(LocalDate.now()))
                .findFirst();
    }

    public boolean isOnVacation() {
        return getCurrentVacation().isPresent();
    }

    public Optional<SickLeave> getCurrentSickLeave() {
        return getEmployeeInformation().getSickLeaves()
                .stream()
                .filter(sickLeave -> sickLeave.getEndDate().isAfter(LocalDate.now()))
                .findFirst();
    }

    public boolean isOnSickLeave() {
        return getCurrentSickLeave().isPresent();
    }

    public Optional<Dismissal> getCurrentDismissal() {
        return Optional.ofNullable(getEmployeeInformation().getDismissal());
    }

    public boolean isDismissed() {
        return getCurrentDismissal().isPresent();
    }

    public boolean isActive() {
        return !isDismissed() && !isOnVacation() && !isOnSickLeave();
    }

    public boolean isNotActive() {
        return !isActive();
    }

    public String getActivityMessage() {
        if (getCurrentVacation().isPresent()) {
            Vacation vacation = getCurrentVacation().get();
            return "В отпуске с %s по %s".formatted(vacation.getStartDate(), vacation.getEndDate());
        } else if (getCurrentSickLeave().isPresent()) {
            SickLeave sickLeave = getCurrentSickLeave().get();
            return "На больничном с %s по %s".formatted(sickLeave.getStartDate(), sickLeave.getEndDate());
        } else if (getCurrentDismissal().isPresent()) {
            Dismissal dismissal = getCurrentDismissal().get();
            return "Уволен %s".formatted(dismissal.getDate());
        } else {
            return "Активен";
        }
    }

}
