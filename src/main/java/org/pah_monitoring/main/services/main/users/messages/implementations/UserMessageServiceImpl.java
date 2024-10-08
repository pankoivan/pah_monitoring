package org.pah_monitoring.main.services.main.users.messages.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.users.messages.UserMessageAddingDto;
import org.pah_monitoring.main.dto.in.users.messages.UserMessageEditingDto;
import org.pah_monitoring.main.dto.in.users.messages.UserMessageSavingDto;
import org.pah_monitoring.main.dto.out.users.messages.UserMessageOutDto;
import org.pah_monitoring.main.entities.main.users.info.UserInformation;
import org.pah_monitoring.main.entities.main.users.messages.UserMessage;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.HospitalEmployee;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.HospitalUser;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.User;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.pah_monitoring.main.repositorites.users.messages.UserMessageRepository;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.main.users.info.interfaces.UserInformationService;
import org.pah_monitoring.main.services.main.users.messages.interfaces.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class UserMessageServiceImpl implements UserMessageService {

    private final UserMessageRepository repository;

    private UserInformationService userInformationService;

    private CurrentUserExtractionService extractionService;

    private CurrentUserCheckService checkService;

    @Qualifier("userMessageMapper")
    private BaseEntityToOutDtoListMapper<UserMessage, UserMessageOutDto> userMessageMapper;

    @Override
    public UserMessage findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Сообщения с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public List<UserInformation> findAllRecipients() {

        Set<UserInformation> userInfoRecipients = repository
                .findAllByAuthorId(extractionService.user().getUserInformation().getId())
                .stream()
                .map(UserMessage::getRecipient)
                .collect(Collectors.toSet());

        Set<UserInformation> userInfoAuthors = repository
                .findAllByRecipientId(extractionService.user().getUserInformation().getId())
                .stream()
                .map(UserMessage::getAuthor)
                .collect(Collectors.toSet());

        userInfoRecipients.addAll(userInfoAuthors);

        return defaultRecipientsSorting(userInfoRecipients);

    }

    @Override
    public List<UserMessageOutDto> findAllMessagesFor(Integer recipientId) throws DataSearchingServiceException {

        List<UserMessage> authorMessages = repository.findAllByAuthorIdAndRecipientId(
                extractionService.user().getUserInformation().getId(),
                userInformationService.findById(recipientId).getId()
        );

        List<UserMessage> recipientMessages = repository.findAllByAuthorIdAndRecipientId(
                userInformationService.findById(recipientId).getId(),
                extractionService.user().getUserInformation().getId()
        );

        authorMessages.addAll(recipientMessages);

        return defaultMessagesSorting(userMessageMapper.mapList(authorMessages));

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
    public void checkDataValidityForActions(User recipient) throws DataValidationServiceException {
        if (recipient.isHospitalEmployee() && ((HospitalEmployee) recipient).isDismissed()) {
            throw new DataValidationServiceException(
                    "Сотрудник уволен, поэтому вы не можете редактировать или удалять, а также оставлять новые сообщения в диалоге с ним"
            );
        }
        if (recipient.isPatient() && recipient.isNotActive()) {
            throw new DataValidationServiceException(
                    "Пациент неактивен, поэтому вы не можете редактировать или удалять, а также оставлять новые сообщения в диалоге с ним"
            );
        }
    }

    @Override
    public void checkAccessRightsForAdding(User recipient) throws NotEnoughRightsServiceException {
        if (!(
                (checkService.isMainAdministrator() && recipient.isAdministrator() ||
                checkService.isAdministrator() && recipient.isMainAdministrator() ||
                checkService.isHospitalUserFromSameHospital(((HospitalUser) recipient).getHospital())) &&
                !checkService.isSelf(recipient)
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    @Override
    public void checkAccessRightsForEditing(User author) throws NotEnoughRightsServiceException {
        if (!checkService.isSelf(author)) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    @Override
    public void checkAccessRightsForDeleting(User author) throws NotEnoughRightsServiceException {
        if (!checkService.isSelf(author)) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    private List<UserInformation> defaultRecipientsSorting(Set<UserInformation> recipients) {
        return recipients
                .stream()
                .sorted(Comparator.comparing(UserInformation::getFullName))
                .toList();
    }

    private List<UserMessageOutDto> defaultMessagesSorting(List<UserMessageOutDto> messages) {
        return messages
                .stream()
                .sorted(Comparator.comparing(UserMessageOutDto::getDate))
                .toList();
    }

}
