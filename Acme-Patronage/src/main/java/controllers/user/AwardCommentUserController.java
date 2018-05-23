package controllers.user;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AwardCommentService;
import services.AwardService;
import services.UserService;
import services.forms.CommentFormService;

import controllers.AbstractController;
import domain.AwardComment;
import domain.Category;
import domain.Project;
import domain.User;
import domain.forms.CommentForm;
import domain.forms.ProjectForm;


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
		public ModelAndView create() {
			final ModelAndView result;
			CommentForm aAForm;

			aAForm = this.commentFormService.create();
			result = this.createModelAndView(aAForm);
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
					String messageError = "project.commit.error";
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
			
			for(int ind=1;ind<4;ind++){
				
				numbers.add(ind);
			}
			
			result = new ModelAndView("awardComment/user/create");
			result.addObject("commentForm", commentForm);
			result.addObject("numbers", numbers);
			result.addObject("message", message);

			return result;
		}
		
}
