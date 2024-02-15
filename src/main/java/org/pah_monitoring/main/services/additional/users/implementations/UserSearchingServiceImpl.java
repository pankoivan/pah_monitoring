package org.pah_monitoring.main.services.additional.users.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.main.users.users.common.HospitalEmployee;
import org.pah_monitoring.main.entities.main.users.users.common.User;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.users.users.AdministratorRepository;
import org.pah_monitoring.main.repositorites.users.users.DoctorRepository;
import org.pah_monitoring.main.repositorites.users.users.MainAdministratorRepository;
import org.pah_monitoring.main.repositorites.users.users.PatientRepository;
import org.pah_monitoring.main.services.additional.users.interfaces.UserSearchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class UserSearchingServiceImpl implements UserSearchingService {

    private final MainAdministratorRepository mainAdministratorRepository;

    private final AdministratorRepository administratorRepository;

    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    @Override
    public User findUserByUserSecurityInformationId(Integer id) throws DataSearchingServiceException {
        return Stream.of(
                        mainAdministratorRepository.findByUserSecurityInformationId(id).orElse(null),
                        administratorRepository.findByUserSecurityInformationId(id).orElse(null),
                        doctorRepository.findByUserSecurityInformationId(id).orElse(null),
                        patientRepository.findByUserSecurityInformationId(id).orElse(null)
                )
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new DataSearchingServiceException(
                        "Не существует пользователя, у которого id логин-информации равен \"%s\"".formatted(id))
                );
    }

    @Override
    public User findUserByUserInformationId(Integer id) throws DataSearchingServiceException {
        return Stream.of(
                        mainAdministratorRepository.findByUserInformationId(id).orElse(null),
                        administratorRepository.findByEmployeeInformationUserInformationId(id).orElse(null),
                        doctorRepository.findByEmployeeInformationUserInformationId(id).orElse(null),
                        patientRepository.findByUserInformationId(id).orElse(null)
                )
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new DataSearchingServiceException(
                        "Не существует пользователя, у которого id общей информации равен \"%s\"".formatted(id))
                );
    }

    @Override
    public HospitalEmployee findHospitalEmployeeByEmployeeInformationId(Integer id) throws DataSearchingServiceException {
        return Stream.of(
                        administratorRepository.findByEmployeeInformationId(id).orElse(null),
                        doctorRepository.findByEmployeeInformationId(id).orElse(null)
                )
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new DataSearchingServiceException(
                        "Не существует пользователя, у которого id рабочей информации равен \"%s\"".formatted(id))
                );
    }

}
