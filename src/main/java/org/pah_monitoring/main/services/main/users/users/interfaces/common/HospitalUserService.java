package org.pah_monitoring.main.services.main.users.users.interfaces.common;

import org.pah_monitoring.main.dto.in.users.users.common.interfaces.HospitalUserAddingInfo;
import org.pah_monitoring.main.dto.in.users.users.common.interfaces.HospitalUserEditingInfo;
import org.pah_monitoring.main.dto.in.users.users.common.interfaces.HospitalUserSavingInfo;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.HospitalUser;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataAddingValidationService;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataEditingValidationService;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataSavingValidationService;
import org.pah_monitoring.main.services.main.validation.interfaces.url.UrlValidationService;

import java.util.List;
import java.util.Map;

public interface HospitalUserService
        <T extends HospitalUser, M extends HospitalUserAddingInfo, R extends HospitalUserEditingInfo, N extends HospitalUserSavingInfo>
        extends DataAddingValidationService<M>, DataEditingValidationService<R>, DataSavingValidationService<N>, UrlValidationService {

    int count();

    List<T> findAll();

    List<T> findAll(Map<String, String> parameters, EntityFilter.PageStat pageStat);

    List<T> findAllByHospitalId(Integer hospitalId) throws DataSearchingServiceException;

    List<T> findAllByHospitalId(Integer hospitalId, Map<String, String> parameters, EntityFilter.PageStat pageStat)
            throws DataSearchingServiceException;

    T findById(Integer id) throws DataSearchingServiceException;

    T add(M addingDto) throws DataSavingServiceException;

    T edit(R editingDto) throws DataSavingServiceException;

    void checkAccessRightsForObtainingAllInHospital(Hospital hospital) throws NotEnoughRightsServiceException;

    void checkAccessRightsForObtainingConcrete(T hospitalUser) throws NotEnoughRightsServiceException;

    void checkAccessRightsForEditing(T hospitalUser) throws NotEnoughRightsServiceException;

}
