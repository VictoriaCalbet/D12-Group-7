
package controllers;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CorporationService;
import services.forms.ActorFormService;
import domain.Corporation;
import domain.forms.ActorForm;

@Controller
@RequestMapping("/corporation")
public class CorporationController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorFormService	actorFormService;

	@Autowired
	private CorporationService	corporationService;


	// Constructor ------------------------------------------------------------

	public CorporationController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Corporation> corporations = new HashSet<Corporation>();

		corporations = this.corporationService.findAll();

		result = new ModelAndView("corporation/list");
		result.addObject("corporations", corporations);
		result.addObject("requestURI", "corporation/list.do");

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
					this.actorFormService.saveFromEdit(actorForm, "CORPORATION");
				else
					this.actorFormService.saveFromCreate(actorForm, "CORPORATION");
				result = new ModelAndView("redirect:../");
			} catch (final Throwable oops) {
				String messageError = "corporation.commit.error";
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
		result.addObject("requestURI", "corporation/edit.do");

		return result;
	}
}
