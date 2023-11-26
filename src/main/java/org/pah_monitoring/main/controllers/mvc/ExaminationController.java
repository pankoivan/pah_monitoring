package org.pah_monitoring.main.controllers.mvc;

import org.pah_monitoring.auxiliary.exceptions.AuthenticationUtilsException;
import org.pah_monitoring.auxiliary.utils.AuthenticationUtils;
import org.pah_monitoring.auxiliary.utils.TempCheck;
import org.pah_monitoring.main.entities.Patient;
import org.pah_monitoring.main.entities.PatientExamination;
import org.pah_monitoring.main.repositorites.PatientCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/examination")
public class ExaminationController {

    @Autowired
    private PatientCardRepository examinationRepository;

    @RequestMapping
    public String returnExaminationPage(Model model, Authentication authentication) throws AuthenticationUtilsException {

        TempCheck.checkRole(model, authentication);

        model.addAttribute("examination", new PatientExamination());

        return "examination";
    }

    @RequestMapping("/processing")
    public String redirectExaminationPageAfterProcessing(Model model,
                                                         Authentication authentication,
                                                         PatientExamination examination,
                                                         MultipartFile bloodTest,
                                                         MultipartFile electrocardiography,
                                                         MultipartFile radiography,
                                                         MultipartFile echocardiography,
                                                         MultipartFile chestTomography) throws IOException,
            AuthenticationUtilsException {

        //System.out.println(bloodTest.getOriginalFilename());

        if (!bloodTest.isEmpty()) {
            String name = upload(bloodTest);
            examination.setBloodTestLink(name);
        }
        if (!electrocardiography.isEmpty()) {
            String name = upload(electrocardiography);
            examination.setElectrocardiographyLink(name);
        }
        if (!radiography.isEmpty()) {
            String name = upload(radiography);
            examination.setRadiographyLink(name);
        }
        if (!echocardiography.isEmpty()) {
            String name = upload(echocardiography);
            examination.setEchocardiographyLink(name);
        }
        if (!chestTomography.isEmpty()) {
            String name = upload(chestTomography);
            examination.setChestTomographyLink(name);
        }

        Patient patient = AuthenticationUtils.extractCurrentUser(authentication, Patient.class);

        examination.setExaminationDate(LocalDate.now());
        examination.setPatient(patient);
        examination.setDoctor(patient.getDoctor());

        examinationRepository.save(examination);

        return "redirect:/progress/%s".formatted(patient.getId());
    }

    private String upload(MultipartFile file) throws IOException {
        String name = "%s%s".formatted(UUID.randomUUID(), file.getOriginalFilename());
        System.out.println(Paths.get("C:/uploads", name));
        file.transferTo(Paths.get("C:/uploads", name));
        return name;
    }

}
