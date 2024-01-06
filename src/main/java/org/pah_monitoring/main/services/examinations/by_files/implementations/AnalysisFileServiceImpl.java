package org.pah_monitoring.main.services.examinations.by_files.implementations;

import org.pah_monitoring.main.repositorites.examinations.by_files.AnalysisFileRepository;
import org.pah_monitoring.main.services.examinations.by_files.interfaces.AnalysisFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalysisFileServiceImpl implements AnalysisFileService {

    private final AnalysisFileRepository repository;

    @Autowired
    public AnalysisFileServiceImpl(AnalysisFileRepository repository) {
        this.repository = repository;
    }

}
