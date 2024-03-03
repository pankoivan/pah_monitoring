package org.pah_monitoring.main.controllers.rest.examinations.indicators;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.main.examinations.indicators.AnalysisFile;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.UrlValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.forbidden.NotEnoughRightsRestControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.FileIndicatorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@AllArgsConstructor
@RestController
@RequestMapping("/download/analysis-file/{filename}")
@PreAuthorize("isAuthenticated()")
public class IndicatorDownloadController {

    @Value("${my.file.upload-path}")
    private final String uploadPath;

    @Qualifier("analysisFileService")
    private final FileIndicatorService<AnalysisFile> analysisFileService;

    @GetMapping
    public ResponseEntity<byte[]> download(@PathVariable("filename") String filename) {
        try {
            AnalysisFile analysisFile = analysisFileService.findByFilename(filename);
            Patient patient = analysisFile.getPatient();
            analysisFileService.checkAccessRightsForObtaining(patient);
            //return Files.readAllBytes(Paths.get(uploadPath, filename));
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = MediaType.APPLICATION_PDF;
            //headers.add(HttpHeaders.CONTENT_DISPOSITION, "");
            //headers.setContentType(new MediaType(MediaType.APPLICATION_PDF, StandardCharsets.UTF_8));
            //headers.setContentDispositionFormData("attachment", filename);
            headers.setContentDisposition(ContentDisposition.formData().filename(filename, StandardCharsets.UTF_8).build());
            return new ResponseEntity<>(Files.readAllBytes(Paths.get(uploadPath, filename)), headers, HttpStatus.OK);
        } catch (DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (IOException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        }
    }

}
