
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.ProjectComment;

@Repository
public interface ProjectCommentRepository extends JpaRepository<ProjectComment, Integer> {

}
