
package controllers.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProjectService;
import services.UserService;
import controllers.AbstractController;
import domain.Project;
import domain.User;

@Controller
@RequestMapping("/project/user")
public class ProjectUserController extends AbstractController {

	@Autowired
	private ProjectService	projectService;
	@Autowired
	private UserService		userService;


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
			projects = this.projectService.findProjectByKeyWord(word);

		result = new ModelAndView("project/list");
		result.addObject("projects", projects);
		result.addObject("message", message);
		result.addObject("requestURI", "project/user/list.do");

		return result;
	}

}
