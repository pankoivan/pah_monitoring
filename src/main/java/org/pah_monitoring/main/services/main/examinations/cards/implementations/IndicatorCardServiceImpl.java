package org.pah_monitoring.main.services.main.examinations.cards.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.examinations.indicators.*;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.*;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.*;
import org.pah_monitoring.main.entities.additional.indicators.FileIndicatorCard;
import org.pah_monitoring.main.entities.additional.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.additional.indicators.InputIndicatorCard;
import org.pah_monitoring.main.entities.main.examinations.indicators.*;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.main.examinations.cards.interfaces.IndicatorCardService;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.FileIndicatorService;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.InputIndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class IndicatorCardServiceImpl implements IndicatorCardService {

    @Qualifier("chestPainService")
    private InputIndicatorService<ChestPain, ChestPainAddingDto, ChestPainTablesDto, ChestPainGraphicsDto> chestPainService;

    @Qualifier("coughService")
    private InputIndicatorService<Cough, CoughAddingDto, CoughTablesDto, CoughGraphicsDto> coughService;

    @Qualifier("faintingService")
    private InputIndicatorService<Fainting, FaintingAddingDto, FaintingTablesDto, FaintingGraphicsDto> faintingService;

    @Qualifier("overallHealthService")
    private InputIndicatorService<OverallHealth, OverallHealthAddingDto, OverallHealthTablesDto, OverallHealthGraphicsDto> overallHealthService;

    @Qualifier("physicalChangesService")
    private InputIndicatorService<PhysicalChanges, PhysicalChangesAddingDto, PhysicalChangesTablesDto, PhysicalChangesGraphicsDto> physicalChangesService;

    @Qualifier("pressureService")
    private InputIndicatorService<Pressure, PressureAddingDto, PressureTablesDto, PressureGraphicsDto> pressureService;

    @Qualifier("pulseOximetryService")
    private InputIndicatorService<PulseOximetry, PulseOximetryAddingDto, PulseOximetryTablesDto, PulseOximetryGraphicsDto> pulseOximetryService;

    @Qualifier("spirometryService")
    private InputIndicatorService<Spirometry, SpirometryAddingDto, SpirometryTablesDto, SpirometryGraphicsDto> spirometryService;

    @Qualifier("vertigoService")
    private InputIndicatorService<Vertigo, VertigoAddingDto, VertigoTablesDto, VertigoGraphicsDto> vertigoService;

    @Qualifier("walkTestService")
    private InputIndicatorService<WalkTest, WalkTestAddingDto, WalkTestTablesDto, WalkTestGraphicsDto> walkTestService;

    private FileIndicatorService<AnalysisFile, AnalysisFileAddingDto> analysisFileService;

    private CurrentUserCheckService checkService;

    @Override
    public List<InputIndicatorCard> getAllInputIndicatorCardsFor(Patient patient) {
        return List.of(
                spirometryService.getInputIndicatorCardFor(patient),
                walkTestService.getInputIndicatorCardFor(patient),
                pulseOximetryService.getInputIndicatorCardFor(patient),
                coughService.getInputIndicatorCardFor(patient),
                chestPainService.getInputIndicatorCardFor(patient),
                faintingService.getInputIndicatorCardFor(patient),
                physicalChangesService.getInputIndicatorCardFor(patient),
                overallHealthService.getInputIndicatorCardFor(patient),
                vertigoService.getInputIndicatorCardFor(patient),
                pressureService.getInputIndicatorCardFor(patient)
        );
    }

    @Override
    public List<FileIndicatorCard> getAllFileIndicatorCardsFor(Patient patient) {
        return Stream.of(AnalysisFile.AnalysisType.values())
                .map(analysisType -> analysisFileService.getFileIndicatorCardFor(analysisType, patient))
                .toList();
    }

    @Override
    public List<IndicatorCard> getAllIndicatorCardsFor(Patient patient) {
        List<IndicatorCard> indicatorCards = new ArrayList<>();
        indicatorCards.addAll(getAllInputIndicatorCardsFor(patient));
        indicatorCards.addAll(getAllFileIndicatorCardsFor(patient));
        return indicatorCards;
    }

    @Override
    public void checkAccessRightsForObtainingAll(Patient patient) throws NotEnoughRightsServiceException {
        if (!(
                checkService.isSelf(patient) ||
                checkService.isOwnDoctor(patient)
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
