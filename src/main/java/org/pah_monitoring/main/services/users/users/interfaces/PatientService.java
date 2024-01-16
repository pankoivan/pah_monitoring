package org.pah_monitoring.main.services.users.users.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.PatientSavingDto;
import org.pah_monitoring.main.entities.users.Patient;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.services.users.users.interfaces.common.UserService;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

import java.util.List;

public interface PatientService extends UserService<Patient, PatientSavingDto>, SavingValidationService<PatientSavingDto> {

    List<Patient> findAllByDoctorId(Integer id) throws DataSearchingServiceException;

}
