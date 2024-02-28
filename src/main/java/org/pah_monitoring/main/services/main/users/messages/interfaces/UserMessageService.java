package org.pah_monitoring.main.services.main.users.messages.interfaces;

import org.pah_monitoring.main.dto.in.users.messages.UserMessageAddingDto;
import org.pah_monitoring.main.dto.in.users.messages.UserMessageEditingDto;
import org.pah_monitoring.main.dto.in.users.messages.UserMessageSavingDto;
import org.pah_monitoring.main.dto.out.users.messages.UserMessageOutDto;
import org.pah_monitoring.main.entities.main.users.messages.UserMessage;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.User;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataAddingValidationService;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataEditingValidationService;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataSavingValidationService;
import org.pah_monitoring.main.services.main.validation.interfaces.url.UrlValidationService;

import java.util.List;
import java.util.Map;

public interface UserMessageService extends
        DataAddingValidationService<UserMessageAddingDto>, DataEditingValidationService<UserMessageEditingDto>,
        DataSavingValidationService<UserMessageSavingDto>, UrlValidationService {

    UserMessage findById(Integer id) throws DataSearchingServiceException;

    List<User> findAllDialogues();

    List<User> findAllDialogues(Map<String, String> parameters, EntityFilter.PageStat pageStat);

    List<UserMessageOutDto> findDialogue(Integer recipientId) throws DataSearchingServiceException;

    List<UserMessageOutDto> findDialogue(Integer recipientId, Map<String, String> parameters, EntityFilter.PageStat pageStat)
            throws DataSearchingServiceException;

    UserMessage add(UserMessageAddingDto addingDto) throws DataSavingServiceException;

    UserMessage edit(UserMessageEditingDto editingDto) throws DataSavingServiceException;

    void deleteById(Integer id) throws DataDeletionServiceException;

    void checkDataValidityForActions(User recipient) throws DataValidationServiceException;

    void checkAccessRightsForAdding(User recipient) throws NotEnoughRightsServiceException;

    void checkAccessRightsForEditing(User author) throws NotEnoughRightsServiceException;

    void checkAccessRightsForDeleting(User author) throws NotEnoughRightsServiceException;

}
