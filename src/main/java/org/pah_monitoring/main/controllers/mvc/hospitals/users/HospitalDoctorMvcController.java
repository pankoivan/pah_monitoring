package org.pah_monitoring.main.controllers.mvc.hospitals.users;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.dto.in.users.users.doctor.DoctorAddingDto;
import org.pah_monitoring.main.dto.in.users.users.doctor.DoctorEditingDto;
import org.pah_monitoring.main.dto.in.users.users.doctor.DoctorSavingDto;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.filtration.enums.users.DoctorFiltrationProperty;
import org.pah_monitoring.main.filtration.enums.users.DoctorSortingProperty;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.pah_monitoring.main.services.additional.mvc.interfaces.PageHeaderService;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping("/hospitals/{id}/doctors")
@PreAuthorize("isAuthenticated()")
public class HospitalDoctorMvcController {

    private final HospitalService hospitalService;

    @Qualifier("doctorService")
    private final HospitalUserService<Doctor, DoctorAddingDto, DoctorEditingDto, DoctorSavingDto> service;

    private final CurrentUserCheckService checkService;

    private final PageHeaderService pageHeaderService;

    @GetMapping
    public String getHospitalDoctorsPage(Model model, @PathVariable("id") String pathId, @RequestParam Map<String, String> parameters) {
        try {
            Hospital hospital = hospitalService.findById(hospitalService.parsePathId(pathId));
            service.checkAccessRightsForObtainingAllInHospital(hospital);
            EntityFilter.PageStat pageStat = new EntityFilter.PageStat();
            model.addAttribute("users", service.findAllByHospitalId(hospital.getId(), parameters, pageStat));
            model.addAttribute("currentPage", pageStat.getCurrentPage());
            model.addAttribute("pagesCount", pageStat.getPagesCount());
            model.addAttribute("filtrationProperties",
                    checkService.isMainAdministrator() ? DoctorFiltrationProperty.subset() : DoctorFiltrationProperty.values()
            );
            model.addAttribute("sortingProperties",
                    checkService.isMainAdministrator() ? DoctorSortingProperty.subset() : DoctorSortingProperty.values()
            );
            model.addAttribute("title", "Врачи");
            model.addAttribute("usersListDescription", "Список врачей");
            model.addAttribute("emptyUsersListMessage", "Список врачей пуст");
            model.addAttribute("isCurrentUserAdministrator", checkService.isAdministrator());
            pageHeaderService.addHeader(model);
            return "users/users";
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationMvcControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsMvcControllerException(e.getMessage(), e);
        }
    }

}
