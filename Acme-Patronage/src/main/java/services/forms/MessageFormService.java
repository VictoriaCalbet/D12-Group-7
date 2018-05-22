
package services.forms;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import services.ActorService;
import services.MessageService;
import domain.Actor;
import domain.Message;
import domain.forms.MessageForm;

@Service
@Transactional
public class MessageFormService {

	// Managed repository -----------------------------------------------------

	// Supporting services ----------------------------------------------------

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public MessageFormService() {
		super();
	}

	public MessageForm create() {
		MessageForm result;

		result = new MessageForm();

		return result;
	}

	public Message saveFromCreate(final MessageForm messageForm) {
		Assert.notNull(messageForm, "message.error.message.null");

		Message message;
		Actor sender;
		Actor recipient;

		sender = this.actorService.findByPrincipal();
		recipient = this.actorService.findOne(messageForm.getRecipientId());

		Assert.notNull(sender, "message.error.message.sender.null");
		Assert.notNull(recipient, "message.error.message.recipient.null");

		message = this.messageService.create();
		message.setHeader(messageForm.getHeader());
		message.setBody(messageForm.getBody());
		message.setAttachmentLink(messageForm.getAttachmentLink());
		message.setSender(sender);
		message.setRecipient(recipient);

		final Message result = this.messageService.saveFromCreate(message);

		return result;
	}
}
