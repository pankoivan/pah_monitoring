package org.pah_monitoring.main.repositorites;

import org.pah_monitoring.main.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Optional<UserDetails> findByUserSecurityInformationEmail(String email);

    List<Doctor> findAllByHospitalId(String hospitalId);

}
