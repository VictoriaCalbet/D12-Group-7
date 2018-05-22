
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m from Message m where m.sender.id = ?1")
	Collection<Message> findAllSentByActorId(int actorId);

	@Query("select m from Message m where m.recipient.id = ?1")
	Collection<Message> findAllReceivedByActorId(int actorId);
}
