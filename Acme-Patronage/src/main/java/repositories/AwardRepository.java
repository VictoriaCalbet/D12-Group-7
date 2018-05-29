
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Award;

@Repository
public interface AwardRepository extends JpaRepository<Award, Integer> {

	@Query("select awd from Project pr join pr.patronages pat join pr.awards awd where pat.user.id = ?1  and pr.dueDate > CURRENT_TIMESTAMP and (pr.economicGoal <= (select sum(ptr.amount) from Patronage ptr where ptr.project.id = pr.id)) and (awd.moneyGoal <= (select sum(ptrr.amount) from Patronage ptrr where ptrr.project.id = pr.id))")
	Collection<Award> findMyAwards(int userId);

	// Dashboard --------------------------------------------------------------

	// Req 12.2.2: The average and standard deviation of awards per project.

	@Query("select avg(p.awards.size) from Project p")
	Double avgAwardsPerProject();

	@Query("select sqrt(sum(p.awards.size * p.awards.size) / count(p.awards.size) - (avg(p.awards.size) * avg(p.awards.size))) from Project p")
	Double stdAwardsPerProject();

	// Req 12.2.3: The average and standard deviation of awards per user.

	@Query("select avg(p.awards.size) from User u left join u.projects p")
	Double avgAwardsPerUser();

	@Query("select sqrt(sum(p.awards.size * p.awards.size) / count(p.awards.size) - (avg(p.awards.size) * avg(p.awards.size))) from User u left join u.projects p")
	Double stdAwardsPerUser();
}
