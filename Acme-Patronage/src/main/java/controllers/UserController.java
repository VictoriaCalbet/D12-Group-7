
package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.ProjectService;
import services.UserService;
import services.forms.ActorFormService;
import domain.Project;
import domain.User;
import domain.forms.ActorForm;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private UserService				userService;

	@Autowired
	private ProjectService			projectService;

	@Autowired
	private ActorFormService		actorFormService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	// Constructor ------------------------------------------------------------

	public UserController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<User> users = new HashSet<User>();
		Map<Integer, Boolean> hasLegitReports = new HashMap<>();

		users = this.userService.findAll();

		try {
			this.administratorService.findByPrincipal();
			hasLegitReports = this.actorService.hasLegitReports(users);
		} catch (final Exception e) {
			// TODO: handle exception
		}

		result = new ModelAndView("user/list");
		result.addObject("users", users);
		result.addObject("hasLegitReports", hasLegitReports);
		result.addObject("requestURI", "user/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int userId) {
		final ModelAndView result;
		User user;
		Collection<Project> createdProjects;
		Collection<Project> fundedProjects;

		user = this.userService.findOne(userId);
		Assert.notNull(user);

		createdProjects = this.projectService.findAllCreatedByUser(userId);
		fundedProjects = this.projectService.findAllFundedByUser(userId);

		result = new ModelAndView("user/display");
		result.addObject("user", user);
		result.addObject("createdProjects", createdProjects);
		result.addObject("fundedProjects", fundedProjects);
		result.addObject("requestURI", "user/display.do");
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		ActorForm actorForm;

		actorForm = this.actorFormService.create();
		result = this.createEditModelAndView(actorForm);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ActorForm actorForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(actorForm);
		else
			try {
				if (actorForm.getId() != 0)
					this.actorFormService.saveFromEdit(actorForm, "USER");
				else
					this.actorFormService.saveFromCreate(actorForm, "USER");
				result = new ModelAndView("redirect:../");
			} catch (final Throwable oops) {
				String messageError = "user.commit.error";
				if (oops.getMessage().contains("message.error"))
					messageError = oops.getMessage();
				result = this.createEditModelAndView(actorForm, messageError);
			}

		return result;
	}

	// Ancillary methods

	public ModelAndView createEditModelAndView(final ActorForm actorForm) {
		ModelAndView result;

		result = this.createEditModelAndView(actorForm, null);

		return result;
	}

	public ModelAndView createEditModelAndView(final ActorForm actorForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("actorForm/edit");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);
		result.addObject("requestURI", "user/edit.do");

		return result;
	}
}
