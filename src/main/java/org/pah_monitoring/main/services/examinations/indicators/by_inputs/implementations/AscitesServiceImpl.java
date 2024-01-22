package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.AscitesAddingDto;
import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.Ascites;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs.AscitesRepository;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations.common.AbstractIndicatorServiceImpl;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.interfaces.AscitesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class AscitesServiceImpl extends AbstractIndicatorServiceImpl<Ascites, AscitesAddingDto> {

    private final AscitesRepository repository;

    private AccessRightsCheckService checkService;

    @Override
    public Ascites findById(Integer id) {
        return null;
    }

    @Override
    public List<Ascites> findAllByPatientId(Integer id) throws DataSearchingServiceException {
        return null;
    }

    @Override
    public Ascites add(AscitesAddingDto addingDto) {
        return null;
    }

}
