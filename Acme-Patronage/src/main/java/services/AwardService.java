
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AwardRepository;
import domain.Award;
import domain.AwardComment;
import domain.User;

@Service
@Transactional
public class AwardService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AwardRepository	awardRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService		userService;


	// Constructors -----------------------------------------------------------

	public AwardService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: Award - create
	public Award create() {
		Award result = null;

		result = new Award();
		result.setAwardComments(new ArrayList<AwardComment>());

		return result;
	}

	public Collection<Award> findAll() {
		Collection<Award> result = null;
		result = this.awardRepository.findAll();
		return result;
	}

	public Award findOne(final int awardId) {
		Award result = null;
		result = this.awardRepository.findOne(awardId);
		return result;
	}

	public Award save(final Award award) {
		Assert.notNull(award);
		Award result = null;
		result = this.awardRepository.save(award);
		return result;
	}

	public Award saveFromCreate(final Award award) {
		Award result = null;
		User user = null;

		user = this.userService.findByPrincipal();

		Assert.notNull(award, "message.error.award.null");
		Assert.notNull(user, "message.error.award.principal.null");
		Assert.isTrue(award.getProject().getCreator().equals(user), "message.error.award.user.owner");
		Assert.isTrue(!award.getProject().getIsCancelled(), "message.error.award.project.isCancelled");

		// Paso 1: realizo la entidad del servicio Award

		result = this.save(award);

		// Paso 2: persisto el resto de relaciones a las que el objeto Award esta relacionada.

		result.getProject().getAwards().add(result);

		return result;
	}

	public Award saveFromEdit(final Award award) {
		Award result = null;
		User user = null;

		user = this.userService.findByPrincipal();

		Assert.notNull(award, "message.error.award.null");
		Assert.notNull(user, "message.error.award.principal.null");
		Assert.isTrue(award.getProject().getCreator().equals(user), "message.error.award.user.owner");
		Assert.isTrue(!award.getProject().getIsDraft(), "message.error.award.project.notPublished");
		Assert.isTrue(!award.getProject().getIsCancelled(), "message.error.award.project.isCancelled");

		// Paso 1: realizo la entidad del servicio Award

		result = this.save(award);

		return result;
	}

	public void flush() {
		this.awardRepository.flush();
	}

	// Other business methods -------------------------------------------------
}
