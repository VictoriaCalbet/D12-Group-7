
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	// Dashboard --------------------------------------------------------------

	// Req 25.2.1: The average and standard deviation of sponsorships per corporation

	@Query("select avg(c.sponsorships.size) from Corporation c")
	Double avgSponsorshipPerCorporation();

	@Query("select sqrt(sum(c.sponsorships.size * c.sponsorships.size) / count(c.sponsorships.size) - (avg(c.sponsorships.size) * avg(c.sponsorships.size))) from Corporation c")
	Double stdSponsorshipPerCorporation();

	// Req 25.2.2: The average and standard deviation of sponsorships per project.

	@Query("select avg(p.sponsorships.size) from Project p")
	Double avgSponsorshipPerProject();

	@Query("select sqrt(sum(p.sponsorships.size * p.sponsorships.size) / count(p.sponsorships.size) - (avg(p.sponsorships.size) * avg(p.sponsorships.size))) from Project p")
	Double stdSponsorshipPerProject();
}
