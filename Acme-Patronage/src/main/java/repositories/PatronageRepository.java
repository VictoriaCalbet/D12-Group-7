
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Patronage;

@Repository
public interface PatronageRepository extends JpaRepository<Patronage, Integer> {
	
	@Query("select p from Patronage p where p.user.id = ?1 and p.project.id = ?2")
	public Collection<Patronage> getPatronagesOfProjectByUser(int userId, int projectId);

	@Query("select p from Patronage p where p.project.creator.id = ?1")
	Collection<Patronage> findAllPatronagesToUserProjects(int userId);

	@Query("select sum(pat.amount) from Patronage pat where pat.project.id = ?1")
	Double findTotalAmount(int projectId);

}
