package org.pah_monitoring.main.services.users.users.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.services.users.users.interfaces.common.UserService;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

import java.util.List;

public interface PatientService extends UserService<Patient, PatientAddingDto, PatientEditingDto>,
        SavingValidationService<PatientSavingDto> {

    List<Patient> findAllByDoctorId(Integer doctorId);

}
