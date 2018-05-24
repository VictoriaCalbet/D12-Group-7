
package controllers.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.PatronageService;
import services.ProjectService;
import services.UserService;
import services.forms.ProjectFormService;
import controllers.AbstractController;
import domain.Category;
import domain.Project;
import domain.User;
import domain.forms.ProjectForm;

@Controller
@RequestMapping("/project/user")
public class ProjectUserController extends AbstractController {

	@Autowired
	private ProjectService		projectService;
	@Autowired
	private UserService			userService;
	@Autowired
	private ProjectFormService	projectFormService;
	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private PatronageService	patronageService;


	public ProjectUserController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false, defaultValue = "") final String word, @RequestParam(required = false) final String message) {
		ModelAndView result;
		Collection<Project> projects = new ArrayList<Project>();
		final User u = this.userService.findByPrincipal();

		if (word == null || word.equals(""))
			projects = u.getProjects();
		else
			projects = this.projectService.findProjectByKeyWordByUser(word, u.getId());

		Map<Integer, Double> totalAmounts = new HashMap<>();
		totalAmounts = this.patronageService.findTotalAmount(projects);

		result = new ModelAndView("project/list");
		result.addObject("principal", u);
		result.addObject("totalAmounts", totalAmounts);
		result.addObject("projects", projects);
		result.addObject("message", message);
		result.addObject("requestURI", "project/user/list.do");

		return result;
	}

	// Creation ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		ProjectForm projectForm;

		projectForm = this.projectFormService.create();
		result = this.createModelAndView(projectForm);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@Valid final ProjectForm projectForm, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createModelAndView(projectForm);
		else
			try {
				this.projectFormService.saveFromCreate(projectForm);
				result = new ModelAndView("redirect:/project/user/list.do");
			} catch (final Throwable oops) {
				String messageError = "project.commit.error";
				if (oops.getMessage().contains("message.error"))
					messageError = oops.getMessage();
				result = this.createModelAndView(projectForm, messageError);
			}

		return result;
	}

	// Edit ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int projectId) {
		ModelAndView result;
		ProjectForm projectForm;

		try {
			projectForm = this.projectFormService.create(projectId);
			result = this.editModelAndView(projectForm);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/project/user/list.do");
			result.addObject("message", oops.getMessage());
		}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final ProjectForm projectForm, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(projectForm);
		else
			try {
				this.projectFormService.saveFromEdit(projectForm);
				result = new ModelAndView("redirect:/project/user/list.do");
			} catch (final Throwable oops) {
				String messageError = "project.commit.error";
				if (oops.getMessage().contains("message.error"))
					messageError = oops.getMessage();
				result = this.editModelAndView(projectForm, messageError);
			}

		return result;
	}

	// Cancel -----------------------------------------------------------------

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int projectId) {
		ModelAndView result;
		try {
			this.projectService.cancel(projectId);
			result = new ModelAndView("redirect:/project/user/list.do");
		} catch (final Throwable oops) {
			String messageError = "project.cancel.error";
			if (oops.getMessage().contains("message.error"))
				messageError = oops.getMessage();
			result = new ModelAndView("redirect:/project/user/list.do");
			result.addObject("message", messageError);
		}

		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int projectId) {
		ModelAndView result;
		try {
			this.projectService.delete(projectId);
			result = new ModelAndView("redirect:/project/user/list.do");
		} catch (final Throwable oops) {
			String messageError = "project.delete.error";
			if (oops.getMessage().contains("message.error"))
				messageError = oops.getMessage();
			result = new ModelAndView("redirect:/project/user/list.do");
			result.addObject("message", messageError);
		}

		return result;
	}

	// Ancillaty methods ------------------------------------------------------

	protected ModelAndView createModelAndView(final ProjectForm projectForm) {
		ModelAndView result;

		result = this.createModelAndView(projectForm, null);

		return result;
	}

	protected ModelAndView createModelAndView(final ProjectForm projectForm, final String message) {
		ModelAndView result;
		final Collection<Category> categories = this.categoryService.findAll();

		result = new ModelAndView("project/user/create");
		result.addObject("projectForm", projectForm);
		result.addObject("categories", categories);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editModelAndView(final ProjectForm projectForm) {
		ModelAndView result;

		result = this.editModelAndView(projectForm, null);

		return result;
	}

	protected ModelAndView editModelAndView(final ProjectForm projectForm, final String message) {
		ModelAndView result;

		final Collection<Category> categories = this.categoryService.findAll();

		result = new ModelAndView("project/user/edit");
		result.addObject("projectForm", projectForm);
		result.addObject("categories", categories);
		result.addObject("message", message);

		return result;
	}

}
