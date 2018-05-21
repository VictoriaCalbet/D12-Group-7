
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PatronageRepository;
import domain.Patronage;

@Service
@Transactional
public class PatronageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PatronageRepository	patronageRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public PatronageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: Patronage - create
	public Patronage create() {
		final Patronage result = null;

		return result;
	}

	public Collection<Patronage> findAll() {
		Collection<Patronage> result = null;
		result = this.patronageRepository.findAll();
		return result;
	}

	public Patronage findOne(final int patronageId) {
		Patronage result = null;
		result = this.patronageRepository.findOne(patronageId);
		return result;
	}

	public Patronage save(final Patronage patronage) {
		Assert.notNull(patronage);
		Patronage result = null;
		result = this.patronageRepository.save(patronage);
		return result;
	}

	// TODO: Patronage - saveFromCreate
	public Patronage saveFromCreate() {
		final Patronage result = null;

		return result;
	}

	// TODO: Patronage - saveFromEdit
	public Patronage saveFromEdit() {
		final Patronage result = null;

		return result;
	}

	public void flush() {
		this.patronageRepository.flush();
	}

	// Other business methods -------------------------------------------------

}
