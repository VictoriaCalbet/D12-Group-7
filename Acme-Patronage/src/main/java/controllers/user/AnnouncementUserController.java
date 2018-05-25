
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

import services.ProjectService;
import services.UserService;
import services.forms.AnnouncementFormService;
import controllers.AbstractController;
import domain.Announcement;
import domain.Project;
import domain.User;
import domain.forms.AnnouncementForm;

@Controller
@RequestMapping("/announcement/user")
public class AnnouncementUserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private AnnouncementFormService	announcementFormService;

	@Autowired
	private ProjectService			projectService;

	@Autowired
	private UserService				userService;


	// Constructors ---------------------------------------------------------

	public AnnouncementUserController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer projectId) {
		ModelAndView result = null;
		Collection<Announcement> announcements = null;
		Project project = null;
		User user = null;
		String requestURI = null;
		String createURI = null;

		user = this.userService.findByPrincipal();
		Assert.notNull(user);

		requestURI = "announcement/user/list.do";
		createURI = "announcement/user/create.do?projectId=" + projectId;

		if (projectId != null) {
			project = this.projectService.findOne(projectId);
			Assert.notNull(project);
			announcements = project.getAnnouncements();
		} else
			announcements = user.getAnnouncements();

		// TODO: ¿es necesario?
		if (project.getCreator().equals(user)) {
			Assert.isTrue(!project.getIsDraft());
			Assert.isTrue(!project.getIsCancelled());
		}

		result = new ModelAndView("announcement/list");
		result.addObject("announcements", announcements);
		result.addObject("requestURI", requestURI);
		result.addObject("createURI", createURI);

		return result;
	}

	// Creation  ------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int projectId) {
		ModelAndView result = null;
		AnnouncementForm announcementForm = null;
		User user = null;
		Project project = null;

		user = this.userService.findByPrincipal();
		project = this.projectService.findOne(projectId);

		Assert.notNull(user);
		Assert.notNull(project);
		Assert.isTrue(project.getCreator().equals(user), "message.error.award.user.owner");

		announcementForm = this.announcementFormService.createFromCreate(projectId);
		result = this.createModelAndView(announcementForm);

		return result;
	}

	// Edition    -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AnnouncementForm announcementForm, final BindingResult bindingResult) {
		ModelAndView result = null;

		if (bindingResult.hasErrors())
			result = this.createModelAndView(announcementForm);
		else
			try {
				this.announcementFormService.saveFromCreate(announcementForm);

				result = new ModelAndView("redirect:/announcement/user/list.do?projectId=" + announcementForm.getProjectId());

			} catch (final Throwable oops) {
				String messageError = "announcement.commit.error";
				if (oops.getMessage().contains("message.error"))
					messageError = oops.getMessage();
				result = this.createModelAndView(announcementForm, messageError);
			}

		return result;
	}

	// Other actions --------------------------------------------------------

	protected ModelAndView createModelAndView(final AnnouncementForm announcementForm) {
		ModelAndView result = null;
		result = this.createModelAndView(announcementForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final AnnouncementForm announcementForm, final String message) {
		ModelAndView result = null;
		String actionURI = null;
		String cancelURI = null;
		User user = null;

		actionURI = "announcement/user/edit.do";

		user = this.userService.findByPrincipal();

		result = new ModelAndView("announcement/create");
		cancelURI = "announcement/user/list.do?projectId=" + announcementForm.getProjectId();

		result.addObject("user", user);
		result.addObject("announcementForm", announcementForm);
		result.addObject("actionURI", actionURI);
		result.addObject("cancelURI", cancelURI);
		result.addObject("message", message);

		return result;
	}
}
