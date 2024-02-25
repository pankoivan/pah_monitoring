package org.pah_monitoring.main.controllers.rest.examinations.indicators;

import jakarta.validation.Valid;
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
import org.springframework.jdbc.datasource.embedded.ConnectionProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/indicators/add")
@PreAuthorize("hasRole('PATIENT')")
public class IndicatorFormRestController {

    @Qualifier("spirometryService")
    private final InputIndicatorService<Spirometry, SpirometryAddingDto> spirometryService;

    @Qualifier("walkTestService")
    private final InputIndicatorService<WalkTest, WalkTestAddingDto> walkTestService;

    @Qualifier("pulseOximetryService")
    private final InputIndicatorService<PulseOximetry, PulseOximetryAddingDto> pulseOximetryService;

    @Qualifier("coughService")
    private final InputIndicatorService<Cough, CoughAddingDto> coughService;

    @Qualifier("chestPainService")
    private final InputIndicatorService<ChestPain, ChestPainAddingDto> chestPainService;

    @Qualifier("faintingService")
    private final InputIndicatorService<Fainting, FaintingAddingDto> faintingService;

    @Qualifier("physicalChangesService")
    private final InputIndicatorService<PhysicalChanges, PhysicalChangesAddingDto> physicalChangesService;

    @Qualifier("overallHealthService")
    private final InputIndicatorService<OverallHealth, OverallHealthAddingDto> overallHealthService;

    @Qualifier("vertigoService")
    private final InputIndicatorService<Vertigo, VertigoAddingDto> vertigoService;

    @Qualifier("pressureService")
    private final InputIndicatorService<Pressure, PressureAddingDto> pressureService;

    @Qualifier("liquidService")
    private final InputIndicatorService<Liquid, LiquidAddingDto> liquidService;

    @Qualifier("weightService")
    private final InputIndicatorService<Weight, WeightAddingDto> weightService;

    @Qualifier("analysisFileService")
    private final FileIndicatorService<AnalysisFile, AnalysisFileAddingDto> analysisFileService;

    @PostMapping("/spirometry")
    public void addSpirometry(@RequestBody @Valid SpirometryAddingDto addingDto, BindingResult bindingResult) {
        try {
            spirometryService.checkDataValidityForAdding(addingDto, bindingResult);
            spirometryService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/walk-test")
    public void addWalkTest(@RequestBody @Valid WalkTestAddingDto addingDto, BindingResult bindingResult) {
        try {
            walkTestService.checkDataValidityForAdding(addingDto, bindingResult);
            walkTestService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/pulse-oximetry")
    public void addPulseOximetry(@RequestBody @Valid PulseOximetryAddingDto addingDto, BindingResult bindingResult) {
        try {
            pulseOximetryService.checkDataValidityForAdding(addingDto, bindingResult);
            pulseOximetryService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/cough")
    public void addCough(@RequestBody @Valid CoughAddingDto addingDto, BindingResult bindingResult) {
        try {
            coughService.checkDataValidityForAdding(addingDto, bindingResult);
            coughService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/chest-pain")
    public void addChestPain(@RequestBody @Valid ChestPainAddingDto addingDto, BindingResult bindingResult) {
        try {
            chestPainService.checkDataValidityForAdding(addingDto, bindingResult);
            chestPainService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/fainting")
    public void addFainting(@RequestBody @Valid FaintingAddingDto addingDto, BindingResult bindingResult) {
        try {
            faintingService.checkDataValidityForAdding(addingDto, bindingResult);
            faintingService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/physical-changes")
    public void addPhysicalChanges(@RequestBody @Valid PhysicalChangesAddingDto addingDto, BindingResult bindingResult) {
        try {
            physicalChangesService.checkDataValidityForAdding(addingDto, bindingResult);
            physicalChangesService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/overall-health")
    public void addOverallHealth(@RequestBody @Valid OverallHealthAddingDto addingDto, BindingResult bindingResult) {
        try {
            overallHealthService.checkDataValidityForAdding(addingDto, bindingResult);
            overallHealthService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/vertigo")
    public void addVertigo(@RequestBody @Valid VertigoAddingDto addingDto, BindingResult bindingResult) {
        try {
            vertigoService.checkDataValidityForAdding(addingDto, bindingResult);
            vertigoService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/pressure")
    public void addPressure(@RequestBody @Valid PressureAddingDto addingDto, BindingResult bindingResult) {
        try {
            pressureService.checkDataValidityForAdding(addingDto, bindingResult);
            pressureService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/liquid")
    public void addLiquid(@RequestBody @Valid LiquidAddingDto addingDto, BindingResult bindingResult) {
        try {
            liquidService.checkDataValidityForAdding(addingDto, bindingResult);
            liquidService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/weight")
    public void addWeight(@RequestBody @Valid WeightAddingDto addingDto, BindingResult bindingResult) {
        try {
            weightService.checkDataValidityForAdding(addingDto, bindingResult);
            weightService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/analysis-file/{concrete}")
    public void addAnalysisFile(@RequestPart("file") MultipartFile file, @PathVariable("concrete") String concrete) {
        /*try {
            analysisFileService.checkDataValidityForAdding(addingDto, bindingResult);
            analysisFileService.add(addingDto);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }*/
        analysisFileService.add(file, AnalysisFile.AnalysisType.fromUrlPart(concrete));
        /*System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());*/
    }

}
