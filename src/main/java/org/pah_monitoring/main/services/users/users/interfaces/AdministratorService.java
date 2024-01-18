package org.pah_monitoring.main.services.users.users.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.users.adding.AdministratorAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.AdministratorEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.AdministratorSavingDto;
import org.pah_monitoring.main.entities.users.users.Administrator;
import org.pah_monitoring.main.services.users.users.interfaces.common.UserService;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

public interface AdministratorService extends UserService<Administrator, AdministratorAddingDto, AdministratorEditingDto>,
        SavingValidationService<AdministratorSavingDto> {

}
