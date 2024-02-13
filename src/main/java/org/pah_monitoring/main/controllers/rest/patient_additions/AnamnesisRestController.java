package org.pah_monitoring.main.controllers.rest.patient_additions;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pah_monitoring.main.dto.in.patient_additions.AnamnesisAddingDto;
import org.pah_monitoring.main.dto.out.patient_additions.AnamnesisOutDto;
import org.pah_monitoring.main.entities.main.patient_additions.Anamnesis;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.DataValidationRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.DataSavingRestControllerException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.pah_monitoring.main.services.main.patient_additions.interfaces.AnamnesisService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/anamnesis")
@PreAuthorize("hasRole('PATIENT')")
public class AnamnesisRestController {

    private final AnamnesisService service;

    @Qualifier("anamnesisMapper")
    private final BaseEntityToOutDtoMapper<Anamnesis, AnamnesisOutDto> anamnesisMapper;

    @PostMapping("/add")
    public AnamnesisOutDto add(@RequestBody @Valid AnamnesisAddingDto addingDto, BindingResult bindingResult) {
        try {
            service.checkDataValidityForAdding(addingDto, bindingResult);
            return anamnesisMapper.map(service.add(addingDto));
        } catch (DataValidationServiceException e) {
            throw new DataValidationRestControllerException(e.getMessage(), e);
        } catch (DataSavingServiceException e) {
            throw new DataSavingRestControllerException(e.getMessage(), e);
        }
    }

}
