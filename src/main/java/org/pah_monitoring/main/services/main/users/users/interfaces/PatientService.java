package org.pah_monitoring.main.services.main.users.users.interfaces;

import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.dto.in.users.users.patient_doctor.PatientDoctorAssigningDto;
import org.pah_monitoring.main.entities.main.patient_additions.Achievement;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

public interface PatientService extends HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> {

    void award(Patient patient, Achievement achievement);

    void assignToDoctor(Patient patient, Doctor doctor);

    void removeFromDoctor(Patient patient);

    List<Patient> findAllByDoctorId(Integer doctorId) throws DataSearchingServiceException;

    List<Patient> findAllByDoctorId(Integer doctorId, Map<String, String> parameters, EntityFilter.PageStat pageStat)
            throws DataSearchingServiceException;

    void checkDataValidityForDoctorAssigning(PatientDoctorAssigningDto assigningDto, BindingResult bindingResult)
            throws DataSearchingServiceException, DataValidationServiceException;

    void checkDataValidityForDoctorRemoval(Patient patient) throws DataValidationServiceException;

    void checkAccessRightsForDoctorAssigning(Patient patient, Doctor doctor) throws NotEnoughRightsServiceException;

    void checkAccessRightsForDoctorRemoval(Patient patient) throws NotEnoughRightsServiceException;

    void checkAccessRightsForObtainingDoctorPatients(Doctor doctor) throws NotEnoughRightsServiceException;

}
