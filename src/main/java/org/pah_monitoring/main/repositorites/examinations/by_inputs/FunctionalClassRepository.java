package org.pah_monitoring.main.repositorites.examinations.by_inputs;

import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.FunctionalClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionalClassRepository extends JpaRepository<FunctionalClass, Integer> {

}
