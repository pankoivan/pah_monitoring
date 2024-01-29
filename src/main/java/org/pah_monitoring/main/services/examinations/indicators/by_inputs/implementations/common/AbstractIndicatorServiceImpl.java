package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.interfaces.common.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
public abstract class AbstractIndicatorServiceImpl<T, M, N, R> implements IndicatorService<T, M, N, R> {

    private CurrentUserExtractionService extractionService;

    private AccessRightsCheckService checkService;

    @Override
    public Optional<LocalDateTime> getLastExaminationDateFor(Patient patient) {
        return findAllByPatient(patient)
                .stream()
                .map(InputIndicator::getDate)
                .max(Comparator.comparing(Function.identity()));
    }

    @Override
    public List<N> forTables(List<T> list) {
        return list
                .stream()
                .map(this::toTablesDto)
                .toList();
    }

    @Override
    public List<R> forGraphics(List<T> list) {
        return list
                .stream()
                .map(this::toGraphicsDto)
                .toList();
    }

    @Override
    public void checkDataValidityForAdding(M addingDto, BindingResult bindingResult) throws DataValidationServiceException {
        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        if (extractionService.patient().getDoctor() == null) {
            throw new DataValidationServiceException("""
                    Вы не можете отправлять результаты наблюдений, так как на данный момент за вами не закреплён ни\
                     один врач. Ожидайте, пока администраторы назначат вам какого-нибудь врача, или обратитесь к ним\
                     посредством личных сообщений в случае долгого ожидания
                    """
            );
        }
    }

    @Override
    public void checkAccessRightsForObtainingAllByPatientId(Patient patient) throws NotEnoughRightsServiceException {
        if (!(
                checkService.isSamePatient(patient) ||
                        checkService.isOwnDoctor(patient)
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    @Override
    public void checkAccessRightsForAdding(Patient patient) throws NotEnoughRightsServiceException {
        if (!checkService.isPatient()) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    protected abstract List<InputIndicator> findAllByPatient(Patient patient);

    protected abstract N toTablesDto(T entity);

    protected abstract R toGraphicsDto(T entity);

}
