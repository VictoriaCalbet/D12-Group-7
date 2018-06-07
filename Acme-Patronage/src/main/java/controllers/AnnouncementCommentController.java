
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementCommentService;
import services.AnnouncementService;
import services.ProjectService;
import domain.AnnouncementComment;
import domain.Project;

@Controller
@RequestMapping("/announcementComment")
public class AnnouncementCommentController extends AbstractController {

	@Autowired
	private AnnouncementCommentService	announcementCommentService;

	@Autowired
	private AnnouncementService			announcementService;

	@Autowired
	private ProjectService				projectService;


	public AnnouncementCommentController() {

		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = true) final int announcementId) {
		ModelAndView result;

		try {

			Assert.notNull(this.announcementService.findOne(announcementId), "message.error.announcement.null");

			Collection<AnnouncementComment> announcementComments;

			announcementComments = this.announcementCommentService.listAllAnnouncementComments(announcementId);

			result = new ModelAndView("announcementComment/list");
			result.addObject("announcementComments", announcementComments);
			result.addObject("requestURI", "announcementComment/list.do");

		} catch (final Throwable oops) {

			String messageError = "announcementComment.commit.error";
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
