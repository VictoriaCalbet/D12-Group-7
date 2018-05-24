
package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AwardService;
import services.ProjectService;
import services.UserService;
import domain.Award;
import domain.Project;
import domain.User;

@Controller
@RequestMapping("/award/user")
public class AwardUserController {

	// Services -------------------------------------------------------------

	@Autowired
	private AwardService	awardService;

	@Autowired
	private ProjectService	projectService;

	@Autowired
	private UserService		userService;


	// Constructors ---------------------------------------------------------

	public AwardUserController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int projectId) {
		ModelAndView result = null;
		Collection<Award> awards = null;
		Project project = null;
		User user = null;
		String requestURI = null;
		String displayURI = null;
		String createURI = null;
		boolean canCreate = false;

		project = this.projectService.findOne(projectId);
		user = this.userService.findByPrincipal();

		Assert.notNull(user);
		Assert.notNull(project);

		if (project.getCreator().equals(user)) {
			Assert.isTrue(!project.getIsDraft() || !project.getIsCancelled());
			canCreate = true;
		}

		requestURI = "award/user/list.do";
		displayURI = "award/user/display.do?awardId=";
		createURI = "award/user/create.do?projectId=" + projectId;

		awards = project.getAwards();

		result = new ModelAndView("award/list");
		result.addObject("awards", awards);
		result.addObject("requestURI", requestURI);
		result.addObject("displayURI", displayURI);
		result.addObject("createURI", createURI);
		result.addObject("canCreate", canCreate);

		return result;
	}

	// Creation  ------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int projectId) {
		ModelAndView result = null;
		Award award = null;

		award = this.awardService.create(projectId);
		result = this.createEditModelAndView(award);

		return result;
	}

	// Display --------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int awardId) {
		ModelAndView result = null;
		Award award = null;
		User user = null;
		String cancelURI = null;
		String editURI = null;
		boolean canEdit = false;

		user = this.userService.findByPrincipal();
		award = this.awardService.findOne(awardId);

		Assert.notNull(user);
		Assert.notNull(award);

		if (award.getProject().getCreator().equals(user)) {
			Assert.isTrue(!award.getProject().getIsDraft() || !award.getProject().getIsCancelled());
			if (award.getProject().getIsDraft())
				canEdit = true;
		}

		cancelURI = "award/user/list.do?projectId=" + award.getProject().getId();
		editURI = "award/user/edit.do?awardId=" + award.getId();

		result = new ModelAndView("award/display");
		result.addObject("award", award);
		result.addObject("cancelURI", cancelURI);
		result.addObject("editURI", editURI);
		result.addObject("canEdit", canEdit);

		return result;
	}

	// Edition    -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int awardId) {
		ModelAndView result = null;
		Award award = null;
		User user = null;

		award = this.awardService.findOne(awardId);
		user = this.userService.findByPrincipal();

		Assert.isTrue(award.getProject().getCreator().equals(user));
		Assert.isTrue(award.getProject().getIsDraft() || !award.getProject().getIsCancelled());

		result = this.createEditModelAndView(award);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Award award, final BindingResult bindingResult) {
		ModelAndView result = null;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(award);
		else
			try {
				if (award.getId() == 0)
					this.awardService.saveFromCreate(award);
				else
					this.awardService.saveFromEdit(award);

				result = new ModelAndView("redirect:/award/user/list.do?projectId=" + award.getProject().getId());

			} catch (final Throwable oops) {
				String messageError = "award.commit.error";
				if (oops.getMessage().contains("message.error"))
					messageError = oops.getMessage();
				result = this.createEditModelAndView(award, messageError);
			}

		return result;
	}

	// Other actions --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Award award) {
		ModelAndView result = null;

		result = this.createEditModelAndView(award, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Award award, final String message) {
		ModelAndView result = null;
		Collection<Project> availableProjects = null;
		String actionURI = null;
		String cancelURI = null;
		User user = null;

		actionURI = "award/user/edit.do";

		user = this.userService.findByPrincipal();

		availableProjects = this.projectService.findProjects(user.getId(), false, false);

		if (award.getId() == 0) {
			result = new ModelAndView("award/create");
			cancelURI = "project/list.do";
		} else {
			result = new ModelAndView("award/edit");
			cancelURI = "award/user/list.do?projectId=" + award.getProject().getId();
		}

		result.addObject("user", user);
		result.addObject("award", award);
		result.addObject("actionURI", actionURI);
		result.addObject("cancelURI", cancelURI);
		result.addObject("availableProjects", availableProjects);
		result.addObject("message", message);

		return result;
	}
}
