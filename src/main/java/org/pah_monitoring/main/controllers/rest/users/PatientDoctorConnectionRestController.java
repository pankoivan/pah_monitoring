package org.pah_monitoring.main.controllers.rest.users;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pah_monitoring.main.dto.in.users.users.doctor.DoctorAddingDto;
import org.pah_monitoring.main.dto.in.users.users.doctor.DoctorEditingDto;
import org.pah_monitoring.main.dto.in.users.users.doctor.DoctorSavingDto;
import org.pah_monitoring.main.dto.in.users.users.patient_doctor.PatientDoctorAssigningDto;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.UrlValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.forbidden.NotEnoughRightsRestControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;
import org.pah_monitoring.main.services.main.users.users.interfaces.PatientService;
import org.pah_monitoring.main.services.main.users.users.interfaces.common.HospitalUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/patient-doctor-connection")
@PreAuthorize("hasRole('ADMINISTRATOR')")
public class PatientDoctorConnectionRestController {

    private final PatientService patientService;

    @Qualifier("doctorService")
    private final HospitalUserService<Doctor, DoctorAddingDto, DoctorEditingDto, DoctorSavingDto> doctorService;

    @PostMapping("/assign")
    public Map<String, String> assignToDoctor(@RequestBody @Valid PatientDoctorAssigningDto assigningDto, BindingResult bindingResult) {
        try {
            Patient patient = patientService.findById(assigningDto.getPatientId());
            Doctor doctor = doctorService.findById(assigningDto.getDoctorId());
            patientService.checkAccessRightsForDoctorAssigning(patient, doctor);
            patientService.checkDataValidityForDoctorAssigning(assigningDto, bindingResult);
            patientService.assignToDoctorAndSend(patient, doctor);
            return Map.of(
                    "patientFullName", patient.getUserInformation().getFullName(),
                    "doctorFullName", doctor.getEmployeeInformation().getUserInformation().getFullName()
            );
        } catch (DataSearchingServiceException | DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

    @PostMapping("/remove/{patientId}")
    public Map<String, String> removeFromDoctor(@PathVariable("patientId") String pathPatientId) {
        try {
            Patient patient = patientService.findById(patientService.parsePathId(pathPatientId));
            Doctor doctor = patient.getDoctor();
            patientService.checkAccessRightsForDoctorRemoval(patient);
            patientService.checkDataValidityForDoctorRemoval(patient);
            patientService.removeFromDoctor(patient);
            return Map.of(
                    "patientFullName", patient.getUserInformation().getFullName(),
                    "doctorFullName", doctor.getEmployeeInformation().getUserInformation().getFullName()
            );
        } catch (UrlValidationServiceException | DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        }
    }

}
