package org.pah_monitoring.main.controllers.rest.examinations.indicators;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.dto.in.examinations.indicators.*;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.*;
import org.pah_monitoring.main.entities.main.examinations.indicators.*;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.InputIndicatorService;
import org.pah_monitoring.main.services.main.users.users.interfaces.PatientService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/patients/{patientId}/examinations/graphics")
@PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
public class IndicatorGraphicRestController {

    private final PatientService patientService;

    @Qualifier("spirometryService")
    private final InputIndicatorService<Spirometry, SpirometryAddingDto> spirometryService;

    @Qualifier("spirometryGraphicMapper")
    private final BaseEntityToOutDtoListMapper<Spirometry, SpirometryGraphicDto> spirometryGraphicMapper;

    @Qualifier("walkTestService")
    private final InputIndicatorService<WalkTest, WalkTestAddingDto> walkTestService;

    @Qualifier("walkTestGraphicMapper")
    private final BaseEntityToOutDtoListMapper<WalkTest, WalkTestGraphicDto> walkTestGraphicMapper;

    @Qualifier("pulseOximetryService")
    private final InputIndicatorService<PulseOximetry, PulseOximetryAddingDto> pulseOximetryService;

    @Qualifier("pulseOximetryGraphicMapper")
    private final BaseEntityToOutDtoListMapper<PulseOximetry, PulseOximetryGraphicDto> pulseOximetryGraphicMapper;

    @Qualifier("pressureService")
    private final InputIndicatorService<Pressure, PressureAddingDto> pressureService;

    @Qualifier("pressureGraphicMapper")
    private final BaseEntityToOutDtoListMapper<Pressure, PressureGraphicDto> pressureGraphicMapper;

    @Qualifier("liquidService")
    private final InputIndicatorService<Liquid, LiquidAddingDto> liquidService;

    @Qualifier("liquidGraphicMapper")
    private final BaseEntityToOutDtoListMapper<Liquid, LiquidGraphicDto> liquidGraphicMapper;

    @Qualifier("weightService")
    private final InputIndicatorService<Weight, WeightAddingDto> weightService;

    @Qualifier("weightGraphicMapper")
    private final BaseEntityToOutDtoListMapper<Weight, WeightGraphicDto> weightGraphicMapper;

}
