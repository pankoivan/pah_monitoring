package org.pah_monitoring.main.services.users.messages.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.messages.UserMessageAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.messages.UserMessageEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.messages.UserMessageSavingDto;
import org.pah_monitoring.main.entities.users.messages.UserMessage;
import org.pah_monitoring.main.exceptions.service.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataAddingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataEditingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataSavingValidationService;

public interface UserMessageService extends
        DataAddingValidationService<UserMessageAddingDto>, DataEditingValidationService<UserMessageEditingDto>,
        DataSavingValidationService<UserMessageSavingDto> {

    UserMessage findById(Integer id) throws DataSearchingServiceException;

    UserMessage add(UserMessageAddingDto addingDto) throws DataSavingServiceException;

    UserMessage edit(UserMessageEditingDto editingDto) throws DataSavingServiceException;

    void deleteById(Integer id) throws DataDeletionServiceException;

}
