
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
import services.ProjectService;
import domain.Actor;
import domain.Announcement;
import domain.Project;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private ProjectService	projectService;

	@Autowired
	private ActorService	actorService;


	// Constructors ---------------------------------------------------------

	public AnnouncementController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int projectId) {
		ModelAndView result = null;
		Collection<Announcement> announcements = null;
		Project project = null;
		Actor actor = null;
		String requestURI = null;

		project = this.projectService.findOne(projectId);

		Assert.notNull(project);

		if (this.actorService.checkLogin()) {
			actor = this.actorService.findByPrincipal();

			if (this.actorService.checkAuthority(actor, "USER"))
				result = new ModelAndView("redirect:/announcement/user/list.do?projectId=" + projectId);

		} else {
			Assert.isTrue(!project.getIsDraft());
			Assert.isTrue(!project.getIsCancelled());

			requestURI = "announcement/list.do";

			announcements = project.getAnnouncements();

			result = new ModelAndView("announcement/list");
			result.addObject("announcements", announcements);
			result.addObject("requestURI", requestURI);
		}

		return result;
	}

	// Display --------------------------------------------------------------

}
