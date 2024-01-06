package org.pah_monitoring.main.repositorites.examinations.schedule;

import org.pah_monitoring.main.entities.examinations.schedule.ExaminationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExaminationScheduleRepository extends JpaRepository<ExaminationSchedule, Integer> {

}
