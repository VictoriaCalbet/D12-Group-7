
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AwardRepository;
import domain.Award;

@Service
@Transactional
public class AwardService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AwardRepository	awardRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public AwardService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: Award - create
	public Award create() {
		final Award result = null;

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

	// TODO: Award - saveFromCreate
	public Award saveFromCreate() {
		final Award result = null;

		return result;
	}

	// TODO: Award - saveFromEdit
	public Award saveFromEdit() {
		final Award result = null;

		return result;
	}

	public void flush() {
		this.awardRepository.flush();
	}

	// Other business methods -------------------------------------------------

}
