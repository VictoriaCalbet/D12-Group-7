
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
import services.UserService;
import domain.Actor;
import domain.Announcement;
import domain.Project;
import domain.User;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private ProjectService	projectService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private UserService		userService;


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
		String requestURI = null;
		String createURI = null;
		Actor actor = null;
		boolean canCreate = false;

		result = new ModelAndView("announcement/list");
		project = this.projectService.findOne(projectId);
		Assert.notNull(project);

		// �Quienes pueden entrar aqui? ADMIN, USER, MODERATOR, CORPORATION, anonymous
		try {
			if (this.actorService.checkLogin()) {
				actor = this.actorService.findByPrincipal();

				if (this.actorService.checkAuthority(actor, "USER")) {			//...es USER
					User principal = null;
					principal = this.userService.findByPrincipal();

					// Los announcements pueden verlos si no es draft
					if (!project.getCreator().equals(principal)) {				// Si no es el creator del project
						Assert.isTrue(!project.getIsDraft());
						announcements = project.getAnnouncements();
					} else {
						announcements = project.getAnnouncements();
						canCreate = true;
					}
				} else if (this.actorService.checkAuthority(actor, "ADMIN"))	//...es ADMIN
					announcements = project.getAnnouncements();
				else {															//...es MODERATOR o CORPORATION
					Assert.isTrue(!project.getIsDraft());
					announcements = project.getAnnouncements();
				}
			} else {															// Si es anonymous
				Assert.isTrue(!project.getIsDraft());
				announcements = project.getAnnouncements();
			}

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/project/list.do?projectId=" + projectId);
			//			result.addObject("message", oops.getMessage());
		}

		requestURI = "announcement/list.do";
		createURI = "announcement/user/create.do?projectId=" + projectId;

		result.addObject("announcements", announcements);
		result.addObject("requestURI", requestURI);
		result.addObject("createURI", createURI);
		result.addObject("canCreate", canCreate);

		return result;
	}

	// Display --------------------------------------------------------------

}
