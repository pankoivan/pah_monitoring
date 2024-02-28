package org.pah_monitoring.main.services.main.users.inactivity.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.users.inactivity.SickLeaveAddingDto;
import org.pah_monitoring.main.entities.main.users.inactivity.SickLeave;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.HospitalEmployee;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.users.inactivity.SickLeaveRepository;
import org.pah_monitoring.main.services.main.users.inactivity.implementations.common.AbstractHospitalEmployeeInactivityServiceImpl;
import org.pah_monitoring.main.services.main.users.info.interfaces.EmployeeInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("sickLeaveService")
public class SickLeaveServiceImpl extends AbstractHospitalEmployeeInactivityServiceImpl<SickLeave, SickLeaveAddingDto, HospitalEmployee> {

    private final SickLeaveRepository repository;

    private EmployeeInformationService employeeInformationService;

    @Override
    public SickLeave add(SickLeaveAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    SickLeave
                            .builder()
                            .employee(employeeInformationService.findById(addingDto.getToWhomId()))
                            .author(getExtractionService().administrator())
                            .comment(addingDto.getComment())
                            .startDate(LocalDate.now())
                            .endDate(addingDto.getEndDate())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    public void checkDataValidityForAdding(SickLeaveAddingDto addingDto, BindingResult bindingResult) throws DataValidationServiceException {
        super.checkDataValidityForAdding(addingDto, bindingResult);
        if (LocalDate.now().isAfter(addingDto.getEndDate())) {
            throw new DataValidationServiceException("Дата окончания больничного должна быть больше даты начала больничного");
        }
    }

}
