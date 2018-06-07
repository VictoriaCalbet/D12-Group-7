
package services.forms;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import services.AnnouncementCommentService;
import services.AnnouncementService;
import services.AwardCommentService;
import services.AwardService;
import services.CommentService;
import services.PatronageService;
import services.ProjectCommentService;
import services.ProjectService;
import services.UserService;
import domain.Announcement;
import domain.AnnouncementComment;
import domain.Award;
import domain.AwardComment;
import domain.Comment;
import domain.Project;
import domain.ProjectComment;
import domain.forms.CommentForm;

@Service
public class CommentFormService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CommentService				commentService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService					userService;

	@Autowired
	private ProjectService				projectService;

	@Autowired
	private AwardService				awardService;

	@Autowired
	private AnnouncementService			announcementService;

	@Autowired
	private PatronageService			patronageService;

	@Autowired
	private ProjectCommentService		projectCommentService;

	@Autowired
	private AwardCommentService			awardCommentService;

	@Autowired
	private AnnouncementCommentService	announcementCommentService;


	// Constructors -----------------------------------------------------------

	public CommentFormService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: CommentForm - create
	public CommentForm create() {
		final CommentForm result = new CommentForm();
		result.setCreationMoment(new Date(System.currentTimeMillis() - 1));

		return result;
	}

	public CommentForm create(final int id) {

		final Comment comment = this.commentService.findOne(id);

		final CommentForm result = new CommentForm();

		result.setCreationMoment(new Date(System.currentTimeMillis() - 1));

		result.setId(comment.getId());

		result.setRating(comment.getRating());

		result.setText(comment.getText());

		return result;
	}

	public Comment saveFromCreate(final CommentForm anC, final String type, final int id) {

		Assert.notNull(type);
		Assert.isTrue(anC.getId() == 0);

		final int userId = this.userService.findByPrincipal().getId();

		Assert.notNull(anC, "message.error.projectComment.null");
		Assert.notNull(anC.getText(), "message.error.projectComment.text.null");

		anC.setCreationMoment(new Date(System.currentTimeMillis() - 1));

		switch (type) {
		case "PROJECT": {

			final ProjectComment result;

			final Project p = this.projectService.findOne(id);

			Assert.notNull(p, "message.error.projectComment.project.null");
			Assert.isTrue(!p.getIsCancelled(), "message.error.projectComment.cancelledProject");
			Assert.isTrue(!p.getIsDraft(), "message.error.projectComment.draftProject");
			final ProjectComment pC = this.projectCommentService.create();

			pC.setText(anC.getText());
			if (anC.getRating() != null)
				pC.setRating(anC.getRating());
			pC.setCreationMoment(new Date(System.currentTimeMillis() - 1));

			pC.setProject(p);

			result = this.projectCommentService.saveFromCreate(pC);

			return result;
		}
		case "AWARD": {

			final AwardComment result;

			final Award a = this.awardService.findOne(id);

			Assert.notNull(a, "message.error.awardComment.award.null");

			Assert.isTrue(!a.getProject().getIsDraft(), "message.error.awardComment.draftProject");
			Assert.isTrue(!a.getProject().getIsCancelled(), "message.error.awardComment.draftProject");

			final AwardComment aC = this.awardCommentService.create();

			aC.setText(anC.getText());

			if (anC.getRating() != null)
				aC.setRating(anC.getRating());

			aC.setCreationMoment(new Date(System.currentTimeMillis() - 1));
			aC.setAward(a);

			result = this.awardCommentService.saveFromCreate(aC);

			return result;
		}
		case "ANNOUNCEMENT": {

			final AnnouncementComment result;

			final Announcement a = this.announcementService.findOne(id);

			Assert.isTrue(!a.getProject().getIsDraft(), "message.error.announcementComment.draftProject");
			Assert.isTrue(!a.getProject().getIsCancelled(), "message.error.announcementComment.draftProject");
			Assert.isTrue(this.patronageService.getPatronagesOfProjectByUser(this.userService.findByPrincipal().getId(), a.getProject().getId()).size() > 0 || this.userService.findOne(userId).equals(a.getUser()),
				"message.error.announcementComment.noPatronages");

			Assert.notNull(a, "message.error.announcementComment.announcement.null");

			final AnnouncementComment aC = this.announcementCommentService.create();

			aC.setText(anC.getText());

			if (anC.getRating() != null)
				aC.setRating(anC.getRating());

			aC.setCreationMoment(new Date(System.currentTimeMillis() - 1));
			aC.setAnnouncement(a);

			result = this.announcementCommentService.saveFromCreate(aC);

			return result;
		}
		default:

			return null;
		}

	}

}
