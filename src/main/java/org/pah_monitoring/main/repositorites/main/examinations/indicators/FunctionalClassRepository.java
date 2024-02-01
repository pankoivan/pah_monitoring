package org.pah_monitoring.main.repositorites.main.examinations.indicators;

import org.pah_monitoring.main.entities.main.examinations.indicators.FunctionalClass;
import org.pah_monitoring.main.repositorites.main.examinations.indicators.common.InputIndicatorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionalClassRepository extends JpaRepository<FunctionalClass, Integer>, InputIndicatorRepository<FunctionalClass> {

}
