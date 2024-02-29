package org.pah_monitoring.main.services.additional.users.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.HospitalEmployee;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.User;
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
    public User findUserByUserSecurityInformationId(Integer securityInformationId) throws DataSearchingServiceException {
        return Stream.of(
                        mainAdministratorRepository.findByUserSecurityInformationId(securityInformationId).orElse(null),
                        administratorRepository.findByUserSecurityInformationId(securityInformationId).orElse(null),
                        doctorRepository.findByUserSecurityInformationId(securityInformationId).orElse(null),
                        patientRepository.findByUserSecurityInformationId(securityInformationId).orElse(null)
                )
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new DataSearchingServiceException(
                        "Не существует пользователя, у которого id логин-информации равен \"%s\"".formatted(securityInformationId))
                );
    }

    @Override
    public User findUserByUserInformationId(Integer userInformationId) throws DataSearchingServiceException {
        return Stream.of(
                        mainAdministratorRepository.findByUserInformationId(userInformationId).orElse(null),
                        administratorRepository.findByEmployeeInformationUserInformationId(userInformationId).orElse(null),
                        doctorRepository.findByEmployeeInformationUserInformationId(userInformationId).orElse(null),
                        patientRepository.findByUserInformationId(userInformationId).orElse(null)
                )
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new DataSearchingServiceException(
                        "Не существует пользователя, у которого id общей информации равен \"%s\"".formatted(userInformationId))
                );
    }

    @Override
    public HospitalEmployee findHospitalEmployeeByEmployeeInformationId(Integer employeeInformationId) throws DataSearchingServiceException {
        return Stream.of(
                        administratorRepository.findByEmployeeInformationId(employeeInformationId).orElse(null),
                        doctorRepository.findByEmployeeInformationId(employeeInformationId).orElse(null)
                )
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new DataSearchingServiceException(
                        "Не существует пользователя, у которого id рабочей информации равен \"%s\"".formatted(employeeInformationId))
                );
    }

}
