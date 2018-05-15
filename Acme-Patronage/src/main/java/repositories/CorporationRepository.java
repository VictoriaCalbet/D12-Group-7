
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Corporation;

@Repository
public interface CorporationRepository extends JpaRepository<Corporation, Integer> {

}
