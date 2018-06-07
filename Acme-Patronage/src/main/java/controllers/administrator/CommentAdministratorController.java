
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementCommentService;
import services.AwardCommentService;
import services.ProjectCommentService;
import controllers.AbstractController;
import domain.AnnouncementComment;
import domain.AwardComment;
import domain.ProjectComment;

@Controller
@RequestMapping("/comment/administrator")
public class CommentAdministratorController extends AbstractController {

	@Autowired
	private AnnouncementCommentService	announcementCommentService;

	@Autowired
	private AwardCommentService			awardCommentService;

	@Autowired
	private ProjectCommentService		projectCommentService;


	public CommentAdministratorController() {

		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<AwardComment> awardComments;

		awardComments = this.awardCommentService.findAll();

		Collection<ProjectComment> projectComments;

		projectComments = this.projectCommentService.findAll();

		Collection<AnnouncementComment> announcementComments;

		announcementComments = this.announcementCommentService.findAll();

		result = new ModelAndView("comment/list");
		result.addObject("awardComments", awardComments);
		result.addObject("projectComments", projectComments);
		result.addObject("announcementComments", announcementComments);
		result.addObject("requestURI", "comment/administrator/list.do");

		return result;

	}

	@RequestMapping(value = "/deleteAwardComment", method = RequestMethod.GET)
	public ModelAndView deleteAwardComment(@RequestParam final int awardCommentId) {
		ModelAndView result = null;

		AwardComment aC;

		try {
			aC = this.awardCommentService.findOne(awardCommentId);
			Assert.notNull(aC, "message.error.awardComment.null");

			this.awardCommentService.delete(aC);
			result = new ModelAndView("redirect:/comment/administrator/list.do");

		} catch (final Throwable oops) {
			String messageError = "comment.delete.error";

			if (oops.getMessage().contains("message.error"))
				messageError = oops.getMessage();

			result = new ModelAndView("comment/list");

			Collection<AwardComment> awardComments;

			awardComments = this.awardCommentService.findAll();

			Collection<ProjectComment> projectComments;

			projectComments = this.projectCommentService.findAll();

			Collection<AnnouncementComment> announcementComments;

			announcementComments = this.announcementCommentService.findAll();

			result.addObject("message", messageError);
			result.addObject("awardComments", awardComments);
			result.addObject("projectComments", projectComments);
			result.addObject("announcementComments", announcementComments);
			result.addObject("requestURI", "comment/administrator/list.do");
		}

		return result;
	}

	@RequestMapping(value = "/deleteProjectComment", method = RequestMethod.GET)
	public ModelAndView deleteProjectComment(@RequestParam final int projectCommentId) {
		ModelAndView result = null;

		ProjectComment aC;

		try {
			aC = this.projectCommentService.findOne(projectCommentId);
			Assert.notNull(aC, "message.error.projectComment.null");

			this.projectCommentService.delete(aC);
			result = new ModelAndView("redirect:/comment/administrator/list.do");

		} catch (final Throwable oops) {
			String messageError = "comment.delete.error";

			if (oops.getMessage().contains("message.error"))
				messageError = oops.getMessage();

			result = new ModelAndView("comment/list");

			Collection<AwardComment> awardComments;

			awardComments = this.awardCommentService.findAll();

			Collection<ProjectComment> projectComments;

			projectComments = this.projectCommentService.findAll();

			Collection<AnnouncementComment> announcementComments;

			announcementComments = this.announcementCommentService.findAll();

			result.addObject("message", messageError);
			result.addObject("awardComments", awardComments);
			result.addObject("projectComments", projectComments);
			result.addObject("announcementComments", announcementComments);
			result.addObject("requestURI", "comment/administrator/list.do");
		}

		return result;
	}

	@RequestMapping(value = "/deleteAnnouncementComment", method = RequestMethod.GET)
	public ModelAndView deleteAnnouncementComment(@RequestParam final int announcementCommentId) {
		ModelAndView result = null;

		AnnouncementComment aC;

		try {
			aC = this.announcementCommentService.findOne(announcementCommentId);
			Assert.notNull(aC, "message.error.announcementComment.null");

			this.announcementCommentService.delete(aC);
			result = new ModelAndView("redirect:/comment/administrator/list.do");

		} catch (final Throwable oops) {
			String messageError = "comment.delete.error";

			if (oops.getMessage().contains("message.error"))
				messageError = oops.getMessage();

			result = new ModelAndView("comment/list");

			Collection<AwardComment> awardComments;

			awardComments = this.awardCommentService.findAll();

			Collection<ProjectComment> projectComments;

			projectComments = this.projectCommentService.findAll();

			Collection<AnnouncementComment> announcementComments;

			announcementComments = this.announcementCommentService.findAll();

			result.addObject("message", messageError);
			result.addObject("awardComments", awardComments);
			result.addObject("projectComments", projectComments);
			result.addObject("announcementComments", announcementComments);
			result.addObject("requestURI", "comment/administrator/list.do");
		}

		return result;
	}

}
