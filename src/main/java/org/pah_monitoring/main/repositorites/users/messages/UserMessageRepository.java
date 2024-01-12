package org.pah_monitoring.main.repositorites.users.messages;

import org.pah_monitoring.main.entities.users.messages.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMessageRepository extends JpaRepository<UserMessage, Integer> {

}
