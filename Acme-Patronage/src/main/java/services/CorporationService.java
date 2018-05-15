
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CorporationRepository;
import domain.Corporation;

@Service
@Transactional
public class CorporationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CorporationRepository	corporationRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CorporationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: Corporation - create
	public Corporation create() {
		final Corporation result = null;

		return result;
	}

	public Collection<Corporation> findAll() {
		Collection<Corporation> result = null;
		result = this.corporationRepository.findAll();
		return result;
	}

	public Corporation findOne(final int corporationId) {
		Corporation result = null;
		result = this.corporationRepository.findOne(corporationId);
		return result;
	}

	public Corporation save(final Corporation corporation) {
		Assert.notNull(corporation);
		Corporation result = null;
		result = this.corporationRepository.save(corporation);
		return result;
	}

	// TODO: Corporation - saveFromCreate
	public Corporation saveFromCreate() {
		final Corporation result = null;

		return result;
	}

	// TODO: Corporation - saveFromEdit
	public Corporation saveFromEdit() {
		final Corporation result = null;

		return result;
	}

	public void flush() {
		this.corporationRepository.flush();
	}

	// Other business methods -------------------------------------------------

}
