
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository	messageRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: Message - create
	public Message create() {
		final Message result = null;

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
		Assert.notNull(message);
		Message result = null;
		result = this.messageRepository.save(message);
		return result;
	}

	// TODO: Message - saveFromCreate
	public Message saveFromCreate() {
		final Message result = null;

		return result;
	}

	// TODO: Message - saveFromEdit
	public Message saveFromEdit() {
		final Message result = null;

		return result;
	}

	public void flush() {
		this.messageRepository.flush();
	}

	// Other business methods -------------------------------------------------

}
