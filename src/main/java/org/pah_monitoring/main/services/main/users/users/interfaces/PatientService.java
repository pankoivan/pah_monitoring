package org.pah_monitoring.main.services.main.users.users.interfaces;

import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.entities.main.patient_additions.Achievement;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;

import java.util.List;
import java.util.Map;

public interface PatientService extends HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> {

    void award(Patient patient, Achievement achievement);

    void assignToDoctor(Patient patient, Doctor doctor);

    void removeFromDoctor(Patient patient);

    List<Patient> findAllByDoctorId(Integer doctorId) throws DataSearchingServiceException;

    List<Patient> findAllByDoctorId(Integer doctorId, Map<String, String> parameters, EntityFilter.PageStat pageStat)
            throws DataSearchingServiceException;

    void checkAccessRightsForObtainingDoctorPatients(Doctor requestedDoctor) throws NotEnoughRightsServiceException;

    void checkAccessRightsForPatientDoctorConnection(Patient patient) throws NotEnoughRightsServiceException;

}
