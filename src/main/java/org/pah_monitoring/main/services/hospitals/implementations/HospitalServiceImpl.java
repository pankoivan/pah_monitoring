package org.pah_monitoring.main.services.hospitals.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.hospitals.HospitalAddingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.repositorites.hospitals.HospitalRepository;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
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

    private final HospitalRepository repository;

    private AccessRightsCheckService checkService;

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
    public Hospital add(HospitalAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    Hospital
                            .builder()
                            .name(addingDto.getName())
                            .oid(namesMap.get(addingDto.getName())) // todo: change in later versions
                            .currentState(Hospital.CurrentState.WAITING_CODE)
                            .date(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    public void upgrade(Hospital hospital) {
        if (hospital.getCurrentState() == Hospital.CurrentState.WAITING_CODE) {
            hospital.setCurrentState(Hospital.CurrentState.WAITING_REGISTRATION);
        } else if (hospital.getCurrentState() == Hospital.CurrentState.WAITING_REGISTRATION) {
            hospital.setCurrentState(Hospital.CurrentState.REGISTERED);
        }
        repository.save(hospital);
    }

    @Override
    public void checkDataValidityForAdding(HospitalAddingDto addingDto, BindingResult bindingResult) throws DataValidationServiceException {
        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        if (repository.existsByName(addingDto.getName())) {
            throw new DataValidationServiceException("Медицинское учреждение \"%s\" уже существует".formatted(addingDto.getName()));
        }
        // todo: change in later versions
        if (!names.contains(addingDto.getName())) {
            throw new DataValidationServiceException("Медицинского учреждения \"%s\" нет в справочнике".formatted(addingDto.getName()));
        }
    }

    @Override
    public void checkHospitalCurrentState(Hospital requestedHospital) throws DataValidationServiceException {
        if (requestedHospital.getCurrentState() != Hospital.CurrentState.REGISTERED) {
            throw new DataValidationServiceException(("Невозможно получить информацию о медицинском учреждении \"%s\", " +
                    "так как оно в данный момент ещё не зарегистрировано, а только ожидает выдачи кода или регистрации администратора")
                    .formatted(requestedHospital.getName())
            );
        }
    }

    @Override
    public void checkAccessRightsForObtainingAll() throws NotEnoughRightsServiceException {
        if (!checkService.isMainAdministrator()) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    @Override
    public void checkAccessRightsForObtainingConcrete(Hospital requestedHospital) throws NotEnoughRightsServiceException {
        if (!(
                checkService.isMainAdministrator() ||
                checkService.isHospitalUserFromSameHospital(requestedHospital)
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
