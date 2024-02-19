package org.pah_monitoring.main.controllers.rest.examinations.indicators;

import lombok.AllArgsConstructor;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.dto.in.examinations.indicators.PressureAddingDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.PressureGraphicsDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.tables.PressureTablesDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.Pressure;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.UrlValidationRestControllerException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoListMapper;
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
@RequestMapping("/rest/indicators")
@PreAuthorize("hasRole('PATIENT')")
public class Temp {

    private final PatientService patientService;

    @Qualifier("pressureService")
    private final GraphicTableInputIndicatorService<Pressure, PressureAddingDto, List<PressureTablesDto>, PressureGraphicsDto> pressureService;

    @GetMapping("/tables/pressure/for/{patientId}")
    public List<PressureTablesDto> get(@PathVariable("patientId") String pathPatientId) {
        try {
            System.out.println(new Mapper().mapList(pressureService.findAllByPatientId(patientService.parsePathId(pathPatientId))));
            return new Mapper().mapList(pressureService.findAllByPatientId(patientService.parsePathId(pathPatientId)));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        }
    }

    private static class Mapper implements BaseEntityToOutDtoListMapper<Pressure, PressureTablesDto> {

        @Override
        public PressureTablesDto map(Pressure pressure) {
            return PressureTablesDto
                    .builder()
                    .formattedDate(DateTimeFormatConstants.DAY_MONTH_YEAR_AT_HOUR_MINUTE_SECOND.format(pressure.getDate()))
                    .upper(pressure.getUpper())
                    .lower(pressure.getLower())
                    .afterExercise(pressure.getAfterExercise() ? "Да" : "Нет")
                    .build();
        }

    }

}
