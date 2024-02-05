package org.pah_monitoring.main.services.main.users.users.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.users.users.adding.DoctorAddingDto;
import org.pah_monitoring.main.dto.in.users.users.editing.DoctorEditingDto;
import org.pah_monitoring.main.dto.in.users.users.saving.DoctorSavingDto;
import org.pah_monitoring.main.entities.main.enums.Role;
import org.pah_monitoring.main.entities.main.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.pah_monitoring.main.repositorites.main.users.users.DoctorRepository;
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
@Service("doctorService")
public class DoctorServiceImpl extends
        AbstractHospitalUserServiceImpl<Doctor, DoctorAddingDto, DoctorEditingDto, DoctorSavingDto> {

    private final DoctorRepository repository;

    private UserSecurityInformationService securityInformationService;

    private EmployeeInformationService employeeInformationService;

    private HospitalService hospitalService;

    @Qualifier("doctorFilter")
    private EntityFilter<Doctor> doctorFilter;

    @Override
    public int count() {
        return (int) repository.count();
    }

    @Override
    public List<Doctor> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Doctor> findAll(Map<String, String> parameters, EntityFilter.PageStat pageStat) {
        return doctorFilter.apply(findAll(), parameters, pageStat);
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
    public List<Doctor> findAllByHospitalId(Integer hospitalId, Map<String, String> parameters, EntityFilter.PageStat pageStat)
            throws DataSearchingServiceException {
        return doctorFilter.apply(findAllByHospitalId(hospitalId), parameters, pageStat);
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

        securityInformationService.checkDataValidityForAdding(addingDto.getUserSecurityInformationAddingDto(), bindingResult);
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
