
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.AwardComment;

@Repository
public interface AwardCommentRepository extends JpaRepository<AwardComment, Integer> {

	@Query("select a.awardComments from Award a where a.id=?1")
	public Collection<AwardComment> listAllAwardComments(int awardId);

	@Query("select a from AwardComment a where a.user.id=?1")
	public Collection<AwardComment> listAllAwardCommentsOfUser(int userId);

}
