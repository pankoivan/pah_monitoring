package org.pah_monitoring.main.repositorites;

import org.pah_monitoring.main.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<UserDetails> findByUserSecurityInformationEmail(String email);

    List<Patient> findAllByHospitalId(String hospitalId);

}
