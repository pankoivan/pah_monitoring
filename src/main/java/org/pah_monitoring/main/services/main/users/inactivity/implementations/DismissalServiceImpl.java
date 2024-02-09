package org.pah_monitoring.main.services.main.users.inactivity.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.users.inactivity.DismissalAddingDto;
import org.pah_monitoring.main.entities.main.users.inactivity.Dismissal;
import org.pah_monitoring.main.entities.main.users.users.common.HospitalEmployee;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.repositorites.main.users.inactivity.DismissalRepository;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.main.users.inactivity.implementations.common.AbstractHospitalEmployeeInactivityServiceImpl;
import org.pah_monitoring.main.services.main.users.info.interfaces.EmployeeInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("dismissalService")
public class DismissalServiceImpl extends AbstractHospitalEmployeeInactivityServiceImpl<Dismissal, DismissalAddingDto, HospitalEmployee> {

    private final DismissalRepository repository;

    private EmployeeInformationService employeeInformationService;

    private CurrentUserExtractionService extractionService;

    @Override
    public Dismissal add(DismissalAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    Dismissal
                            .builder()
                            .employee(employeeInformationService.findById(addingDto.getToWhomId()))
                            .author(extractionService.administrator())
                            .comment(addingDto.getComment())
                            .date(LocalDate.now())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

}
