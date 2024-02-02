package org.pah_monitoring.main.services.main.users.inactivity.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.constants.QuantityRestrictionConstants;
import org.pah_monitoring.main.dto.in.users.inactivity.DismissalAddingDto;
import org.pah_monitoring.main.entities.main.users.inactivity.Dismissal;
import org.pah_monitoring.main.entities.main.users.users.Administrator;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.HospitalEmployee;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.main.users.inactivity.DismissalRepository;
import org.pah_monitoring.main.repositorites.main.users.users.AdministratorRepository;
import org.pah_monitoring.main.repositorites.main.users.users.DoctorRepository;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.additional.users.interfaces.UserSearchingService;
import org.pah_monitoring.main.services.main.users.inactivity.interfaces.DismissalService;
import org.pah_monitoring.main.services.main.users.info.interfaces.EmployeeInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class DismissalServiceImpl implements DismissalService {

    private final DismissalRepository repository;

    private final AdministratorRepository administratorRepository;

    private final DoctorRepository doctorRepository;

    private EmployeeInformationService employeeInformationService;

    private CurrentUserExtractionService extractionService;

    private CurrentUserCheckService checkService;

    private UserSearchingService searchingService;

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

    @Override
    public void checkDataValidityForAdding(DismissalAddingDto addingDto, BindingResult bindingResult) throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

        if (addingDto.getToWhomId().equals(extractionService.hospitalEmployee().getEmployeeInformation().getId())) {
            throw new DataValidationServiceException("Невозможно назначить увольнение самому себе");
        }

        HospitalEmployee hospitalEmployee;
        try {
            hospitalEmployee = searchingService.findHospitalEmployeeByHospitalEmployeeInformationId(addingDto.getToWhomId());
        } catch (DataSearchingServiceException e) {
            throw new DataValidationServiceException(e.getMessage(), e);
        }

        if (
                hospitalEmployee instanceof Administrator &&
                        administratorRepository.count() <= QuantityRestrictionConstants.MIN_NUMBER_OF_ACTIVE_ADMINS_IN_HOSPITAL
        ) {
            throw new DataValidationServiceException(
                    "Минимальное число активных администраторов в медицинском учреждении должно составлять: %s"
                            .formatted(QuantityRestrictionConstants.MIN_NUMBER_OF_ACTIVE_ADMINS_IN_HOSPITAL)
            );
        }

        if (
                hospitalEmployee instanceof Doctor &&
                        doctorRepository.count() <= QuantityRestrictionConstants.MIN_NUMBER_OF_ACTIVE_DOCTORS_IN_HOSPITAL
        ) {
            throw new DataValidationServiceException(
                    "Минимальное число активных врачей в медицинском учреждении должно составлять: %s"
                            .formatted(QuantityRestrictionConstants.MIN_NUMBER_OF_ACTIVE_ADMINS_IN_HOSPITAL)
            );
        }

    }

    @Override
    public void checkAccessRightsForAdding(HospitalEmployee hospitalEmployee) throws NotEnoughRightsServiceException {
        if (!checkService.isAdministratorFromSameHospital(hospitalEmployee.getHospital())) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
