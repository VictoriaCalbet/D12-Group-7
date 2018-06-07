
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
import domain.Announcement;
import domain.Project;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AnnouncementServiceTest extends AbstractTest {

	// The SUT (Service Under Test) -------------------------------------------

	@Autowired
	private AnnouncementService	announcementService;

	@Autowired
	private ProjectService		projectService;

	@Autowired
	private UserService			userService;


	// Tests ------------------------------------------------------------------

	/**
	 * Acme-Patronage: Requirement 16.4
	 * 
	 * List the awards of every project if they have any
	 * 
	 * 
	 * Positive test 1: An actor logged as user get project announcements
	 * Positive test 2: An actor logged as moderator get project announcements
	 * Positive test 3: An actor logged as admin get project announcements
	 * Positive test 4: An actor logged as corporation get project announcements
	 */

	@Test
	public void testListAnnouncementDriver() {
		final Object[][] testingData = {
			{
				"user1", "project1", null
			}, {
				"moderator1", "project1", null
			}, {
				"admin", "project1", null
			}, {
				"corporation1", "project1", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testListAnnouncementsTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void testListAnnouncementsTemplate(final String actor, final String projectName, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(actor);

			Collection<Announcement> announcements = null;
			Project project = null;

			project = this.projectService.findOne(this.getEntityId(projectName));
			announcements = project.getAnnouncements();

			Assert.isTrue(announcements.size() == 1);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/**
	 * Acme-Patronage: Requirement 22.1
	 * 
	 * Post an announcement of a project he or she has created.
	 * Announcements cannot be edited or deleted.
	 * 
	 * 
	 * Positive test 1: An actor logged as user post an announcement.
	 * Positive test 2: An actor logged as user post an announcement in project that it's not the creator.
	 * Negative test 3: An actor logged as user post an announcement with null title
	 * Negative test 4: An actor logged as user post an announcement with null description
	 * Negative test 5: An actor logged as moderator post an announcement
	 * Negative test 6: An actor logged as administrator post an announcement
	 * Negative test 7: An actor logged as corporation post an announcement
	 */

	@Test
	public void testPostAnAnnouncementDriver() {
		final Object[][] testingData = {
			{
				"user1", "project1", "Announcement title", "AnnouncementDescription", null
			}, {
				"user1", "project2", "Announcement title", "AnnouncementDescription", IllegalArgumentException.class
			}, {
				"user1", "project1", null, "AnnouncementDescription", ConstraintViolationException.class
			}, {
				"user1", "project1", "Announcement title", null, ConstraintViolationException.class
			}, {
				"moderator1", "project1", "Announcement title", "AnnouncementDescription", IllegalArgumentException.class
			}, {
				"admin", "project1", "Announcement title", "Announcement description", IllegalArgumentException.class
			}, {
				"corporation1", "project1", "Announcement title", "AnnouncementDescription", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testPostAnAnnouncementDriver((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void testPostAnAnnouncementDriver(final String actor, final String projectName, final String announcementTitle, final String announcementDescription, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(actor);

			User user = null;
			Announcement announcement = null;
			Project project = null;

			user = this.userService.findByPrincipal();
			announcement = this.announcementService.create(this.getEntityId(projectName));
			project = this.projectService.findOne(this.getEntityId(projectName));

			announcement.setTitle(announcementTitle);
			announcement.setDescription(announcementDescription);
			announcement.setUser(user);
			announcement.setProject(project);

			this.announcementService.saveFromCreate(announcement);
			this.announcementService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/**
	 * Acme-Patronage: Requirement 22.2
	 * 
	 * Display a stream of announcements regarding the projects that he or
	 * she has funded.
	 * 
	 * Positive test 1: an user display his or her stream of announcements
	 * Negative test 2: an moderator display a stream of announcements
	 * Negative test 3: an admin display a stream of announcements
	 * Negative test 4: an corporation display a stream of announcements
	 */
	@Test
	public void testStreamOfAnnouncementDriver() {
		final Object[][] testingData = {
			{
				"user1", "project1", null
			}, {
				"moderator1", "project1", IllegalArgumentException.class
			}, {
				"admin", "project1", IllegalArgumentException.class
			}, {
				"corporation1", "project1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testStreamOfAnnouncementsTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void testStreamOfAnnouncementsTemplate(final String actor, final String projectName, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(actor);

			Collection<Announcement> announcements = null;
			User user = null;

			user = this.userService.findByPrincipal();
			announcements = this.announcementService.findStreamThatIFunded(user.getId());

			Assert.isTrue(announcements.size() == 2);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}
}
