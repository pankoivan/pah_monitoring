package org.pah_monitoring.main.entities.main.users.users.common;

import org.pah_monitoring.main.entities.main.users.inactivity.Dismissal;
import org.pah_monitoring.main.entities.main.users.inactivity.SickLeave;
import org.pah_monitoring.main.entities.main.users.inactivity.Vacation;
import org.pah_monitoring.main.entities.main.users.inactivity.common.Inactivity;
import org.pah_monitoring.main.entities.main.users.info.EmployeeInformation;

import java.time.LocalDate;
import java.util.Optional;

public abstract class HospitalEmployee extends HospitalUser {

    public abstract EmployeeInformation getEmployeeInformation();

    public boolean isOnVacation() {
        return getCurrentVacation().isPresent();
    }

    public boolean isOnSickLeave() {
        return getCurrentSickLeave().isPresent();
    }

    public boolean isDismissed() {
        return getCurrentDismissal().isPresent();
    }

    @Override
    public boolean isActive() {
        return !isDismissed() && !isOnVacation() && !isOnSickLeave();
    }

    @Override
    public boolean isNotActive() {
        return !isActive();
    }

    @Override
    public Optional<Inactivity> getCurrentInactivity() {
        if (getCurrentVacation().isPresent()) {
            return Optional.of(getCurrentVacation().get());
        } else if (getCurrentSickLeave().isPresent()) {
            return Optional.of(getCurrentSickLeave().get());
        } else if (getCurrentDismissal().isPresent()) {
            return Optional.of(getCurrentDismissal().get());
        } else {
            return Optional.empty();
        }
    }

    private Optional<Vacation> getCurrentVacation() {
        return getEmployeeInformation().getVacations()
                .stream()
                .filter(vacation -> vacation.getEndDate().isAfter(LocalDate.now()))
                .findFirst();
    }

    private Optional<SickLeave> getCurrentSickLeave() {
        return getEmployeeInformation().getSickLeaves()
                .stream()
                .filter(sickLeave -> sickLeave.getEndDate().isAfter(LocalDate.now()))
                .findFirst();
    }

    private Optional<Dismissal> getCurrentDismissal() {
        return Optional.ofNullable(getEmployeeInformation().getDismissal());
    }

}
