
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AwardCommentRepository;
import domain.AwardComment;

@Service
@Transactional
public class AwardCommentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AwardCommentRepository	awardCommentRepository;


	// Supporting services ----------------------------------------------------
	
	@Autowired
	private UserService userService;

	// Constructors -----------------------------------------------------------

	public AwardCommentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: AwardComment - create
	public AwardComment create() {
		final AwardComment result = null;

		return result;
	}

	public Collection<AwardComment> findAll() {
		Collection<AwardComment> result = null;
		result = this.awardCommentRepository.findAll();
		return result;
	}

	public AwardComment findOne(final int awardCommentId) {
		AwardComment result = null;
		result = this.awardCommentRepository.findOne(awardCommentId);
		return result;
	}

	public AwardComment save(final AwardComment awardComment) {
		Assert.notNull(awardComment);
		AwardComment result = null;
		result = this.awardCommentRepository.save(awardComment);
		return result;
	}

	// TODO: AnnouncementComment - saveFromCreate
	public AwardComment saveFromCreate(final AwardComment aC) {
		
		Assert.notNull(aC);
		Assert.notNull(aC.getRating());
		Assert.notNull(aC.getText());
		Assert.notNull(aC.getAward());
		
		aC.setUser(this.userService.findByPrincipal());
		
		aC.setCreationMoment(new Date(System.currentTimeMillis() - 1));
		
		final AwardComment result = this.awardCommentRepository.save(aC);

		return result;
	}

	// TODO: AnnouncementComment - saveFromEdit
	public AwardComment saveFromEdit() {
		final AwardComment result = null;

		return result;
	}

	public void flush() {
		this.awardCommentRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Collection<AwardComment> listAllAwardComments(int awardId){
		
		Collection<AwardComment> result = this.awardCommentRepository.listAllAwardComments(awardId);
		
		return result;
		
	}
	
public Collection<AwardComment> listAllAwardCommentsOfUser(int userId){
		
		Collection<AwardComment> result = this.awardCommentRepository.listAllAwardCommentsOfUser(userId);
		
		return result;
		
	}
	
}
