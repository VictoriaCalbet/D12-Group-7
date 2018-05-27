
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AwardCommentRepository;
import domain.Administrator;
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
	
	@Autowired
	private AdministratorService administratorService;

	// Constructors -----------------------------------------------------------

	public AwardCommentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: AwardComment - create
	public AwardComment create() {
		final AwardComment result = new AwardComment();
		
		result.setCreationMoment(new Date(System.currentTimeMillis() - 1));

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
		
		Assert.isTrue(aC.getId()==0);
		Assert.notNull(aC,"message.error.awardComment.null");
		Assert.notNull(aC.getText(),"message.error.awardComment.text.null");
		Assert.notNull(aC.getAward(),"message.error.awardComment.award.null");
		
		aC.setUser(this.userService.findByPrincipal());
		
		aC.setCreationMoment(new Date(System.currentTimeMillis() - 1));
		
		final AwardComment result = this.awardCommentRepository.save(aC);

		return result;
	}
	
	public void delete(final AwardComment aC){
		
		Assert.notNull(aC,"message.error.awardComment.null");
		
		Administrator admin = this.administratorService.findByPrincipal();
		
		Assert.notNull(admin,"message.error.awardComment.notAnAdmin");
		
		this.awardCommentRepository.delete(aC);
		
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
