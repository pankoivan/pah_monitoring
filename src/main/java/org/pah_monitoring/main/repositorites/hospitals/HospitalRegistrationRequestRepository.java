package org.pah_monitoring.main.repositorites.hospitals;

import org.pah_monitoring.main.entities.main.hospitals.HospitalRegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospitalRegistrationRequestRepository extends JpaRepository<HospitalRegistrationRequest, Integer> {

    boolean existsByPassport(String passport);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    Optional<HospitalRegistrationRequest> findByEmail(String email);

}
