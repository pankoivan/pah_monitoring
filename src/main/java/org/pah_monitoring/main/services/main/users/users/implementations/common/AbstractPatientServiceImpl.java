package org.pah_monitoring.main.services.main.users.users.implementations.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
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
                getCheckService().isSameDoctor(requestedDoctor) ||
                getCheckService().isAdministratorFromSameHospital(requestedDoctor.getHospital())
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
