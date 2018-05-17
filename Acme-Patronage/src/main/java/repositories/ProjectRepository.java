
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	@Query("select p from Project p where p.dueDate > CURRENT_TIMESTAMP and p.isCancelled = false and p.isDraft = false")
	Collection<Project> findProjectFutureDueDate();

}
