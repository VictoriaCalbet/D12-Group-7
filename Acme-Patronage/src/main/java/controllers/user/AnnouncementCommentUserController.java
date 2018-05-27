package controllers.user;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementCommentService;
import services.AnnouncementService;
import services.PatronageService;
import services.UserService;
import services.forms.CommentFormService;

import controllers.AbstractController;
import domain.Announcement;
import domain.AnnouncementComment;
import domain.User;
import domain.forms.CommentForm;

@Controller
@RequestMapping("/announcementComment/user")
public class AnnouncementCommentUserController extends AbstractController{

	@Autowired
	private AnnouncementCommentService announcementCommentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PatronageService patronageService;
	
	@Autowired
	private AnnouncementService announcementService;
	
	@Autowired
	private CommentFormService commentFormService;
	
	public AnnouncementCommentUserController(){
		
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<AnnouncementComment> announcementComments;
		final User u = this.userService.findByPrincipal();
		
		announcementComments = this.announcementCommentService.listAllAnnouncementCommentsOfUser(u.getId());
		
		result = new ModelAndView("announcementComment/list");
		result.addObject("announcementComments", announcementComments);
		result.addObject("requestURI", "announcementComment/user/list.do");
		
		return result;
		
	}
	
	// Creation ----------------------------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create(final int announcementId) {
			ModelAndView result;
			CommentForm cForm;
			
			try{
				
				Announcement a = this.announcementService.findOne(announcementId);
				User user = this.userService.findByPrincipal();
				Assert.notNull(a,"message.error.announcement.null");
				Assert.isTrue(!a.getProject().getIsDraft(),"message.error.announcementComment.draftProject");
				Assert.isTrue(!a.getProject().getIsCancelled(),"message.error.announcementComment.cancelledProject");
				Assert.isTrue((this.patronageService.getPatronagesOfProjectByUser(user.getId(),a.getProject().getId()).size())>0 || user.equals(a.getUser()),"message.error.announcementComment.noPatronages");
				cForm = this.commentFormService.create();
				result = this.createModelAndView(cForm);
				
			}catch(final Throwable oops) {
				String messageError = "announcementComment.commit.error";
				if (oops.getMessage().contains("message.error"))
					messageError = oops.getMessage();
				result = new ModelAndView("announcementComment/list");
				User user = this.userService.findByPrincipal();
				Collection<AnnouncementComment> announcementComments = this.announcementCommentService.listAllAnnouncementCommentsOfUser(user.getId());
				result.addObject("message",messageError);
				result.addObject("announcementComments",announcementComments);
			}

			return result;
			
			

		}

		@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
		public ModelAndView create(@Valid final CommentForm commentForm, final BindingResult binding, int announcementId) {

			ModelAndView result;

			if (binding.hasErrors())
				result = this.createModelAndView(commentForm);
			else
				try {
					this.commentFormService.saveFromCreate(commentForm, "ANNOUNCEMENT",announcementId);
					result = new ModelAndView("redirect:/announcementComment/user/list.do");
				} catch (final Throwable oops) {
					String messageError = "announcementComment.commit.error";
					if (oops.getMessage().contains("message.error"))
						messageError = oops.getMessage();
					result = this.createModelAndView(commentForm, messageError);
				}

			return result;
		}

		protected ModelAndView createModelAndView(final CommentForm commentForm) {
			ModelAndView result;

			result = this.createModelAndView(commentForm, null);

			return result;
		}

		protected ModelAndView createModelAndView(final CommentForm commentForm, final String message) {
			ModelAndView result;

			Collection<Integer> numbers = new ArrayList<Integer>();
			
			for(int ind=1;ind<6;ind++){
				
				numbers.add(ind);
			}
			
			result = new ModelAndView("announcementComment/edit");
			result.addObject("commentForm", commentForm);
			result.addObject("numbers", numbers);
			result.addObject("message", message);

			return result;
		}
		
	
}
