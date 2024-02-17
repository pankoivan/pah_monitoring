package org.pah_monitoring.main.services.main.examinations.cards.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.examinations.indicators.*;
import org.pah_monitoring.main.entities.additional.indicators.IndicatorCard;
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

    @Qualifier("spirometryService")
    private InputIndicatorService<Spirometry, SpirometryAddingDto> spirometryService;

    @Qualifier("walkTestService")
    private InputIndicatorService<WalkTest, WalkTestAddingDto> walkTestService;

    @Qualifier("pulseOximetryService")
    private InputIndicatorService<PulseOximetry, PulseOximetryAddingDto> pulseOximetryService;

    @Qualifier("coughService")
    private InputIndicatorService<Cough, CoughAddingDto> coughService;

    @Qualifier("chestPainService")
    private InputIndicatorService<ChestPain, ChestPainAddingDto> chestPainService;

    @Qualifier("faintingService")
    private InputIndicatorService<Fainting, FaintingAddingDto> faintingService;

    @Qualifier("physicalChangesService")
    private InputIndicatorService<PhysicalChanges, PhysicalChangesAddingDto> physicalChangesService;

    @Qualifier("overallHealthService")
    private InputIndicatorService<OverallHealth, OverallHealthAddingDto> overallHealthService;

    @Qualifier("vertigoService")
    private InputIndicatorService<Vertigo, VertigoAddingDto> vertigoService;

    @Qualifier("pressureService")
    private InputIndicatorService<Pressure, PressureAddingDto> pressureService;

    @Qualifier("liquidService")
    private InputIndicatorService<Liquid, LiquidAddingDto> liquidService;

    @Qualifier("weightService")
    private InputIndicatorService<Weight, WeightAddingDto> weightService;

    @Qualifier("analysisFileService")
    private FileIndicatorService<AnalysisFile, AnalysisFileAddingDto> analysisFileService;

    private CurrentUserCheckService checkService;

    @Override
    public List<IndicatorCard> getAllIndicatorCardsFor(Patient patient) {
        List<IndicatorCard> cards = new ArrayList<>(List.of(
                spirometryService.getIndicatorCardFor(patient),
                walkTestService.getIndicatorCardFor(patient),
                pulseOximetryService.getIndicatorCardFor(patient),
                coughService.getIndicatorCardFor(patient),
                chestPainService.getIndicatorCardFor(patient),
                faintingService.getIndicatorCardFor(patient),
                physicalChangesService.getIndicatorCardFor(patient),
                overallHealthService.getIndicatorCardFor(patient),
                vertigoService.getIndicatorCardFor(patient),
                pressureService.getIndicatorCardFor(patient),
                liquidService.getIndicatorCardFor(patient),
                weightService.getIndicatorCardFor(patient)
        ));
        cards.addAll(getAllFileIndicatorCardsFor(patient));
        return cards;
    }

    @Override
    public void checkAccessRightsForObtainingAll(Patient patient) throws NotEnoughRightsServiceException {
        if (!(
                patient.isActive() &&
                (checkService.isSelf(patient) ||
                checkService.isOwnDoctor(patient)) ||
                patient.isNotActive() &&
                checkService.isDoctorFromSameHospital(patient.getHospital())
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    private List<IndicatorCard> getAllFileIndicatorCardsFor(Patient patient) {
        return Stream.of(AnalysisFile.AnalysisType.values())
                .map(analysisType -> analysisFileService.getIndicatorCardFor(analysisType, patient))
                .toList();
    }

}
