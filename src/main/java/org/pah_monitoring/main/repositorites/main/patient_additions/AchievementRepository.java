package org.pah_monitoring.main.repositorites.main.patient_additions;

import org.pah_monitoring.main.entities.main.patient_additions.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Integer> {

}
