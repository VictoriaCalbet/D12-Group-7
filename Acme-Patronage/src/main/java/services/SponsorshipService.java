
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Corporation;
import domain.Patronage;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CorporationService		corporationService;

	@Autowired
	private ProjectService			projectService;


	// Constructors -----------------------------------------------------------

	public SponsorshipService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Sponsorship create() {
		final Sponsorship result;
		result = new Sponsorship();
		result.setCorporation(this.isCorporationAunthenticate());
		return result;
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result = null;
		result = this.sponsorshipRepository.findAll();
		return result;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Sponsorship result = null;
		result = this.sponsorshipRepository.findOne(sponsorshipId);
		return result;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Sponsorship result = null;
		result = this.sponsorshipRepository.save(sponsorship);
		return result;
	}

	public Sponsorship saveFromCreate(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship, "message.error.sponsorship.null");
		sponsorship.setCorporation(this.isCorporationAunthenticate());
		Assert.notNull(sponsorship.getBannerURL(), "message.error.sponsorship.null.banner");
		Assert.notNull(sponsorship.getPageURL(), "message.error.sponsorship.null.page");
		Assert.notNull(sponsorship.getProject(), "message.error.sponsorship.null.project");
		Assert.isTrue(sponsorship.getProject().getDueDate().after(LocalDate.now().toDate()), "message.error.sponsorship.pastduedate");
		this.checkEconomicGoal(sponsorship);
		Sponsorship sponsorshipInDB;
		sponsorshipInDB = this.sponsorshipRepository.save(sponsorship);

		return sponsorshipInDB;
	}

	public Sponsorship saveFromEdit(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship.getCorporation());
		this.isCorrectCorporationAunthenticate(sponsorship.getCorporation().getId());
		Assert.notNull(sponsorship, "message.error.advertisement.null");
		Assert.notNull(sponsorship.getBannerURL(), "message.error.sponsorship.null.banner");
		Assert.notNull(sponsorship.getPageURL(), "message.error.sponsorship.null.page");
		Assert.notNull(sponsorship.getProject(), "message.error.sponsorship.null.project");

		Sponsorship sponsorshipInDB;
		sponsorshipInDB = this.findOne(sponsorship.getId());
		Assert.notNull(sponsorshipInDB);
		Assert.isTrue(sponsorship.getProject().getId() == sponsorshipInDB.getProject().getId(), "message.error.sponsorship.editproject");
		this.isCorrectCorporationAunthenticate(sponsorshipInDB.getCorporation().getId());

		sponsorshipInDB = this.sponsorshipRepository.save(sponsorship);

		return sponsorshipInDB;
	}

	public void deleteByCorporation(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship, "message.error.sponsorship.null");

		Sponsorship sponsorshipInDB;
		sponsorshipInDB = this.sponsorshipRepository.findOne(sponsorship.getId());
		Assert.notNull(sponsorshipInDB, "message.error.sponsorship.null.indb");
		this.isCorrectCorporationAunthenticate(sponsorshipInDB.getCorporation().getId());
		this.sponsorshipRepository.delete(sponsorshipInDB);
	}
	public void flush() {
		this.sponsorshipRepository.flush();
	}

	// Other business methods -------------------------------------------------
	public Sponsorship getRandomSponsorshipByProjectId(final int projectId) {
		final List<Sponsorship> sponsorships = new ArrayList<Sponsorship>(this.projectService.findOne(projectId).getSponsorships());
		final int nSponsorships = sponsorships.size();
		Sponsorship s = null;
		if (nSponsorships != 0) {
			final Random random = new Random();
			final int randomIndex = random.nextInt(nSponsorships);
			s = sponsorships.get(randomIndex);
		}

		return s;
	}
	//Auxiliar method ---------------------------------------------------------
	private Corporation isCorporationAunthenticate() {
		Corporation actor;
		actor = this.corporationService.findByPrincipal();
		Assert.notNull(actor);
		String authority;
		authority = actor.getUserAccount().getAuthorities().iterator().next().getAuthority();
		Assert.isTrue(authority.equals("CORPORATION"), "message.error.sponsorship.notcorporation");
		return actor;
	}

	private void isCorrectCorporationAunthenticate(final int corporationId) {
		Assert.isTrue(this.isCorporationAunthenticate().getId() == corporationId, "message.error.sponsorship.badcorporation");
	}

	private void checkEconomicGoal(final Sponsorship sponsorship) {
		Double totalAmountReached;
		totalAmountReached = 0.0;
		for (final Patronage p : sponsorship.getProject().getPatronages())
			totalAmountReached = totalAmountReached + p.getAmount();
		Assert.isTrue(sponsorship.getProject().getEconomicGoal() > totalAmountReached, "message.error.sponsorship.economicgoalreached");
	}

	// Dashboard --------------------------------------------------------------

	// Req 25.2.1: The average and standard deviation of sponsorships per corporation.

	public Double avgSponsorshipPerCorporation() {
		return this.sponsorshipRepository.avgSponsorshipPerCorporation();
	}

	public Double stdSponsorshipPerCorporation() {
		return this.sponsorshipRepository.stdSponsorshipPerCorporation();
	}

	// Req 25.2.2: The average and standard deviation of sponsorships per project.

	public Double avgSponsorshipPerProject() {
		return this.sponsorshipRepository.avgSponsorshipPerProject();
	}

	public Double stdSponsorshipPerProject() {
		return this.sponsorshipRepository.stdSponsorshipPerProject();
	}

}
