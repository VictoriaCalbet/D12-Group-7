
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.AwardComment;

@Repository
public interface AwardCommentRepository extends JpaRepository<AwardComment, Integer> {

}
