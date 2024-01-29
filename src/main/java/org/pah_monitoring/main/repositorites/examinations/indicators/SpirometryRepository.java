package org.pah_monitoring.main.repositorites.examinations.indicators;

import org.pah_monitoring.main.entities.examinations.indicators.Spirometry;
import org.pah_monitoring.main.repositorites.examinations.indicators.common.InputIndicatorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpirometryRepository extends JpaRepository<Spirometry, Integer>, InputIndicatorRepository<Spirometry> {

}
