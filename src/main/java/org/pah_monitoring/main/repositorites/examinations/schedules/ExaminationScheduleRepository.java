package org.pah_monitoring.main.repositorites.examinations.schedules;

import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.schedules.ExaminationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExaminationScheduleRepository extends JpaRepository<ExaminationSchedule, Integer> {

    List<ExaminationSchedule> findAllByPatientId(Integer patientId);

    Optional<ExaminationSchedule> findByIndicatorTypeAndPatientId(IndicatorType indicatorType, Integer patientId);

}
