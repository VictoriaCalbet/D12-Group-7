
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository	messageRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Message create() {
		Message result;

		result = new Message();

		return result;
	}

	public Collection<Message> findAll() {
		Collection<Message> result = null;
		result = this.messageRepository.findAll();
		return result;
	}

	public Message findOne(final int messageId) {
		Message result = null;
		result = this.messageRepository.findOne(messageId);
		return result;
	}

	public Message save(final Message message) {
		Assert.notNull(message, "message.error.message.null");
		Message result = null;
		result = this.messageRepository.save(message);
		return result;
	}

	public Message saveFromCreate(final Message message) {
		Assert.notNull(message, "message.error.message.null");

		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(message.getSender().getId() == principal.getId(), "message.error.message.sender.principal");
		Assert.isTrue(message.getRecipient().getId() != principal.getId(), "message.error.message.recipient.principal");

		final Message result = this.save(message);

		return result;
	}

	public void flush() {
		this.messageRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Collection<Message> findAllSentByActorId(final int actorId) {
		return this.messageRepository.findAllSentByActorId(actorId);
	}

	public Collection<Message> findAllReceivedByActorId(final int actorId) {
		return this.messageRepository.findAllReceivedByActorId(actorId);
	}
}
