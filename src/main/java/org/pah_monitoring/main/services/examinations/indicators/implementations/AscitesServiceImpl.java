package org.pah_monitoring.main.services.examinations.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.auxiliary.InputIndicatorCard;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.AscitesAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.AscitesGraphicsDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.AscitesTablesDto;
import org.pah_monitoring.main.entities.enums.IndicatorType;
import org.pah_monitoring.main.entities.examinations.indicators.Ascites;
import org.pah_monitoring.main.entities.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.AscitesRepository;
import org.pah_monitoring.main.services.examinations.indicators.implementations.common.AbstractInputIndicatorServiceImpl;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("ascitesService")
public class AscitesServiceImpl extends AbstractInputIndicatorServiceImpl<Ascites, AscitesAddingDto, AscitesTablesDto, AscitesGraphicsDto> {

    private final AscitesRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.ASCITES;
    }

    @Override
    public InputIndicatorCard getInputIndicatorCardFor(Patient patient) {
        return InputIndicatorCard
                .builder()
                .workingName("ascites")
                .name(getIndicatorType().getAlias())
                .filename("ascites.jpg")
                .postFormRef("/indicators/ascites")
                .tablesRef("/patients/%s/examinations/tables?ascites".formatted(patient.getId()))
                .graphicsRef("/patients/%s/examinations/graphics?ascites".formatted(patient.getId()))
                .schedule(getScheduleFor(patient).orElse(null))
                .date(getLastExaminationDateFor(patient).orElse(null))
                .build();
    }

    @Override
    public List<Ascites> findAllByPatientId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(id).getId());
    }

    @Override
    public Ascites add(AscitesAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    Ascites
                            .builder()
                            .liquidAmount(addingDto.getLiquidAmount())
                            .contentInfection(addingDto.getContentInfection())
                            .responseToDrugTherapy(addingDto.getResponseToDrugTherapy())
                            .date(LocalDateTime.now())
                            .patient(getExtractionService().patient())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    protected List<InputIndicator> findAllByPatient(Patient patient) {
        return repository.findAllByPatient(patient);
    }

    @Override
    protected AscitesTablesDto toTablesDto(Ascites ascites) {
        return AscitesTablesDto
                .builder()
                .build();
    }

    @Override
    protected AscitesGraphicsDto toGraphicsDto(Ascites ascites) {
        return AscitesGraphicsDto
                .builder()
                .build();
    }

}
