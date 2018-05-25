
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CorporationRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Corporation;
import domain.Sponsorship;

@Service
@Transactional
public class CorporationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CorporationRepository	corporationRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public CorporationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Corporation create() {
		Corporation result;
		UserAccount userAccount;

		result = new Corporation();
		result.setSponsorships(new HashSet<Sponsorship>());

		userAccount = this.userAccountService.create("CORPORATION");
		result.setUserAccount(userAccount);

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
		Assert.notNull(corporation, "message.error.corporation.null");
		Corporation result = null;
		result = this.corporationRepository.save(corporation);
		return result;
	}

	public Corporation saveFromCreate(final Corporation corporation) {
		Assert.notNull(corporation, "message.error.corporation.null");

		final Corporation result;

		// Check unlogged
		Assert.isTrue(!this.actorService.checkLogin(), "message.error.corporation.unlogged");

		// Check authority
		boolean isCorporation;
		isCorporation = this.actorService.checkAuthority(corporation, "CORPORATION");
		Assert.isTrue(isCorporation, "message.error.corporation.authority.wrong");

		// Check repeated username
		UserAccount possibleRepeated;
		possibleRepeated = this.userAccountService.findByUsername(corporation.getUserAccount().getUsername());
		Assert.isNull(possibleRepeated, "message.error.corporation.username.repeated");

		result = this.save(corporation);

		return result;
	}

	public Corporation saveFromEdit(final Corporation corporation) {
		Assert.notNull(corporation, "message.error.corporation.null");

		Corporation result;
		Corporation principal;

		// Check principal
		principal = this.findByPrincipal();
		Assert.notNull(principal, "message.error.corporation.principal.null");
		Assert.isTrue(principal.getId() == corporation.getId(), "message.error.corporation.principal.self");

		// Check authority
		boolean isCorporation;
		isCorporation = this.actorService.checkAuthority(corporation, "CORPORATION");
		Assert.isTrue(isCorporation, "message.error.corporation.authority.wrong");

		// Encoding password
		UserAccount userAccount;
		userAccount = corporation.getUserAccount();
		userAccount = this.userAccountService.modifyPassword(userAccount);
		corporation.setUserAccount(userAccount);

		result = this.save(corporation);

		return result;
	}

	public void flush() {
		this.corporationRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Corporation findByPrincipal() {
		Corporation result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.corporationRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	// Dashboard --------------------------------------------------------------

	// Req 25.2.5: The corporations that have, at least, a 10% more sponsorships than the average.

	public Collection<Corporation> findAllWith10PercentMoreSponsorshipsThanAvg() {
		return this.corporationRepository.findAllWith10PercentMoreSponsorshipsThanAvg();
	}
}
