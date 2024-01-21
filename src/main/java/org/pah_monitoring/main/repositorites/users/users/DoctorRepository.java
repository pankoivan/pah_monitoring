package org.pah_monitoring.main.repositorites.users.users;

import org.pah_monitoring.main.entities.users.users.Doctor;
import org.pah_monitoring.main.entities.users.users.common.interfaces.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Optional<UserDetails> findByUserSecurityInformationEmail(String email);

    Optional<User> findByUserSecurityInformationId(Integer id);

    Optional<User> findByEmployeeInformationUserInformationId(Integer id);

    Optional<User> findByEmployeeInformationId(Integer id);

    List<Doctor> findAllByHospitalId(Integer hospitalId);

}
