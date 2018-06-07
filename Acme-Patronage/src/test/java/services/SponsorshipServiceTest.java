
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Project;
import domain.Sponsorship;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private SponsorshipService	sponsorshipService;
	@Autowired
	private ProjectService		projectService;


	// Tests ------------------------------------------------------------------

	/**
	 * Acme-Patronage: Requirement 23.1
	 * 
	 * An actor who is authenticated as a corporation must be able to:
	 * Manage its sponsorships, thus create, edit and delete them.
	 * 
	 * 
	 * 
	 * Positive test1: Corporation create a sponsorship.
	 * Negative test2: Corporation try to create a sponsorship with wrong url banner.
	 * Negative test3: Unaunthenticate actor try to create a sponsorship.
	 */
	@Test
	public void testSaveFromCreateSponsorship() {

		//Sponsorship: bannerURL, actor, exception.
		final Object[][] testingData = {
			{
				"http://www.banner.com", "corporation1", null
			}, {
				"banner", "corporation1", ConstraintViolationException.class
			}, {
				"http://www.banner.com", null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testSaveFromCreateSponsorshipTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void testSaveFromCreateSponsorshipTemplate(final String bannerURL, final String actor, final Class<?> expectedException) {
		Class<?> caught = null;
		try {

			this.authenticate(actor);
			final Sponsorship sponsorship;
			sponsorship = this.sponsorshipService.create();
			final Project project;
			project = this.projectService.findOne(this.getEntityId("project1"));
			sponsorship.setProject(project);
			sponsorship.setBannerURL(bannerURL);
			sponsorship.setPageURL("http://www.page.com");
			this.sponsorshipService.saveFromCreate(sponsorship);
			this.unauthenticate();
			this.sponsorshipService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/**
	 * Acme-Patronage: Requirement 23.1
	 * 
	 * An actor who is authenticated as a corporation must be able to:
	 * Manage its sponsorships, thus create, edit and delete them.
	 * 
	 * 
	 * 
	 * Positive test1: Corporation1 edit a sponsorship.
	 * Negative test2: Corporation2 try to edit a sponsorship.
	 * Negative test3: Unaunthenticate actor try to edit a sponsorship.
	 */
	@Test
	public void testSaveFromEditSponsorship() {

		//Sponsorship: actor, exception.
		final Object[][] testingData = {
			{
				"corporation1", null
			}, {
				"corporation2", IllegalArgumentException.class
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testSaveFromEditSponsorshipTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}
	protected void testSaveFromEditSponsorshipTemplate(final String actor, final Class<?> expectedException) {
		Class<?> caught = null;
		try {

			this.authenticate("corporation1");
			Sponsorship sponsorship;
			sponsorship = this.sponsorshipService.create();
			Project project;
			project = this.projectService.findOne(this.getEntityId("project1"));
			sponsorship.setProject(project);
			sponsorship.setBannerURL("http://www.banner.com");
			sponsorship.setPageURL("http://www.page.com");
			sponsorship = this.sponsorshipService.saveFromCreate(sponsorship);
			this.unauthenticate();

			this.authenticate(actor);
			sponsorship.setBannerURL("http://www.image.com");
			this.sponsorshipService.saveFromEdit(sponsorship);
			this.unauthenticate();

			this.sponsorshipService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/**
	 * Acme-Patronage: Requirement 23.1
	 * 
	 * An actor who is authenticated as a corporation must be able to:
	 * Manage its sponsorships, thus create, edit and delete them.
	 * 
	 * 
	 * 
	 * Positive test1: Corporation remove a sponsorship.
	 * Negative test2: User try to remove a sponsorship.
	 * Negative test3: Unaunthenticate actor try to remove a sponsorship.
	 */
	@Test
	public void testDeleteByCorporation() {

		//Sponsorship: actor, exception.
		final Object[][] testingData = {
			{
				"corporation1", null
			}, {
				"user1", IllegalArgumentException.class
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testDeleteByCorporationTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}
	protected void testDeleteByCorporationTemplate(final String actor, final Class<?> expectedException) {
		Class<?> caught = null;
		try {

			this.authenticate("corporation1");
			Sponsorship sponsorship;
			sponsorship = this.sponsorshipService.create();
			Project project;
			project = this.projectService.findOne(this.getEntityId("project1"));
			sponsorship.setProject(project);
			sponsorship.setBannerURL("http://www.banner.com");
			sponsorship.setPageURL("http://www.page.com");
			sponsorship = this.sponsorshipService.saveFromCreate(sponsorship);
			this.unauthenticate();

			this.authenticate(actor);
			this.sponsorshipService.deleteByCorporation(sponsorship);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();

		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/**
	 * Acme-Patronage: Requirement 34
	 * 
	 * Whenever a project is displayed, a random sponsorship must be
	 * selected and its banner shown, if any.
	 * 
	 * 
	 * Positive test1: Display sponsorship.
	 * 
	 */

	@Test
	public void testRandomSponsorshipByProjectId() {

		//Exception
		final Object[][] testingData = {
			{
				null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testRandomSponsorshipByProjectIdTemplate((Class<?>) testingData[i][0]);

	}
	protected void testRandomSponsorshipByProjectIdTemplate(final Class<?> expectedException) {
		Class<?> caught = null;
		try {

			Assert.notNull(this.sponsorshipService.getRandomSponsorshipByProjectId(this.getEntityId("project1")));
			this.sponsorshipService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}
}
