package org.pah_monitoring.main.repositorites.examinations.by_inputs;

import org.pah_monitoring.main.entities.examinations.by_inputs.WalkTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalkTestRepository extends JpaRepository<WalkTest, Integer> {

}
