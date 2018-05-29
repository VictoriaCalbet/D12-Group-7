
package controllers;

import java.util.Collection;
import java.util.Date;

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
import services.UserService;
import domain.Actor;
import domain.Award;
import domain.Project;
import domain.User;

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

	@Autowired
	private UserService		userService;


	// Constructors ---------------------------------------------------------

	public AwardController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int projectId, @RequestParam(required = false) final String message) {
		ModelAndView result = null;
		Collection<Award> awards = null;
		Project project = null;
		String requestURI = null;
		String displayURI = null;
		String createURI = null;
		String editURI = null;
		String deleteURI = null;
		Actor actor = null;
		boolean canCreate = false;

		result = new ModelAndView("award/list");

		// ¿Quienes pueden entrar aqui? ADMIN, USER, MODERATOR, CORPORATION, anonymous
		try {
			project = this.projectService.findOne(projectId);
			Assert.notNull(project, "message.error.award.project.null");

			if (this.actorService.checkLogin()) {		// Si el usuario logueado en el sistema...
				actor = this.actorService.findByPrincipal();

				if (this.actorService.checkAuthority(actor, "USER")) {			//...es USER
					User principal = null;
					principal = this.userService.findByPrincipal();

					// Solo el creador puede ver los premios si es draft
					if (!project.getCreator().equals(principal)) {				// Si no es el creator del project
						Assert.isTrue(!project.getIsDraft(), "message.error.award.project.isNotPublished");
						awards = project.getAwards();
					} else {
						awards = project.getAwards();
						if (!project.getIsCancelled() && project.getDueDate().after(new Date(System.currentTimeMillis() - 1000)))
							canCreate = true;
					}
				} else if (this.actorService.checkAuthority(actor, "ADMIN"))	//...es ADMIN
					awards = project.getAwards();
				else {															//...es MODERATOR o CORPORATION
					Assert.isTrue(!project.getIsCancelled(), "message.error.award.project.isCancelled");
					Assert.isTrue(!project.getIsDraft(), "message.error.award.project.isNotPublished");
					awards = project.getAwards();
				}
			} else {															// Si es anonymous	
				Assert.isTrue(!project.getIsCancelled(), "message.error.award.project.isCancelled");
				Assert.isTrue(!project.getIsDraft(), "message.error.award.project.isNotPublished");
				awards = project.getAwards();
			}

			requestURI = "award/list.do";
			displayURI = "award/display.do?awardId=";
			createURI = "award/user/create.do?projectId=" + projectId;
			editURI = "award/user/edit.do?awardId=";
			deleteURI = "award/user/delete.do?awardId=";

			result.addObject("awards", awards);
			result.addObject("requestURI", requestURI);
			result.addObject("displayURI", displayURI);
			result.addObject("createURI", createURI);
			result.addObject("editURI", editURI);
			result.addObject("deleteURI", deleteURI);
			result.addObject("canCreate", canCreate);
			result.addObject("message", message);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/project/list.do");
			result.addObject("message", oops.getMessage());
		}

		return result;
	}
	// Display --------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int awardId) {
		ModelAndView result = null;
		Award award = null;
		Project project = null;
		String cancelURI = null;
		String editURI = null;
		Actor actor = null;

		result = new ModelAndView("award/display");

		// ¿Quienes pueden entrar aqui? ADMIN, USER, MODERATOR, CORPORATION, anonymous
		try {
			award = this.awardService.findOne(awardId);
			Assert.notNull(award, "message.error.award.null");

			project = award.getProject();
			Assert.notNull(project, "message.error.award.project.null");

			if (this.actorService.checkLogin()) {				// Si el usuario logueado en el sistema...
				actor = this.actorService.findByPrincipal();

				if (this.actorService.checkAuthority(actor, "USER")) {			//...es USER
					User principal = null;
					principal = this.userService.findByPrincipal();

					// Solo el creador puede ver un premio si es draft
					if (!project.getCreator().equals(principal)) {      // Si user no es el creador del proyecto...
						Assert.isTrue(!project.getIsCancelled(), "message.error.award.project.isCancelled");
						Assert.isTrue(!project.getIsDraft(), "message.error.award.project.isNotPublished");
					}
				} else if (this.actorService.checkAuthority(actor, "ADMIN")) {	//...es ADMIN
					// Puede visualizarlo directamente
				} else {														//...es MODERATOR o CORPORATION
					Assert.isTrue(!project.getIsCancelled(), "message.error.award.project.isCancelled");
					Assert.isTrue(!project.getIsDraft(), "message.error.award.project.isNotPublished");
				}

			} else {															// Si es anonymous
				Assert.isTrue(!project.getIsCancelled(), "message.error.award.project.isCancelled");
				Assert.isTrue(!project.getIsDraft(), "message.error.award.project.isNotPublished");
			}

			cancelURI = "award/list.do?projectId=" + award.getProject().getId();
			editURI = "award/user/edit.do?awardId=" + award.getId();

			result.addObject("award", award);
			result.addObject("cancelURI", cancelURI);
			result.addObject("editURI", editURI);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/project/list.do");
			result.addObject("message", oops.getMessage());
		}

		return result;
	}
}
