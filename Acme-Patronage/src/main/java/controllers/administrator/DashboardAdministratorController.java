
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AwardService;
import services.ProjectService;
import services.UserService;
import controllers.AbstractController;
import domain.Project;
import domain.User;

@Controller
@RequestMapping("/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ProjectService	projectService;

	@Autowired
	private AwardService	awardService;

	@Autowired
	private UserService		userService;


	// Constructors ---------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result = null;
		result = new ModelAndView("administrator/dashboard");

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

		result.addObject("requestURI", "administrator/dashboard.do");
		return result;
	}
}
