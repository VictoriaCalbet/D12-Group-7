
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ModeratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Moderator;

@Service
@Transactional
public class ModeratorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ModeratorRepository	moderatorRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ModeratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: Moderator - create
	public Moderator create() {
		final Moderator result = null;

		return result;
	}

	public Collection<Moderator> findAll() {
		Collection<Moderator> result = null;
		result = this.moderatorRepository.findAll();
		return result;
	}

	public Moderator findOne(final int moderatorId) {
		Moderator result = null;
		result = this.moderatorRepository.findOne(moderatorId);
		return result;
	}

	public Moderator save(final Moderator moderator) {
		Assert.notNull(moderator);
		Moderator result = null;
		result = this.moderatorRepository.save(moderator);
		return result;
	}

	// TODO: Moderator - saveFromCreate
	public Moderator saveFromCreate() {
		final Moderator result = null;

		return result;
	}

	// TODO: Moderator - saveFromEdit
	public Moderator saveFromEdit() {
		final Moderator result = null;

		return result;
	}

	public void flush() {
		this.moderatorRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Moderator findByPrincipal() {
		Moderator result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.moderatorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}
}
