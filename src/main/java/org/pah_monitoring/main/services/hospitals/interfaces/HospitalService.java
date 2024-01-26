package org.pah_monitoring.main.services.hospitals.interfaces;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.hospitals.HospitalAddingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataAddingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.url.UrlValidationService;

import java.util.List;

public interface HospitalService extends DataAddingValidationService<HospitalAddingDto>, UrlValidationService {

    List<Hospital> findAll();

    Hospital findById(Integer id) throws DataSearchingServiceException;

    Hospital add(HospitalAddingDto addingDto) throws DataSavingServiceException;

    void upgrade(Hospital hospital);

    void checkHospitalCurrentState(Hospital requestedHospital) throws DataValidationServiceException;

    void checkAccessRightsForObtainingConcrete(Hospital requestedHospital) throws NotEnoughRightsServiceException;

    AdministratorStatistics getAdministratorStatistics(Hospital hospital);

    DoctorStatistics getDoctorStatistics(Hospital hospital);

    PatientStatistics getPatientStatistics(Hospital hospital);

    @NoArgsConstructor @AllArgsConstructor @Data
    class AdministratorStatistics {
        private int activeCount;
        private int vacationCount;
        private int sickLeaveCount;
        private int dismissalCount;
    }

    @NoArgsConstructor @AllArgsConstructor @Data
    class DoctorStatistics {
        private int activeCount;
        private int vacationCount;
        private int sickLeaveCount;
        private int dismissalCount;
        private int withoutPatientsCount;
    }

    @NoArgsConstructor @AllArgsConstructor @Data
    class PatientStatistics {
        private int activeCount;
        private int inActiveCount;
        private int unallocated;
    }

}
