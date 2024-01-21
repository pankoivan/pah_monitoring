package org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs;

import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.OverallHealth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverallHealthRepository extends JpaRepository<OverallHealth, Integer> {

}
