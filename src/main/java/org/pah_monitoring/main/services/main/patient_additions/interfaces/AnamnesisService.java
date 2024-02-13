package org.pah_monitoring.main.services.main.patient_additions.interfaces;

import org.pah_monitoring.main.dto.in.patient_additions.AnamnesisAddingDto;
import org.pah_monitoring.main.entities.main.patient_additions.Anamnesis;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataAddingValidationService;

public interface AnamnesisService extends DataAddingValidationService<AnamnesisAddingDto> {

    Anamnesis findByPatientId(Integer patientId) throws DataSearchingServiceException;

    Anamnesis add(AnamnesisAddingDto addingDto) throws DataSavingServiceException;

    void checkAccessRightsForObtaining(Patient patient) throws NotEnoughRightsServiceException;

}
