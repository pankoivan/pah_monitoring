package org.pah_monitoring.main.services.examinations.examinations.interfaces;

import org.pah_monitoring.main.entities.examinations.examinations.Examination;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;

public interface ExaminationService {

    Examination create() throws DataSavingServiceException;

}
