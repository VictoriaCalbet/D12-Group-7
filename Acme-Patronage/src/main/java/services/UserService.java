
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Announcement;
import domain.Comment;
import domain.Patronage;
import domain.Project;
import domain.Report;
import domain.User;

@Service
@Transactional
public class UserService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserRepository		userRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public UserService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public User create() {
		User result;
		UserAccount userAccount;

		result = new User();
		result.setAnnouncements(new HashSet<Announcement>());
		result.setComments(new HashSet<Comment>());
		result.setPatronages(new HashSet<Patronage>());
		result.setProjects(new HashSet<Project>());
		result.setReports(new HashSet<Report>());

		userAccount = this.userAccountService.create("USER");
		result.setUserAccount(userAccount);

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
		Assert.notNull(user, "message.error.user.null");
		User result = null;
		result = this.userRepository.save(user);
		return result;
	}

	public User saveFromCreate(final User user) {
		Assert.notNull(user, "message.error.user.null");

		final User result;

		// Check unlogged
		Assert.isTrue(!this.actorService.checkLogin(), "message.error.user.unlogged");

		// Check authority
		boolean isUser;
		isUser = this.actorService.checkAuthority(user, "USER");
		Assert.isTrue(isUser, "message.error.user.authority.wrong");

		// Check repeated username
		UserAccount possibleRepeated;
		possibleRepeated = this.userAccountService.findByUsername(user.getUserAccount().getUsername());
		Assert.isNull(possibleRepeated, "message.error.user.username.repeated");

		result = this.save(user);

		return result;
	}

	public User saveFromEdit(final User user) {
		Assert.notNull(user, "message.error.user.null");

		User result;
		User principal;

		// Check principal
		principal = this.findByPrincipal();
		Assert.notNull(principal, "message.error.user.principal.null");
		Assert.isTrue(principal.getId() == user.getId(), "message.error.user.principal.self");

		// Check authority
		boolean isUser;
		isUser = this.actorService.checkAuthority(user, "USER");
		Assert.isTrue(isUser, "message.error.user.authority.wrong");

		// Encoding password
		UserAccount userAccount;
		userAccount = user.getUserAccount();
		userAccount = this.userAccountService.modifyPassword(userAccount);
		user.setUserAccount(userAccount);

		result = this.save(user);

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

	// Dashboard

	// Req 12.2.5: The users who have, at least, a 10% more projects than the average.

	public Collection<User> findAllWith10PercentMoreProjectsThanAvg() {
		return this.userRepository.findAllWith10PercentMoreProjectsThanAvg();
	}

	// Req 12.2.6: The users who have, at least, 10% more patronages than the average.

	public Collection<User> findAllWith10PercentMorePatronagesThanAvg() {
		return this.userRepository.findAllWith10PercentMorePatronagesThanAvg();
	}

	// Req 33.3.6: The ratio of banned users.

	public Double ratioBannedUsers() {
		return this.userRepository.ratioBannedUsers();
	}
}
