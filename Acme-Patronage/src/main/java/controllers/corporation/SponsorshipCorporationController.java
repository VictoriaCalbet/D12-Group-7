
package controllers.corporation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CorporationService;
import services.ProjectService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Corporation;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship/corporation")
public class SponsorshipCorporationController extends AbstractController {

	@Autowired
	private SponsorshipService	sponsorshipService;
	@Autowired
	private CorporationService	corporationService;
	@Autowired
	private ProjectService		projectService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		List<Sponsorship> sponsorships;
		sponsorships = new ArrayList<Sponsorship>();
		Corporation corporation;
		corporation = this.corporationService.findByPrincipal();
		sponsorships.addAll(corporation.getSponsorships());
		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "sponsorship/corporation/list.do");
		result.addObject("deleteURI", "sponsorship/corporation/delete.do?sponsorshipId=");
		result.addObject("editable", true);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {
		final ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		result = this.createEditModelAndView(sponsorship);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.create();
		result = this.createEditModelAndView(sponsorship);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsorship);
		else
			try {

				if (sponsorship.getId() != 0)
					this.sponsorshipService.saveFromEdit(sponsorship);
				else
					this.sponsorshipService.saveFromCreate(sponsorship);
				result = new ModelAndView("redirect:/sponsorship/corporation/list.do");
			} catch (final Throwable oops) {
				String messageError = "sponsorship.commit.error";

				if (oops.getMessage().contains("message.error"))
					messageError = oops.getMessage();
				result = this.createEditModelAndView(sponsorship, messageError);
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorshipInDB;
		sponsorshipInDB = this.sponsorshipService.findOne(sponsorshipId);
		try {
			this.sponsorshipService.deleteByCorporation(sponsorshipInDB);
			result = new ModelAndView("redirect:/sponsorship/corporation/list.do");
		} catch (final Throwable oops) {
			List<Sponsorship> sponsorships;
			sponsorships = new ArrayList<Sponsorship>();
			Corporation corporation;
			corporation = this.corporationService.findByPrincipal();
			sponsorships.addAll(corporation.getSponsorships());
			result = new ModelAndView("sponsorship/list");
			result.addObject("sponsorships", sponsorships);
			result.addObject("requestURI", "sponsorship/corporation/list.do");
			result.addObject("editable", true);
			String messageError = "sponsorship.commit.error";

			if (oops.getMessage().contains("message.error"))
				messageError = oops.getMessage();
			result.addObject("message", messageError);
			result.addObject("deleteURI", "sponsorship/corporation/delete.do?sponsorshipId=");

		}
		return result;

	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		ModelAndView result;

		result = this.createEditModelAndView(sponsorship, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String message) {
		ModelAndView result;

		result = new ModelAndView("sponsorship/edit");
		result.addObject("sponsorship", sponsorship);
		result.addObject("message", message);
		result.addObject("requestURI", "sponsorship/corporation/edit.do");
		result.addObject("projects", this.projectService.findProjectsForSponsorships());
		if (sponsorship.getId() == 0)
			result.addObject("createBoolean", true);
		else
			result.addObject("createBoolean", false);
		return result;
	}

}
