package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.ProjectComment;

import services.ProjectCommentService;

@Controller
@RequestMapping("/projectComment")
public class ProjectCommentController extends AbstractController{
	
	@Autowired
	private ProjectCommentService projectCommentService;
	
	
	//Constructor
	
	public ProjectCommentController(){
		
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required=true) final int projectId) {
		ModelAndView result;
		
		Collection<ProjectComment> projectComments;

		projectComments = this.projectCommentService.listAllProjectComments(projectId);
		
		result = new ModelAndView("projectComment/list");
		result.addObject("projectComments", projectComments);
		result.addObject("requestURI", "projectComments/list.do");
		
		return result;
		
	}
	

}
