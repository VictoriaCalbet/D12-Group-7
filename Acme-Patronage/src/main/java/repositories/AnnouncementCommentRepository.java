
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.AnnouncementComment;

@Repository
public interface AnnouncementCommentRepository extends JpaRepository<AnnouncementComment, Integer> {

}
