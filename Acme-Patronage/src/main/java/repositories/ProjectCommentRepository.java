
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

}
