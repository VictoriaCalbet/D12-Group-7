
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Corporation;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private CorporationService		corporationService;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public SponsorshipService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: Sponsorship - create
	public Sponsorship create() {
		final Sponsorship result = null;
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
		this.isCorrectCorporationAunthenticate(sponsorshipInDB.getCorporation().getId());

		sponsorshipInDB = this.sponsorshipRepository.save(sponsorship);

		return sponsorshipInDB;
	}

	public void flush() {
		this.sponsorshipRepository.flush();
	}

	// Other business methods -------------------------------------------------
	//Auxiliar method ---------------------------------------------------------
	private Corporation isCorporationAunthenticate() {
		Corporation actor;
		actor = this.corporationService.findByPrincipal();
		Assert.notNull(actor);
		String authority;
		authority = actor.getUserAccount().getAuthorities().iterator().next().getAuthority();
		Assert.isTrue(authority.equals("CORPORATION"), "message.error.advertisement.notcorporation");
		return actor;
	}

	private void isCorrectCorporationAunthenticate(final int corporationId) {
		Assert.isTrue(this.isCorporationAunthenticate().getId() == corporationId, "message.error.advertisement.badcorporation");
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
