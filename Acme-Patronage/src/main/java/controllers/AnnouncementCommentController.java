package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.AnnouncementComment;

import services.AnnouncementCommentService;

@Controller
@RequestMapping("/announcementComment")
public class AnnouncementCommentController extends AbstractController{

	@Autowired
	private AnnouncementCommentService announcementCommentService;
	
	public AnnouncementCommentController(){
		
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required=true) final int announcementId) {
		ModelAndView result;
		
		Collection<AnnouncementComment> announcementComments;

		announcementComments = this.announcementCommentService.listAllAnnouncementComments(announcementId);
		
		result = new ModelAndView("announcementComment/list");
		result.addObject("announcementComments", announcementComments);
		result.addObject("requestURI", "announcementComment/list.do");
		
		return result;
		
	}
	
}
