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

import services.AwardCommentService;
import services.AwardService;
import services.UserService;
import services.forms.CommentFormService;

import controllers.AbstractController;
import domain.Award;
import domain.AwardComment;
import domain.User;
import domain.forms.CommentForm;


@Controller
@RequestMapping("/awardComment/user")
public class AwardCommentUserController extends AbstractController{
	
	@Autowired
	private AwardCommentService awardCommentService;
	
	@Autowired
	private UserService			userService;
	
	@Autowired
	private AwardService			awardService;
	
	@Autowired
	private CommentFormService			commentFormService;
	
	public AwardCommentUserController(){
		
		super();
		
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<AwardComment> awardComments;
		final User u = this.userService.findByPrincipal();

		awardComments = this.awardCommentService.listAllAwardCommentsOfUser(u.getId());
		
		result = new ModelAndView("awardComment/list");
		result.addObject("awardComments", awardComments);
		result.addObject("requestURI", "awardComments/user/list.do");
		
		return result;
		
	}
	
	// Creation ----------------------------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create(final int awardId) {
			ModelAndView result;
			CommentForm aAForm;
			
			try{
				
				Award a = this.awardService.findOne(awardId);
				
				Assert.notNull(a,"message.error.awardComment.id.null");
				Assert.isTrue(!a.getProject().getIsDraft(),"message.error.awardComment.draftProject");
				Assert.isTrue(!a.getProject().getIsCancelled(),"message.error.awardComment.cancelledProject");
				aAForm = this.commentFormService.create();
				result = this.createModelAndView(aAForm);
				
			}catch(final Throwable oops) {
				String messageError = "awardComment.commit.error";
				if (oops.getMessage().contains("message.error"))
					messageError = oops.getMessage();
				result = new ModelAndView("awardComment/list");
				User user = this.userService.findByPrincipal();
				Collection<AwardComment> awardComments = this.awardCommentService.listAllAwardCommentsOfUser(user.getId());
				result.addObject("message",messageError);
				result.addObject("awardComments",awardComments);
			}

			return result;
			
			

		}

		@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
		public ModelAndView create(@Valid final CommentForm commentForm, final BindingResult binding, int awardId) {

			ModelAndView result;

			if (binding.hasErrors())
				result = this.createModelAndView(commentForm);
			else
				try {
					this.commentFormService.saveFromCreate(commentForm, "AWARD",awardId);
					result = new ModelAndView("redirect:/awardComment/user/list.do");
				} catch (final Throwable oops) {
					String messageError = "awardComment.commit.error";
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
			
			result = new ModelAndView("awardComment/edit");
			result.addObject("commentForm", commentForm);
			result.addObject("numbers", numbers);
			result.addObject("message", message);

			return result;
		}
		
}
