package services.forms;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import domain.Announcement;
import domain.AnnouncementComment;
import domain.Award;
import domain.AwardComment;
import domain.Comment;
import domain.Project;
import domain.ProjectComment;
import domain.User;
import domain.forms.CommentForm;

import repositories.CommentRepository;
import services.ActorService;
import services.AnnouncementCommentService;
import services.AnnouncementService;
import services.AwardCommentService;
import services.AwardService;
import services.CommentService;
import services.PatronageService;
import services.ProjectCommentService;
import services.ProjectService;
import services.UserService;

public class CommentFormService {
	
	// Managed repository -----------------------------------------------------

		@Autowired
		private CommentService	commentService;


		// Supporting services ----------------------------------------------------

		@Autowired
		private UserService userService;
		
		
		
		@Autowired
		private ActorService actorService;
		
		@Autowired
		private ProjectService projectService;
		
		@Autowired
		private AwardService awardService;
		
		@Autowired
		private AnnouncementService announcementService;
		
		@Autowired
		private PatronageService patronageService;
		
		@Autowired
		private ProjectCommentService projectCommentService;
		
		@Autowired
		private AwardCommentService awardCommentService;
		
		@Autowired
		private AnnouncementCommentService announcementCommentService;
		
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
			
			Comment comment = this.commentService.findOne(id);
			
			final CommentForm result = new CommentForm();
			
			result.setCreationMoment(new Date(System.currentTimeMillis() - 1));
			
			result.setId(comment.getId());
			
			result.setRating(comment.getRating());
			
			result.setText(comment.getText());

			return result;
		}
		
		public Comment saveFromCreate(CommentForm anC,final String type, int id) {
			
			Assert.notNull(type);
			
			int userId = this.userService.findByPrincipal().getId();
			
			//TODO: Check that the user who is going to post a comment has funded the proyect 
			
			
			Assert.notNull(anC);
			Assert.notNull(anC.getText());
			
			anC.setCreationMoment(new Date(System.currentTimeMillis() - 1));
			
			switch (type) {
			case "PROJECT":{
				
				final ProjectComment result;
				
				final Project p = this.projectService.findOne(id);
				
				Assert.notNull(p);
				
				final ProjectComment pC = this.projectCommentService.create();
				
				pC.setText(anC.getText());
				pC.setRating(anC.getRating());
				pC.setCreationMoment(anC.getCreationMoment());
				
				pC.setProject(p);
				
				result = this.projectCommentService.saveFromCreate(pC);
				
				return result;
			}case "AWARD": {
				
				final AwardComment result;
				
				final Award a = this.awardService.findOne(id);
				
				Assert.notNull(a);
				
				final AwardComment aC = this.awardCommentService.create();
				
				aC.setText(anC.getText());
				aC.setRating(anC.getRating());
				aC.setCreationMoment(anC.getCreationMoment());
				aC.setAward(a);
				
				result = this.awardCommentService.saveFromCreate(aC);
				
				return result;
			}case "ANNOUNCEMENT": {
				
				final AnnouncementComment result;
				
				final Announcement a = this.announcementService.findOne(id);
				
				Assert.isTrue(this.userService.findOne(userId).equals(a.getUser()));
				Assert.isTrue(this.patronageService.getPatronagesOfProjectByUser(this.userService.findByPrincipal().getId(),a.getProject().getId()).size()>0);
				
				Assert.notNull(a);
				
				final AnnouncementComment aC = this.announcementCommentService.create();
				
				aC.setText(anC.getText());
				aC.setRating(anC.getRating());
				aC.setCreationMoment(anC.getCreationMoment());
				aC.setAnnouncement(a);
				
				result = this.announcementCommentService.saveFromCreate(aC);
				
				return result;
			}default:
			
				return null;
		}	
			
		}

}