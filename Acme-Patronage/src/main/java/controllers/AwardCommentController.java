
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AwardCommentService;
import services.AwardService;
import services.ProjectService;
import domain.AwardComment;
import domain.Project;

@Controller
@RequestMapping("/awardComment")
public class AwardCommentController extends AbstractController {

	@Autowired
	private AwardCommentService	awardCommentService;

	@Autowired
	private ProjectService		projectService;

	@Autowired
	private AwardService		awardService;


	public AwardCommentController() {

		super();

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = true) final int awardId) {
		ModelAndView result;

		try {

			Assert.notNull(this.awardService.findOne(awardId), "message.error.award.null");

			Collection<AwardComment> awardComments;

			awardComments = this.awardCommentService.listAllAwardComments(awardId);

			result = new ModelAndView("awardComment/list");
			result.addObject("awardComments", awardComments);
			result.addObject("requestURI", "awardComments/list.do");
		} catch (final Throwable oops) {

			String messageError = "awardComment.commit.error";
			if (oops.getMessage().contains("message.error"))
				messageError = oops.getMessage();
			result = new ModelAndView("project/list");
			final Collection<Project> projects = this.projectService.findProjectFutureDueDate();
			result.addObject("message", messageError);
			result.addObject("projects", projects);
		}
		return result;

	}

}
