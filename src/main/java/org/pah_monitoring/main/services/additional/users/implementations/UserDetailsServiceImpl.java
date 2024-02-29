package org.pah_monitoring.main.services.additional.users.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.repositorites.users.users.AdministratorRepository;
import org.pah_monitoring.main.repositorites.users.users.DoctorRepository;
import org.pah_monitoring.main.repositorites.users.users.MainAdministratorRepository;
import org.pah_monitoring.main.repositorites.users.users.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MainAdministratorRepository mainAdministratorRepository;

    private final AdministratorRepository administratorRepository;

    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

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
                .orElseThrow(() -> new UsernameNotFoundException("Пользователя с почтой \"%s\" не существует".formatted(email)));
    }

}
