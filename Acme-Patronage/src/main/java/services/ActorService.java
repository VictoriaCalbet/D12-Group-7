
package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.User;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository	actorRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Actor save(final Actor actor) {
		Assert.notNull(actor, "message.error.actor.null");
		Actor result;
		result = this.actorRepository.save(actor);
		return result;
	}

	public void flush() {
		this.actorRepository.flush();
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;
		result = this.actorRepository.findAll();
		return result;
	}

	public Actor findOne(final int actorId) {
		Actor result;
		result = this.actorRepository.findOne(actorId);
		return result;
	}

	public Actor findOneByUsername(final String username) {
		Actor result;
		result = this.actorRepository.findOneByUsername(username);
		return result;
	}

	public void ban(final int actorId) {

		final Actor principal = this.findByPrincipal();
		Assert.isTrue(this.checkAuthority(principal, "ADMIN"));

		final Actor actor = this.findOne(actorId);
		Assert.notNull(actor, "message.error.actor.null");
		Assert.isTrue(actor.getUserAccount().getIsEnabled(), "message.error.actor.enabled");
		Assert.isTrue(this.hasLegitReports(actorId), "message.error.actor.report.legit");

		actor.getUserAccount().setIsEnabled(false);

		this.save(actor);
	}

	public void unban(final int actorId) {

		final Actor principal = this.findByPrincipal();
		Assert.isTrue(this.checkAuthority(principal, "ADMIN"));

		final Actor actor = this.findOne(actorId);
		Assert.notNull(actor, "message.error.actor.null");
		Assert.isTrue(!actor.getUserAccount().getIsEnabled(), "message.error.actor.disabled");

		actor.getUserAccount().setIsEnabled(true);

		this.save(actor);

	}
	// Other business methods -------------------------------------------------

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.actorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public boolean checkAuthority(final Actor actor, final String authority) {
		boolean result = false;
		Collection<Authority> authorities;

		try {
			authorities = actor.getUserAccount().getAuthorities();

			for (final Authority a : authorities)
				if (a.getAuthority().equals(authority.toUpperCase())) {
					result = true;
					break;
				}
		} catch (final IllegalArgumentException e) {
			result = false;
		}

		return result;
	}

	public boolean checkLogin() {
		boolean result;

		result = true;

		try {
			this.findByPrincipal();
		} catch (final Throwable e) {
			result = false;
		}
		return result;
	}

	public Boolean hasLegitReports(final int userId) {
		return this.actorRepository.hasLegitReports(userId);
	}

	public Map<Integer, Boolean> hasLegitReports(final Collection<User> users) {
		Assert.notNull(users);

		final Map<Integer, Boolean> result = new HashMap<>();

		for (final User u : users) {
			final Boolean hasLegitReports = this.hasLegitReports(u.getId());
			result.put(u.getId(), hasLegitReports);
		}

		return result;
	}

}
