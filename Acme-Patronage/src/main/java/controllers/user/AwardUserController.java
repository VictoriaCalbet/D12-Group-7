
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
import services.forms.AwardFormService;
import domain.Award;
import domain.Project;
import domain.User;
import domain.forms.AwardForm;

@Controller
@RequestMapping("/award/user")
public class AwardUserController {

	// Services -------------------------------------------------------------

	@Autowired
	private AwardService		awardService;

	@Autowired
	private AwardFormService	awardFormService;

	@Autowired
	private ProjectService		projectService;

	@Autowired
	private UserService			userService;


	// Constructors ---------------------------------------------------------

	public AwardUserController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int projectId, @RequestParam(required = false) final String message) {
		ModelAndView result = null;
		Collection<Award> awards = null;
		Project project = null;
		User user = null;
		String requestURI = null;
		String displayURI = null;
		String createURI = null;
		String editURI = null;
		String deleteURI = null;
		boolean canCreate = false;

		project = this.projectService.findOne(projectId);
		user = this.userService.findByPrincipal();

		Assert.notNull(user);
		Assert.notNull(project);

		if (!project.getCreator().equals(user)) {      // Si user no es el creador del proyecto...
			Assert.isTrue(!project.getIsDraft());
			Assert.isTrue(!project.getIsCancelled());
		} else
			canCreate = true;

		requestURI = "award/user/list.do";
		displayURI = "award/user/display.do?awardId=";
		createURI = "award/user/create.do?projectId=" + projectId;
		editURI = "award/user/edit.do?awardId=";
		deleteURI = "award/user/delete.do?awardId=";

		awards = project.getAwards();

		result = new ModelAndView("award/list");
		result.addObject("awards", awards);
		result.addObject("requestURI", requestURI);
		result.addObject("displayURI", displayURI);
		result.addObject("createURI", createURI);
		result.addObject("editURI", editURI);
		result.addObject("deleteURI", deleteURI);
		result.addObject("canCreate", canCreate);
		result.addObject("message", message);

		return result;
	}

	// Creation  ------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int projectId) {
		ModelAndView result = null;
		AwardForm awardForm = null;
		User user = null;
		Project project = null;

		user = this.userService.findByPrincipal();
		project = this.projectService.findOne(projectId);

		Assert.notNull(user);
		Assert.notNull(project);
		Assert.isTrue(project.getCreator().equals(user), "message.error.award.user.owner");

		awardForm = this.awardFormService.createFromCreate(projectId);
		result = this.createEditModelAndView(awardForm);

		return result;
	}

	// Display --------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int awardId) {
		ModelAndView result = null;
		Award award = null;
		Project project = null;
		User user = null;
		String cancelURI = null;
		String editURI = null;

		user = this.userService.findByPrincipal();
		award = this.awardService.findOne(awardId);
		project = award.getProject();

		Assert.notNull(user);
		Assert.notNull(award);

		if (!project.getCreator().equals(user)) {      // Si user no es el creador del proyecto...
			Assert.isTrue(!project.getIsDraft());
			Assert.isTrue(!project.getIsCancelled());
		}

		cancelURI = "award/user/list.do?projectId=" + award.getProject().getId();
		editURI = "award/user/edit.do?awardId=" + award.getId();

		result = new ModelAndView("award/display");
		result.addObject("award", award);
		result.addObject("cancelURI", cancelURI);
		result.addObject("editURI", editURI);

		return result;
	}

	// Edition    -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int awardId) {
		ModelAndView result = null;
		AwardForm awardForm = null;
		Award award = null;
		User user = null;

		award = this.awardService.findOne(awardId);
		user = this.userService.findByPrincipal();

		Assert.isTrue(award.getProject().getCreator().equals(user));
		Assert.isTrue(award.getProject().getIsDraft());
		Assert.isTrue(!award.getProject().getIsCancelled());

		awardForm = this.awardFormService.createFromEdit(awardId);
		result = this.createEditModelAndView(awardForm);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AwardForm awardForm, final BindingResult bindingResult) {
		ModelAndView result = null;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(awardForm);
		else
			try {
				if (awardForm.getId() == 0)
					this.awardFormService.saveFromCreate(awardForm);
				else
					this.awardFormService.saveFromEdit(awardForm);

				result = new ModelAndView("redirect:/award/user/list.do?projectId=" + awardForm.getProjectId());

			} catch (final Throwable oops) {
				String messageError = "award.commit.error";
				if (oops.getMessage().contains("message.error"))
					messageError = oops.getMessage();
				result = this.createEditModelAndView(awardForm, messageError);
			}

		return result;
	}

	// Delete    ------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int awardId) {
		ModelAndView result = null;
		Award award = null;
		User user = null;

		award = this.awardService.findOne(awardId);
		user = this.userService.findByPrincipal();

		try {
			Assert.isTrue(award.getProject().getCreator().equals(user), "message.error.award.user.owner");
			Assert.isTrue(award.getProject().getIsDraft(), "message.error.award.project.isPublished");

			this.awardService.delete(award);
			result = new ModelAndView("redirect:/award/user/list.do?projectId=" + award.getProject().getId());
			result.addObject("message", "award.delete.success");
		} catch (final Throwable oops) {
			String messageError = "award.delete.error";

			if (oops.getMessage().contains("message.error"))
				messageError = oops.getMessage();

			result = new ModelAndView("redirect:/award/user/list.do?projectId=" + award.getProject().getId());
			result.addObject("message", messageError);
		}

		return result;
	}
	// Other actions --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final AwardForm awardForm) {
		ModelAndView result = null;
		result = this.createEditModelAndView(awardForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final AwardForm awardForm, final String message) {
		ModelAndView result = null;
		String actionURI = null;
		String cancelURI = null;
		User user = null;

		actionURI = "award/user/edit.do";

		user = this.userService.findByPrincipal();

		if (awardForm.getId() == 0) {
			result = new ModelAndView("award/create");
			cancelURI = "project/list.do";
		} else {
			result = new ModelAndView("award/edit");
			cancelURI = "award/user/list.do?projectId=" + awardForm.getProjectId();
		}

		result.addObject("user", user);
		result.addObject("awardForm", awardForm);
		result.addObject("actionURI", actionURI);
		result.addObject("cancelURI", cancelURI);
		result.addObject("message", message);

		return result;
	}
}
