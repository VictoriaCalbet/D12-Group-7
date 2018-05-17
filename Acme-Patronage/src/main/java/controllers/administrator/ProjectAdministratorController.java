
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ProjectService;
import controllers.AbstractController;
import domain.Project;

@Controller
@RequestMapping("/project/administrator")
public class ProjectAdministratorController extends AbstractController {

	@Autowired
	private ProjectService			projectService;
	@Autowired
	private AdministratorService	administratorService;


	public ProjectAdministratorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false, defaultValue = "") final String word, @RequestParam(required = false) final String message) {
		ModelAndView result;
		Collection<Project> projects = new ArrayList<Project>();

		if (word == null || word.equals(""))
			projects = this.projectService.findAllOrdered();
		else
			projects = this.projectService.findProjectByKeyWordByAdmin(word);

		result = new ModelAndView("project/list");
		result.addObject("projects", projects);
		result.addObject("message", message);
		result.addObject("requestURI", "project/administrator/list.do");

		return result;
	}

}
