
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

	@Query("select p from Project p where (p.title like %?1% or p.description like %?1%) and p.creator.id = ?2 order by p.creationMoment desc")
	Collection<Project> findProjectByKeyWordByUser(String keyWord, int userId);

	@Query("select p from Project p order by p.creationMoment desc")
	Collection<Project> findAllOrdered();

	@Query("select p from Project p where p.creator.id = ?1 and p.isDraft IS FALSE and p.isCancelled is FALSE")
	Collection<Project> findAllCreatedbyUser(int userId);

	@Query("select distinct(p.project) from Patronage p where p.user.id = ?1")
	Collection<Project> findAllFundedByUser(int userId);

	@Query("select count(r) from Project p join p.reports r where r.isLegit = true and p.id = ?1")
	Integer isProjectLegitComplaint(int projectId);

<<<<<<< HEAD
	@Query("select p from Project p where p.isDraft=true")
	Collection<Project> findDraftProjects();
=======
	@Query("select p from Project p where p.creator.id = ?1 order by p.creationMoment desc")
	Collection<Project> projectUser(int userId);
>>>>>>> 6c3aac767854838db84e236acfd620b7d9c87480

}
