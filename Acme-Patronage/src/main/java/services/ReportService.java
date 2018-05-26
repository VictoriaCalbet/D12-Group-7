
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Moderator;
import domain.Report;
import domain.User;

@Service
@Transactional
public class ReportService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ReportRepository	reportRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private UserService			userService;

	@Autowired
	private ModeratorService	moderatorService;


	// Constructors -----------------------------------------------------------

	public ReportService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Report create() {
		final Report result;
		result = new Report();
		result.setUser(this.isUserAunthenticate());
		return result;
	}

	public Collection<Report> findAll() {
		Collection<Report> result = null;
		result = this.reportRepository.findAll();
		return result;
	}

	public Report findOne(final int reportId) {
		Report result = null;
		result = this.reportRepository.findOne(reportId);
		return result;
	}

	public Report save(final Report report) {
		Assert.notNull(report);
		Report result = null;
		result = this.reportRepository.save(report);
		return result;
	}

	public Report saveFromCreate(final Report report) {
		Assert.notNull(report, "message.error.report.null");
		Assert.isTrue(report.getId() == 0, "message.error.report.editingreport");
		Assert.notNull(report.getProject(), "message.error.report.notproject");
		Assert.isTrue(!report.getProject().getIsDraft(), "message.error.report.draft");
		Assert.isTrue(!report.getProject().getIsCancelled(), "message.error.report.cancelled");
		Assert.isTrue(report.getProject().getDueDate().after(LocalDate.now().toDate()), "message.error.report.pastduedate");
		report.setIsLegit(null);
		report.setUser(this.isUserAunthenticate());
		report.setReason(null);
		Report result;
		result = this.reportRepository.save(report);
		return result;
	}

	public void flush() {
		this.reportRepository.flush();
	}

	// Other business methods -------------------------------------------------
	public Report acceptReport(final int reportId, final String reason) {
		Report reportInDB;
		reportInDB = this.reportRepository.findOne(reportId);
		Assert.notNull(reportInDB, "message.error.report.notreportindb");
		Assert.isNull(reportInDB.getIsLegit(), "message.error.report.revised");
		this.isModeratorAunthenticate();
		Boolean isLegit;
		isLegit = true;
		reportInDB.setIsLegit(isLegit);
		reportInDB.setReason(reason);
		Report savedReport;
		savedReport = this.reportRepository.save(reportInDB);
		return savedReport;

	}

	public Report rejectReport(final int reportId) {
		Report reportInDB;
		reportInDB = this.reportRepository.findOne(reportId);
		Assert.notNull(reportInDB, "message.error.report.notreportindb");
		Assert.isNull(reportInDB.getIsLegit(), "message.error.report.revised");
		this.isModeratorAunthenticate();
		Boolean isLegit;
		isLegit = false;
		reportInDB.setIsLegit(isLegit);
		Report savedReport;
		savedReport = this.reportRepository.save(reportInDB);
		return savedReport;

	}
	public List<Report> listUnrevisedReports() {
		List<Report> result;

		result = new ArrayList<Report>(this.reportRepository.findByLegitimacyNull());
		return result;
	}

	public List<Report> listAcceptedReports() {
		List<Report> result;
		Boolean isLegit;
		isLegit = true;
		result = new ArrayList<Report>(this.reportRepository.findByLegitimacy(isLegit));
		return result;
	}
	//Auxiliar method ---------------------------------------------------------
	private User isUserAunthenticate() {
		User actor;
		actor = this.userService.findByPrincipal();
		Assert.notNull(actor, "message.error.report.notuser");
		String authority;
		authority = actor.getUserAccount().getAuthorities().iterator().next().getAuthority();
		Assert.isTrue(authority.equals("USER"), "message.error.report.notuser");
		return actor;
	}
	private Moderator isModeratorAunthenticate() {
		Moderator actor;
		actor = this.moderatorService.findByPrincipal();
		Assert.notNull(actor, "message.error.report.notmoderator");
		String authority;
		authority = actor.getUserAccount().getAuthorities().iterator().next().getAuthority();
		Assert.isTrue(authority.equals("MODERATOR"), "message.error.report.notmoderator");
		return actor;
	}

	// Dashboard --------------------------------------------------------------

	// Req 33.3.1: The average and standard deviation of complaints per project.

	public Double avgReportsPerProject() {
		return this.reportRepository.avgReportsPerProject();
	}

	public Double stdReportsPerProject() {
		return this.reportRepository.stdReportsPerProject();
	}

	// Req 33.3.2: The ratio of complaints per project.

	public Double ratioReportsPerProject() {
		return this.reportRepository.ratioReportsPerProject();
	}

	// Req 33.3.3: The ratio of complaints per user.

	public Double ratioReportsPerUser() {
		return this.reportRepository.ratioReportsPerUser();
	}

	// Req 33.3.4: The ratio of legit complaints.

	public Double ratioLegitReports() {
		return this.reportRepository.ratioLegitReports();
	}
}
