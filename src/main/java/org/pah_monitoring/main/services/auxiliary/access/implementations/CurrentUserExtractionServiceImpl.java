package org.pah_monitoring.main.services.auxiliary.access.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.users.users.Administrator;
import org.pah_monitoring.main.entities.users.users.Doctor;
import org.pah_monitoring.main.entities.users.users.MainAdministrator;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.entities.users.users.common.interfaces.HospitalEmployee;
import org.pah_monitoring.main.entities.users.users.common.interfaces.HospitalUser;
import org.pah_monitoring.main.entities.users.users.common.interfaces.User;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.CurrentUserExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class CurrentUserExtractionServiceImpl implements CurrentUserExtractionService {

    @Override
    public User user() throws NullPointerException, ClassCastException {
        return (User) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public HospitalUser hospitalUser() throws NullPointerException, ClassCastException {
        return (HospitalUser) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public HospitalEmployee hospitalEmployee() throws NullPointerException, ClassCastException {
        return (HospitalEmployee) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public MainAdministrator mainAdministrator() throws NullPointerException, ClassCastException {
        return (MainAdministrator) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public Administrator administrator() throws NullPointerException, ClassCastException {
        return (Administrator) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public Doctor doctor() throws NullPointerException, ClassCastException {
        return (Doctor) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public Patient patient() throws NullPointerException, ClassCastException {
        return (Patient) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}
