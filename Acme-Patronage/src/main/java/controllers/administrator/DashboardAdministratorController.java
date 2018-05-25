
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AwardService;
import services.CorporationService;
import services.ProjectCommentService;
import services.ProjectService;
import services.ReportService;
import services.SponsorshipService;
import services.UserService;
import controllers.AbstractController;
import domain.Corporation;
import domain.Project;
import domain.User;

@Controller
@RequestMapping("/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ProjectService			projectService;

	@Autowired
	private AwardService			awardService;

	@Autowired
	private UserService				userService;

	@Autowired
	private SponsorshipService		sponsorshipService;

	@Autowired
	private ProjectCommentService	projectCommentService;

	@Autowired
	private CorporationService		corporationService;

	@Autowired
	private ReportService			reportService;


	// Constructors ---------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result = null;
		result = new ModelAndView("administrator/dashboard");

		// LEVEL C:

		// Req 12.2.1: The average and standard deviation of projects per user.
		final Double avgProjectsPerUser = this.projectService.avgProjectsPerUser();
		result.addObject("avgProjectsPerUser", avgProjectsPerUser);

		final Double stdProjectsPerUser = this.projectService.stdProjectsPerUser();
		result.addObject("stdProjectsPerUser", stdProjectsPerUser);

		// Req 12.2.2: The average and standard deviation of awards per project.

		final Double avgAwardsPerProject = this.awardService.avgAwardsPerProject();
		result.addObject("avgAwardsPerProject", avgAwardsPerProject);

		final Double stdAwardsPerProject = this.awardService.stdAwardsPerProject();
		result.addObject("stdAwardsPerProject", stdAwardsPerProject);

		// Req 12.2.3: The average and standard deviation of awards per user.

		final Double avgAwardsPerUser = this.awardService.avgAwardsPerUser();
		result.addObject("avgAwardsPerUser", avgAwardsPerUser);

		final Double stdAwardsPerUser = this.awardService.stdAwardsPerUser();
		result.addObject("stdAwardsPerUser", stdAwardsPerUser);

		// Req 12.2.4: The ratio of funded projects.

		final Double ratioFundedProjects = this.projectService.ratioFundedProjects();
		result.addObject("ratioFundedProjects", ratioFundedProjects);

		// Req 12.2.5: The users who have, at least, a 10% more projects than the average.

		final Collection<User> findAllWith10PercentMoreProjectsThanAvg = this.userService.findAllWith10PercentMoreProjectsThanAvg();
		result.addObject("findAllWith10PercentMoreProjectsThanAvg", findAllWith10PercentMoreProjectsThanAvg);

		// Req 12.2.6: The users who have, at least, 10% more patronages than the average.

		final Collection<User> findAllWith10PercentMorePatronagesThanAvg = this.userService.findAllWith10PercentMorePatronagesThanAvg();
		result.addObject("findAllWith10PercentMorePatronagesThanAvg", findAllWith10PercentMorePatronagesThanAvg);

		// Req 12.2.7: The top-5 active projects (with a limit date not due) with more raised money.
		final Collection<Project> top5ActiveProjectsWithMoreRaisedMoney = this.projectService.top5ActiveProjectsWithMoreRaisedMoney();
		result.addObject("top5ActiveProjectsWithMoreRaisedMoney", top5ActiveProjectsWithMoreRaisedMoney);

		// Req 12.2.8: The top-5 due projects with more raised money.

		final Collection<Project> top5DueProjectsWithMoreRaisedMoney = this.projectService.top5DueProjectsWithMoreRaisedMoney();
		result.addObject("top5DueProjectsWithMoreRaisedMoney", top5DueProjectsWithMoreRaisedMoney);

		// LEVEL B:

		// Req 25.2.1: The average and standard deviation of sponsorships per corporation.

		final Double avgSponsorshipPerCorporation = this.sponsorshipService.avgSponsorshipPerCorporation();
		result.addObject("avgSponsorshipPerCorporation", avgSponsorshipPerCorporation);

		final Double stdSponsorshipPerCorporation = this.sponsorshipService.stdSponsorshipPerCorporation();
		result.addObject("stdSponsorshipPerCorporation", stdSponsorshipPerCorporation);

		// Req 25.2.2: The average and standard deviation of sponsorships per project.

		final Double avgSponsorshipPerProject = this.sponsorshipService.avgSponsorshipPerProject();
		result.addObject("avgSponsorshipPerProject", avgSponsorshipPerProject);

		final Double stdSponsorshipPerProject = this.sponsorshipService.stdSponsorshipPerProject();
		result.addObject("stdSponsorshipPerProject", stdSponsorshipPerProject);

		// Req 25.2.3: The average and standard deviation of comments per project.

		final Double avgCommentsPerProject = this.projectCommentService.avgCommentsPerProject();
		result.addObject("avgCommentsPerProject", avgCommentsPerProject);

		final Double stdCommentsPerProject = this.projectCommentService.stdCommentsPerProject();
		result.addObject("stdCommentsPerProject", stdCommentsPerProject);

		// Req 25.2.4: The projects that have, at least, a 10% more sponsorships than the average.

		final Collection<Project> findAllProjectWith10PercentMoreSponsorshipsThanAvg = this.projectService.findAllWith10PercentMoreSponsorshipsThanAvg();
		result.addObject("findAllProjectWith10PercentMoreSponsorshipsThanAvg", findAllProjectWith10PercentMoreSponsorshipsThanAvg);

		// Req 25.2.5: The corporations that have, at least, a 10% more sponsorships than the average.

		final Collection<Corporation> findAllCorporationWith10PercentMoreSponsorshipsThanAvg = this.corporationService.findAllWith10PercentMoreSponsorshipsThanAvg();
		result.addObject("findAllCorporationWith10PercentMoreSponsorshipsThanAvg", findAllCorporationWith10PercentMoreSponsorshipsThanAvg);

		// LEVEL A:

		// Req 33.3.1: The average and standard deviation of complaints per project.

		final Double avgReportsPerProject = this.reportService.avgReportsPerProject();
		result.addObject("avgReportsPerProject", avgReportsPerProject);

		final Double stdReportsPerProject = this.reportService.stdReportsPerProject();
		result.addObject("stdReportsPerProject", stdReportsPerProject);

		// Req 33.3.2: The ratio of complaints per project.

		final Double ratioReportsPerProject = this.reportService.ratioReportsPerProject();
		result.addObject("ratioReportsPerProject", ratioReportsPerProject);

		// Req 33.3.3: The ratio of complaints per user.

		final Double ratioReportsPerUser = this.reportService.ratioReportsPerUser();
		result.addObject("ratioReportsPerUser", ratioReportsPerUser);

		// Req 33.3.4: The ratio of legit complaints.

		final Double ratioLegitReports = this.reportService.ratioLegitReports();
		result.addObject("ratioLegitReports", ratioLegitReports);

		// Req 33.3.6: The ratio of banned users.

		final Double ratioBannedUsers = this.userService.ratioBannedUsers();
		result.addObject("ratioBannedUsers", ratioBannedUsers);

		result.addObject("requestURI", "administrator/dashboard.do");
		return result;
	}
}
