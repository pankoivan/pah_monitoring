package org.pah_monitoring.main.repositorites.examinations;

import org.pah_monitoring.main.entities.examinations.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Integer> {

}
