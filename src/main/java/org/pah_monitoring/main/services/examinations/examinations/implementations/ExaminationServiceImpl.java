package org.pah_monitoring.main.services.examinations.examinations.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.examinations.examinations.Examination;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.repositorites.examinations.examinations.ExaminationRepository;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.examinations.examinations.interfaces.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class ExaminationServiceImpl implements ExaminationService {

    private final ExaminationRepository repository;

    private CurrentUserExtractionService extractionService;

    @Override
    public Examination create() throws DataSavingServiceException {
        try {
            Patient patient = extractionService.patient();
            return repository.save(
                    Examination
                            .builder()
                            .patient(patient)
                            .doctor(patient.getDoctor())
                            .date(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("Наблюдение не было создано", e);
        }
    }

}
