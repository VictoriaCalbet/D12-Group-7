
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
import domain.Award;
import domain.AwardComment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AwardCommentServiceTest extends AbstractTest {

	//Service being tested

	@Autowired
	private AwardCommentService	awardCommentService;

	//Supporting services needed for the tests

	@Autowired
	private UserService			userService;

	@Autowired
	private AwardService		awardService;


	/***
	 * 
	 * Requirement 20.5. An actor who is not authenticated must be able to list the comments of an award.
	 * Requirement 21.1. An actor who is authenticated must be able to do the same as an actor who is not authenticated, but register to the
	 * system
	 * Testing cases:
	 * 
	 * Test 1: positive case.
	 * Test 2: negative case. The award's id isn't correct.
	 */

	@Test
	public void listAwardComments() {

		final int awardId = this.getEntityId("award2");
		final int projectId = this.getEntityId("project1");

		final Object testingData[][] = {
			//List award comments: int, expected exception
			{
				awardId, null
			}, {
				projectId, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listAwardComments((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	protected void listAwardComments(final int id, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			final Collection<AwardComment> aComments = this.awardCommentService.listAllAwardComments(id);
			Assert.isTrue(aComments.size() == 1);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
		}

		this.checkExceptions(expectedException, caught);
	}

	/***
	 * 
	 * Requirement 22.4. An actor authenticated as a user must be able to post a comment in an award, even if he or she hasn't funded it.
	 * 
	 * Testing cases:
	 * 
	 * Test 1: positive case. The rating is null.
	 * Test 2: positive case. The rating has a value different than null.
	 * Test 3: negative case. The text is null.
	 * Test 4: negative case. The award is null.
	 */
	@Test
	public void postAwardComment() {

		final Award award = this.awardService.findOne(this.getEntityId("award1"));

		final Object testingData[][] = {
			//AwardComment: text, rating, creationMoment, user, award
			{
				"text", null, (new Date(System.currentTimeMillis() - 1)), "user2", award, null
			}, {

				"text", 4, (new Date(System.currentTimeMillis() - 1)), "user2", award, null
			}, {

				null, 4, (new Date(System.currentTimeMillis() - 1)), "user2", award, IllegalArgumentException.class
			}, {

				"text", 4, (new Date(System.currentTimeMillis() - 1)), "user2", null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.postAwardCommentsUser((String) testingData[i][0], (Integer) testingData[i][1], (Date) testingData[i][2], (String) testingData[i][3], (Award) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	protected void postAwardCommentsUser(final String text, final Integer rating, final Date cMoment, final String user, final Award award, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(user);

			final AwardComment aC = this.awardCommentService.create();

			aC.setRating(rating);
			aC.setUser(this.userService.findByPrincipal());
			aC.setText(text);
			aC.setAward(award);
			this.awardCommentService.saveFromCreate(aC);

			this.unauthenticate();
			this.awardCommentService.flush();

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
	 * Test 3: negative case. The award comment to delete is null.
	 */

	@Test
	public void testDeleteAwardComment() {

		final AwardComment aC = this.awardCommentService.findOne(this.getEntityId("awardComment2"));

		//Delete award comment: string, award comment, expected exception

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
			this.deleteAwardComment((String) testingData[i][0], (AwardComment) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void deleteAwardComment(final String principal, final AwardComment aC, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			this.awardCommentService.delete(aC);
			this.unauthenticate();
			this.awardCommentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

}
