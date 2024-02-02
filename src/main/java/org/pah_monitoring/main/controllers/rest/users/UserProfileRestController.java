package org.pah_monitoring.main.controllers.rest.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.dto.in.users.info.editing.EmployeeInformationEditingDto;
import org.pah_monitoring.main.dto.in.users.info.editing.UserInformationEditingDto;
import org.pah_monitoring.main.dto.in.users.info.editing.UserSecurityInformationEditingDto;
import org.pah_monitoring.main.dto.out.users.info.EmployeeInformationOutDto;
import org.pah_monitoring.main.dto.out.users.info.UserInformationOutDto;
import org.pah_monitoring.main.dto.out.users.info.UserSecurityInformationOutDto;
import org.pah_monitoring.main.entities.main.users.info.EmployeeInformation;
import org.pah_monitoring.main.entities.main.users.info.UserInformation;
import org.pah_monitoring.main.entities.main.users.info.UserSecurityInformation;
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
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/user-profile")
@PreAuthorize("isAuthenticated()")
public class UserProfileRestController {

    private final UserSecurityInformationService securityInformationService;

    @Qualifier("userSecurityInformationMapper")
    private final BaseEntityToOutDtoMapper<UserSecurityInformation, UserSecurityInformationOutDto> userSecurityInformationMapper;

    private final UserInformationService userInformationService;

    @Qualifier("userInformationMapper")
    private final BaseEntityToOutDtoMapper<UserInformation, UserInformationOutDto> userInformationMapper;

    private final EmployeeInformationService employeeInformationService;

    @Qualifier("employeeInformationMapper")
    private final BaseEntityToOutDtoMapper<EmployeeInformation, EmployeeInformationOutDto> employeeInformationMapper;

    private final UserSearchingService userSearchingService;

    /*@GetMapping("/get/user-security-info/{id}")
    public UserSecurityInformationOutDto getUserSecurityInfo(@PathVariable("id") String pathId) {
        try {
            return userSecurityInformationMapper.map(securityInformationService.findById(securityInformationService.parsePathId(pathId)));
        } catch (UrlValidationServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }*/

    @PostMapping("/edit/login-info")
    public UserSecurityInformationOutDto editLoginInfo(@RequestBody @Valid UserSecurityInformationEditingDto editingDto,
                                                       BindingResult bindingResult) {
        try {
            securityInformationService.checkAccessRightsForEditing(userSearchingService.findUserByUserSecurityInformationId(editingDto.getId()));
            securityInformationService.checkDataValidityForEditing(editingDto, bindingResult);
            return userSecurityInformationMapper.map(securityInformationService.edit(editingDto));
        } catch (DataValidationServiceException | DataSearchingServiceException e) {
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
            userInformationService.checkAccessRightsForEditing(userSearchingService.findUserByUserInformationId(editingDto.getId()));
            userInformationService.checkDataValidityForEditing(editingDto, bindingResult);
            return userInformationMapper.map(userInformationService.edit(editingDto));
        } catch (DataValidationServiceException | DataSearchingServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/edit/employee-info")
    public EmployeeInformationOutDto editEmployeeInfo(@RequestBody @Valid EmployeeInformationEditingDto editingDto,
                                                      BindingResult bindingResult) {
        try {
            employeeInformationService.checkAccessRightsForEditing(
                    userSearchingService.findHospitalEmployeeByHospitalEmployeeInformationId(editingDto.getId())
            );
            employeeInformationService.checkDataValidityForEditing(editingDto, bindingResult);
            return employeeInformationMapper.map(employeeInformationService.edit(editingDto));
        } catch (DataValidationServiceException | DataSearchingServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

}
