
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	header;
	private String	body;
	private String	attachmentLink;


	public String getHeader() {
		return this.header;
	}

	public void setHeader(final String header) {
		this.header = header;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public String getAttachmentLink() {
		return this.attachmentLink;
	}

	public void setAttachmentLink(final String attachmentLink) {
		this.attachmentLink = attachmentLink;
	}


	// Relationships ----------------------------------------------------------

	private Actor	sender;
	private Actor	recipient;


	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	public Actor getRecipient() {
		return this.recipient;
	}

	public void setRecipient(final Actor recipient) {
		this.recipient = recipient;
	}
}
