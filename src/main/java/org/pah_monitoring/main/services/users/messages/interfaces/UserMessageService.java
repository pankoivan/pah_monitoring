package org.pah_monitoring.main.services.users.messages.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.messages.UserMessageAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.messages.UserMessageEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.messages.UserMessageSavingDto;
import org.pah_monitoring.main.entities.users.messages.UserMessage;
import org.pah_monitoring.main.entities.users.users.common.interfaces.User;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataAddingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataEditingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataSavingValidationService;

import java.util.List;

public interface UserMessageService extends
        DataAddingValidationService<UserMessageAddingDto>, DataEditingValidationService<UserMessageEditingDto>,
        DataSavingValidationService<UserMessageSavingDto> {

    UserMessage findById(Integer id) throws DataSearchingServiceException;

    List<User> findAllRecipients() throws DataSearchingServiceException;

    List<UserMessage> findDialogueByRecipientId(Integer recipientId);

    UserMessage add(UserMessageAddingDto addingDto) throws DataSavingServiceException;

    UserMessage edit(UserMessageEditingDto editingDto) throws DataSavingServiceException;

    void deleteById(Integer id) throws DataDeletionServiceException;

    void checkAccessRightsForAdding(User recipient) throws NotEnoughRightsServiceException;

    void checkAccessRightsForEditing(User author) throws NotEnoughRightsServiceException;

    void checkAccessRightsForDeleting(User author) throws NotEnoughRightsServiceException;

}
