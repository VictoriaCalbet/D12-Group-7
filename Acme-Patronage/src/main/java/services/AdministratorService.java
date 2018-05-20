
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	//Managed repository -----------------------------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	//Supporting services ----------------------------------------------------

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ActorService			actorService;


	//Constructors -----------------------------------------------------------

	public AdministratorService() {
		super();
	}

	//Simple CRUD methods ----------------------------------------------------

	public Administrator create() {
		Administrator result;
		UserAccount userAccount;

		result = new Administrator();

		userAccount = this.userAccountService.create("ADMIN");
		result.setUserAccount(userAccount);

		return result;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result = null;
		result = this.administratorRepository.findAll();
		return result;
	}

	public Administrator findOne(final int adminId) {
		Administrator result = null;
		result = this.administratorRepository.findOne(adminId);
		return result;
	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator, "message.error.admin.null");
		Administrator result = null;
		result = this.administratorRepository.save(administrator);
		return result;
	}

	public Administrator saveFromCreate(final Administrator administrator) {
		Assert.notNull(administrator, "message.error.admin.null");

		final Administrator result;
		Administrator principal;

		// Check principal
		principal = this.findByPrincipal();
		Assert.notNull(principal, "message.error.admin.principal.null");

		// Check authority
		boolean isAdmin;
		isAdmin = this.actorService.checkAuthority(administrator, "ADMIN");
		Assert.isTrue(isAdmin, "message.error.admin.authority.wrong");

		// Check repeated username
		UserAccount possibleRepeated;
		possibleRepeated = this.userAccountService.findByUsername(administrator.getUserAccount().getUsername());
		Assert.isNull(possibleRepeated, "message.error.admin.username.repeated");

		result = this.save(administrator);

		return result;
	}

	public Administrator saveFromEdit(final Administrator administrator) {
		Assert.notNull(administrator, "message.error.admin.null");

		Administrator result = null;
		Administrator principal;

		// Check principal
		principal = this.findByPrincipal();
		Assert.notNull(principal, "message.error.admin.principal.null");
		Assert.isTrue(principal.getId() == administrator.getId(), "message.error.admin.principal.self");

		// Check authority
		boolean isAdmin;
		isAdmin = this.actorService.checkAuthority(administrator, "ADMIN");
		Assert.isTrue(isAdmin, "message.error.admin.authority.wrong");

		// Encoding password
		UserAccount userAccount;
		userAccount = administrator.getUserAccount();
		userAccount = this.userAccountService.modifyPassword(userAccount);
		administrator.setUserAccount(userAccount);

		result = this.save(administrator);

		return result;
	}

	public void flush() {
		this.administratorRepository.flush();
	}

	//Other business methods -------------------------------------------------

	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.administratorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}
}
