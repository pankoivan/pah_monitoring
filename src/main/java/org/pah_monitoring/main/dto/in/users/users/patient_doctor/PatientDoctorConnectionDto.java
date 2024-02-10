package org.pah_monitoring.main.dto.in.users.users.patient_doctor;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PatientDoctorConnectionDto {

    @NotNull(message = "Поле \"patientId\" не должно отсутствовать")
    private Integer patientId;

    @NotNull(message = "Поле \"doctorId\" не должно отсутствовать")
    private Integer doctorId;

}
