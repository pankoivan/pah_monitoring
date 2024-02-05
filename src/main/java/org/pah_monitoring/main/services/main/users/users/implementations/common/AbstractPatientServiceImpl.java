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
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Getter
public abstract class AbstractPatientServiceImpl extends
        AbstractHospitalUserServiceImpl<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> {

    public abstract List<Patient> findAllByDoctorId(Integer doctorId) throws DataSearchingServiceException;

    public abstract List<Patient> findAllByDoctorId(Integer doctorId, Map<String, String> parameters, EntityFilter.PageStat pageStat)
            throws DataSearchingServiceException;

    public void checkAccessRightsForObtainingDoctorPatients(Doctor requestedDoctor) throws NotEnoughRightsServiceException {
        if (!(
                getCheckService().isSelf(requestedDoctor) ||
                getCheckService().isAdministratorFromSameHospital(requestedDoctor.getHospital())
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
