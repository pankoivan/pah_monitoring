package org.pah_monitoring.main.services.users.users.implementations.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.users.users.Doctor;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.NotEnoughRightsServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
public abstract class AbstractPatientServiceImpl extends
        AbstractHospitalUserServiceImpl<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> {

    public abstract List<Patient> findAllByDoctorId(Integer doctorId) throws DataSearchingServiceException;

    public void checkAccessRightsForObtainingDoctorPatients(Doctor requestedDoctor) throws NotEnoughRightsServiceException {
        if (!(
                getCheckService().isSameUser(requestedDoctor) ||
                getCheckService().isAdministratorFromSameHospital(requestedDoctor.getHospital())
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
