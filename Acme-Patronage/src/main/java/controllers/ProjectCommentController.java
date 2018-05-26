package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Project;
import domain.ProjectComment;

import services.ProjectCommentService;
import services.ProjectService;

@Controller
@RequestMapping("/projectComment")
public class ProjectCommentController extends AbstractController{
	
	@Autowired
	private ProjectCommentService projectCommentService;
	
	@Autowired
	private ProjectService projectService;
	//Constructor
	
	public ProjectCommentController(){
		
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required=true) final int projectId) {
		ModelAndView result;
		
		try{
			
			Assert.notNull(this.projectService.findOne(projectId),"message.error.project.null");
			
			
			Collection<ProjectComment> projectComments;
			
			projectComments = this.projectCommentService.listAllProjectComments(projectId);
		
			result = new ModelAndView("projectComment/list");
			result.addObject("projectComments", projectComments);
			result.addObject("requestURI", "projectComments/list.do");
		}catch(Throwable oops){
			
			String messageError = "projectComment.commit.error";
			if (oops.getMessage().contains("message.error"))
				messageError = oops.getMessage();
			result = new ModelAndView("project/list");
			Collection<Project> projects = this.projectService.findProjectFutureDueDate();
			result.addObject("message",messageError);
			result.addObject("projects",projects);
			
		}
		return result;
		
	}
	

}
