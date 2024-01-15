package org.pah_monitoring.main.services.users.users.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.PatientSavingDto;
import org.pah_monitoring.main.entities.users.Patient;
import org.pah_monitoring.main.services.users.users.interfaces.common.UserInterface;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

public interface PatientService extends UserInterface<Patient, PatientSavingDto>, SavingValidationService<PatientSavingDto> {

}
