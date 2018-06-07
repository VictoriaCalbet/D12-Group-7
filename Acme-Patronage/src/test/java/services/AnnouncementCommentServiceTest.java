
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Announcement;
import domain.AnnouncementComment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AnnouncementCommentServiceTest extends AbstractTest {

	//Service being tested

	@Autowired
	private AnnouncementCommentService	announcementCommentService;

	//Supporting services

	@Autowired
	private UserService					userService;

	@Autowired
	private AnnouncementService			announcementService;


	/***
	 * 
	 * Requirement 20.6. An actor who is not authenticated must be able to list the comments of an announcement.
	 * Requirement 21.1. An actor who is authenticated must be able to do the same as an actor who is not authenticated, but register to the
	 * system
	 * Testing cases:
	 * 
	 * Test 1: positive case.
	 * Test 2: negative case. The announcement's id isn't correct.
	 */

	@Test
	public void listAnnouncementComments() {

		final int announcementId = this.getEntityId("announcement2");
		final int projectId = this.getEntityId("project1");

		final Object testingData[][] = {
			//List project comments: int, expected exception
			{
				announcementId, null
			}, {
				projectId, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listAnnouncementComments((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	protected void listAnnouncementComments(final int id, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			final Collection<AnnouncementComment> anComments = this.announcementCommentService.listAllAnnouncementComments(id);
			Assert.isTrue(anComments.size() == 1);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
		}

		this.checkExceptions(expectedException, caught);
	}

	/***
	 * 
	 * Requirement 22.5. An actor authenticated as a user must be able to post a comment in an announcement, only if he or she has funded the
	 * related project.
	 * 
	 * Testing cases:
	 * 
	 * Test 1: positive case. The rating is null.
	 * Test 2: positive case. The rating has a value different than null.
	 * Test 3: negative case. The text is null.
	 * Test 4: negative case. The announcement is null.
	 * Test 5: the user hasn't funded the related project.
	 */
	@Test
	public void postAnnouncementComment() {

		final Announcement announcement = this.announcementService.findOne(this.getEntityId("announcement1"));

		final Object testingData[][] = {
			//AnnouncementComment: text, rating, creationMoment, user, project
			{
				"text", null, (new Date(System.currentTimeMillis() - 1)), "user1", announcement, null
			}, {

				"text", 4, (new Date(System.currentTimeMillis() - 1)), "user1", announcement, null
			}, {

				null, 4, (new Date(System.currentTimeMillis() - 1)), "user1", announcement, IllegalArgumentException.class
			}, {

				"text", 4, (new Date(System.currentTimeMillis() - 1)), "user1", null, IllegalArgumentException.class
			}, {

				"text", 4, (new Date(System.currentTimeMillis() - 1)), "user3", null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.postAnnouncementCommentsUser((String) testingData[i][0], (Integer) testingData[i][1], (Date) testingData[i][2], (String) testingData[i][3], (Announcement) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	protected void postAnnouncementCommentsUser(final String text, final Integer rating, final Date cMoment, final String user, final Announcement announcement, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(user);

			final AnnouncementComment aC = this.announcementCommentService.create();

			aC.setRating(rating);
			aC.setUser(this.userService.findByPrincipal());
			aC.setText(text);
			aC.setAnnouncement(announcement);
			this.announcementCommentService.saveFromCreate(aC);

			this.unauthenticate();
			this.announcementCommentService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/***
	 * 
	 * Requirement 24.1. An actor authenticated as an administrator must be able to delete a comment if he or she finds it inappropriate.
	 * 
	 * Testing cases:
	 * 
	 * Test 1: positive case.
	 * Test 2: negative case. A user tries to delete a comment.
	 * Test 3: negative case. The announcement comment to delete is null.
	 */

	@Test
	public void testDeleteAnnouncementComment() {

		final AnnouncementComment aC = this.announcementCommentService.findOne(this.getEntityId("announcementComment1"));

		final Object[][] testingData = {
			{
				"admin", aC, null
			}, {
				"user2", aC, IllegalArgumentException.class
			}, {
				"admin", null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteAnnouncementComment((String) testingData[i][0], (AnnouncementComment) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void deleteAnnouncementComment(final String principal, final AnnouncementComment pC, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			this.announcementCommentService.delete(pC);
			this.unauthenticate();
			this.announcementCommentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

}
