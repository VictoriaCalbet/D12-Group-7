/*
 * AboutUsController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CategoryService;
import services.PatronageService;
import services.ProjectService;
import services.SponsorshipService;
import services.UserService;
import domain.Actor;
import domain.Category;
import domain.Project;
import domain.Sponsorship;
import domain.User;

@Controller
@RequestMapping("/project")
public class ProjectController extends AbstractController {

	@Autowired
	private ProjectService		projectService;

	@Autowired
	private PatronageService	patronageService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserService			userService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	// Constructors -----------------------------------------------------------

	public ProjectController() {
		super();
	}

	//Listing 

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false, defaultValue = "") final String word, @RequestParam(required = false) final String message) {
		ModelAndView result;
		Collection<Project> projects = new ArrayList<Project>();
		Collection<Project> projectReports = new ArrayList<Project>();
		projectReports = this.projectService.findProjectWithReportLegit();

		if (word == null || word.equals(""))
			projects = this.projectService.findProjectFutureDueDate();
		else
			projects = this.projectService.findProjectByKeyWord(word);

		User principal = null;
		if (this.actorService.checkLogin()) {
			final Actor a = this.actorService.findByPrincipal();
			if (this.actorService.checkAuthority(a, "USER"))
				principal = this.userService.findByPrincipal();
		}

		Map<Integer, Double> totalAmounts = new HashMap<>();
		totalAmounts = this.patronageService.findTotalAmount(projects);

		result = new ModelAndView("project/list");
		result.addObject("totalAmounts", totalAmounts);
		result.addObject("principal", principal);
		result.addObject("projects", projects);
		result.addObject("projectReports", projectReports);
		result.addObject("message", message);
		result.addObject("requestURI", "project/list.do");

		return result;
	}

	//List ordered

	@RequestMapping(value = "/listOrdered", method = RequestMethod.GET)
	public ModelAndView listOrdered(@RequestParam(required = false, defaultValue = "") final String word, @RequestParam(required = false) final String message) {
		ModelAndView result;
		Collection<Project> projects = new ArrayList<Project>();
		Collection<Project> projectReports = new ArrayList<Project>();
		projectReports = this.projectService.findProjectWithReportLegit();

		if (word == null || word.equals(""))
			projects = this.projectService.findProjectFutureDueDateOrdered();

		User principal = null;
		if (this.actorService.checkLogin()) {
			final Actor a = this.actorService.findByPrincipal();
			if (this.actorService.checkAuthority(a, "USER"))
				principal = this.userService.findByPrincipal();
		}

		Map<Integer, Double> totalAmounts = new HashMap<>();
		totalAmounts = this.patronageService.findTotalAmount(projects);

		result = new ModelAndView("project/list");
		result.addObject("totalAmounts", totalAmounts);
		result.addObject("principal", principal);
		result.addObject("projects", projects);
		result.addObject("projectReports", projectReports);
		result.addObject("message", message);
		result.addObject("requestURI", "project/list.do");

		return result;
	}

	@RequestMapping(value = "/listByCategory", method = RequestMethod.GET)
	public ModelAndView listByCategory(@RequestParam(required = false, defaultValue = "") final String word, @RequestParam final int categoryId, @RequestParam(required = false) final String message) {
		ModelAndView result;
		Collection<Project> projects = new ArrayList<Project>();
		final Category c = this.categoryService.findOne(categoryId);
		Collection<Project> projectReports = new ArrayList<Project>();
		projectReports = this.projectService.findProjectWithReportLegit();

		if (word == null || word.equals(""))
			projects = this.projectService.findProjectByCategory(c.getId());

		User principal = null;
		if (this.actorService.checkLogin()) {
			final Actor a = this.actorService.findByPrincipal();
			if (this.actorService.checkAuthority(a, "USER"))
				principal = this.userService.findByPrincipal();
		}

		Map<Integer, Double> totalAmounts = new HashMap<>();
		totalAmounts = this.patronageService.findTotalAmount(projects);

		result = new ModelAndView("project/list");
		result.addObject("projects", projects);
		result.addObject("principal", principal);
		result.addObject("totalAmounts", totalAmounts);
		result.addObject("c", c);
		result.addObject("categoryId", categoryId);
		result.addObject("projectReports", projectReports);
		result.addObject("message", message);
		result.addObject("requestURI", "project/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView info(@RequestParam final int projectId) {
		ModelAndView result = new ModelAndView();

		final Project project = this.projectService.findOne(projectId);
		result = this.infoModelAndView(project);
		return result;
	}

	// Ancillaty methods
	protected ModelAndView infoModelAndView(final Project project) {
		ModelAndView result;

		result = this.infoModelAndView(project, null);

		return result;
	}

	protected ModelAndView infoModelAndView(final Project project, final String message) {
		ModelAndView result;
		result = new ModelAndView("project/display");

		Sponsorship sponsorship;
		sponsorship = this.sponsorshipService.getRandomSponsorshipByProjectId(project.getId());

		result.addObject("sponsorshipBanner", sponsorship);

		result.addObject("project", project);
		result.addObject("message", message);

		return result;

	}

}
