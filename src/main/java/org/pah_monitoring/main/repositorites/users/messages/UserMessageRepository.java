package org.pah_monitoring.main.repositorites.users.messages;

import org.pah_monitoring.main.entities.users.messages.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMessageRepository extends JpaRepository<UserMessage, Integer> {

    List<UserMessage> findAllByAuthorId(Integer id);

    List<UserMessage> findAllByAuthorIdAndRecipientId(Integer authorId, Integer recipientId);

}
