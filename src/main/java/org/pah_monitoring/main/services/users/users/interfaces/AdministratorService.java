package org.pah_monitoring.main.services.users.users.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.AdministratorSavingDto;
import org.pah_monitoring.main.entities.users.Administrator;
import org.pah_monitoring.main.services.users.users.interfaces.common.UserInterface;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

public interface AdministratorService extends UserInterface<Administrator, AdministratorSavingDto>, SavingValidationService<AdministratorSavingDto> {

}
