package org.pah_monitoring.main.services.main.users.users.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.users.users.adding.AdministratorAddingDto;
import org.pah_monitoring.main.dto.in.users.users.editing.AdministratorEditingDto;
import org.pah_monitoring.main.dto.in.users.users.saving.AdministratorSavingDto;
import org.pah_monitoring.main.entities.main.enums.Role;
import org.pah_monitoring.main.entities.main.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.entities.main.users.users.Administrator;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.pah_monitoring.main.repositorites.main.users.users.AdministratorRepository;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.main.users.info.interfaces.EmployeeInformationService;
import org.pah_monitoring.main.services.main.users.info.interfaces.UserSecurityInformationService;
import org.pah_monitoring.main.services.main.users.users.implementations.common.AbstractHospitalUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("administratorService")
public class AdministratorServiceImpl extends
        AbstractHospitalUserServiceImpl<Administrator, AdministratorAddingDto, AdministratorEditingDto, AdministratorSavingDto> {

    private final AdministratorRepository repository;

    private UserSecurityInformationService securityInformationService;

    private EmployeeInformationService employeeInformationService;

    private HospitalService hospitalService;

    @Qualifier("administratorFilter")
    private EntityFilter<Administrator> administratorFilter;

    @Override
    public List<Administrator> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Administrator> findAll(Map<String, String> parameters, EntityFilter.PageStat pageStat) {
        return administratorFilter.apply(findAll(), parameters, pageStat);
    }

    @Override
    public Administrator findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Администратор с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public List<Administrator> findAllByHospitalId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByHospitalId(hospitalService.findById(id).getId());
    }

    @Override
    public List<Administrator> findAllByHospitalId(Integer hospitalId, Map<String, String> parameters, EntityFilter.PageStat pageStat)
            throws DataSearchingServiceException {
        return administratorFilter.apply(findAllByHospitalId(hospitalId), parameters, pageStat);
    }

    @Override
    public Administrator add(AdministratorAddingDto addingDto) throws DataSavingServiceException {

        try {
            RegistrationSecurityCode code = getCodeService().findByStringUuid(addingDto.getCode());
            return repository.save(
                    Administrator
                            .builder()
                            .userSecurityInformation(securityInformationService.add(addingDto.getUserSecurityInformationAddingDto()))
                            .employeeInformation(employeeInformationService.add(addingDto.getEmployeeInformationAddingDto()))
                            .hospital(code.getHospital())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }

    }

    @Override
    public Administrator edit(AdministratorEditingDto editingDto) throws DataSavingServiceException {

        try {
            Administrator administrator = findById(editingDto.getId());
            return repository.save(
                    Administrator
                            .builder()
                            .id(administrator.getId())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(editingDto), e);
        }

    }

    @Override
    public void checkDataValidityForAdding(AdministratorAddingDto addingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        super.checkDataValidityForAdding(addingDto, bindingResult);

        checkDataValidityForSaving(addingDto, bindingResult);

        securityInformationService.checkDataValidityForAdding(addingDto.getUserSecurityInformationAddingDto(), bindingResult);
        employeeInformationService.checkDataValidityForAdding(addingDto.getEmployeeInformationAddingDto(), bindingResult);

    }

    @Override
    public void checkDataValidityForEditing(AdministratorEditingDto editingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        checkDataValidityForSaving(editingDto, bindingResult);

    }

    @Override
    public void checkDataValidityForSaving(AdministratorSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

    }

    @Override
    protected Role getRole() {
        return Role.ADMINISTRATOR;
    }

}
