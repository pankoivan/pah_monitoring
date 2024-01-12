package org.pah_monitoring.main.repositorites.hospital;

import org.pah_monitoring.main.entities.hospital.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

}
