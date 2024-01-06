package org.pah_monitoring.main.repositorites.other;

import org.pah_monitoring.main.entities.other.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Integer> {

}
