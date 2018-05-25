
package controllers.actor;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageService;
import services.forms.MessageFormService;
import controllers.AbstractController;
import domain.Actor;
import domain.Message;
import domain.forms.MessageForm;

@Controller
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MessageService		messageService;

	@Autowired
	private MessageFormService	messageFormService;

	@Autowired
	private ActorService		actorService;


	// Constructor ------------------------------------------------------------

	public MessageActorController() {
		super();
	}

	@RequestMapping(value = "/outbox", method = RequestMethod.GET)
	public ModelAndView listSentMessages() {
		final ModelAndView result;
		Collection<Message> messages = new HashSet<Message>();
		final Actor principal = this.actorService.findByPrincipal();

		messages = this.messageService.findAllSentByActorId(principal.getId());

		result = new ModelAndView("message/list");
		result.addObject("messages", messages);
		result.addObject("requestURI", "message/actor/outbox.do");

		return result;
	}

	@RequestMapping(value = "/inbox", method = RequestMethod.GET)
	public ModelAndView listReceivedMessages() {
		final ModelAndView result;
		Collection<Message> messages = new HashSet<Message>();
		final Actor principal = this.actorService.findByPrincipal();

		messages = this.messageService.findAllReceivedByActorId(principal.getId());

		result = new ModelAndView("message/list");
		result.addObject("messages", messages);
		result.addObject("requestURI", "message/actor/inbox.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		MessageForm messageForm;

		messageForm = this.messageFormService.create();
		result = this.createEditModelAndView(messageForm);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MessageForm messageForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(messageForm);
		else
			try {
				this.messageFormService.saveFromCreate(messageForm);
				result = new ModelAndView("redirect:outbox.do");
			} catch (final Throwable oops) {
				String messageError = "message.commit.error";
				if (oops.getMessage().contains("message.error"))
					messageError = oops.getMessage();
				result = this.createEditModelAndView(messageForm, messageError);
			}

		return result;
	}
	// Ancillary methods

	public ModelAndView createEditModelAndView(final MessageForm messageForm) {
		ModelAndView result;

		result = this.createEditModelAndView(messageForm, null);

		return result;
	}

	public ModelAndView createEditModelAndView(final MessageForm messageForm, final String message) {
		ModelAndView result;
		Collection<Actor> recipients;

		recipients = this.actorService.findAll();
		recipients.remove(this.actorService.findByPrincipal());

		result = new ModelAndView("message/edit");
		result.addObject("messageForm", messageForm);
		result.addObject("recipients", recipients);
		result.addObject("message", message);
		result.addObject("requestURI", "message/actor/edit.do");

		return result;
	}
}
