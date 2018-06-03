
package controllers.user;

import java.util.Collection;
import java.util.Date;

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
import controllers.AbstractController;
import domain.Award;
import domain.Project;
import domain.User;
import domain.forms.AwardForm;

@Controller
@RequestMapping("/award/user")
public class AwardUserController extends AbstractController {

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
	public ModelAndView list() {
		ModelAndView result = null;
		Collection<Award> awards = null;
		String requestURI = null;
		String displayURI = null;
		User user = null;

		result = new ModelAndView("award/list");

		try {
			user = this.userService.findByPrincipal();
			Assert.notNull(user, "message.error.award.principal.null");
			awards = this.awardService.findMyAwards(user.getId());

			requestURI = "award/user/list.do";
			displayURI = "award/display.do?awardId=";

			result.addObject("awards", awards);
			result.addObject("requestURI", requestURI);
			result.addObject("displayURI", displayURI);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/project/list.do");
			result.addObject("message", oops.getMessage());
		}

		return result;
	}
	// Creation  ------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int projectId) {
		ModelAndView result = null;
		AwardForm awardForm = null;
		User user = null;
		Project project = null;

		try {
			user = this.userService.findByPrincipal();
			project = this.projectService.findOne(projectId);

			Assert.notNull(user, "message.error.award.principal.null");
			Assert.notNull(project, "message.error.award.null");
			Assert.isTrue(project.getCreator().equals(user), "message.error.award.user.owner");
			Assert.isTrue(!project.getIsCancelled(), "message.error.award.project.isCancelled");
			Assert.isTrue(project.getDueDate().after(new Date(System.currentTimeMillis() - 1000)), "message.error.award.dueDateIsPast");

			awardForm = this.awardFormService.createFromCreate(projectId);
			result = this.createEditModelAndView(awardForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/award/list.do?projectId=" + projectId);
			result.addObject("message", oops.getMessage());
		}

		return result;
	}

	// Display --------------------------------------------------------------

	// Edition    -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int awardId) {
		ModelAndView result = null;
		AwardForm awardForm = null;
		Award award = null;
		User user = null;

		try {
			award = this.awardService.findOne(awardId);
			user = this.userService.findByPrincipal();

			Assert.notNull(user, "message.error.award.principal.null");
			Assert.notNull(award, "message.error.award.null");
			Assert.isTrue(award.getProject().getCreator().equals(user), "message.error.award.user.owner");
			Assert.isTrue(award.getProject().getIsDraft(), "message.error.award.project.isPublished");
			Assert.isTrue(!award.getProject().getIsCancelled(), "message.error.award.project.isCancelled");

			awardForm = this.awardFormService.createFromEdit(awardId);
			result = this.createEditModelAndView(awardForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/project/list.do");
			result.addObject("message", oops.getMessage());
		}

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

				result = new ModelAndView("redirect:/award/list.do?projectId=" + awardForm.getProjectId());

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

		try {
			award = this.awardService.findOne(awardId);
			user = this.userService.findByPrincipal();

			Assert.notNull(award, "message.error.award.null");
			Assert.notNull(user, "message.error.award.principal.null");
			Assert.isTrue(award.getProject().getCreator().equals(user), "message.error.award.user.owner");
			Assert.isTrue(!award.getProject().getIsCancelled(), "message.error.award.project.isCancelled");
			Assert.isTrue(award.getProject().getIsDraft(), "message.error.award.project.isPublished");
			Assert.isTrue(award.getProject().getDueDate().after(new Date(System.currentTimeMillis() - 1000)), "message.error.award.dueDateIsPast");

			this.awardService.delete(award);

			result = new ModelAndView("redirect:/award/list.do?projectId=" + award.getProject().getId());
			result.addObject("message", "award.delete.success");
		} catch (final Throwable oops) {
			String messageError = "award.delete.error";

			if (oops.getMessage().contains("message.error"))
				messageError = oops.getMessage();

			if (award != null)
				result = new ModelAndView("redirect:/award/list.do?projectId=" + award.getProject().getId());
			else
				result = new ModelAndView("redirect:/project/list.do");

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
		Project project = null;
		User user = null;

		actionURI = "award/user/edit.do";

		user = this.userService.findByPrincipal();
		project = this.projectService.findOne(awardForm.getProjectId());
		awardForm.setMinimumPatronageAmount(project.getMinimumPatronageAmount());

		cancelURI = "award/list.do?projectId=" + awardForm.getProjectId();

		if (awardForm.getId() == 0)
			result = new ModelAndView("award/create");
		else
			result = new ModelAndView("award/edit");

		result.addObject("user", user);
		result.addObject("awardForm", awardForm);
		result.addObject("actionURI", actionURI);
		result.addObject("cancelURI", cancelURI);
		result.addObject("message", message);

		return result;
	}
}
