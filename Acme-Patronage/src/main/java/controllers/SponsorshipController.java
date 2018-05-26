
package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProjectService;
import domain.Project;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship")
public class SponsorshipController extends AbstractController {

	@Autowired
	private ProjectService	projectService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int projectId) {
		final ModelAndView result;
		List<Sponsorship> sponsorships;
		sponsorships = new ArrayList<Sponsorship>();
		Project project;
		project = this.projectService.findOne(projectId);
		sponsorships.addAll(project.getSponsorships());
		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "sponsorship/corporation/list.do");
		result.addObject("deleteURI", "sponsorship/corporation/delete.do?sponsorshipId=");
		result.addObject("editable", false);
		return result;
	}
}
