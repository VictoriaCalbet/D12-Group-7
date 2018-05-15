
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	//Managed repository -----------------------------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;


	//Supporting services ----------------------------------------------------

	//Constructors -----------------------------------------------------------

	public AdministratorService() {
		super();
	}

	//Simple CRUD methods ----------------------------------------------------

	// TODO: User - create
	public Administrator create() {
		final Administrator result = null;

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
		Assert.notNull(administrator);
		Administrator result = null;
		result = this.administratorRepository.save(administrator);
		return result;
	}

	// TODO: User - saveFromCreate
	public Administrator saveFromCreate() {
		final Administrator result = null;

		return result;
	}

	// TODO: User - saveFromEdit
	public Administrator saveFromEdit() {
		final Administrator result = null;

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
