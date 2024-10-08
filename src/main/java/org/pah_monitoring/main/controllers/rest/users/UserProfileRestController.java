package org.pah_monitoring.main.controllers.rest.users;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pah_monitoring.main.dto.in.users.info.employee.EmployeeInformationEditingDto;
import org.pah_monitoring.main.dto.in.users.info.security.UserSecurityInformationEditingDto;
import org.pah_monitoring.main.dto.in.users.info.user.UserInformationEditingDto;
import org.pah_monitoring.main.dto.out.users.info.EmployeeInformationOutDto;
import org.pah_monitoring.main.dto.out.users.info.UserInformationOutDto;
import org.pah_monitoring.main.dto.out.users.info.UserSecurityInformationOutDto;
import org.pah_monitoring.main.entities.main.users.info.EmployeeInformation;
import org.pah_monitoring.main.entities.main.users.info.UserInformation;
import org.pah_monitoring.main.entities.main.users.info.UserSecurityInformation;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.HospitalEmployee;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.User;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.forbidden.NotEnoughRightsRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.pah_monitoring.main.services.additional.users.interfaces.UserSearchingService;
import org.pah_monitoring.main.services.main.users.info.interfaces.EmployeeInformationService;
import org.pah_monitoring.main.services.main.users.info.interfaces.UserInformationService;
import org.pah_monitoring.main.services.main.users.info.interfaces.UserSecurityInformationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/user-profile")
@PreAuthorize("isAuthenticated()")
public class UserProfileRestController {

    private final UserSecurityInformationService securityInformationService;

    private final UserInformationService userInformationService;

    private final EmployeeInformationService employeeInformationService;

    private final UserSearchingService userSearchingService;

    @Qualifier("userSecurityInformationMapper")
    private final BaseEntityToOutDtoMapper<UserSecurityInformation, UserSecurityInformationOutDto> userSecurityInformationMapper;

    @Qualifier("userInformationMapper")
    private final BaseEntityToOutDtoMapper<UserInformation, UserInformationOutDto> userInformationMapper;

    @Qualifier("employeeInformationMapper")
    private final BaseEntityToOutDtoMapper<EmployeeInformation, EmployeeInformationOutDto> employeeInformationMapper;

    @PostMapping("/edit/login-info")
    public UserSecurityInformationOutDto editLoginInfo(@RequestBody @Valid UserSecurityInformationEditingDto editingDto,
                                                       BindingResult bindingResult) {
        try {
            User user = userSearchingService.findUserByUserSecurityInformationId(editingDto.getId());
            securityInformationService.checkAccessRightsForEditing(user);
            securityInformationService.checkUserActivity(user);
            securityInformationService.checkDataValidityForEditing(editingDto, bindingResult);
            return userSecurityInformationMapper.map(securityInformationService.edit(editingDto));
        } catch (DataSearchingServiceException | DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/edit/user-info")
    public UserInformationOutDto editUserInfo(@RequestBody @Valid UserInformationEditingDto editingDto,
                                              BindingResult bindingResult) {
        try {
            User user = userSearchingService.findUserByUserInformationId(editingDto.getId());
            userInformationService.checkAccessRightsForEditing(user);
            userInformationService.checkUserActivity(user);
            userInformationService.checkDataValidityForEditing(editingDto, bindingResult);
            return userInformationMapper.map(userInformationService.edit(editingDto));
        } catch (DataSearchingServiceException | DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/edit/employee-info")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'DOCTOR')")
    public EmployeeInformationOutDto editEmployeeInfo(@RequestBody @Valid EmployeeInformationEditingDto editingDto,
                                                      BindingResult bindingResult) {
        try {
            HospitalEmployee hospitalEmployee = userSearchingService.findHospitalEmployeeByEmployeeInformationId(editingDto.getId());
            employeeInformationService.checkAccessRightsForEditing(hospitalEmployee);
            employeeInformationService.checkHospitalEmployeeActivity(hospitalEmployee);
            employeeInformationService.checkDataValidityForEditing(editingDto, bindingResult);
            return employeeInformationMapper.map(employeeInformationService.edit(editingDto));
        } catch (DataSearchingServiceException | DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

}
