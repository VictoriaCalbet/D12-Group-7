
package services.forms;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import security.UserAccount;
import security.UserAccountService;
import services.ActorService;
import services.AdministratorService;
import services.CorporationService;
import services.ModeratorService;
import services.UserService;
import domain.Actor;
import domain.Administrator;
import domain.Corporation;
import domain.Moderator;
import domain.User;
import domain.forms.ActorForm;

@Service
@Transactional
public class ActorFormService {

	// Managed repository -----------------------------------------------------

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private UserService				userService;

	@Autowired
	private ModeratorService		moderatorService;

	@Autowired
	private CorporationService		corporationService;


	// Constructors -----------------------------------------------------------

	public ActorFormService() {
		super();
	}

	public ActorForm create() {
		ActorForm result;

		result = new ActorForm();

		return result;
	}

	public ActorForm createFromActor() {
		ActorForm result;
		final Actor actor = this.actorService.findByPrincipal();

		result = new ActorForm();
		result.setId(actor.getId());
		result.setFullName(actor.getFullName());
		result.setEmail(actor.getEmail());
		result.setBio(actor.getBio());
		result.setTelephone(actor.getTelephone());
		result.setUsername(actor.getUserAccount().getUsername());

		return result;
	}

	public Actor saveFromCreate(final ActorForm actorForm, final String authority) {
		Assert.notNull(actorForm, "message.error.actor.null");
		Assert.isTrue(actorForm.getPassword().equals(actorForm.getRepeatedPassword()), "message.error.actor.password.mismatch");
		Assert.isTrue(actorForm.getAcceptTerms(), "message.error.actor.acceptTerms.false");
		Assert.isTrue(actorForm.getId() == 0, "message.error.actor.id");

		switch (authority) {
		case "ADMIN": {
			final Administrator result;
			Administrator principal;

			principal = this.administratorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal, "ÄDMIN"), "message.error.actor.admin.login");

			final Administrator administrator;
			UserAccount userAccount;

			userAccount = this.userAccountService.createComplete(actorForm.getUsername(), actorForm.getPassword(), "ADMIN");
			administrator = this.administratorService.create();

			administrator.setFullName(actorForm.getFullName());
			administrator.setEmail(actorForm.getEmail());
			administrator.setBio(actorForm.getBio());
			administrator.setTelephone(actorForm.getTelephone());
			administrator.setUserAccount(userAccount);

			result = this.administratorService.saveFromCreate(administrator);

			return result;
		}
		case "USER": {
			final User result;
			final User user;
			final UserAccount userAccount;

			Assert.isTrue(!this.actorService.checkLogin(), "message.error.actor.user.login");

			userAccount = this.userAccountService.createComplete(actorForm.getUsername(), actorForm.getPassword(), "USER");
			user = this.userService.create();

			user.setFullName(actorForm.getFullName());
			user.setEmail(actorForm.getEmail());
			user.setBio(actorForm.getBio());
			user.setTelephone(actorForm.getTelephone());
			user.setUserAccount(userAccount);

			result = this.userService.saveFromCreate(user);

			return result;
		}
		case "MODERATOR": {
			final Moderator result;
			Administrator principal;

			principal = this.administratorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal, "ADMIN"), "message.error.actor.admin.login");

			final Moderator moderator;
			UserAccount userAccount;

			userAccount = this.userAccountService.createComplete(actorForm.getUsername(), actorForm.getPassword(), "MODERATOR");
			moderator = this.moderatorService.create();

			moderator.setFullName(actorForm.getFullName());
			moderator.setEmail(actorForm.getEmail());
			moderator.setBio(actorForm.getBio());
			moderator.setTelephone(actorForm.getTelephone());
			moderator.setUserAccount(userAccount);

			result = this.moderatorService.saveFromCreate(moderator);

			return result;
		}
		case "CORPORATION": {
			final Corporation result;
			final Corporation corporation;
			final UserAccount userAccount;

			Assert.isTrue(!this.actorService.checkLogin(), "message.error.actor.corporation.login");

			userAccount = this.userAccountService.createComplete(actorForm.getUsername(), actorForm.getPassword(), "CORPORATION");
			corporation = this.corporationService.create();

			corporation.setFullName(actorForm.getFullName());
			corporation.setEmail(actorForm.getEmail());
			corporation.setBio(actorForm.getBio());
			corporation.setTelephone(actorForm.getTelephone());
			corporation.setUserAccount(userAccount);

			result = this.corporationService.saveFromCreate(corporation);

			return result;
		}
		default:
			return null;
		}

	}

	public Actor saveFromEdit(final ActorForm actorForm, final String authority) {
		Assert.notNull(actorForm, "message.error.actor.null");
		Assert.isTrue(actorForm.getPassword().equals(actorForm.getRepeatedPassword()), "message.error.actor.password.mismatch");

		switch (authority) {
		case "ADMIN": {
			final Actor principal = this.actorService.findByPrincipal();
			final UserAccount userAccount;

			Assert.isTrue(principal.getId() == actorForm.getId(), "message.error.actor.edit.self");

			userAccount = principal.getUserAccount();
			userAccount.setPassword(actorForm.getPassword());

			final Administrator result;
			final Administrator administrator;

			administrator = this.administratorService.findByPrincipal();

			administrator.setFullName(actorForm.getFullName());
			administrator.setEmail(actorForm.getEmail());
			administrator.setBio(actorForm.getBio());
			administrator.setTelephone(actorForm.getTelephone());
			administrator.setUserAccount(userAccount);

			result = this.administratorService.saveFromEdit(administrator);

			return result;
		}
		case "USER": {
			final Actor principal = this.actorService.findByPrincipal();
			final UserAccount userAccount;

			Assert.isTrue(principal.getId() == actorForm.getId(), "message.error.actor.edit.self");

			userAccount = principal.getUserAccount();
			userAccount.setPassword(actorForm.getPassword());

			final User result;
			final User user;

			user = this.userService.findByPrincipal();

			user.setFullName(actorForm.getFullName());
			user.setEmail(actorForm.getEmail());
			user.setBio(actorForm.getBio());
			user.setTelephone(actorForm.getTelephone());
			user.setUserAccount(userAccount);

			result = this.userService.saveFromEdit(user);

			return result;
		}
		case "MODERATOR": {
			final Actor principal = this.actorService.findByPrincipal();
			final UserAccount userAccount;

			Assert.isTrue(principal.getId() == actorForm.getId(), "message.error.actor.edit.self");

			userAccount = principal.getUserAccount();
			userAccount.setPassword(actorForm.getPassword());

			final Moderator result;
			final Moderator moderator;

			moderator = this.moderatorService.findByPrincipal();

			moderator.setFullName(actorForm.getFullName());
			moderator.setEmail(actorForm.getEmail());
			moderator.setBio(actorForm.getBio());
			moderator.setTelephone(actorForm.getTelephone());
			moderator.setUserAccount(userAccount);

			result = this.moderatorService.saveFromEdit(moderator);

			return result;
		}
		case "CORPORATION": {
			final Actor principal = this.actorService.findByPrincipal();
			final UserAccount userAccount;

			Assert.isTrue(principal.getId() == actorForm.getId(), "message.error.actor.edit.self");

			userAccount = principal.getUserAccount();
			userAccount.setPassword(actorForm.getPassword());

			final Corporation result;
			final Corporation corporation;

			corporation = this.corporationService.findByPrincipal();

			corporation.setFullName(actorForm.getFullName());
			corporation.setEmail(actorForm.getEmail());
			corporation.setBio(actorForm.getBio());
			corporation.setTelephone(actorForm.getTelephone());
			corporation.setUserAccount(userAccount);

			result = this.corporationService.saveFromEdit(corporation);

			return result;
		}
		default:
			return null;
		}

	}
}
