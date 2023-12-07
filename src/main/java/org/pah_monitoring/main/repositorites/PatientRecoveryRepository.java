package org.pah_monitoring.main.repositorites;

import org.pah_monitoring.main.entities.Recovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRecoveryRepository extends JpaRepository<Recovery, Integer> {

}
