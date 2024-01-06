package org.pah_monitoring.main.repositorites.examinations.by_files;

import org.pah_monitoring.main.entities.examinations.by_files.AnalysisFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisFileRepository extends JpaRepository<AnalysisFile, Integer> {

}
