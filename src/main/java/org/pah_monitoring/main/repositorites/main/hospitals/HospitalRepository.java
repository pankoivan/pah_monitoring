package org.pah_monitoring.main.repositorites.main.hospitals;

import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

    boolean existsByName(String name);

}
