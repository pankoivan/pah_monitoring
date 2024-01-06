package org.pah_monitoring.main.repositorites.examinations.by_inputs;

import org.pah_monitoring.main.entities.examinations.by_inputs.Pressure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PressureRepository extends JpaRepository<Pressure, Integer> {

}
