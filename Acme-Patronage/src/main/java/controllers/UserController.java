
package controllers;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ProjectService;
import services.UserService;
import domain.Project;
import domain.User;

@Controller
@RequestMapping("/user")
public class UserController {

	// Services ---------------------------------------------------------------

	@Autowired
	private UserService		userService;

	@Autowired
	private ProjectService	projectService;


	// Constructor ------------------------------------------------------------

	public UserController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<User> users = new HashSet<User>();

		users = this.userService.findAll();

		result = new ModelAndView("user/list");
		result.addObject("users", users);
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
		createdProjects = this.projectService.findAllCreatedByUser(userId);
		fundedProjects = this.projectService.findAllFundedByUser(userId);

		result = new ModelAndView("user/display");
		result.addObject("user", user);
		result.addObject("createdProjects", createdProjects);
		result.addObject("fundedProjects", fundedProjects);
		result.addObject("requestURI", "user/display.do");
		return result;
	}
}
