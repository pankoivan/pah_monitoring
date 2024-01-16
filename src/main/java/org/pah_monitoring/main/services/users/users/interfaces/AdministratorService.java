package org.pah_monitoring.main.services.users.users.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.AdministratorSavingDto;
import org.pah_monitoring.main.entities.users.Administrator;
import org.pah_monitoring.main.services.users.users.interfaces.common.UserService;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

public interface AdministratorService extends UserService<Administrator, AdministratorSavingDto>,
        SavingValidationService<AdministratorSavingDto> {

}
