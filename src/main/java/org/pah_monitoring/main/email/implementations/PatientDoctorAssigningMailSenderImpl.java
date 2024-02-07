package org.pah_monitoring.main.email.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.email.interfaces.EmailSender;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Component("doctorAssigningEmailSender")
public class PatientDoctorAssigningMailSenderImpl implements EmailSender<Doctor> {

    @Override
    public void send(String recipient, Doctor content) {

    }

}
