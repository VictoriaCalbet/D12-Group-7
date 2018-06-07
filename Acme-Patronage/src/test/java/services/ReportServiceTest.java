
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Report;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReportServiceTest extends AbstractTest {

	@Autowired
	private ReportService	reportService;

	@Autowired
	private ProjectService	projectService;


	/**
	 * Acme-Patronage: Requirement 26.1
	 * 
	 * An actor who is authenticated as a user must be able to:
	 * Fill a complaint about any project.
	 * 
	 * 
	 * 
	 * Positive test1: User create report.
	 * Negative test2: Moderator try to create a report.
	 * Negative test3: Unaunthenticate actor try to create a report.
	 */
	@Test
	public void testSaveFromCreateReport() {

		//actor, exception.
		final Object[][] testingData = {
			{
				"user1", null
			}, {
				"moderator1", IllegalArgumentException.class
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testSaveFromCreateReportTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}
	protected void testSaveFromCreateReportTemplate(final String actor, final Class<?> expectedException) {
		Class<?> caught = null;
		try {
			this.authenticate(actor);
			Report report;
			report = this.reportService.create();
			report.setComplaint("Complaint");
			report.setProject(this.projectService.findOne(this.getEntityId("project1")));
			this.reportService.saveFromCreate(report);
			this.unauthenticate();
			this.reportService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}
	/**
	 * Acme-Patronage: Requirement 28.1
	 * 
	 * An actor who is authenticated as a moderator must be able to:
	 * Accept or reject a complaint
	 * 
	 * 
	 * 
	 * Positive test1: Moderator accept report.
	 * Negative test2: User try to accept a report.
	 * Negative test3: Unaunthenticate actor try to accept a report.
	 */
	@Test
	public void testAcceptReport() {

		//actor, exception.
		final Object[][] testingData = {
			{
				"moderator1", null
			}, {
				"user1", IllegalArgumentException.class
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testAcceptReportTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}
	protected void testAcceptReportTemplate(final String actor, final Class<?> expectedException) {
		Class<?> caught = null;
		try {
			this.authenticate("user1");
			Report report;
			report = this.reportService.create();
			report.setComplaint("Complaint");
			report.setProject(this.projectService.findOne(this.getEntityId("project1")));
			report = this.reportService.saveFromCreate(report);
			this.unauthenticate();

			this.authenticate(actor);
			this.reportService.acceptReport(report.getId(), "Reason");
			this.unauthenticate();
			this.reportService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/**
	 * Acme-Patronage: Requirement 28.1
	 * 
	 * An actor who is authenticated as a moderator must be able to:
	 * Accept or reject a complaint
	 * 
	 * 
	 * 
	 * Positive test1: Moderator reject report.
	 * Negative test2: User try to reject a report.
	 * Negative test3: Unaunthenticate actor try to reject a report.
	 */
	@Test
	public void testRejectReport() {

		//actor, exception.
		final Object[][] testingData = {
			{
				"moderator1", null
			}, {
				"user1", IllegalArgumentException.class
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testAcceptReportTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}
	protected void testRejectReportTemplate(final String actor, final Class<?> expectedException) {
		Class<?> caught = null;
		try {
			this.authenticate("user1");
			Report report;
			report = this.reportService.create();
			report.setComplaint("Complaint");
			report.setProject(this.projectService.findOne(this.getEntityId("project1")));
			report = this.reportService.saveFromCreate(report);
			this.unauthenticate();

			this.authenticate(actor);
			this.reportService.rejectReport(report.getId());
			this.unauthenticate();
			this.reportService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/**
	 * Acme-Patronage: Requirement 28.1
	 * 
	 * An actor who is authenticated as a moderator must be able to:
	 * Accept or reject a complaint
	 * 
	 * 
	 * 
	 * Positive test1: Moderator list reports to revised.
	 * 
	 */
	@Test
	public void testListUnrevisedReports() {

		//Exception
		final Object[][] testingData = {
			{
				null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testListUnrevisedReportsTemplate((Class<?>) testingData[i][0]);

	}
	protected void testListUnrevisedReportsTemplate(final Class<?> expectedException) {
		Class<?> caught = null;
		try {
			this.authenticate("user1");
			Report report;
			report = this.reportService.create();
			report.setComplaint("Complaint");
			report.setProject(this.projectService.findOne(this.getEntityId("project1")));
			report = this.reportService.saveFromCreate(report);
			this.unauthenticate();

			Assert.notNull(this.reportService.listUnrevisedReports().size() > 0);
			this.reportService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/**
	 * Acme-Patronage: Requirement 14
	 * 
	 * Users can fill a report about a project.
	 * A report is a complaint about any aspect of the project.
	 * Moderators will investigate and decide if the complaint is legit.
	 * If so, it will be accepted and automatically delivered to the
	 * administrator. The system must store the reason why the report
	 * was accepted.
	 * 
	 * 
	 * 
	 * Positive test1: list reports accepted.
	 * 
	 */
	@Test
	public void testListAcceptedReports() {

		//Exception
		final Object[][] testingData = {
			{
				null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testListAcceptedReportsTemplate((Class<?>) testingData[i][0]);

	}

	protected void testListAcceptedReportsTemplate(final Class<?> expectedException) {
		Class<?> caught = null;
		try {
			this.authenticate("user1");
			Report report;
			report = this.reportService.create();
			report.setComplaint("Complaint");
			report.setProject(this.projectService.findOne(this.getEntityId("project1")));
			report = this.reportService.saveFromCreate(report);
			this.unauthenticate();

			this.authenticate("moderator1");
			this.reportService.acceptReport(report.getId(), "Reason");
			this.unauthenticate();

			Assert.notNull(this.reportService.listAcceptedReports().size() > 0);
			this.reportService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}
}
