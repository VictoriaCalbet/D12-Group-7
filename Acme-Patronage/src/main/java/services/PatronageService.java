
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PatronageRepository;
import domain.CreditCard;
import domain.Patronage;
import domain.User;

@Service
@Transactional
public class PatronageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PatronageRepository	patronageRepository;

	@Autowired
	private UserService			userService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ProjectService		projectService;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public PatronageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Patronage create(final int projectId) {
		final Patronage result = new Patronage();
		result.setCreationDate(new Date(System.currentTimeMillis() - 1));
		result.setIsCancelled(false);
		result.setProject(this.projectService.findOne(projectId));
		final User principal = this.userService.findByPrincipal();
		result.setUser(principal);
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

	public Patronage saveFromCreate(final Patronage patronage) {
		Assert.notNull(patronage, "message.error.patronage.null");
		Assert.isTrue(this.checkCreditCard(patronage.getCreditCard()), "message.error.patronage.invalidCreditCard");

		Patronage result;

		//Check that credit card expires after the project due date
		final Calendar cal = Calendar.getInstance();
		cal.setTime(patronage.getProject().getDueDate());

		if ((patronage.getCreditCard().getExpirationYear()) == (cal.get(Calendar.YEAR)))
			Assert.isTrue((patronage.getCreditCard().getExpirationMonth()) >= (cal.get(Calendar.MONTH) + 1));
		Assert.isTrue((patronage.getCreditCard().getExpirationYear()) >= (cal.get(Calendar.YEAR)));
		final User principal = this.userService.findByPrincipal();

		Assert.isTrue(this.actorService.checkAuthority(principal, "USER"), "message.error.patronage.create.user");

		result = this.save(patronage);

		return result;
	}
	public Patronage saveFromEdit(final Patronage patronage) {
		Assert.notNull(patronage, "message.error.patronage.null");
		Assert.isTrue(this.checkCreditCard(patronage.getCreditCard()), "message.error.patronage.invalidCreditCard");
		Patronage result;
		final User principal = this.userService.findByPrincipal();
		//Check that credit card expires after the project due date
		final Calendar cal = Calendar.getInstance();
		cal.setTime(patronage.getProject().getDueDate());

		Assert.isTrue((patronage.getCreditCard().getExpirationMonth()) < (cal.get(Calendar.MONTH) + 1));
		Assert.isTrue((patronage.getCreditCard().getExpirationYear()) < (cal.get(Calendar.YEAR)));

		Assert.isTrue(this.actorService.checkAuthority(principal, "USER"), "message.error.patronage.principal.owner ");
		result = this.save(patronage);
		return result;
	}

	public void cancelPatronage(final Patronage patronage) {
		Assert.notNull(patronage, "message.error.patronage.null");
		final User principal = this.userService.findByPrincipal();

		Assert.isTrue(principal == patronage.getUser(), "message.error.cancel.patronage.notOwner");
		Assert.isTrue(this.actorService.checkAuthority(principal, "USER"), "message.error.patronage.principal.owner ");
		Assert.isTrue(principal.getPatronages().contains(patronage), "message.error.cancel.patronage.notOwner");
		patronage.setIsCancelled(true);
		this.save(patronage);

	}

	public void flush() {
		this.patronageRepository.flush();
	}

	private boolean checkCreditCard(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		Assert.notNull(creditCard.getHolderName());
		Assert.notNull(creditCard.getBrandName());
		Assert.notNull(creditCard.getNumber());
		Assert.notNull(creditCard.getExpirationMonth());
		Assert.notNull(creditCard.getExpirationYear());
		Assert.notNull(creditCard.getCvv());

		boolean result = false;
		Date now = null;

		now = new Date();
		final Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		final int year = cal.get(Calendar.YEAR);
		final int month = cal.get(Calendar.MONTH) + 1;

		if (creditCard.getExpirationYear() > year)
			result = true;
		else if (creditCard.getExpirationYear() == year) {
			if (creditCard.getExpirationMonth() >= month)
				result = true;
			else
				result = false;
		} else
			result = false;

		return result;
	}
	// Other business methods -------------------------------------------------
	public Collection<Patronage> findAllPatronagesToUserProjects(final int userId) {
		return this.patronageRepository.findAllPatronagesToUserProjects(userId);
	}

	public Double findTotalAmount(final int projectId) {
		return this.patronageRepository.findTotalAmount(projectId);
	}
}
