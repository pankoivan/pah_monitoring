package org.pah_monitoring.main.services.hospitals.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.hospitals.HospitalAddingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.rest_client.RegistryHospital;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.rest_client.RestClientServiceException;
import org.pah_monitoring.main.repositorites.hospitals.HospitalRepository;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.auxiliary.rest_client.interfaces.RegistryRestClientService;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository repository;

    private AccessRightsCheckService checkService;

    private RegistryRestClientService registryRestClientService;

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
            // todo: make cache for all Hospitals in registryService
            RegistryHospital registryHospital = registryRestClientService.selected(addingDto.getName()).orElseThrow();
            return repository.save(
                    Hospital
                            .builder()
                            .name(registryHospital.getName())
                            .oid(registryHospital.getOid())
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
        hospital.setDate(LocalDateTime.now());
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
        try {
            if (registryRestClientService.selected(addingDto.getName()).isEmpty()) {
                throw new DataValidationServiceException("Медицинского учреждения \"%s\" нет в справочнике".formatted(addingDto.getName()));
            }
        } catch (RestClientServiceException e) {
            throw new DataValidationServiceException(e.getMessage(), e);
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
    public void checkAccessRightsForObtainingConcrete(Hospital requestedHospital) throws NotEnoughRightsServiceException {
        if (!(
                checkService.isMainAdministrator() ||
                checkService.isHospitalUserFromSameHospital(requestedHospital)
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
