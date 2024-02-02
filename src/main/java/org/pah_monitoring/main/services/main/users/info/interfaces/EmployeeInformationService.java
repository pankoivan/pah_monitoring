package org.pah_monitoring.main.services.main.users.info.interfaces;

import org.pah_monitoring.main.dto.in.users.info.adding.EmployeeInformationAddingDto;
import org.pah_monitoring.main.dto.in.users.info.editing.EmployeeInformationEditingDto;
import org.pah_monitoring.main.dto.in.users.info.saving.EmployeeInformationSavingDto;
import org.pah_monitoring.main.entities.main.users.info.EmployeeInformation;
import org.pah_monitoring.main.services.main.users.info.interfaces.common.UserInfoService;

public interface EmployeeInformationService extends
        UserInfoService<EmployeeInformation, EmployeeInformationAddingDto, EmployeeInformationEditingDto, EmployeeInformationSavingDto> {

}
