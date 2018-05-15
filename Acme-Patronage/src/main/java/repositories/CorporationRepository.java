
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Corporation;

@Repository
public interface CorporationRepository extends JpaRepository<Corporation, Integer> {

	@Query("select c from Corporation c where c.userAccount.id = ?1")
	Corporation findByUserAccountId(int userAccountId);
}
