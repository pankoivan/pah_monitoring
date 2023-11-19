package org.pah_monitoring.main.repositorites;

import org.pah_monitoring.main.entities.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMessageRepository extends JpaRepository<UserMessage, Integer> {

}
