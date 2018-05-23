
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ProjectComment;

@Repository
public interface ProjectCommentRepository extends JpaRepository<ProjectComment, Integer> {

	@Query("select p.projectComments from Project p where p.id=?1")
	public Collection<ProjectComment> listAllProjectComments(int projectId);

	@Query("select p from ProjectComment p where p.user.id=?1")
	public Collection<ProjectComment> listAllProjectCommentsOfUser(int userId);

	// Dashboard --------------------------------------------------------------

	// Req 25.2.3: The average and standard deviation of comments per project.

	@Query("select avg(p.projectComments.size) from Project p")
	Double avgCommentsPerProject();

	@Query("select sqrt(sum(p.projectComments.size * p.projectComments.size) / count(p.projectComments.size) - (avg(p.projectComments.size) * avg(p.projectComments.size))) from Project p")
	Double stdCommentsPerProject();

}
