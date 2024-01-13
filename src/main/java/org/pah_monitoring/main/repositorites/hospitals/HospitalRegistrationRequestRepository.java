package org.pah_monitoring.main.repositorites.hospitals;

import org.pah_monitoring.main.entities.hospitals.HospitalRegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRegistrationRequestRepository extends JpaRepository<HospitalRegistrationRequest, Integer> {

    boolean existsByPassport(String passport);

    boolean existsByPhoneNumber(String phoneNumber);

}
