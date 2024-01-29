package org.pah_monitoring.main.repositorites.examinations.indicators;

import org.pah_monitoring.main.entities.examinations.indicators.WalkTest;
import org.pah_monitoring.main.repositorites.examinations.indicators.common.InputIndicatorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalkTestRepository extends JpaRepository<WalkTest, Integer>, InputIndicatorRepository<WalkTest> {

}
