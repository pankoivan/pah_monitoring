package org.pah_monitoring.main.services.users.inactivity.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.users.inactivity.SickLeaveAddingDto;
import org.pah_monitoring.main.entities.users.inactivity.SickLeave;
import org.pah_monitoring.main.entities.users.users.common.interfaces.HospitalEmployee;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.repositorites.users.inactivity.SickLeaveRepository;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.users.inactivity.interfaces.SickLeaveService;
import org.pah_monitoring.main.services.users.info.interfaces.EmployeeInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class SickLeaveServiceImpl implements SickLeaveService {

    private final SickLeaveRepository repository;

    private EmployeeInformationService employeeInformationService;

    private CurrentUserExtractionService extractionService;

    private AccessRightsCheckService checkService;

    @Override
    public List<SickLeave> findAllByEmployeeInformationId(Integer id) {
        return repository.findAllByEmployeeId(id);
    }

    @Override
    public SickLeave add(SickLeaveAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    SickLeave
                            .builder()
                            .employee(employeeInformationService.findById(addingDto.getToWhomId()))
                            .author(extractionService.administrator())
                            .comment(addingDto.getComment())
                            .startDate(addingDto.getStartDate())
                            .endDate(addingDto.getEndDate())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    public void checkDataValidityForAdding(SickLeaveAddingDto addingDto, BindingResult bindingResult) throws DataValidationServiceException {
        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        if (addingDto.getStartDate().isAfter(addingDto.getEndDate())) {
            throw new DataValidationServiceException("Дата окончания отпуска должна быть больше даты начала отпуска");
        }
    }

    @Override
    public void checkAccessRightsForAdding(HospitalEmployee hospitalEmployee) throws NotEnoughRightsServiceException {
        if (!checkService.isAdministratorFromSameHospital(hospitalEmployee.getHospital())) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
