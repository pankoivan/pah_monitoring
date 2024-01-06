package org.pah_monitoring.main.repositorites.users.users;

import org.pah_monitoring.main.entities.users.users.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<UserDetails> findByUserSecurityInformationEmail(String email);

}
