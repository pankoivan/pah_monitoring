package org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs;

import org.pah_monitoring.main.entities.examinations.indicators.Spirometry;
import org.pah_monitoring.main.repositorites.examinations.indicators.common.IndicatorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpirometryRepository extends JpaRepository<Spirometry, Integer>, IndicatorRepository<Spirometry> {

}
