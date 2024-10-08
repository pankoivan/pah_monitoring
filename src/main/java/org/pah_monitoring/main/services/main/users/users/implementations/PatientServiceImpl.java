package org.pah_monitoring.main.services.main.users.users.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.users.users.doctor.DoctorAddingDto;
import org.pah_monitoring.main.dto.in.users.users.doctor.DoctorEditingDto;
import org.pah_monitoring.main.dto.in.users.users.doctor.DoctorSavingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.dto.in.users.users.patient_doctor.PatientDoctorAssigningDto;
import org.pah_monitoring.main.email.interfaces.EmailSender;
import org.pah_monitoring.main.entities.main.enums.Role;
import org.pah_monitoring.main.entities.main.patient_additions.Achievement;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.email.EmailSendingException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.pah_monitoring.main.repositorites.users.users.PatientRepository;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.main.users.info.interfaces.UserInformationService;
import org.pah_monitoring.main.services.main.users.info.interfaces.UserSecurityInformationService;
import org.pah_monitoring.main.services.main.users.users.implementations.common.AbstractHospitalUserServiceImpl;
import org.pah_monitoring.main.services.main.users.users.interfaces.PatientService;
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
public class PatientServiceImpl extends AbstractHospitalUserServiceImpl<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto>
        implements PatientService {

    private final PatientRepository repository;

    private UserSecurityInformationService securityInformationService;

    private UserInformationService userInformationService;

    private HospitalService hospitalService;

    @Qualifier("patientFilter")
    private EntityFilter<Patient> patientFilter;

    @Qualifier("doctorService")
    private HospitalUserService<Doctor, DoctorAddingDto, DoctorEditingDto, DoctorSavingDto> doctorService;

    @Qualifier("doctorAssigningEmailSender")
    private EmailSender<Doctor> doctorAssigningEmailSender;

    @Override
    public void award(Patient patient, Achievement achievement) {
        if (patient.isActive()) {
            patient.addAchievement(achievement);
            repository.save(patient);
        }
    }

    @Override
    public void assignToDoctor(Patient patient, Doctor doctor) {
        if (patient.isActive()) {
            patient.setDoctor(doctor);
            repository.save(patient);
        }
    }

    @Override
    public void assignToDoctorAndSend(Patient patient, Doctor doctor) {
        assignToDoctor(patient, doctor);
        new Thread(() -> {
            try {
                doctorAssigningEmailSender.send(patient.getUserSecurityInformation().getEmail(), doctor);
            } catch (EmailSendingException ignored) {}
        }).start();
    }

    @Override
    public void removeFromDoctor(Patient patient) {
        if (patient.isActive()) {
            patient.removeDoctor();
            repository.save(patient);
        }
    }

    @Override
    public List<Patient> findAllByDoctorId(Integer doctorId) throws DataSearchingServiceException {
        return repository.findAllByDoctorId(doctorService.findById(doctorId).getId());
    }

    @Override
    public List<Patient> findAllByDoctorId(Integer doctorId, Map<String, String> parameters, EntityFilter.PageStat pageStat)
            throws DataSearchingServiceException {
        return patientFilter.apply(findAllByDoctorId(doctorId), parameters, pageStat);
    }

    @Override
    public int count() {
        return (int) repository.count();
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
                () -> new DataSearchingServiceException("Пациента с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public List<Patient> findAllByHospitalId(Integer hospitalId) throws DataSearchingServiceException {
        return repository.findAllByHospitalId(hospitalService.findById(hospitalId).getId());
    }

    @Override
    public List<Patient> findAllByHospitalId(Integer hospitalId, Map<String, String> parameters, EntityFilter.PageStat pageStat)
            throws DataSearchingServiceException {
        return patientFilter.apply(findAllByHospitalId(hospitalId), parameters, pageStat);
    }

    @Override
    public Patient add(PatientAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    Patient
                            .builder()
                            .userSecurityInformation(securityInformationService.add(addingDto.getUserSecurityInformationAddingDto()))
                            .userInformation(userInformationService.add(addingDto.getUserInformationAddingDto()))
                            .hospital(getCodeService().findByStringUuid(addingDto.getCode()).getHospital())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    public Patient edit(PatientEditingDto editingDto) throws DataSavingServiceException {
        try {
            return repository.save(findById(editingDto.getId()));
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(editingDto), e);
        }
    }

    @Override
    public void checkDataValidityForAdding(PatientAddingDto addingDto, BindingResult bindingResult) throws DataValidationServiceException {

        super.checkDataValidityForAdding(addingDto, bindingResult);

        checkDataValidityForSaving(addingDto, bindingResult);

        securityInformationService.checkDataValidityForAdding(addingDto.getUserSecurityInformationAddingDto(), bindingResult);
        userInformationService.checkDataValidityForAdding(addingDto.getUserInformationAddingDto(), bindingResult);

    }

    @Override
    public void checkDataValidityForEditing(PatientEditingDto editingDto, BindingResult bindingResult) throws DataValidationServiceException {
        checkDataValidityForSaving(editingDto, bindingResult);
    }

    @Override
    public void checkDataValidityForSaving(PatientSavingDto savingDto, BindingResult bindingResult) throws DataValidationServiceException {
        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
    }

    @Override
    public void checkDataValidityForDoctorAssigning(PatientDoctorAssigningDto assigningDto, BindingResult bindingResult)
            throws DataSearchingServiceException, DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

        if (findById(assigningDto.getPatientId()).isNotActive()) {
            throw new DataValidationServiceException("Пациент с id \"%s\" неактивен".formatted(assigningDto.getPatientId()));
        }

    }

    @Override
    public void checkDataValidityForDoctorRemoval(Patient patient) throws DataValidationServiceException {
        if (patient.hasNoDoctor()) {
            throw new DataValidationServiceException("У пациента с id \"%s\" нет врача".formatted(patient.getId()));
        }
        if (patient.isNotActive()) {
            throw new DataValidationServiceException("Пациент с id \"%s\" неактивен".formatted(patient.getId()));
        }
    }

    @Override
    public void checkAccessRightsForDoctorAssigning(Patient patient, Doctor doctor) throws NotEnoughRightsServiceException {
        if (!(
                getCheckService().isAdministratorFromSameHospital(patient.getHospital()) &&
                getCheckService().isAdministratorFromSameHospital(doctor.getHospital())
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    @Override
    public void checkAccessRightsForDoctorRemoval(Patient patient) throws NotEnoughRightsServiceException {
        if (!getCheckService().isAdministratorFromSameHospital(patient.getHospital())) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    @Override
    public void checkAccessRightsForObtainingDoctorPatients(Doctor requestedDoctor) throws NotEnoughRightsServiceException {
        if (!(
                getCheckService().isSelf(requestedDoctor) ||
                getCheckService().isAdministratorFromSameHospital(requestedDoctor.getHospital())
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    @Override
    protected Role getRole() {
        return Role.PATIENT;
    }

}
