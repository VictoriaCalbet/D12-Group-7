
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {

	//	@Query("select ann from Announcement ann where ann.project in (select distinct(pat.project.id) from Patronage pat where pat.user.id = ?1) order by ann.creationMoment desc")
	//	Collection<Announcement> findStreamThatIFunded(int userId);

	@Query("select distinct(a) from Patronage pt join pt.project.announcements a where pt.user.id = ?1 order by a.creationMoment desc")
	Collection<Announcement> findStreamThatIFunded(int userId);
}
