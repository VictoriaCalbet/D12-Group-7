
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Patronage;

@Repository
public interface PatronageRepository extends JpaRepository<Patronage, Integer> {
	
	@Query("select p from Patronage p where p.user = ?1 and p.project = ?2")
	public Collection<Patronage> getPatronagesOfProjectByUser(int userId, int projectId);

}
