package org.pah_monitoring.main.repositorites;

import org.pah_monitoring.main.entities.examinations.examination.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientExaminationRepository extends JpaRepository<Examination, Integer> {

    List<Examination> findAllByPatientId(int id);

}
