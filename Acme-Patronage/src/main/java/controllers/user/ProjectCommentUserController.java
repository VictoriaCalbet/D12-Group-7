
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

import services.ProjectCommentService;
import services.ProjectService;
import services.UserService;
import services.forms.CommentFormService;
import controllers.AbstractController;
import domain.Project;
import domain.ProjectComment;
import domain.User;
import domain.forms.CommentForm;

@Controller
@RequestMapping("/projectComment/user")
public class ProjectCommentUserController extends AbstractController {

	//Services needed

	@Autowired
	private ProjectCommentService	projectCommentService;

	@Autowired
	private UserService				userService;

	@Autowired
	private ProjectService			projectService;

	@Autowired
	private CommentFormService		commentFormService;


	//Constructor

	public ProjectCommentUserController() {

		super();
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<ProjectComment> projectComments;
		final User u = this.userService.findByPrincipal();

		projectComments = this.projectCommentService.listAllProjectCommentsOfUser(u.getId());

		result = new ModelAndView("projectComment/list");
		result.addObject("projectComments", projectComments);
		result.addObject("requestURI", "projectComment/user/list.do");

		return result;

	}

	// Creation ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int projectId) {
		ModelAndView result;
		CommentForm cForm;

		try {

			final Project p = this.projectService.findOne(projectId);

			Assert.notNull(p, "message.error.projectComment.project.null");
			Assert.isTrue(!p.getIsDraft(), "message.error.projectComment.draftProject");
			Assert.isTrue(!p.getIsCancelled(), "message.error.projectComment.cancelledProject");
			cForm = this.commentFormService.create();
			result = this.createModelAndView(cForm);

		} catch (final Throwable oops) {
			String messageError = "projectComment.commit.error";
			if (oops.getMessage().contains("message.error"))
				messageError = oops.getMessage();
			result = new ModelAndView("projectComment/list");
			final User user = this.userService.findByPrincipal();
			final Collection<ProjectComment> projectComments = this.projectCommentService.listAllProjectCommentsOfUser(user.getId());
			result.addObject("message", messageError);
			result.addObject("projectComments", projectComments);
		}

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@Valid final CommentForm commentForm, final BindingResult binding, final int projectId) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createModelAndView(commentForm);
		else
			try {
				this.commentFormService.saveFromCreate(commentForm, "PROJECT", projectId);
				result = new ModelAndView("redirect:/projectComment/user/list.do");
			} catch (final Throwable oops) {
				String messageError = "projectComment.commit.error";
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

		final Collection<Integer> numbers = new ArrayList<Integer>();

		for (int ind = 1; ind < 6; ind++)
			numbers.add(ind);

		result = new ModelAndView("projectComment/edit");
		result.addObject("commentForm", commentForm);
		result.addObject("numbers", numbers);
		result.addObject("message", message);

		return result;
	}

}
