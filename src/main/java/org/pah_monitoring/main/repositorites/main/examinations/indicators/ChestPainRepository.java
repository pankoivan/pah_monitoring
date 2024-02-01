package org.pah_monitoring.main.repositorites.main.examinations.indicators;

import org.pah_monitoring.main.entities.main.examinations.indicators.ChestPain;
import org.pah_monitoring.main.repositorites.main.examinations.indicators.common.InputIndicatorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChestPainRepository extends JpaRepository<ChestPain, Integer>, InputIndicatorRepository<ChestPain> {

}
