package org.pah_monitoring.main.services.users.users.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.users.adding.DoctorAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.DoctorEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.DoctorSavingDto;
import org.pah_monitoring.main.entities.users.Doctor;
import org.pah_monitoring.main.services.users.users.interfaces.common.UserService;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

public interface DoctorService extends UserService<Doctor, DoctorAddingDto, DoctorEditingDto>,
        SavingValidationService<DoctorSavingDto> {

}
