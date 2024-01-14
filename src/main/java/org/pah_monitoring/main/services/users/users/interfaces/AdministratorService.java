package org.pah_monitoring.main.services.users.users.interfaces;

import org.pah_monitoring.main.entities.dto.saving.AdministratorSavingDto;
import org.pah_monitoring.main.entities.users.Administrator;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

public interface AdministratorService extends SavingValidationService<AdministratorSavingDto> {

    Administrator save(AdministratorSavingDto administratorDto) throws DataSavingServiceException;

}
