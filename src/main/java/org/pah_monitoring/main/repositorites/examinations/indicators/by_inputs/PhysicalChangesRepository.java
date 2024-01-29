package org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs;

import org.pah_monitoring.main.entities.examinations.indicators.PhysicalChanges;
import org.pah_monitoring.main.repositorites.examinations.indicators.common.IndicatorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicalChangesRepository extends JpaRepository<PhysicalChanges, Integer>, IndicatorRepository<PhysicalChanges> {

}
