
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	@Autowired
	private UserService			userService;

	@Autowired
	private ModeratorService	moderatorService;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ReportService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: Report - create
	public Report create() {
		final Report result = null;

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
		Assert.notNull(report);
		Assert.isTrue(report.getId() == 0);
		Assert.notNull(report.getProject());
		Boolean isLegit;
		isLegit = null;
		report.setIsLegit(isLegit);
		report.setUser(this.isUserAunthenticate());
		Report result;
		result = this.reportRepository.save(report);
		return result;
	}

	public void flush() {
		this.reportRepository.flush();
	}

	// Other business methods -------------------------------------------------
	public Report acceptReport(final int reportId) {
		Report reportInDB;
		reportInDB = this.reportRepository.findOne(reportId);
		Assert.notNull(reportInDB);
		Assert.isNull(reportInDB.getIsLegit());
		this.isModeratorAunthenticate();
		Boolean isLegit;
		isLegit = true;
		reportInDB.setIsLegit(isLegit);
		Report savedReport;
		savedReport = this.reportRepository.save(reportInDB);
		return savedReport;

	}

	public Report rejectReport(final int reportId) {
		Report reportInDB;
		reportInDB = this.reportRepository.findOne(reportId);
		Assert.notNull(reportInDB);
		Assert.isNull(reportInDB.getIsLegit());
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
		Boolean isLegit;
		isLegit = null;
		result = new ArrayList<Report>(this.reportRepository.findByLegitimacy(isLegit));
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
		Assert.notNull(actor);
		String authority;
		authority = actor.getUserAccount().getAuthorities().iterator().next().getAuthority();
		Assert.isTrue(authority.equals("USER"), "message.error.report.notuser");
		return actor;
	}
	private Moderator isModeratorAunthenticate() {
		Moderator actor;
		actor = this.moderatorService.findByPrincipal();
		Assert.notNull(actor);
		String authority;
		authority = actor.getUserAccount().getAuthorities().iterator().next().getAuthority();
		Assert.isTrue(authority.equals("MODERATOR"), "message.error.report.notmoderator");
		return actor;
	}
}
