
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AnnouncementCommentRepository;
import domain.Announcement;
import domain.AnnouncementComment;

@Service
@Transactional
public class AnnouncementCommentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AnnouncementCommentRepository	announcementCommentRepository;


	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService userService;
	
	// Constructors -----------------------------------------------------------

	public AnnouncementCommentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: AnnouncementComment - create
	public AnnouncementComment create() {
		final AnnouncementComment result = new AnnouncementComment();
		result.setCreationMoment(new Date(System.currentTimeMillis() - 1));

		return result;
	}

	public Collection<AnnouncementComment> findAll() {
		Collection<AnnouncementComment> result = null;
		result = this.announcementCommentRepository.findAll();
		return result;
	}

	public AnnouncementComment findOne(final int announcementCommentId) {
		AnnouncementComment result = null;
		result = this.announcementCommentRepository.findOne(announcementCommentId);
		return result;
	}

	public AnnouncementComment save(final AnnouncementComment announcementComment) {
		Assert.notNull(announcementComment);
		AnnouncementComment result = null;
		result = this.announcementCommentRepository.save(announcementComment);
		return result;
	}

	// TODO: AnnouncementComment - saveFromCreate
	public AnnouncementComment saveFromCreate(AnnouncementComment anC) {
		
		Assert.notNull(anC);
		Assert.notNull(anC.getText());
		Assert.notNull(anC.getRating());
		Assert.notNull(anC.getAnnouncement());
		
		anC.setCreationMoment(new Date(System.currentTimeMillis() - 1));
		anC.setUser(this.userService.findByPrincipal());
		
		final AnnouncementComment result = this.announcementCommentRepository.save(anC);
		
		return result;
	}
	
	public void delete(AnnouncementComment aC){
		
		Assert.notNull(aC,"message.error.announcementComment.null");
		
		this.announcementCommentRepository.delete(aC.getId());
		
	}
	

	public void flush() {
		this.announcementCommentRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Collection<AnnouncementComment> listAllAnnouncementComments(int announcementId){
		
		Collection<AnnouncementComment> result = this.announcementCommentRepository.listAllAnnouncementComments(announcementId);
	
		return result;
	}
	
	public Collection<AnnouncementComment> listAllAnnouncementCommentsOfUser(int userId){
		
		Collection<AnnouncementComment> result = this.announcementCommentRepository.listAllAnnouncementCommentsOfUser(userId);
	
		return result;
	}
	
}
