
package domain.forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

public class MessageForm {

	// Attributes -------------------------------------------------------------

	private int		id;
	private String	header;
	private String	body;
	private String	attachmentLink;
	private int		senderId;
	private int		recipientId;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getHeader() {
		return this.header;
	}

	public void setHeader(final String header) {
		this.header = header;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getAttachmentLink() {
		return this.attachmentLink;
	}

	public void setAttachmentLink(final String attachmentLink) {
		this.attachmentLink = attachmentLink;
	}

	public int getSenderId() {
		return this.senderId;
	}

	public void setSenderId(final int senderId) {
		this.senderId = senderId;
	}

	public int getRecipientId() {
		return this.recipientId;
	}

	public void setRecipientId(final int recipientId) {
		this.recipientId = recipientId;
	}

}
