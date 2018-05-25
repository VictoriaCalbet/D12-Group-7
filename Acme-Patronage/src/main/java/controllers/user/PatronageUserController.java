
package controllers.user;

import java.util.ArrayList;
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

import services.PatronageService;
import services.UserService;
import services.forms.PatronageFormService;
import controllers.AbstractController;
import domain.Patronage;
import domain.User;
import domain.forms.PatronageForm;

@Controller
@RequestMapping("/patronage/user")
public class PatronageUserController extends AbstractController {

	//Services

	@Autowired
	private PatronageFormService	patronageFormService;

	@Autowired
	private PatronageService		patronageService;

	@Autowired
	private UserService				userService;


	//Constructor

	public PatronageUserController() {
		super();
	}

	//Creating 
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int projectId) {
		ModelAndView result;

		PatronageForm patronageForm;

		patronageForm = this.patronageFormService.create(projectId);

		result = this.createEditModelAndView(patronageForm);

		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		User principal;

		principal = this.userService.findByPrincipal();

		Collection<Patronage> patronages = new ArrayList<Patronage>();
		patronages = principal.getPatronages();

		result = new ModelAndView("patronage/user/list");
		result.addObject("requestURI", "patronage/user/list.do");
		result.addObject("principal", principal);
		result.addObject("patronages", patronages);

		return result;

	}

	@RequestMapping(value = "/listPatronagesToMyProjects", method = RequestMethod.GET)
	public ModelAndView listPatronagesToMyProjects() {
		final ModelAndView result;

		User principal;

		principal = this.userService.findByPrincipal();
		final Collection<Patronage> patronages = this.patronageService.findAllPatronagesToUserProjects(principal.getId());
		result = new ModelAndView("patronage/user/listPatronagesToMyProjects");

		result.addObject("requestURI", "patronage/user/listPatronagesToMyProjects.do");

		result.addObject("principal", principal);
		result.addObject("patronages", patronages);

		return result;

	}
	//Editing
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int projectId) {

		Assert.notNull(projectId);

		ModelAndView result;
		final PatronageForm patronageForm;
		try {
			patronageForm = this.patronageFormService.create(projectId);
			result = this.createEditModelAndView(patronageForm);
		} catch (final Throwable oops) {
			String messageError = "patronage.commit.error";
			if (oops.getMessage().contains("message.error"))
				messageError = oops.getMessage();
			result = this.listModelAndView("redirect:/project/user/list.do", messageError);
		}

		return result;
	}
	//Saving 
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PatronageForm patronageForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(patronageForm);
		else
			try {
				if (patronageForm.getId() > 0)
					this.patronageFormService.saveFromEdit(patronageForm);

				else
					this.patronageFormService.saveFromCreate(patronageForm);

				result = new ModelAndView("redirect:/patronage/user/list.do");

			} catch (final Throwable oops) {
				String messageError = "patronage.commit.error";
				if (oops.getMessage().contains("message.error"))
					messageError = oops.getMessage();
				result = this.createEditModelAndView(patronageForm, messageError);

			}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final PatronageForm patronageForm) {
		ModelAndView result;

		result = this.createEditModelAndView(patronageForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PatronageForm patronageForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("patronage/user/edit");

		result.addObject("patronageForm", patronageForm);
		result.addObject("message", message);
		result.addObject("requestURI", "patronage/user/edit.do");
		return result;
	}

	protected ModelAndView listModelAndView(final String url) {
		ModelAndView result;

		result = this.listModelAndView(url, null);

		return result;
	}

	protected ModelAndView listModelAndView(final String url, final String message) {
		ModelAndView result;

		result = new ModelAndView(url);

		result.addObject("message", message);
		result.addObject("requestURI", url);
		return result;
	}
}
