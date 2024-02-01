package org.pah_monitoring.main.repositorites.main.users.inactivity;

import org.pah_monitoring.main.entities.main.users.inactivity.PatientInactivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientInactivityRepository extends JpaRepository<PatientInactivity, Integer> {

}
