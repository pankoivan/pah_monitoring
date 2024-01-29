package org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs;

import org.pah_monitoring.main.entities.examinations.indicators.LiquidAndWeight;
import org.pah_monitoring.main.repositorites.examinations.indicators.common.IndicatorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquidAndWeightRepository extends JpaRepository<LiquidAndWeight, Integer>, IndicatorRepository<LiquidAndWeight> {

}
