package org.pah_monitoring.main.repositorites.users.info;

import org.pah_monitoring.main.entities.main.users.info.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, Integer> {

}
