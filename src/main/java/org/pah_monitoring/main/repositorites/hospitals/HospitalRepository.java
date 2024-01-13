package org.pah_monitoring.main.repositorites.hospitals;

import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

    boolean existsByName(String name);

}
