
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ModeratorRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Administrator;
import domain.Moderator;

@Service
@Transactional
public class ModeratorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ModeratorRepository		moderatorRepository;

	//Supporting services ----------------------------------------------------

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public ModeratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Moderator create() {
		Moderator result;
		UserAccount userAccount;

		result = new Moderator();

		userAccount = this.userAccountService.create("MODERATOR");
		result.setUserAccount(userAccount);

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
		Assert.notNull(moderator, "message.error.moderator.null");
		Moderator result = null;
		result = this.moderatorRepository.save(moderator);
		return result;
	}

	public Moderator saveFromCreate(final Moderator moderator) {
		Assert.notNull(moderator, "message.error.moderator.null");

		final Moderator result;
		Administrator principal;

		// Check principal
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal, "message.error.admin.principal.null");

		// Check authority
		boolean isModerator;
		isModerator = this.actorService.checkAuthority(moderator, "MODERATOR");
		Assert.isTrue(isModerator, "message.error.moderator.authority.wrong");

		// Check repeated username
		UserAccount possibleRepeated;
		possibleRepeated = this.userAccountService.findByUsername(moderator.getUserAccount().getUsername());
		Assert.isNull(possibleRepeated, "message.error.moderator.username.repeated");

		result = this.save(moderator);

		return result;
	}

	public Moderator saveFromEdit(final Moderator moderator) {
		Assert.notNull(moderator, "message.error.moderator.null");

		Moderator result = null;
		Moderator principal;

		// Check principal
		principal = this.findByPrincipal();
		Assert.notNull(principal, "message.error.moderator.principal.null");
		Assert.isTrue(principal.getId() == moderator.getId(), "message.error.moderator.principal.self");

		// Check authority
		boolean isModerator;
		isModerator = this.actorService.checkAuthority(moderator, "MODERATOR");
		Assert.isTrue(isModerator, "message.error.moderator.authority.wrong");

		// Encoding password
		UserAccount userAccount;
		userAccount = moderator.getUserAccount();
		userAccount = this.userAccountService.modifyPassword(userAccount);
		moderator.setUserAccount(userAccount);

		result = this.save(moderator);

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
