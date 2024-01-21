package org.pah_monitoring.main.services.examinations.indicators.by_files.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.indicators.by_files.AnalysisFileRepository;
import org.pah_monitoring.main.services.examinations.indicators.by_files.interfaces.AnalysisFileService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AnalysisFileServiceImpl implements AnalysisFileService {

    private final AnalysisFileRepository repository;

}
