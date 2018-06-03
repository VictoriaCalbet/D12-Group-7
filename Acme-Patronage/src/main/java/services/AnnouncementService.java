
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AnnouncementRepository;
import domain.Announcement;
import domain.AnnouncementComment;
import domain.User;

@Service
@Transactional
public class AnnouncementService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AnnouncementRepository	announcementRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService				userService;


	// Constructors -----------------------------------------------------------

	public AnnouncementService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Announcement create(final int projectId) {
		Announcement result = null;

		result = new Announcement();
		result.setAnnouncementComments(new ArrayList<AnnouncementComment>());
		result.setCreationMoment(new Date(System.currentTimeMillis() - 1000));

		return result;
	}

	public Collection<Announcement> findAll() {
		Collection<Announcement> result = null;
		result = this.announcementRepository.findAll();
		return result;
	}

	public Announcement findOne(final int announcementId) {
		Announcement result = null;
		result = this.announcementRepository.findOne(announcementId);
		return result;
	}

	public Announcement save(final Announcement announcement) {
		Assert.notNull(announcement, "message.error.announcement.null");
		Announcement result = null;
		result = this.announcementRepository.save(announcement);
		return result;
	}

	public Announcement saveFromCreate(final Announcement announcement) {
		Announcement result = null;
		User user = null;

		user = this.userService.findByPrincipal();

		Assert.isTrue(announcement.getId() == 0, "message.error.announcement.edit");
		Assert.notNull(announcement, "message.error.announcement.null");
		Assert.notNull(user, "message.error.announcement.principal.null");
		Assert.isTrue(announcement.getProject().getCreator().equals(user), "message.error.announcement.user.owner");
		announcement.setCreationMoment(new Date(System.currentTimeMillis() - 1000));

		// Paso 1: realizo la entidad del servicio Announcement

		result = this.save(announcement);

		// Paso 2: persisto el resto de relaciones a las que el objeto Announcement esta relacionada.

		return result;
	}
	public void flush() {
		this.announcementRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Collection<Announcement> findStreamThatIFunded(final int userId) {
		Collection<Announcement> result = null;
		result = this.announcementRepository.findStreamThatIFunded(userId);
		return result;
	}

}
