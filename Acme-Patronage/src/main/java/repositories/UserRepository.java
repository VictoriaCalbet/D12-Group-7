
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.userAccount.id = ?1")
	User findByUserAccountId(int userAccountId);

	// Dashboard --------------------------------------------------------------

	// Req 12.2.5: The users who have, at least, a 10% more projects than the average.

	@Query("select u from User u where u.projects.size > (select avg(u2.projects.size)*1.1 from User u2)")
	Collection<User> findAllWith10PercentMoreProjectsThanAvg();

	// Req 12.2.6: The users who have, at least, 10% more patronages than the average.

	@Query("select u from User u join u.projects p where p.patronages.size > (select avg(p2.patronages.size)*1.1 from User u2 join u2.projects p2) group by u")
	Collection<User> findAllWith10PercentMorePatronagesThanAvg();

	// Req 33.3.5: The average and standard deviation of banned users.

	// Req 33.3.6: The ratio of banned users.

	@Query("select count(u1)*1.0 / (select count(u2)*1.0 from User u2 where u2.userAccount.isEnabled = true) from User u1 where u1.userAccount.isEnabled = false")
	Double ratioBannedUsers();
}
