package org.pah_monitoring.main.repositorites.examinations.indicators;

import org.pah_monitoring.main.entities.main.examinations.indicators.Weight;
import org.pah_monitoring.main.repositorites.examinations.indicators.common.InputIndicatorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeightRepository extends JpaRepository<Weight, Integer>, InputIndicatorRepository<Weight> {

}
