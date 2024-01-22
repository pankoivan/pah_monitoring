package org.pah_monitoring.main.repositorites.examinations.examinations;

import org.pah_monitoring.main.entities.examinations.examinations.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Integer> {

    List<Examination> findAllByPatientId(Integer id);

}
