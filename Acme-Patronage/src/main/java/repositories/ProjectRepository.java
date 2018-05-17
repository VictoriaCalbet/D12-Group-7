
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	@Query("select p from Project p where p.dueDate > CURRENT_TIMESTAMP and p.isCancelled = false and p.isDraft = false order by p.creationMoment desc")
	Collection<Project> findProjectFutureDueDate();

	@Query("select p from Project p where (p.title like %?1% or p.description like %?1%) and p.isDraft = false order by p.creationMoment desc")
	Collection<Project> findProjectByKeyWord(String keyWord);

	@Query("select p from Project p where (p.title like %?1% or p.description like %?1%) order by p.creationMoment desc")
	Collection<Project> findProjectByKeyWordByAdmin(String keyWord);

	@Query("select p from Project p order by p.creationMoment desc")
	Collection<Project> findAllOrdered();
}
