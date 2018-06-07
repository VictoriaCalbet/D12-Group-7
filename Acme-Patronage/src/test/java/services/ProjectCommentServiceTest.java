
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
import domain.Project;
import domain.ProjectComment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProjectCommentServiceTest extends AbstractTest {

	//Service being tested

	@Autowired
	private ProjectCommentService	projectCommentService;

	//Supporting services

	@Autowired
	private UserService				userService;

	@Autowired
	private ProjectService			projectService;


	/***
	 * 
	 * Requirement 20.4. An actor who is not authenticated must be able to list the comments of a project.
	 * Requirement 21.1. An actor who is authenticated must be able to do the same as an actor who is not authenticated, but register to the
	 * system
	 * Testing cases:
	 * 
	 * Test 1: positive case.
	 * Test 2: negative case. The project's id isn't correct.
	 */

	@Test
	public void listProjectComments() {

		final int awardId = this.getEntityId("award2");
		final int projectId = this.getEntityId("project1");

		final Object testingData[][] = {
			//List project comments: int, expected exception
			{
				projectId, null
			}, {
				awardId, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listProjectComments((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	protected void listProjectComments(final int id, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			final Collection<ProjectComment> pComments = this.projectCommentService.listAllProjectComments(id);
			Assert.isTrue(pComments.size() == 1);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
		}

		this.checkExceptions(expectedException, caught);
	}

	/***
	 * 
	 * Requirement 22.3. An actor authenticated as a user must be able to post a comment in a project, even if he or she hasn't funded it.
	 * 
	 * Testing cases:
	 * 
	 * Test 1: positive case. The rating is null.
	 * Test 2: positive case. The rating has a value different than null.
	 * Test 3: negative case. The text is null.
	 * Test 4: negative case. The project is null.
	 */
	@Test
	public void postProjectComment() {

		final Project project = this.projectService.findOne(this.getEntityId("project1"));

		final Object testingData[][] = {
			//ProjectComment: text, rating, creationMoment, user, project
			{
				"text", null, (new Date(System.currentTimeMillis() - 1)), "user2", project, null
			}, {

				"text", 4, (new Date(System.currentTimeMillis() - 1)), "user2", project, null
			}, {

				null, 4, (new Date(System.currentTimeMillis() - 1)), "user2", project, IllegalArgumentException.class
			}, {

				"text", 4, (new Date(System.currentTimeMillis() - 1)), "user2", null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.postProjectCommentsUser((String) testingData[i][0], (Integer) testingData[i][1], (Date) testingData[i][2], (String) testingData[i][3], (Project) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	protected void postProjectCommentsUser(final String text, final Integer rating, final Date cMoment, final String user, final Project project, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(user);

			final ProjectComment aC = this.projectCommentService.create();

			aC.setRating(rating);
			aC.setUser(this.userService.findByPrincipal());
			aC.setText(text);
			aC.setProject(project);
			this.projectCommentService.saveFromCreate(aC);

			this.unauthenticate();
			this.projectCommentService.flush();

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
	 * Test 3: negative case. The project comment to delete is null.
	 */

	@Test
	public void testDeleteProjectComment() {

		final ProjectComment pC = this.projectCommentService.findOne(this.getEntityId("projectComment1"));

		final Object[][] testingData = {
			{
				"admin", pC, null
			}, {
				"user2", pC, IllegalArgumentException.class
			}, {
				"admin", null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteProjectComment((String) testingData[i][0], (ProjectComment) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void deleteProjectComment(final String principal, final ProjectComment pC, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			this.projectCommentService.delete(pC);
			this.unauthenticate();
			this.projectCommentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

}
