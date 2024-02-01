package org.pah_monitoring.main.services.main.users.users.interfaces.common;

import org.pah_monitoring.main.entities.additional.dto.saving.users.users.common.HospitalUserAddingInfo;
import org.pah_monitoring.main.entities.additional.dto.saving.users.users.common.HospitalUserEditingInfo;
import org.pah_monitoring.main.entities.additional.dto.saving.users.users.common.HospitalUserSavingInfo;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.HospitalUser;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.additional.validation.interfaces.data.saving.DataAddingValidationService;
import org.pah_monitoring.main.services.additional.validation.interfaces.data.saving.DataEditingValidationService;
import org.pah_monitoring.main.services.additional.validation.interfaces.data.saving.DataSavingValidationService;
import org.pah_monitoring.main.services.additional.validation.interfaces.url.UrlValidationService;

import java.util.List;

public interface HospitalUserService
        <T extends HospitalUser, M extends HospitalUserAddingInfo, R extends HospitalUserEditingInfo, N extends HospitalUserSavingInfo>
        extends DataAddingValidationService<M>, DataEditingValidationService<R>, DataSavingValidationService<N>, UrlValidationService {

    List<T> findAll();

    List<T> findAllByHospitalId(Integer hospitalId) throws DataSearchingServiceException;

    T findById(Integer id) throws DataSearchingServiceException;

    T add(M addingDto) throws DataSavingServiceException;

    T edit(R editingDto) throws DataSavingServiceException;

    void checkAccessRightsForObtainingAllInHospital(Hospital requestedHospital) throws NotEnoughRightsServiceException;

    void checkAccessRightsForObtainingConcrete(T requestedHospitalUser) throws NotEnoughRightsServiceException;

    void checkAccessRightsForEditing(T requestedEditingHospitalUser) throws NotEnoughRightsServiceException;

}
