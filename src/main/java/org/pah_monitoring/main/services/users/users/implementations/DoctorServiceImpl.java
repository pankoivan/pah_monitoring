package org.pah_monitoring.main.services.users.users.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.DoctorAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.DoctorEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.DoctorSavingDto;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.entities.users.users.Doctor;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.users.DoctorRepository;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.users.info.interfaces.EmployeeInformationService;
import org.pah_monitoring.main.services.users.info.interfaces.UserSecurityInformationService;
import org.pah_monitoring.main.services.users.users.implementations.common.AbstractHospitalUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("doctorService")
public class DoctorServiceImpl extends
        AbstractHospitalUserServiceImpl<Doctor, DoctorAddingDto, DoctorEditingDto, DoctorSavingDto> {

    private final DoctorRepository repository;

    private UserSecurityInformationService securityInformationService;

    private EmployeeInformationService employeeInformationService;

    private HospitalService hospitalService;

    @Override
    public List<Doctor> findAll() {
        return repository.findAll();
    }

    @Override
    public Doctor findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Врач с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public List<Doctor> findAllByHospitalId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByHospitalId(hospitalService.findById(id).getId());
    }

    @Override
    public Doctor add(DoctorAddingDto addingDto) throws DataSavingServiceException {

        try {
            RegistrationSecurityCode code = getCodeService().findByStringUuid(addingDto.getCode());
            return repository.save(
                    Doctor
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
    public Doctor edit(DoctorEditingDto editingDto) throws DataSavingServiceException {

        try {
            Doctor doctor = findById(editingDto.getId());
            return repository.save(
                    Doctor
                            .builder()
                            .id(doctor.getId())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(editingDto), e);
        }

    }

    @Override
    public void checkDataValidityForAdding(DoctorAddingDto addingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        super.checkDataValidityForAdding(addingDto, bindingResult);

        checkDataValidityForSaving(addingDto, bindingResult);

        securityInformationService.checkDataValidityForSaving(addingDto.getUserSecurityInformationAddingDto(), bindingResult);
        employeeInformationService.checkDataValidityForAdding(addingDto.getEmployeeInformationAddingDto(), bindingResult);

    }

    @Override
    public void checkDataValidityForEditing(DoctorEditingDto editingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        checkDataValidityForSaving(editingDto, bindingResult);

    }

    @Override
    public void checkDataValidityForSaving(DoctorSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

    }

    @Override
    protected Role getRole() {
        return Role.DOCTOR;
    }

}
