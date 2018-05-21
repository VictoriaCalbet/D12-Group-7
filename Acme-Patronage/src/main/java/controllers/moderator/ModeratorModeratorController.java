
package controllers.moderator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.forms.ActorFormService;
import domain.forms.ActorForm;

@Controller
@RequestMapping("/moderator/moderator")
public class ModeratorModeratorController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorFormService	actorFormService;


	// Constructor ------------------------------------------------------------

	public ModeratorModeratorController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		ActorForm actorForm;

		actorForm = this.actorFormService.createFromActor();
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
					this.actorFormService.saveFromEdit(actorForm, "MODERATOR");
				else
					this.actorFormService.saveFromCreate(actorForm, "MODERATOR");
				result = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				String messageError = "moderator.commit.error";
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
		result.addObject("requestURI", "moderator/moderator/edit.do");

		return result;
	}
}
