
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AwardService;
import services.ProjectService;
import domain.Actor;
import domain.Award;
import domain.Project;

@Controller
@RequestMapping("/award")
public class AwardController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private AwardService	awardService;

	@Autowired
	private ProjectService	projectService;

	@Autowired
	private ActorService	actorService;


	// Constructors ---------------------------------------------------------

	public AwardController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int projectId) {
		ModelAndView result = null;
		Collection<Award> awards = null;
		Actor actor = null;
		Project project = null;
		String requestURI = null;
		String displayURI = null;

		project = this.projectService.findOne(projectId);

		Assert.notNull(project);

		if (this.actorService.checkLogin()) {

			actor = this.actorService.findByPrincipal();

			if (this.actorService.checkAuthority(actor, "USER"))
				if (!project.getCreator().equals(actor))
					Assert.isTrue(!project.getIsDraft() || !project.getIsCancelled());
		} else {
			Assert.isTrue(!project.getIsDraft());
			Assert.isTrue(!project.getIsCancelled());
		}

		requestURI = "award/list.do";
		displayURI = "award/display.do?awardId=";

		awards = project.getAwards();

		result = new ModelAndView("award/list");
		result.addObject("awards", awards);
		result.addObject("requestURI", requestURI);
		result.addObject("displayURI", displayURI);

		return result;
	}
	// Display --------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int awardId) {
		ModelAndView result = null;
		Award award = null;
		Actor actor = null;
		Project project = null;
		String cancelURI = null;

		award = this.awardService.findOne(awardId);
		project = award.getProject();
		cancelURI = "award/list.do?projectId=" + award.getProject().getId();

		Assert.notNull(award);

		if (this.actorService.checkLogin()) {

			actor = this.actorService.findByPrincipal();

			if (this.actorService.checkAuthority(actor, "USER"))
				if (!project.getCreator().equals(actor))
					Assert.isTrue(!project.getIsDraft() || !project.getIsCancelled());
		} else {
			Assert.isTrue(!project.getIsDraft());
			Assert.isTrue(!project.getIsCancelled());
		}

		result = new ModelAndView("award/display");
		result.addObject("award", award);
		result.addObject("cancelURI", cancelURI);

		return result;
	}
}
