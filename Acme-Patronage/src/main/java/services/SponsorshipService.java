
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public SponsorshipService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: Sponsorship - create
	public Sponsorship create() {
		final Sponsorship result = null;

		return result;
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result = null;
		result = this.sponsorshipRepository.findAll();
		return result;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Sponsorship result = null;
		result = this.sponsorshipRepository.findOne(sponsorshipId);
		return result;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Sponsorship result = null;
		result = this.sponsorshipRepository.save(sponsorship);
		return result;
	}

	// TODO: Sponsorship - saveFromCreate
	public Sponsorship saveFromCreate() {
		final Sponsorship result = null;

		return result;
	}

	// TODO: Sponsorship - saveFromEdit
	public Sponsorship saveFromEdit() {
		final Sponsorship result = null;

		return result;
	}

	public void flush() {
		this.sponsorshipRepository.flush();
	}

	// Other business methods -------------------------------------------------

}
