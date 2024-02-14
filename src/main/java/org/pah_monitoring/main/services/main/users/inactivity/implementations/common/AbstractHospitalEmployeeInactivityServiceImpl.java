package org.pah_monitoring.main.services.main.users.inactivity.implementations.common;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.constants.QuantityRestrictionConstants;
import org.pah_monitoring.main.dto.in.users.inactivity.common.InactivityAddingDto;
import org.pah_monitoring.main.dto.in.users.users.administrator.AdministratorAddingDto;
import org.pah_monitoring.main.dto.in.users.users.administrator.AdministratorEditingDto;
import org.pah_monitoring.main.dto.in.users.users.administrator.AdministratorSavingDto;
import org.pah_monitoring.main.dto.in.users.users.doctor.DoctorAddingDto;
import org.pah_monitoring.main.dto.in.users.users.doctor.DoctorEditingDto;
import org.pah_monitoring.main.dto.in.users.users.doctor.DoctorSavingDto;
import org.pah_monitoring.main.entities.main.users.inactivity.common.Inactivity;
import org.pah_monitoring.main.entities.main.users.users.Administrator;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.entities.main.users.users.common.HospitalEmployee;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.additional.users.interfaces.UserSearchingService;
import org.pah_monitoring.main.services.main.users.inactivity.interfaces.common.InactivityService;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
public abstract class AbstractHospitalEmployeeInactivityServiceImpl
        <T extends Inactivity, M extends InactivityAddingDto, N extends HospitalEmployee> implements InactivityService<T, M, N> {

    @Qualifier("administratorService")
    private HospitalUserService<Administrator, AdministratorAddingDto, AdministratorEditingDto, AdministratorSavingDto> administratorService;

    @Qualifier("doctorService")
    private HospitalUserService<Doctor, DoctorAddingDto, DoctorEditingDto, DoctorSavingDto> doctorService;

    private UserSearchingService searchingService;

    private CurrentUserCheckService checkService;

    @Override
    public void checkDataValidityForAdding(M addingDto, BindingResult bindingResult) throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

        HospitalEmployee hospitalEmployee;
        try {
            hospitalEmployee = searchingService.findHospitalEmployeeByEmployeeInformationId(addingDto.getToWhomId());
        } catch (DataSearchingServiceException e) {
            throw new DataValidationServiceException(e.getMessage(), e);
        }

        if (hospitalEmployee.isNotActive()) {
            throw new DataValidationServiceException("Этот сотрудник уже находится в отпуске, на больничном или уволен");
        }

        if (checkService.isSelf(hospitalEmployee)) {
            throw new DataValidationServiceException("Нельзя назначить отпуск или больничный самому себе, а также уволить самого себя");
        }

        if (
                hospitalEmployee.isAdministrator() &&
                administratorService.count() <= QuantityRestrictionConstants.MIN_NUMBER_OF_ACTIVE_ADMINS_IN_HOSPITAL
        ) {
            throw new DataValidationServiceException(
                    "Минимальное число активных администраторов в медицинском учреждении должно составлять: %s"
                            .formatted(QuantityRestrictionConstants.MIN_NUMBER_OF_ACTIVE_ADMINS_IN_HOSPITAL)
            );
        }

        if (
                hospitalEmployee.isDoctor() &&
                doctorService.count() <= QuantityRestrictionConstants.MIN_NUMBER_OF_ACTIVE_DOCTORS_IN_HOSPITAL
        ) {
            throw new DataValidationServiceException(
                    "Минимальное число активных врачей в медицинском учреждении должно составлять: %s"
                            .formatted(QuantityRestrictionConstants.MIN_NUMBER_OF_ACTIVE_ADMINS_IN_HOSPITAL)
            );
        }

        if (
                hospitalEmployee.isDoctor() && ((Doctor) hospitalEmployee).hasPatients()
        ) {
            throw new DataValidationServiceException("""
                    У этого врача всё ещё есть пациенты, поэтому его нельзя отправлять в отпуск, на больничный или увольнять.\
                     Для осуществления этих действий переведите всех его пациентов к другому врачу
                    """
            );
        }

    }

    @Override
    public void checkAccessRightsForAdding(HospitalEmployee hospitalEmployee) throws NotEnoughRightsServiceException {
        if (
                !checkService.isAdministratorFromSameHospital(hospitalEmployee.getHospital()) ||
                checkService.isSelf(hospitalEmployee)
        ) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
