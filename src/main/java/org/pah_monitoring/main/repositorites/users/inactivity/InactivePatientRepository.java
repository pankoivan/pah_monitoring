package org.pah_monitoring.main.repositorites.users.inactivity;

import org.pah_monitoring.main.entities.users.inactivity.PatientInactivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InactivePatientRepository extends JpaRepository<PatientInactivity, Integer> {

}
