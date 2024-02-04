package org.pah_monitoring.main.controllers.mvc.hospitals;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.pah_monitoring.main.services.additional.hospitals.interfaces.HospitalUsersStatisticsService;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/hospitals")
public class HospitalMvcController {

    private final HospitalService service;

    private final HospitalUsersStatisticsService statisticsService;

    private final CurrentUserCheckService checkService;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    @PreAuthorize("hasRole('MAIN_ADMINISTRATOR')")
    public String getHospitalsPage(Model model, @RequestParam Map<String, String> parameters) {
        EntityFilter.PageStat pageStat = new EntityFilter.PageStat();
        model.addAttribute("hospitals", service.findAll(parameters, pageStat));
        model.addAttribute("currentPage", pageStat.getCurrentPage());
        model.addAttribute("pagesCount", pageStat.getPagesCount());
        pageHeaderService.addHeader(model);
        return "hospitals/hospitals";
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String getHospitalPage(Model model, @PathVariable("id") String pathId) {
        try {
            Hospital hospital = service.findById(service.parsePathId(pathId));
            service.checkHospitalCurrentState(hospital);
            service.checkAccessRightsForObtainingConcrete(hospital);
            model.addAttribute("hospital", hospital);
            model.addAttribute("adminStat", statisticsService.getAdministratorStatistics(hospital));
            model.addAttribute("doctorStat", statisticsService.getDoctorStatistics(hospital));
            model.addAttribute("patientStat", statisticsService.getPatientStatistics(hospital));
            model.addAttribute("isMainAdmin", checkService.isMainAdministrator());
            pageHeaderService.addHeader(model);
            return "hospitals/hospital";
        } catch (UrlValidationServiceException | DataSearchingServiceException | DataValidationServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
