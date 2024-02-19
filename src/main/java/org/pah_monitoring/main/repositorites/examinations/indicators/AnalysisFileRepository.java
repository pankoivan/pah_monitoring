package org.pah_monitoring.main.repositorites.examinations.indicators;

import org.pah_monitoring.main.entities.main.examinations.indicators.AnalysisFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalysisFileRepository extends JpaRepository<AnalysisFile, Integer> {

    List<AnalysisFile> findAllByAnalysisTypeAndPatientId(AnalysisFile.AnalysisType analysisType, Integer patientId);

}
