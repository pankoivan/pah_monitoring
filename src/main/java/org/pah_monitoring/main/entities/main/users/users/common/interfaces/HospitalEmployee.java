package org.pah_monitoring.main.entities.main.users.users.common.interfaces;

import org.pah_monitoring.main.entities.main.users.inactivity.Dismissal;
import org.pah_monitoring.main.entities.main.users.inactivity.SickLeave;
import org.pah_monitoring.main.entities.main.users.inactivity.Vacation;
import org.pah_monitoring.main.entities.main.users.inactivity.common.interfaces.Inactivity;
import org.pah_monitoring.main.entities.main.users.info.EmployeeInformation;

import java.time.LocalDate;
import java.util.Optional;

public interface HospitalEmployee extends HospitalUser {

    EmployeeInformation getEmployeeInformation();

    default boolean isOnVacation() {
        return getCurrentVacation().isPresent();
    }

    default boolean isOnSickLeave() {
        return getCurrentSickLeave().isPresent();
    }

    default boolean isDismissed() {
        return getCurrentDismissal().isPresent();
    }

    @Override
    default boolean isActive() {
        return !isDismissed() && !isOnVacation() && !isOnSickLeave();
    }

    @Override
    default boolean isNotActive() {
        return !isActive();
    }

    @Override
    default Optional<Inactivity> getCurrentInactivity() {
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
