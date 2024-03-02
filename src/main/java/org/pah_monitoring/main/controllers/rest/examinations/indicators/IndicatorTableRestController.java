package org.pah_monitoring.main.controllers.rest.examinations.indicators;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.dto.in.examinations.indicators.*;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.*;
import org.pah_monitoring.main.entities.main.examinations.indicators.*;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.UrlValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.forbidden.NotEnoughRightsRestControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.InputIndicatorService;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/patients/{patientId}/examinations/tables")
@PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
public class IndicatorTableRestController {

    @Qualifier("patientService")
    private final HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

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
    public List<SpirometryTableDto> getSpirometryTable(@PathVariable("patientId") String pathPatientId,
                                                       @RequestParam(value = "period", required = false) String period) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            spirometryService.checkAccessRightsForObtaining(patient);
            return spirometryTableMapper.mapList(spirometryService.findAllByPatientId(patient.getId(), period));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/walk-test")
    public List<WalkTestTableDto> getWalkTestTable(@PathVariable("patientId") String pathPatientId,
                                                   @RequestParam(value = "period", required = false) String period) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            walkTestService.checkAccessRightsForObtaining(patient);
            return walkTestTableMapper.mapList(walkTestService.findAllByPatientId(patient.getId(), period));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/pulse-oximetry")
    public List<PulseOximetryTableDto> getPulseOximetryTable(@PathVariable("patientId") String pathPatientId,
                                                             @RequestParam(value = "period", required = false) String period) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            pulseOximetryService.checkAccessRightsForObtaining(patient);
            return pulseOximetryTableMapper.mapList(pulseOximetryService.findAllByPatientId(patient.getId(), period));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/cough")
    public List<CoughTableDto> getCoughTable(@PathVariable("patientId") String pathPatientId,
                                             @RequestParam(value = "period", required = false) String period) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            coughService.checkAccessRightsForObtaining(patient);
            return coughTableMapper.mapList(coughService.findAllByPatientId(patient.getId(), period));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/chest-pain")
    public List<ChestPainTableDto> getChestPainTable(@PathVariable("patientId") String pathPatientId,
                                                     @RequestParam(value = "period", required = false) String period) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            chestPainService.checkAccessRightsForObtaining(patient);
            return chestPainTableMapper.mapList(chestPainService.findAllByPatientId(patient.getId(), period));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/fainting")
    public List<FaintingTableDto> getFaintingTable(@PathVariable("patientId") String pathPatientId,
                                                   @RequestParam(value = "period", required = false) String period) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            faintingService.checkAccessRightsForObtaining(patient);
            return faintingTableMapper.mapList(faintingService.findAllByPatientId(patient.getId(), period));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/physical-changes")
    public List<PhysicalChangesTableDto> getPhysicalChangesTable(@PathVariable("patientId") String pathPatientId,
                                                                 @RequestParam(value = "period", required = false) String period) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            physicalChangesService.checkAccessRightsForObtaining(patient);
            return physicalChangesTableMapper.mapList(physicalChangesService.findAllByPatientId(patient.getId(), period));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/overall-health")
    public List<OverallHealthTableDto> getOverallHealthTable(@PathVariable("patientId") String pathPatientId,
                                                             @RequestParam(value = "period", required = false) String period) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            overallHealthService.checkAccessRightsForObtaining(patient);
            return overallHealthTableMapper.mapList(overallHealthService.findAllByPatientId(patient.getId(), period));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/vertigo")
    public List<VertigoTableDto> getVertigoTable(@PathVariable("patientId") String pathPatientId,
                                                 @RequestParam(value = "period", required = false) String period) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            vertigoService.checkAccessRightsForObtaining(patient);
            return vertigoTableMapper.mapList(vertigoService.findAllByPatientId(patient.getId(), period));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/pressure")
    public List<PressureTableDto> getPressureTable(@PathVariable("patientId") String pathPatientId,
                                                   @RequestParam(value = "period", required = false) String period) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            pressureService.checkAccessRightsForObtaining(patient);
            return pressureTableMapper.mapList(pressureService.findAllByPatientId(patient.getId(), period));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/liquid")
    public List<LiquidTableDto> getLiquidTable(@PathVariable("patientId") String pathPatientId,
                                               @RequestParam(value = "period", required = false) String period) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            liquidService.checkAccessRightsForObtaining(patient);
            return liquidTableMapper.mapList(liquidService.findAllByPatientId(patient.getId(), period));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/weight")
    public List<WeightTableDto> getWeightTable(@PathVariable("patientId") String pathPatientId,
                                               @RequestParam(value = "period", required = false) String period) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            weightService.checkAccessRightsForObtaining(patient);
            return weightTableMapper.mapList(weightService.findAllByPatientId(patient.getId(), period));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

}
