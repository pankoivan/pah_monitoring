package org.pah_monitoring.main.services.implementations;

import org.pah_monitoring.main.repositorites.AdministratorRepository;
import org.pah_monitoring.main.repositorites.DoctorRepository;
import org.pah_monitoring.main.repositorites.MainAdministratorRepository;
import org.pah_monitoring.main.repositorites.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MainAdministratorRepository mainAdministratorRepository;

    private final AdministratorRepository administratorRepository;

    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    @Autowired
    public UserDetailsServiceImpl(MainAdministratorRepository mainAdministratorRepository,
                                  AdministratorRepository administratorRepository,
                                  DoctorRepository doctorRepository, PatientRepository patientRepository) {

        this.mainAdministratorRepository = mainAdministratorRepository;
        this.administratorRepository = administratorRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return Stream.of(
                mainAdministratorRepository.findByUserSecurityInformationEmail(email).orElse(null),
                administratorRepository.findByUserSecurityInformationEmail(email).orElse(null),
                doctorRepository.findByUserSecurityInformationEmail(email).orElse(null),
                patientRepository.findByUserSecurityInformationEmail(email).orElse(null)
        )
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User with email \"%s\" doesn't exists".formatted(email)));
    }

}
