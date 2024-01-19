package org.pah_monitoring.main.services.users.users.implementations.common;

import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.users.users.Doctor;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.NotEnoughRightsServiceException;

import java.util.List;

public abstract class AbstractPatientServiceImpl extends
        AbstractHospitalUserServiceImpl<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> {

    public abstract void checkAccessForObtainingDoctorPatients(Doctor requestedDoctor) throws NotEnoughRightsServiceException;

    public abstract List<Patient> findAllByDoctorId(Integer doctorId) throws DataSearchingServiceException;

}
