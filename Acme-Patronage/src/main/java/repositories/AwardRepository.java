
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Award;

@Repository
public interface AwardRepository extends JpaRepository<Award, Integer> {

}
