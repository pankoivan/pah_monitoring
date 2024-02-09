package org.pah_monitoring.main.services.main.users.inactivity.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.users.inactivity.VacationAddingDto;
import org.pah_monitoring.main.entities.main.users.inactivity.Vacation;
import org.pah_monitoring.main.entities.main.users.users.common.HospitalEmployee;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.main.users.inactivity.VacationRepository;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.main.users.inactivity.implementations.common.AbstractHospitalEmployeeInactivityServiceImpl;
import org.pah_monitoring.main.services.main.users.info.interfaces.EmployeeInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("vacationService")
public class VacationServiceImpl extends AbstractHospitalEmployeeInactivityServiceImpl<Vacation, VacationAddingDto, HospitalEmployee> {

    private final VacationRepository repository;

    private EmployeeInformationService employeeInformationService;

    private CurrentUserExtractionService extractionService;

    @Override
    public Vacation add(VacationAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    Vacation
                            .builder()
                            .employee(employeeInformationService.findById(addingDto.getToWhomId()))
                            .author(extractionService.administrator())
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
    public void checkDataValidityForAdding(VacationAddingDto addingDto, BindingResult bindingResult) throws DataValidationServiceException {
        super.checkDataValidityForAdding(addingDto, bindingResult);
        if (LocalDate.now().isAfter(addingDto.getEndDate())) {
            throw new DataValidationServiceException("Дата окончания отпуска должна быть больше даты начала отпуска");
        }
    }

}
