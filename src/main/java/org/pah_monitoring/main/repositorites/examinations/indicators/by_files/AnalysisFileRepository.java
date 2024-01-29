package org.pah_monitoring.main.repositorites.examinations.indicators.by_files;

import org.pah_monitoring.main.entities.examinations.indicators.AnalysisFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisFileRepository extends JpaRepository<AnalysisFile, Integer> {

}
