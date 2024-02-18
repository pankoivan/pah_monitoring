package org.pah_monitoring.main.controllers.rest.examinations.indicators;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.dto.in.examinations.indicators.*;
import org.pah_monitoring.main.entities.main.examinations.indicators.*;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.FileIndicatorService;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.InputIndicatorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/indicators/add")
@PreAuthorize("hasRole('PATIENT')")
public class IndicatorRestController {

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

    @PostMapping("spirometry")
    public Spirometry addSpirometry(@RequestBody SpirometryAddingDto addingDto, BindingResult bindingResult) {
        try {
            spirometryService.checkDataValidityForAdding(addingDto, bindingResult);
            return spirometryService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("walk-test")
    public WalkTest addWalkTest(@RequestBody WalkTestAddingDto addingDto, BindingResult bindingResult) {
        try {
            walkTestService.checkDataValidityForAdding(addingDto, bindingResult);
            return walkTestService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("pulse-oximetry")
    public PulseOximetry addPulseOximetry(@RequestBody PulseOximetryAddingDto addingDto, BindingResult bindingResult) {
        try {
            pulseOximetryService.checkDataValidityForAdding(addingDto, bindingResult);
            return pulseOximetryService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("cough")
    public Cough addCough(@RequestBody CoughAddingDto addingDto, BindingResult bindingResult) {
        try {
            coughService.checkDataValidityForAdding(addingDto, bindingResult);
            return coughService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("chest-pain")
    public ChestPain addChestPain(@RequestBody ChestPainAddingDto addingDto, BindingResult bindingResult) {
        try {
            chestPainService.checkDataValidityForAdding(addingDto, bindingResult);
            return chestPainService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("fainting")
    public Fainting addFainting(@RequestBody FaintingAddingDto addingDto, BindingResult bindingResult) {
        try {
            faintingService.checkDataValidityForAdding(addingDto, bindingResult);
            return faintingService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("physical-changes")
    public PhysicalChanges addPhysicalChanges(@RequestBody PhysicalChangesAddingDto addingDto, BindingResult bindingResult) {
        try {
            physicalChangesService.checkDataValidityForAdding(addingDto, bindingResult);
            return physicalChangesService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("overall-health")
    public OverallHealth addOverallHealth(@RequestBody OverallHealthAddingDto addingDto, BindingResult bindingResult) {
        try {
            overallHealthService.checkDataValidityForAdding(addingDto, bindingResult);
            return overallHealthService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("vertigo")
    public Vertigo addVertigo(@RequestBody VertigoAddingDto addingDto, BindingResult bindingResult) {
        try {
            vertigoService.checkDataValidityForAdding(addingDto, bindingResult);
            return vertigoService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("pressure")
    public Pressure addPressure(@RequestBody PressureAddingDto addingDto, BindingResult bindingResult) {
        try {
            pressureService.checkDataValidityForAdding(addingDto, bindingResult);
            return pressureService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("liquid")
    public Liquid addLiquid(@RequestBody LiquidAddingDto addingDto, BindingResult bindingResult) {
        try {
            liquidService.checkDataValidityForAdding(addingDto, bindingResult);
            return liquidService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("weight")
    public Weight addWeight(@RequestBody WeightAddingDto addingDto, BindingResult bindingResult) {
        try {
            weightService.checkDataValidityForAdding(addingDto, bindingResult);
            return weightService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("analysis-file")
    public AnalysisFile addAnalysisFile(@RequestBody AnalysisFileAddingDto addingDto, BindingResult bindingResult) {
        try {
            analysisFileService.checkDataValidityForAdding(addingDto, bindingResult);
            return analysisFileService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

}
