package org.pah_monitoring.main.services.users.users.interfaces.common;

import org.pah_monitoring.main.entities.dto.saving.users.users.common.HospitalUserAddingInfo;
import org.pah_monitoring.main.entities.dto.saving.users.users.common.HospitalUserEditingInfo;
import org.pah_monitoring.main.entities.dto.saving.users.users.common.HospitalUserSavingInfo;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.users.users.common.HospitalUser;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.NotEnoughRightsServiceException;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataAddingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataEditingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataSavingValidationService;

import java.util.List;

public interface HospitalUserService
        <T extends HospitalUser, M extends HospitalUserAddingInfo, R extends HospitalUserEditingInfo, N extends HospitalUserSavingInfo>
        extends DataAddingValidationService<M>, DataEditingValidationService<R>, DataSavingValidationService<N> {

    List<T> findAll();

    void checkAccessForObtainingUser(T requestedUser) throws NotEnoughRightsServiceException;

    T findById(Integer id) throws DataSearchingServiceException;

    void checkAccessForObtainingHospitalUsers(Hospital requestedHospital) throws NotEnoughRightsServiceException;

    List<T> findAllByHospitalId(Integer hospitalId) throws DataSearchingServiceException;

    T add(M addingDto) throws DataSavingServiceException;

    T edit(R editingDto) throws DataSearchingServiceException, DataSavingServiceException;

    void checkAccessForEditing(T requestedEditingUser) throws NotEnoughRightsServiceException;

}
