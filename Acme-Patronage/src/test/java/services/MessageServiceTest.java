
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Actor;
import domain.Message;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	/**
	 * Acme-Patronage: Req 27.1
	 * 
	 * An actor who is authenticated must be able to:
	 * Send a message to any actor.
	 * 
	 * Positive test1: Correct message
	 * Negative test2: Wrong sender.
	 * Negative test3: Wrong recipient.
	 */
	@Test
	public void testSaveFromCreateAdmin() {
		// Message: Header, body, attachmentLink, sender, recipient, expected exception.
		final Object[][] testingData = {
			{
				"testHeader1", "testBody1", "https://duckduckgo.com/about", "user1", "user2", null
			}, {
				"testHeader2", "testBody2", "https://duckduckgo.com/about", "testUser1", "user2", IllegalArgumentException.class
			}, {
				"testHeader3", "testBody3", "https://duckduckgo.com/about", "user1", "testUser2", NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testSaveFromCreateAdminTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	protected void testSaveFromCreateAdminTemplate(final String header, final String body, final String attachmentLink, final String sender, final String recipient, final Class<?> expectedException) {

		Class<?> caught = null;

		try {
			this.authenticate(sender);

			final Message result;

			result = this.messageService.create();
			result.setHeader(header);
			result.setBody(body);
			result.setAttachmentLink(attachmentLink);

			final Actor senderActor = this.actorService.findOneByUsername(sender);
			final Actor recipientActor = this.actorService.findOneByUsername(recipient);

			result.setSender(senderActor);
			result.setRecipient(recipientActor);

			this.messageService.saveFromCreate(result);
			this.messageService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);

	}
}
