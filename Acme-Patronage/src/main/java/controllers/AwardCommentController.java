package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AwardCommentService;
import services.AwardService;
import services.UserService;
import services.forms.CommentFormService;
import domain.AwardComment;
import domain.User;

@Controller
@RequestMapping("/awardComment")
public class AwardCommentController {

	@Autowired
	private AwardCommentService awardCommentService;
	
	@Autowired
	private UserService			userService;
	
	@Autowired
	private AwardService			awardService;
	
	@Autowired
	private CommentFormService			commentFormService;
	
	public AwardCommentController(){
		
		super();
		
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required=true) int awardId) {
		ModelAndView result;
		
		Collection<AwardComment> awardComments;

		awardComments = this.awardCommentService.listAllAwardComments(awardId);
		
		result = new ModelAndView("awardComment/list");
		result.addObject("awardComments", awardComments);
		result.addObject("requestURI", "awardComments/list.do");
		
		return result;
		
	}
	
}
