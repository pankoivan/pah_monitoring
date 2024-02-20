package org.pah_monitoring.main.controllers.rest.examinations.indicators;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.dto.in.examinations.indicators.*;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.*;
import org.pah_monitoring.main.entities.main.examinations.indicators.*;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.UrlValidationRestControllerException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.InputIndicatorService;
import org.pah_monitoring.main.services.main.users.users.interfaces.PatientService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/patients/{patientId}/examinations/tables")
@PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
public class IndicatorTableRestController {

    private final PatientService patientService;

    @Qualifier("spirometryService")
    private final InputIndicatorService<Spirometry, SpirometryAddingDto> spirometryService;

    @Qualifier("spirometryTableMapper")
    private final BaseEntityToOutDtoListMapper<Spirometry, SpirometryTableDto> spirometryTableMapper;

    @Qualifier("walkTestService")
    private final InputIndicatorService<WalkTest, WalkTestAddingDto> walkTestService;

    @Qualifier("walkTestTableMapper")
    private final BaseEntityToOutDtoListMapper<WalkTest, WalkTestTableDto> walkTestTableMapper;

    @Qualifier("pulseOximetryService")
    private final InputIndicatorService<PulseOximetry, PulseOximetryAddingDto> pulseOximetryService;

    @Qualifier("pulseOximetryTableMapper")
    private final BaseEntityToOutDtoListMapper<PulseOximetry, PulseOximetryTableDto> pulseOximetryTableMapper;

    @Qualifier("coughService")
    private final InputIndicatorService<Cough, CoughAddingDto> coughService;

    @Qualifier("coughTableMapper")
    private final BaseEntityToOutDtoListMapper<Cough, CoughTableDto> coughTableMapper;

    @Qualifier("chestPainService")
    private final InputIndicatorService<ChestPain, ChestPainAddingDto> chestPainService;

    @Qualifier("chestPainTableMapper")
    private final BaseEntityToOutDtoListMapper<ChestPain, ChestPainTableDto> chestPainTableMapper;

    @Qualifier("faintingService")
    private final InputIndicatorService<Fainting, FaintingAddingDto> faintingService;

    @Qualifier("faintingTableMapper")
    private final BaseEntityToOutDtoListMapper<Fainting, FaintingTableDto> faintingTableMapper;

    @Qualifier("physicalChangesService")
    private final InputIndicatorService<PhysicalChanges, PhysicalChangesAddingDto> physicalChangesService;

    @Qualifier("physicalChangesTableMapper")
    private final BaseEntityToOutDtoListMapper<PhysicalChanges, PhysicalChangesTableDto> physicalChangesTableMapper;

    @Qualifier("overallHealthService")
    private final InputIndicatorService<OverallHealth, OverallHealthAddingDto> overallHealthService;

    @Qualifier("overallHealthTableMapper")
    private final BaseEntityToOutDtoListMapper<OverallHealth, OverallHealthTableDto> overallHealthTableMapper;

    @Qualifier("vertigoService")
    private final InputIndicatorService<Vertigo, VertigoAddingDto> vertigoService;

    @Qualifier("vertigoTableMapper")
    private final BaseEntityToOutDtoListMapper<Vertigo, VertigoTableDto> vertigoTableMapper;

    @Qualifier("pressureService")
    private final InputIndicatorService<Pressure, PressureAddingDto> pressureService;

    @Qualifier("pressureTableMapper")
    private final BaseEntityToOutDtoListMapper<Pressure, PressureTableDto> pressureTableMapper;

    @Qualifier("liquidService")
    private final InputIndicatorService<Liquid, LiquidAddingDto> liquidService;

    @Qualifier("liquidTableMapper")
    private final BaseEntityToOutDtoListMapper<Liquid, LiquidTableDto> liquidTableMapper;

    @Qualifier("weightService")
    private final InputIndicatorService<Weight, WeightAddingDto> weightService;

    @Qualifier("weightTableMapper")
    private final BaseEntityToOutDtoListMapper<Weight, WeightTableDto> weightTableMapper;

    @GetMapping("/spirometry")
    public List<SpirometryTableDto> getSpirometryTable(@PathVariable("patientId") String pathPatientId) {
        try {
            return spirometryTableMapper.mapList(spirometryService.findAllByPatientId(patientService.parsePathId(pathPatientId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/walk-test")
    public List<WalkTestTableDto> getWalkTestTable(@PathVariable("patientId") String pathPatientId) {
        try {
            return walkTestTableMapper.mapList(walkTestService.findAllByPatientId(patientService.parsePathId(pathPatientId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/pulse-oximetry")
    public List<PulseOximetryTableDto> getPulseOximetryTable(@PathVariable("patientId") String pathPatientId) {
        try {
            return pulseOximetryTableMapper.mapList(pulseOximetryService.findAllByPatientId(patientService.parsePathId(pathPatientId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/cough")
    public List<CoughTableDto> getCoughTable(@PathVariable("patientId") String pathPatientId) {
        try {
            return coughTableMapper.mapList(coughService.findAllByPatientId(patientService.parsePathId(pathPatientId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/chest-pain")
    public List<ChestPainTableDto> getChestPainTable(@PathVariable("patientId") String pathPatientId) {
        try {
            return chestPainTableMapper.mapList(chestPainService.findAllByPatientId(patientService.parsePathId(pathPatientId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/fainting")
    public List<FaintingTableDto> getFaintingTable(@PathVariable("patientId") String pathPatientId) {
        try {
            return faintingTableMapper.mapList(faintingService.findAllByPatientId(patientService.parsePathId(pathPatientId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/physical-changes")
    public List<PhysicalChangesTableDto> getPhysicalChangesTable(@PathVariable("patientId") String pathPatientId) {
        try {
            return physicalChangesTableMapper.mapList(physicalChangesService.findAllByPatientId(patientService.parsePathId(pathPatientId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/overall-health")
    public List<OverallHealthTableDto> getOverallHealthTable(@PathVariable("patientId") String pathPatientId) {
        try {
            return overallHealthTableMapper.mapList(overallHealthService.findAllByPatientId(patientService.parsePathId(pathPatientId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/vertigo")
    public List<VertigoTableDto> getVertigoTable(@PathVariable("patientId") String pathPatientId) {
        try {
            return vertigoTableMapper.mapList(vertigoService.findAllByPatientId(patientService.parsePathId(pathPatientId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/pressure")
    public List<PressureTableDto> getPressureTable(@PathVariable("patientId") String pathPatientId) {
        try {
            System.out.println(pressureTableMapper.mapList(pressureService.findAllByPatientId(patientService.parsePathId(pathPatientId))));
            return pressureTableMapper.mapList(pressureService.findAllByPatientId(patientService.parsePathId(pathPatientId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/liquid")
    public List<LiquidTableDto> getLiquidTable(@PathVariable("patientId") String pathPatientId) {
        try {
            return liquidTableMapper.mapList(liquidService.findAllByPatientId(patientService.parsePathId(pathPatientId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/weight")
    public List<WeightTableDto> getWeightTable(@PathVariable("patientId") String pathPatientId) {
        try {
            return weightTableMapper.mapList(weightService.findAllByPatientId(patientService.parsePathId(pathPatientId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        }
    }

}
