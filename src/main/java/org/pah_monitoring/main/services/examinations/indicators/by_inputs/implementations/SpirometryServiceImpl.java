package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.by_inputs.SpirometryAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.adding.PatientAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.editing.PatientEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.users.saving.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.SpirometryGraphicsDto;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.SpirometryTablesDto;
import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.Spirometry;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs.SpirometryRepository;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations.common.AbstractIndicatorServiceImpl;
import org.pah_monitoring.main.services.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("spirometryService")
public class SpirometryServiceImpl extends AbstractIndicatorServiceImpl
        <Spirometry, SpirometryAddingDto, SpirometryTablesDto, SpirometryGraphicsDto> {

    private final SpirometryRepository repository;

    @Qualifier("patientService")
    private HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Override
    public List<Spirometry> findAllByPatientId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByPatientId(patientService.findById(id).getId());
    }

    @Override
    public Spirometry add(SpirometryAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    Spirometry
                            .builder()
                            .vlc(addingDto.getVlc())
                            .avlc(addingDto.getAvlc())
                            .rlv(addingDto.getRlv())
                            .vfe1(addingDto.getVfe1())
                            .date(LocalDateTime.now())
                            .patient(getExtractionService().patient())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    protected SpirometryTablesDto toTablesDto(Spirometry spirometry) {
        return SpirometryTablesDto
                .builder()
                .build();
    }

    @Override
    protected SpirometryGraphicsDto toGraphicsDto(Spirometry spirometry) {
        return SpirometryGraphicsDto
                .builder()
                .build();
    }

}
