
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.Project;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProjectServiceTest extends AbstractTest {

	@Autowired
	private ProjectService	projectService;
	@Autowired
	private UserService		userService;
	@Autowired
	private CategoryService	categoryService;


	/***
	 * 
	 * Requirement 16.2, 16.3 to Acme-Project
	 * 
	 * List the projects with a future due date and browse them by categories.
	 * List the projects ordered by creation moment (new ones on top).
	 * 
	 * Testing cases:
	 * Positive test 1: List the project.
	 */

	@Test
	public void listProjectAnonymous() {

		final Object testingData[][] = {
			//expected exception
			{
				null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listProjectAnonymous((Class<?>) testingData[i][0]);
	}
	protected void listProjectAnonymous(final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			final Collection<Project> projects = this.projectService.findProjectFutureDueDate();
			Assert.isTrue(projects.size() == 1);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
		}

		this.checkExceptions(expectedException, caught);
	}

	/***
	 * 
	 * Requirement 16.2, 16.3 to Acme-Project
	 * 
	 * List the projects with a future due date and browse them by categories.
	 * List the projects ordered by creation moment (new ones on top).
	 * 
	 * Testing cases:
	 * Positive test 1: List the project.
	 */

	@Test
	public void listProjectUser() {

		final Object testingData[][] = {
			//principal expected exception
			{
				"user3", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listProjects((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void listProjects(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			final Collection<Project> projects = this.userService.findByPrincipal().getProjects();
			Assert.isTrue(projects.size() == 3);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/***
	 * 
	 * Requirement 16.7 to Acme-Project
	 * 
	 * Search for a project using a single keyword that must appear in its title
	 * or description
	 * 
	 * Testing cases:
	 * Positive test 1: Search a word in the title
	 */

	@Test
	public void searchProjectAnonymous() {

		final String w = "is";

		final Object testingData[][] = {
			//expected exception
			{
				w, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.searchProjectAnonymous((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	protected void searchProjectAnonymous(final String word, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			final Collection<Project> projects = this.projectService.findProjectByKeyWord(word);
			Assert.isTrue(projects.size() == 4);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
		}

		this.checkExceptions(expectedException, caught);
	}

	// Tests ------------------------------------------------------------------

	/***
	 * Acme-Project: Requirement 18.1
	 * 
	 * Create a project. The user who has created a project is commonly
	 * referred as the creator
	 * 
	 * Positive test 1: Create a project.
	 * Negative test 2: Create a project with a past due date.
	 * Negative test 3: Create a project with minimum patronage amount greater than economic goal.
	 * Negative test 4: Create a project without title.
	 * Negative test 5: Create a project without description.
	 * Negative test 6: Create a project by administrator.
	 * Negative test 7: Create a project by moderator.
	 * Negative test 8: Create a project with minimum patronage amount negative.
	 * Negative test 9: Create a project with economic goal negative.
	 * Negative test 10: Create a project with economic goal smaller than minimum patronage amount.
	 * 
	 */
	@Test
	public void testCreateProject() {
		final Category c1 = this.categoryService.findOne(this.getEntityId("category1"));

		final Object[][] testingData = {
			// principal, title, description, dueDate, economicGoal, minimumPatronageAmount, isDraft, category, expected exception
			{
				"user1", "New project", "description of new project", new DateTime().plusDays(10).toDate(), 2000.0, 5.5, false, c1, null
			}, {
				"user1", "New project", "description of new project", new DateTime().plusDays(-10).toDate(), 2000.0, 5.5, false, c1, IllegalArgumentException.class
			}, {
				"user1", "New project", "description of new project", new DateTime().plusDays(10).toDate(), 20.0, 500.5, false, c1, IllegalArgumentException.class
			}, {
				"user1", "", "description of new project", new DateTime().plusDays(10).toDate(), 2000.0, 5.5, false, c1, ConstraintViolationException.class
			}, {
				"user1", "New project", "", new DateTime().plusDays(10).toDate(), 2000.0, 5.5, false, c1, ConstraintViolationException.class
			}, {
				"admin", "New project", "description of new project", new DateTime().plusDays(10).toDate(), 2000.0, 5.5, false, c1, IllegalArgumentException.class
			}, {
				"moderator1", "New project", "description of new project", new DateTime().plusDays(10).toDate(), 2000.0, 5.5, false, c1, IllegalArgumentException.class
			}, {
				"user1", "New project", "description of new project", new DateTime().plusDays(10).toDate(), 2000.0, -5.5, false, c1, ConstraintViolationException.class
			}, {
				"user1", "New project", "description of new project", new DateTime().plusDays(10).toDate(), -2000.0, 5.5, false, c1, IllegalArgumentException.class
			}, {
				"user1", "New project", "description of new project", new DateTime().plusDays(10).toDate(), 2.0, 5.5, false, c1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createProjectTemplated((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (double) testingData[i][4], (double) testingData[i][5], (boolean) testingData[i][6],
				(Category) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void createProjectTemplated(final String principal, final String title, final String description, final Date dueDate, final double economicGoal, final double minimumPatronageAmount, final boolean isDraft, final Category c,
		final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Project p = this.projectService.create();
			p.setTitle(title);
			p.setDescription(description);
			p.setDueDate(dueDate);
			p.setEconomicGoal(economicGoal);
			p.setMinimumPatronageAmount(minimumPatronageAmount);
			p.setIsDraft(isDraft);
			p.setCategory(c);
			this.projectService.saveFromCreate(p);
			this.unauthenticate();
			this.projectService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/***
	 * 
	 * 
	 * Requirement 18.2 to Acme-Project
	 * 
	 * Edit a project as long as it's in draft mode. Once the project is in final
	 * mode, it cannot be modified and it's considered as published
	 * 
	 * Positive test 1: Edit a project.
	 * Negative test 2: Edit a project with a past due date.
	 * Negative test 3: Edit a project with economic goal smaller than minimum patronage amount.
	 */
	@Test
	public void testEditProject() {

		final Project p5 = this.projectService.findOne(this.getEntityId("project5"));

		final Object[][] testingData = {
			// principal, title, dueDate, economicGoal, minimumPatronageAmount, isDraft, project, expected exception
			{
				"user3", "project34", new DateTime().plusHours(1).toDate(), 2000.0, 5.5, false, p5, null
			}, {
				"user3", "project34", new DateTime().plusDays(-10).toDate(), 2000.0, 5.5, false, p5, IllegalArgumentException.class
			}, {
				"user3", "project34", new DateTime().plusDays(10).toDate(), 2.0, 500.0, false, p5, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editProjectTemplated((String) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], (double) testingData[i][3], (double) testingData[i][4], (boolean) testingData[i][5], (Project) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}

	protected void editProjectTemplated(final String principal, final String title, final Date dueDate, final double economicGoal, final double minimumPatronageAmount, final boolean isDraft, final Project p, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			p.setTitle(title);
			p.setEconomicGoal(economicGoal);
			p.setMinimumPatronageAmount(minimumPatronageAmount);
			p.setDueDate(dueDate);
			p.setIsDraft(isDraft);

			this.projectService.saveFromEdit(p);
			this.unauthenticate();
			this.projectService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/***
	 * 
	 * 
	 * Requirement 18.3 to Acme-Project
	 * 
	 * Delete a project as long as it hasn't any patronages. If any, the project
	 * can only be cancelled.
	 * 
	 * Positive test 1: Delete a project
	 * Negative test 2: Delete a project with patronages.
	 * Negative test 3º Delete a cancelled project.
	 */
	@Test
	public void testDeleteProject() {

		final Project p1 = this.projectService.findOne(this.getEntityId("project5"));
		final Project p2 = this.projectService.findOne(this.getEntityId("project4"));
		final Project p3 = this.projectService.findOne(this.getEntityId("project2"));

		final Object[][] testingData = {
			{
				"user3", p1, null
			}, {
				"user2", p3, IllegalArgumentException.class
			}, {
				"user3", p2, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteProjectTemplated((String) testingData[i][0], (Project) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void deleteProjectTemplated(final String principal, final Project p, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			this.projectService.delete(p.getId());
			this.unauthenticate();
			this.projectService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/***
	 * 
	 * 
	 * Requirement 18.3 to Acme-Project
	 * 
	 * Delete a project as long as it hasn't any patronages. If any, the project
	 * can only be cancelled.
	 * 
	 * Positive test 1: Cancel a project
	 * Negative test 2: Cancel a cancelled project.
	 * Negative test 3º Cancel a project with past due date.
	 */
	@Test
	public void testCancelProject() {

		final Project p1 = this.projectService.findOne(this.getEntityId("project1"));
		final Project p2 = this.projectService.findOne(this.getEntityId("project3"));
		final Project p3 = this.projectService.findOne(this.getEntityId("project4"));

		final Object[][] testingData = {
			{
				"user1", p1, null
			}, {
				"user3", p2, IllegalArgumentException.class
			}, {
				"user3", p3, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.cancelProjectTemplated((String) testingData[i][0], (Project) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void cancelProjectTemplated(final String principal, final Project p, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			this.projectService.cancel(p.getId());
			this.unauthenticate();
			this.projectService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/***
	 * 
	 * Requirement 29.2 to Acme-Project
	 * 
	 * Delete a project with a legit complaint.
	 * 
	 * Positive test 1: Delete a project from the database by an admin.
	 * Negative test 2: Delete a project without a legit complaint.
	 * Negative test 3: Delete a project by other actor.
	 */
	@Test
	public void testDeleteProjectAdmin() {
		final Project p1 = this.projectService.findOne(this.getEntityId("project4"));
		final Project p2 = this.projectService.findOne(this.getEntityId("project1"));

		final Object[][] testingData = {
			{
				"admin", p1, null
			}, {
				"admin", p2, IllegalArgumentException.class
			}, {
				"user1", p1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteProjectAdminTemplated((String) testingData[i][0], (Project) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void deleteProjectAdminTemplated(final String principal, final Project p, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			this.projectService.deleteAdmin(p.getId());
			this.unauthenticate();
			this.projectService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

}
