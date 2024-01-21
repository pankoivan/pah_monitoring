package org.pah_monitoring.main.controllers.rest.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.users.info.editing.EmployeeInformationEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.editing.UserInformationEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.editing.UserSecurityInformationEditingDto;
import org.pah_monitoring.main.entities.users.info.EmployeeInformation;
import org.pah_monitoring.main.entities.users.info.UserInformation;
import org.pah_monitoring.main.entities.users.info.UserSecurityInformation;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.forbidden.NotEnoughRightsRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.services.auxiliary.users.interfaces.UserSearchingService;
import org.pah_monitoring.main.services.users.info.interfaces.EmployeeInformationService;
import org.pah_monitoring.main.services.users.info.interfaces.UserInformationService;
import org.pah_monitoring.main.services.users.info.interfaces.UserSecurityInformationService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/user-info-editing")
public class UserInfoEditingRestController {

    private final UserSecurityInformationService securityInformationService;

    private final UserInformationService userInformationService;

    private final EmployeeInformationService employeeInformationService;

    private final UserSearchingService userSearchingService;

    @PostMapping("/security-user-info")
    public UserSecurityInformation editSecurityUserInfo(@RequestBody @Valid UserSecurityInformationEditingDto editingDto,
                                                        BindingResult bindingResult) {
        try {
            securityInformationService.checkAccessRightsForEditing(userSearchingService.findUserByUserSecurityInformationId(editingDto.getId()));
            securityInformationService.checkDataValidityForEditing(editingDto, bindingResult);
            return securityInformationService.edit(editingDto);
        } catch (DataValidationServiceException | DataSearchingServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/common-user-info")
    public UserInformation editCommonUserInfo(@RequestBody @Valid UserInformationEditingDto editingDto,
                                              BindingResult bindingResult) {
        try {
            userInformationService.checkAccessRightsForEditing(userSearchingService.findUserByUserInformationId(editingDto.getId()));
            userInformationService.checkDataValidityForEditing(editingDto, bindingResult);
            return userInformationService.edit(editingDto);
        } catch (DataValidationServiceException | DataSearchingServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/hospital-employee-info")
    public EmployeeInformation editHospitalEmployeeInfo(@RequestBody @Valid EmployeeInformationEditingDto editingDto,
                                                        BindingResult bindingResult) {
        try {
            employeeInformationService.checkAccessRightsForEditing(userSearchingService.findUserByEmployeeInformationId(editingDto.getId()));
            employeeInformationService.checkDataValidityForEditing(editingDto, bindingResult);
            return employeeInformationService.edit(editingDto);
        } catch (DataValidationServiceException | DataSearchingServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

}
