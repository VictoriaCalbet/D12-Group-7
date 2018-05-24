
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Corporation;

@Repository
public interface CorporationRepository extends JpaRepository<Corporation, Integer> {

	@Query("select c from Corporation c where c.userAccount.id = ?1")
	Corporation findByUserAccountId(int userAccountId);

	// Dashboard --------------------------------------------------------------

	// Req 25.2.5: The corporations that have, at least, a 10% more sponsorships than the average.

	@Query("select c from Corporation c where c.sponsorships.size > (select avg(c2.sponsorships.size)*1.1 from Corporation c2)")
	Collection<Corporation> findAllWith10PercentMoreSponsorshipsThanAvg();
}
