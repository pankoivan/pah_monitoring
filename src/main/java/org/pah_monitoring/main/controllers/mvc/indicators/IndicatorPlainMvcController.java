package org.pah_monitoring.main.controllers.mvc.indicators;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.dto.in.examinations.indicators.OverallHealthAddingDto;
import org.pah_monitoring.main.dto.in.examinations.indicators.PhysicalChangesAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientAddingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientEditingDto;
import org.pah_monitoring.main.dto.in.users.users.patient.PatientSavingDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.plain.OverallHealthPlainDto;
import org.pah_monitoring.main.dto.out.examinations.indicators.plain.PhysicalChangesPlainDto;
import org.pah_monitoring.main.entities.main.examinations.indicators.AnalysisFile;
import org.pah_monitoring.main.entities.main.examinations.indicators.OverallHealth;
import org.pah_monitoring.main.entities.main.examinations.indicators.PhysicalChanges;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.FileIndicatorService;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.IndicatorService;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.InputIndicatorService;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Stream;

@AllArgsConstructor
@Controller
@RequestMapping("/patients/{patientId}/examinations/plain")
@PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
public class IndicatorPlainMvcController {

    @Qualifier("patientService")
    private final HospitalUserService<Patient, PatientAddingDto, PatientEditingDto, PatientSavingDto> patientService;

    @Qualifier("physicalChangesService")
    private final InputIndicatorService<PhysicalChanges, PhysicalChangesAddingDto> physicalChangesService;

    @Qualifier("physicalChangesPlainMapper")
    private final BaseEntityToOutDtoMapper<PhysicalChanges, PhysicalChangesPlainDto> physicalChangesPlainMapper;

    @Qualifier("overallHealthService")
    private final InputIndicatorService<OverallHealth, OverallHealthAddingDto> overallHealthService;

    @Qualifier("overallHealthPlainMapper")
    private final BaseEntityToOutDtoMapper<OverallHealth, OverallHealthPlainDto> overallHealthPlainMapper;

    @Qualifier("analysisFileService")
    private final FileIndicatorService<AnalysisFile> analysisFileService;

    private final CurrentUserCheckService checkService;

    private final PageHeaderService pageHeaderService;

    @GetMapping("/physical-changes/{id}")
    public String getPhysicalChangesPage(Model model, @PathVariable("patientId") String pathPatientId, @PathVariable("id") String pathId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            physicalChangesService.checkAccessRightsForObtaining(patient);
            model.addAttribute(
                    "physicalChanges",
                    physicalChangesPlainMapper.map(physicalChangesService.findById(physicalChangesService.parsePathId(pathId)))
            );
            model.addAttribute("patient", patient);
            model.addAttribute("isSelf", checkService.isSelf(patient));
            pageHeaderService.addHeader(model);
            return "indicators/plain/physical-changes-plain";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/overall-health/{id}")
    public String getOverallHealthPage(Model model, @PathVariable("patientId") String pathPatientId, @PathVariable("id") String pathId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            overallHealthService.checkAccessRightsForObtaining(patient);
            model.addAttribute(
                    "overallHealth",
                    overallHealthPlainMapper.map(overallHealthService.findById(overallHealthService.parsePathId(pathId)))
            );
            model.addAttribute("patient", patient);
            model.addAttribute("isSelf", checkService.isSelf(patient));
            pageHeaderService.addHeader(model);
            return "indicators/plain/overall-health-plain";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

    @GetMapping("/analysis-file/{concrete}")
    public String getAnalysisFilePage(Model model, @PathVariable("patientId") String pathPatientId, @PathVariable("concrete") String concrete) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            analysisFileService.checkAccessRightsForObtaining(patient);
            AnalysisFile.AnalysisType analysisType = AnalysisFile.AnalysisType.fromUrlPart(concrete);
            model.addAttribute("name", analysisType.getName());
            model.addAttribute("files", analysisFileService.findAllByPatientId(analysisType, patient.getId()));
            model.addAttribute("patient", patient);
            model.addAttribute("isSelf", checkService.isSelf(patient));

            model.addAttribute("periodsFirstPart", Stream.of(IndicatorService.Period.values()).skip(0).limit(3));
            model.addAttribute("periodsSecondPart", Stream.of(IndicatorService.Period.values()).skip(3).limit(3));
            model.addAttribute("periodsThirdPart", Stream.of(IndicatorService.Period.values()).skip(6).limit(3));

            pageHeaderService.addHeader(model);
            return "indicators/plain/analysis-file-plain";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
