package org.pah_monitoring.main.services.main.hospitals.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.hospitals.HospitalAddingDto;
import org.pah_monitoring.main.entities.additional.rest_client.RegistryHospital;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.rest_client.RestClientServiceException;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.pah_monitoring.main.repositorites.main.hospitals.HospitalRepository;
import org.pah_monitoring.main.services.additional.rest_client.interfaces.RegistryRestClientService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository repository;

    private CurrentUserCheckService checkService;

    private RegistryRestClientService registryRestClientService;

    @Qualifier("hospitalFilter")
    private EntityFilter<Hospital> hospitalFilter;

    @Override
    public List<Hospital> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Hospital> findAll(Map<String, String> parameters, EntityFilter.PageStat pageStat) {
        return hospitalFilter.apply(findAll(), parameters, pageStat);
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
    public void downgrade(Hospital hospital) {
        if (hospital.getCurrentState() == Hospital.CurrentState.WAITING_REGISTRATION) {
            hospital.setCurrentState(Hospital.CurrentState.WAITING_CODE);
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
