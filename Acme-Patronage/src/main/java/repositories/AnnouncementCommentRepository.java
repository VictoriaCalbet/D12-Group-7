
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.AnnouncementComment;

@Repository
public interface AnnouncementCommentRepository extends JpaRepository<AnnouncementComment, Integer> {

	@Query("select an.announcementComments from Announcement an where an.id=?1")
	public Collection<AnnouncementComment> listAllAnnouncementComments(int announcementId);

	@Query("select an from AnnouncementComment an where an.user.id=?1")
	public Collection<AnnouncementComment> listAllAnnouncementCommentsOfUser(int userId);

}
