package org.pah_monitoring.main.services.users.messages.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.users.messages.UserMessageAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.messages.UserMessageEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.messages.UserMessageSavingDto;
import org.pah_monitoring.main.entities.users.info.UserInformation;
import org.pah_monitoring.main.entities.users.messages.UserMessage;
import org.pah_monitoring.main.entities.users.users.Administrator;
import org.pah_monitoring.main.entities.users.users.common.interfaces.HospitalUser;
import org.pah_monitoring.main.entities.users.users.common.interfaces.User;
import org.pah_monitoring.main.exceptions.service.*;
import org.pah_monitoring.main.repositorites.users.messages.UserMessageRepository;
import org.pah_monitoring.main.services.auxiliary.auth.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.auxiliary.auth.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.auxiliary.users.interfaces.UserSearchingService;
import org.pah_monitoring.main.services.users.info.interfaces.UserInformationService;
import org.pah_monitoring.main.services.users.messages.interfaces.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class UserMessageServiceImpl implements UserMessageService {

    private final UserMessageRepository repository;

    private UserInformationService userInformationService;

    private CurrentUserExtractionService extractionService;

    private AccessRightsCheckService checkService;

    private UserSearchingService searchingService;

    @Override
    public UserMessage findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Сообщение с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public List<User> findAllRecipients() throws DataSearchingServiceException {

        List<UserInformation> userInfoRecipients = repository.findAllByAuthorId(extractionService.user().getUserInformation().getId())
                .stream()
                .map(UserMessage::getRecipient)
                .distinct()
                .toList();

        List<User> usersRecipients = new ArrayList<>();

        for (UserInformation info : userInfoRecipients) {
            usersRecipients.add(searchingService.findUserByUserInformationId(info.getId()));
        }

        return usersRecipients;

    }

    @Override
    public List<UserMessage> findDialogueByRecipientId(Integer recipientId) {
        return repository.findAllByAuthorIdAndRecipientId(extractionService.user().getUserInformation().getId(), recipientId);
    }

    @Override
    public UserMessage add(UserMessageAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    UserMessage
                            .builder()
                            .author(extractionService.user().getUserInformation())
                            .recipient(userInformationService.findById(addingDto.getRecipientId()))
                            .text(addingDto.getText())
                            .date(LocalDateTime.now())
                            .editingDate(null)
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    public UserMessage edit(UserMessageEditingDto editingDto) throws DataSavingServiceException {
        try {
            UserMessage message = findById(editingDto.getId());
            return repository.save(
                    UserMessage
                            .builder()
                            .id(message.getId())
                            .author(message.getAuthor())
                            .recipient(message.getRecipient())
                            .text(editingDto.getText())
                            .date(message.getDate())
                            .editingDate(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(editingDto), e);
        }
    }

    @Override
    public void deleteById(Integer id) throws DataDeletionServiceException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new DataDeletionServiceException("Сущность с идентификатором \"%s\" не была удалена".formatted(id), e);
        }
    }

    @Override
    public void checkDataValidityForAdding(UserMessageAddingDto addingDto, BindingResult bindingResult) throws DataValidationServiceException {
        checkDataValidityForSaving(addingDto, bindingResult);
    }

    @Override
    public void checkDataValidityForEditing(UserMessageEditingDto editingDto, BindingResult bindingResult) throws DataValidationServiceException {
        checkDataValidityForSaving(editingDto, bindingResult);
    }

    @Override
    public void checkDataValidityForSaving(UserMessageSavingDto savingDto, BindingResult bindingResult) throws DataValidationServiceException {
        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
    }

    @Override
    public void checkAccessRightsForAdding(User recipient) throws NotEnoughRightsServiceException {

        // todo: later

        if (!(
                checkService.isMainAdministrator() && recipient instanceof Administrator ||
                checkService.isHospitalUserFromSameHospital(((HospitalUser) recipient).getHospital())
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    @Override
    public void checkAccessRightsForEditing(User author) throws NotEnoughRightsServiceException {
        if (!checkService.isSameUser(author)) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    @Override
    public void checkAccessRightsForDeleting(User author) throws NotEnoughRightsServiceException {
        if (!checkService.isSameUser(author)) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
