package org.pah_monitoring.main.services.hospitals.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.hospitals.HospitalSavingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.hospitals.HospitalRepository;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
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

    private HospitalRepository repository;

    @Override
    public List<Hospital> findAll() {
        return repository.findAll();
    }

    @Override
    public Hospital findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Медицинское учреждение с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public Hospital findByIdWithCurrentStateCheck(Integer id) throws DataSearchingServiceException, DataValidationServiceException {
        Hospital hospital = findById(id);
        if (hospital.getCurrentState() == Hospital.CurrentState.REGISTERED) {
            return hospital;
        } else {
            throw new DataValidationServiceException(("Невозможно получить информацию о медицинском учреждении \"%s\", " +
                    "так как оно в данный момент ещё не зарегистрировано, а только ожидает выдачи кода или регистрации администратора")
                            .formatted(hospital.getName())
            );
        }
    }

    @Override
    public Hospital add(HospitalSavingDto savingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    Hospital
                            .builder()
                            .name(savingDto.getName())
                            .oid(namesMap.get(savingDto.getName())) // todo: change in later versions
                            .currentState(Hospital.CurrentState.WAITING_CODE)
                            .date(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }
    }

    @Override
    public void codeReceived(Hospital hospital) {
        hospital.setCurrentState(Hospital.CurrentState.WAITING_REGISTRATION);
        repository.save(hospital);
    }

    @Override
    public void registered(Hospital hospital) {
        if (hospital.getCurrentState() != Hospital.CurrentState.REGISTERED) {
            hospital.setCurrentState(Hospital.CurrentState.REGISTERED);
            repository.save(hospital);
        }
    }

    @Override
    public void checkDataValidityForSaving(HospitalSavingDto savingDto, BindingResult bindingResult) throws DataValidationServiceException {
        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        if (repository.existsByName(savingDto.getName())) {
            throw new DataValidationServiceException("Медицинское учреждение \"%s\" уже существует".formatted(savingDto.getName()));
        }
        // todo: change in later versions
        if (!names.contains(savingDto.getName())) {
            throw new DataValidationServiceException("Медицинского учреждения \"%s\" нет в справочнике".formatted(savingDto.getName()));
        }
    }

}
