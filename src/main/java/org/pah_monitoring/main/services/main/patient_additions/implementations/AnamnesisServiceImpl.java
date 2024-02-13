package org.pah_monitoring.main.services.main.patient_additions.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.patient_additions.AnamnesisAddingDto;
import org.pah_monitoring.main.entities.main.patient_additions.Anamnesis;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.main.patient_additions.AnamnesisRepository;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.main.patient_additions.interfaces.AnamnesisService;
import org.pah_monitoring.main.services.main.users.users.interfaces.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class AnamnesisServiceImpl implements AnamnesisService {

    private final AnamnesisRepository repository;

    private PatientService patientService;

    private CurrentUserExtractionService extractionService;

    private CurrentUserCheckService checkService;

    @Override
    public Anamnesis findByPatientId(Integer patientId) throws DataSearchingServiceException {
        return patientService.findById(patientId).getAnamnesis();
    }

    @Override
    public Anamnesis add(AnamnesisAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    Anamnesis
                            .builder()
                            .heartDisease(addingDto.getHeartDisease())
                            .lungDisease(addingDto.getLungDisease())
                            .relativesDiseases(addingDto.getRelativesDiseases())
                            .bloodClotting(addingDto.getBloodClotting())
                            .diabetes(addingDto.getDiabetes())
                            .height(addingDto.getHeight())
                            .weight(addingDto.getWeight())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    public void checkDataValidityForAdding(AnamnesisAddingDto addingDto, BindingResult bindingResult) throws DataValidationServiceException {
        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        try {
            if (extractionService.patient().hasNoAnamnesis()) {
                throw new DataValidationServiceException("Нельзя добавить больше одного анамнеза");
            }
        } catch (NullPointerException | ClassCastException e) {
            throw new DataValidationServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void checkAccessRightsForObtaining(Patient patient) throws NotEnoughRightsServiceException {
        if (!(
                checkService.isSelf(patient) ||
                checkService.isOwnDoctor(patient)
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
