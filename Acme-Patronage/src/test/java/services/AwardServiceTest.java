
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Award;
import domain.Project;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AwardServiceTest extends AbstractTest {

	// The SUT (Service Under Test) -------------------------------------------

	@Autowired
	private AwardService	awardService;

	@Autowired
	private ProjectService	projectService;


	// Tests ------------------------------------------------------------------

	/**
	 * Acme-Patronage: Requirement 18.4
	 * 
	 * Manage the awards he or she has created. The user is able to create, edit and
	 * delete an award. Awards can be edited as long as its project is not published.
	 * However, further awards can be added to the project even if it’s published.
	 */

	/**
	 * 
	 * Create an award.
	 * 
	 * Positive test 1: An actor logged as user create a award to his or her project
	 * Negative test 2: An actor logged as user create a award with null name
	 * Negative test 3: An actor logged as user create a award with null description
	 * Negative test 4: An actor logged as user create a award with money goal too low
	 * Negative test 5: An actor logged as user create a award that it's not the creator
	 * Negative test 6: An actor logged as admin create a project
	 * Negative test 7: An actor logged as moderator create a project
	 * Negative test 8: An actor logged as corporation create a project
	 */

	@Test
	public void testCreateAnAwardDriver() {
		final Object[][] testingData = {
			{
				"user1", "project1", "Award name", "Award description", 60d, null
			}, {
				"user1", "project1", null, "Award description", 60d, ConstraintViolationException.class
			}, {
				"user1", "project1", "Award name", null, 60d, ConstraintViolationException.class
			}, {
				"user1", "project1", "Award name", "Award description", 0d, IllegalArgumentException.class
			}, {
				"user1", "project2", "Award name", "Award description", 60d, IllegalArgumentException.class
			}, {
				"admin", "project1", "Award name", "Award description", 60d, IllegalArgumentException.class
			}, {
				"moderator1", "project1", "Award name", "Award description", 60d, IllegalArgumentException.class
			}, {
				"corporation1", "project1", "Award name", "Award description", 60d, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testCreateAnAwardTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (double) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	protected void testCreateAnAwardTemplate(final String actor, final String projectName, final String awardName, final String awardDescription, final double awardMoneyGoal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(actor);

			Award award = null;
			Project project = null;

			award = this.awardService.create(this.getEntityId(projectName));
			project = this.projectService.findOne(this.getEntityId(projectName));

			award.setName(awardName);
			award.setDescription(awardDescription);
			award.setMoneyGoal(awardMoneyGoal);
			award.setProject(project);

			this.awardService.saveFromCreate(award);
			this.awardService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/**
	 * 
	 * Edit an award.
	 * 
	 * Positive test 1: An actor logged as user edit a award to his or her project
	 * Negative test 2: An actor logged as user edit a award with null name
	 * Negative test 3: An actor logged as user edit a award with null description
	 * Negative test 4: An actor logged as user edit a award that it's not owner.
	 */

	@Test
	public void testEditAnAwardDriver() {
		final Object[][] testingData = {
			{
				"user3", "award5", "Award name modified", "Award description modified", 60d, null
			}, {
				"user3", "award5", null, "Award description modified", 60d, ConstraintViolationException.class
			}, {
				"user3", "award5", "Award name modified", null, 60d, ConstraintViolationException.class
			}, {
				"user1", "award5", "Award name modified", "Award description modified", 60d, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testEditAnAwardTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (double) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	protected void testEditAnAwardTemplate(final String actor, final String awardEntity, final String awardNameModified, final String awardDescriptionModified, final double awardMoneyGoalModified, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(actor);

			Award award = null;

			award = this.awardService.findOne(this.getEntityId(awardEntity));

			award.setName(awardNameModified);
			award.setDescription(awardDescriptionModified);
			award.setMoneyGoal(awardMoneyGoalModified);

			this.awardService.saveFromEdit(award);
			this.awardService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/**
	 * 
	 * Delete an award.
	 * 
	 * Positive test 1: An actor logged as user delete a award to his or her project
	 * Negative test 2: An actor logged as user delete a award that it's not owner
	 * Negative test 3: An actor logged as user delete a award with published project
	 * Negative test 4: An actor logged as admin delete a award
	 */

	@Test
	public void testDeleteAnAwardDriver() {
		final Object[][] testingData = {
			{
				"user3", "award5", null
			}, {
				"user3", "award1", IllegalArgumentException.class
			}, {
				"user1", "award1", IllegalArgumentException.class
			}, {
				"user1", "award1", IllegalArgumentException.class
			}, {
				"admin", "award1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testDeleteAnAwardTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void testDeleteAnAwardTemplate(final String actor, final String awardEntity, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(actor);

			Award award = null;

			award = this.awardService.findOne(this.getEntityId(awardEntity));

			this.awardService.delete(award);

			this.awardService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/**
	 * Acme-Patronage: Requirement 16.4
	 * 
	 * List the awards of every project if they have any.
	 * 
	 * Positive test 1: An actor logged as user list awards of a project
	 * Positive test 2: An actor logged as moderator list awards of a project
	 * Positive test 3: An actor logged as admin list awards of a project
	 * Positive test 4: An actor logged as corporation list awards of a project
	 * Negative test 5: An actor logged as user list awards of a cancelled project
	 * Negative test 6: An actor logged as admin list awards of a cancelled project
	 * Negative test 7: An actor logged as moderator list awards of a cancelled project
	 * Negative test 8: An actor logged as corporation list awards of a cancelled project
	 */

	@Test
	public void testListAwardsOfAProjectDriver() {
		final Object[][] testingData = {
			{
				"user1", "project1", null
			}, {
				"moderator1", "project1", null
			}, {
				"admin", "project1", null
			}, {
				"corporation1", "project1", null
			}, {
				"user1", "project4", IllegalArgumentException.class
			}, {
				"admin", "project4", IllegalArgumentException.class
			}, {
				"moderator1", "project4", IllegalArgumentException.class
			}, {
				"corporation1", "project4", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testListAwardsOfAProjectTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void testListAwardsOfAProjectTemplate(final String actor, final String projectName, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(actor);

			Collection<Award> awards = null;
			Project project = null;

			project = this.projectService.findOne(this.getEntityId(projectName));
			awards = project.getAwards();

			Assert.isTrue(awards.size() == 1);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}
}
