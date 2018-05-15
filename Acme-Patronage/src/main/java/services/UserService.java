
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;
import domain.User;

@Service
@Transactional
public class UserService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserRepository	userRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public UserService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: User - create
	public User create() {
		final User result = null;

		return result;
	}

	public Collection<User> findAll() {
		Collection<User> result = null;
		result = this.userRepository.findAll();
		return result;
	}

	public User findOne(final int userId) {
		User result = null;
		result = this.userRepository.findOne(userId);
		return result;
	}

	public User save(final User user) {
		Assert.notNull(user);
		User result = null;
		result = this.userRepository.save(user);
		return result;
	}

	// TODO: User - saveFromCreate
	public User saveFromCreate() {
		final User result = null;

		return result;
	}

	// TODO: User - saveFromEdit
	public User saveFromEdit() {
		final User result = null;

		return result;
	}

	public void flush() {
		this.userRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public User findByPrincipal() {
		User result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.userRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}
}
