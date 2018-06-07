
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Actor;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActorServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private ActorService	actorService;


	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	/**
	 * Acme-Patronage: Requirement 29.1:
	 * 
	 * An actor who is authenticated as a administrator must be able to:
	 * Ban or unban a user if he or she has created a project with a legit complaint.
	 * 
	 * Positive test1: Correct ban and unban.
	 * Negative test2: The admin tries to ban a user without complaints.
	 * Negative test3: A user tries to ban another user.
	 */
	@Test
	public void testBanAndUnbanUser() {
		// Administrator: Logged user, user to ban, expected exception.
		final Object[][] testingData = {
			{
				"administrator1", "user3", null
			}, {
				"administrator1", "user1", IllegalArgumentException.class
			}, {
				"user1", "user3", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testSaveFromCreateAdminTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void testSaveFromCreateAdminTemplate(final String loggedUser, final String userToBan, final Class<?> expectedException) {

		Class<?> caught = null;

		try {

			final Actor admin = this.actorService.findOne(this.getEntityId(loggedUser));
			final Actor user = this.actorService.findOne(this.getEntityId(userToBan));

			this.authenticate(admin.getUserAccount().getUsername());

			this.actorService.ban(user.getId());
			this.actorService.unban(user.getId());

			this.actorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);

	}
}
