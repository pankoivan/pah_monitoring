package org.pah_monitoring.main.repositorites.examinations.by_inputs;

import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.LiquidAndWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquidAndWeightRepository extends JpaRepository<LiquidAndWeight, Integer> {

}
