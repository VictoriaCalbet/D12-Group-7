
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;

@Controller
@RequestMapping("/user/administrator")
public class UserAdministratorController extends AbstractController {

	@Autowired
	private ActorService	actorService;


	public UserAdministratorController() {
		super();
	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int userId) {
		ModelAndView result;

		try {
			this.actorService.ban(userId);
			result = new ModelAndView("redirect:/user/list.do");
		} catch (final Throwable oops) {
			String messageError = "user.commit.error";
			if (oops.getMessage().contains("message.error"))
				messageError = oops.getMessage();
			result = new ModelAndView("redirect:/user/list.do");
			result.addObject("message", messageError);
		}

		return result;
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(@RequestParam final int userId) {
		ModelAndView result;

		try {
			this.actorService.unban(userId);
			result = new ModelAndView("redirect:/user/list.do");
		} catch (final Throwable oops) {
			String messageError = "user.commit.error";
			if (oops.getMessage().contains("message.error"))
				messageError = oops.getMessage();
			result = new ModelAndView("redirect:/user/list.do");
			result.addObject("message", messageError);
		}

		return result;
	}

}
