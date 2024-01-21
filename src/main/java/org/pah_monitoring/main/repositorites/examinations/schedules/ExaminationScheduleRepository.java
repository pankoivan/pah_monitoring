package org.pah_monitoring.main.repositorites.examinations.schedules;

import org.pah_monitoring.main.entities.examinations.schedules.ExaminationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExaminationScheduleRepository extends JpaRepository<ExaminationSchedule, Integer> {

    List<ExaminationSchedule> findAllByPatientId(Integer id);

}
