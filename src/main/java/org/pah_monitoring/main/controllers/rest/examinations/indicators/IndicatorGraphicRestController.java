package org.pah_monitoring.main.controllers.rest.examinations.indicators;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.dto.in.examinations.indicators.*;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.graphics.*;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/patients/{patientId}/examinations/graphics")
@PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
public class IndicatorGraphicRestController {

    @Qualifier("patientService")
    private final HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

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

    @GetMapping("/spirometry")
    public List<SpirometryGraphicDto> getSpirometryGraphic(@PathVariable("patientId") String pathPatientId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            spirometryService.checkAccessRightsForObtaining(patient);
            return spirometryGraphicMapper.mapList(spirometryService.findAllByPatientId(patient.getId()));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/walk-test")
    public List<WalkTestGraphicDto> getWalkTestGraphic(@PathVariable("patientId") String pathPatientId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            walkTestService.checkAccessRightsForObtaining(patient);
            return walkTestGraphicMapper.mapList(walkTestService.findAllByPatientId(patient.getId()));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/pulse-oximetry")
    public List<PulseOximetryGraphicDto> getPulseOximetryGraphic(@PathVariable("patientId") String pathPatientId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            pulseOximetryService.checkAccessRightsForObtaining(patient);
            return pulseOximetryGraphicMapper.mapList(pulseOximetryService.findAllByPatientId(patient.getId()));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/pressure")
    public List<PressureGraphicDto> getPressureGraphic(@PathVariable("patientId") String pathPatientId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            pressureService.checkAccessRightsForObtaining(patient);
            return pressureGraphicMapper.mapList(pressureService.findAllByPatientId(patient.getId()));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/liquid")
    public List<LiquidGraphicDto> getLiquidGraphic(@PathVariable("patientId") String pathPatientId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            liquidService.checkAccessRightsForObtaining(patient);
            return liquidGraphicMapper.mapList(liquidService.findAllByPatientId(patient.getId()));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/weight")
    public List<WeightGraphicDto> getWeightGraphic(@PathVariable("patientId") String pathPatientId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            weightService.checkAccessRightsForObtaining(patient);
            return weightGraphicMapper.mapList(weightService.findAllByPatientId(patient.getId()));
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

}
