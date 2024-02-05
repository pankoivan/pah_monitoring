package org.pah_monitoring.main.services.main.users.users.interfaces;

import org.pah_monitoring.main.dto.in.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.main.enums.AchievementEnum;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;

import java.util.List;
import java.util.Map;

public interface PatientService extends HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> {

    void award(Patient patient, AchievementEnum achievement) throws DataSearchingServiceException, DataSavingServiceException;

    List<Patient> findAllByDoctorId(Integer doctorId) throws DataSearchingServiceException;

    List<Patient> findAllByDoctorId(Integer doctorId, Map<String, String> parameters, EntityFilter.PageStat pageStat)
            throws DataSearchingServiceException;

    void checkAccessRightsForObtainingDoctorPatients(Doctor requestedDoctor) throws NotEnoughRightsServiceException;

}
