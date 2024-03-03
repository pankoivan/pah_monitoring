package org.pah_monitoring.main.controllers.rest.examinations.indicators;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.main.examinations.indicators.AnalysisFile;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.UrlValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.forbidden.NotEnoughRightsRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataDownloadingRestControllerException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataDownloadingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.FileIndicatorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@AllArgsConstructor
@RestController
@RequestMapping("/download/{filename}")
@PreAuthorize("isAuthenticated()")
public class IndicatorDownloadController {

    @Qualifier("analysisFileService")
    private final FileIndicatorService<AnalysisFile> analysisFileService;

    @GetMapping
    public ResponseEntity<byte[]> download(@PathVariable("filename") String filename) {
        try {

            AnalysisFile analysisFile = analysisFileService.findByFilename(filename);
            analysisFileService.checkAccessRightsForObtaining(analysisFile.getPatient());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(
                    ContentDisposition
                            .formData()
                            .filename(filename, StandardCharsets.UTF_8)
                            .build()
            );

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(analysisFileService.download(analysisFile));

        } catch (DataSearchingServiceException e) {
            throw new UrlValidationRestControllerException(e.getMessage(), e);
        } catch (NotEnoughRightsServiceException e) {
            throw new NotEnoughRightsRestControllerException(e.getMessage(), e);
        } catch (DataDownloadingServiceException e) {
            throw new DataDownloadingRestControllerException(e.getMessage(), e);
        }
    }

}
