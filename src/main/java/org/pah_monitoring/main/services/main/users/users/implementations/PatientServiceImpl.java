package org.pah_monitoring.main.services.main.users.users.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.users.users.adding.DoctorAddingDto;
import org.pah_monitoring.main.dto.in.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.editing.DoctorEditingDto;
import org.pah_monitoring.main.dto.in.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.saving.DoctorSavingDto;
import org.pah_monitoring.main.dto.in.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.main.enums.Role;
import org.pah_monitoring.main.entities.main.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.pah_monitoring.main.repositorites.main.users.users.PatientRepository;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.main.users.info.interfaces.UserInformationService;
import org.pah_monitoring.main.services.main.users.info.interfaces.UserSecurityInformationService;
import org.pah_monitoring.main.services.main.users.users.implementations.common.AbstractPatientServiceImpl;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("patientService")
public class PatientServiceImpl extends AbstractPatientServiceImpl {

    private final PatientRepository repository;

    private UserSecurityInformationService securityInformationService;

    private UserInformationService userInformationService;

    private HospitalService hospitalService;

    @Qualifier("patientFilter")
    private EntityFilter<Patient> patientFilter;

    @Qualifier("doctorService")
    private HospitalUserService<Doctor, DoctorAddingDto, DoctorEditingDto, DoctorSavingDto> doctorService;

    @Override
    public List<Patient> findAllByDoctorId(Integer doctorId) throws DataSearchingServiceException {
        return doctorService.findById(doctorId).getPatients();
    }

    @Override
    public List<Patient> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Patient> findAll(Map<String, String> parameters, EntityFilter.PageStat pageStat) {
        return patientFilter.apply(findAll(), parameters, pageStat);
    }

    @Override
    public Patient findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Пациент с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public List<Patient> findAllByHospitalId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByHospitalId(hospitalService.findById(id).getId());
    }

    @Override
    public Patient add(PatientAddingDto addingDto) throws DataSavingServiceException {

        try {
            RegistrationSecurityCode code = getCodeService().findByStringUuid(addingDto.getCode());
            return repository.save(
                    Patient
                            .builder()
                            .userSecurityInformation(securityInformationService.add(addingDto.getUserSecurityInformationAddingDto()))
                            .userInformation(userInformationService.add(addingDto.getUserInformationAddingDto()))
                            .hospital(code.getHospital())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }

    }

    @Override
    public Patient edit(PatientEditingDto savingDto) throws DataSavingServiceException {

        try {
            Patient patient = findById(savingDto.getId());
            return repository.save(
                    Patient
                            .builder()
                            .id(patient.getId())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }

    }

    @Override
    public void checkDataValidityForAdding(PatientAddingDto addingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        super.checkDataValidityForAdding(addingDto, bindingResult);

        checkDataValidityForSaving(addingDto, bindingResult);

        securityInformationService.checkDataValidityForAdding(addingDto.getUserSecurityInformationAddingDto(), bindingResult);
        userInformationService.checkDataValidityForAdding(addingDto.getUserInformationAddingDto(), bindingResult);

    }

    @Override
    public void checkDataValidityForEditing(PatientEditingDto editingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        checkDataValidityForSaving(editingDto, bindingResult);

    }

    @Override
    public void checkDataValidityForSaving(PatientSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

    }

    @Override
    protected Role getRole() {
        return Role.PATIENT;
    }

}
