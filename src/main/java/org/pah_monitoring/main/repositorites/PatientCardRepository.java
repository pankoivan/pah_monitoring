package org.pah_monitoring.main.repositorites;

import org.pah_monitoring.main.entities.PatientExamination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientCardRepository extends JpaRepository<PatientExamination, Integer> {

}
