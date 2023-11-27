package org.pah_monitoring.main.controllers.mvc;

import org.pah_monitoring.auxiliary.exceptions.AuthenticationUtilsException;
import org.pah_monitoring.auxiliary.utils.TempCheck;
import org.pah_monitoring.main.repositorites.PatientExaminationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/progress")
public class ProgressController {

    @Autowired
    private PatientExaminationRepository patientExaminationRepository;

    @RequestMapping("/{id}")
    public String returnProgressPage(Model model,
                                     Authentication authentication,
                                     @PathVariable("id") int id) throws AuthenticationUtilsException {

        TempCheck.checkRole(model, authentication);

        model.addAttribute("examinations", patientExaminationRepository.findAllByPatientId(id));

        return "progress";
    }

}
