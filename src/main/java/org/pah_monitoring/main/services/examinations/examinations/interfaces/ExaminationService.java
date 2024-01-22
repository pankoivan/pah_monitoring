package org.pah_monitoring.main.services.examinations.examinations.interfaces;

import org.pah_monitoring.main.entities.examinations.examinations.Examination;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;

import java.util.List;

public interface ExaminationService {

    List<Examination> findAllByPatientId(Integer id) throws DataSearchingServiceException;

    Examination create() throws DataSavingServiceException;

}
