package org.pah_monitoring.main.services.hospitals.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.HospitalSavingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.exceptions.service.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.UrlValidationServiceException;
import org.pah_monitoring.main.repositorites.hospitals.HospitalRepository;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class HospitalServiceImpl implements HospitalService {

    // todo: remove in later versions

    private static final List<String> names = List.of(
            "Медицинское учреждение 1",
            "Медицинское учреждение 2",
            "Медицинское учреждение 3"
    );

    public static final Map<String, String> namesMap = Map.of(
            "Медицинское учреждение 1", "OID для медицинского учреждения 1",
            "Медицинское учреждение 2", "OID для медицинского учреждения 2",
            "Медицинское учреждение 3", "OID для медицинского учреждения 3"
    );

    private final HospitalRepository repository;

    @Override
    public Hospital save(HospitalSavingDto hospitalDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    Hospital
                            .builder()
                            .name(hospitalDto.getName())
                            .oid(namesMap.get(hospitalDto.getName())) // todo: change in later versions
                            .currentState(Hospital.CurrentState.WAITING_CODE)
                            .date(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(hospitalDto), e);
        }
    }

    @Override
    public void deleteById(Integer id) throws DataDeletionServiceException {

    }

    @Override
    public void checkDataValidityForSaving(HospitalSavingDto hospitalDto, BindingResult bindingResult) throws DataValidationServiceException {
        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        if (repository.existsByName(hospitalDto.getName())) {
            throw new DataValidationServiceException("Медицинское учреждение \"%s\" уже существует".formatted(hospitalDto.getName()));
        }
        // todo: change in later versions
        if (!names.contains(hospitalDto.getName())) {
            throw new DataValidationServiceException("Медицинского учреждения \"%s\" нет в справочнике".formatted(hospitalDto.getName()));
        }
    }

    @Override
    public void checkDataValidityForDeleting(Hospital hospital) throws DataValidationServiceException {

    }

    @Override
    public int parsePathId(String pathId) throws UrlValidationServiceException {
        return 0;
    }

}
