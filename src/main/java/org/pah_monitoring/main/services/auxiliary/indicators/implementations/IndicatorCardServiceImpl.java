package org.pah_monitoring.main.services.auxiliary.indicators.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.auxiliary.indicators.FileIndicatorCard;
import org.pah_monitoring.main.entities.auxiliary.indicators.IndicatorCard;
import org.pah_monitoring.main.entities.auxiliary.indicators.InputIndicatorCard;
import org.pah_monitoring.main.entities.dto.saving.examinations.indicators.*;
import org.pah_monitoring.main.entities.dto.transferring.indicators.graphics.*;
import org.pah_monitoring.main.entities.dto.transferring.indicators.tables.*;
import org.pah_monitoring.main.entities.examinations.indicators.*;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.auxiliary.indicators.interfaces.IndicatorCardService;
import org.pah_monitoring.main.services.examinations.indicators.interfaces.AnalysisFileService;
import org.pah_monitoring.main.services.examinations.indicators.interfaces.common.InputIndicatorService;
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

    private AnalysisFileService<AnalysisFile, AnalysisFileAddingDto> analysisFileService;

    @Qualifier("ascitesService")
    private InputIndicatorService<Ascites, AscitesAddingDto, AscitesTablesDto, AscitesGraphicsDto> ascitesService;

    @Qualifier("chestPainService")
    private InputIndicatorService<ChestPain, ChestPainAddingDto, ChestPainTablesDto, ChestPainGraphicsDto> chestPainService;

    @Qualifier("coughService")
    private InputIndicatorService<Cough, CoughAddingDto, CoughTablesDto, CoughGraphicsDto> coughService;

    @Qualifier("faintingService")
    private InputIndicatorService<Fainting, FaintingAddingDto, FaintingTablesDto, FaintingGraphicsDto> faintingService;

    @Qualifier("functionalClassService")
    private InputIndicatorService<FunctionalClass, FunctionalClassAddingDto, FunctionalClassTablesDto, FunctionalClassGraphicsDto> functionalClassService;

    @Qualifier("liquidAndWeightService")
    private InputIndicatorService<LiquidAndWeight, LiquidAndWeightAddingDto, LiquidAndWeightTablesDto, LiquidAndWeightGraphicsDto> liquidAndWeightService;

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

    private AccessRightsCheckService checkService;

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
                ascitesService.getInputIndicatorCardFor(patient),
                overallHealthService.getInputIndicatorCardFor(patient),
                vertigoService.getInputIndicatorCardFor(patient),
                pressureService.getInputIndicatorCardFor(patient),
                liquidAndWeightService.getInputIndicatorCardFor(patient),
                functionalClassService.getInputIndicatorCardFor(patient)
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
                checkService.isSamePatient(patient) ||
                        checkService.isOwnDoctor(patient)
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
