package org.pah_monitoring.main.repositorites;

import org.pah_monitoring.main.entities.PatientRecovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRecoveryRepository extends JpaRepository<PatientRecovery, Integer> {

}
