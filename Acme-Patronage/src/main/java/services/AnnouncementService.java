
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
import domain.Project;
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

	@Autowired
	private ProjectService			projectService;


	// Constructors -----------------------------------------------------------

	public AnnouncementService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Announcement create(final int projectId) {
		Announcement result = null;
		Project project = null;

		result = new Announcement();
		project = this.projectService.findOne(projectId);
		result.setAnnouncementComments(new ArrayList<AnnouncementComment>());
		result.setCreationMoment(new Date());

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
		Assert.notNull(announcement);
		Announcement result = null;
		result = this.announcementRepository.save(announcement);
		return result;
	}

	public Announcement saveFromCreate(final Announcement announcement) {
		Announcement result = null;
		User user = null;

		user = this.userService.findByPrincipal();

		Assert.notNull(announcement, "message.error.announcement.null");
		Assert.notNull(user, "message.error.announcement.principal.null");
		Assert.isTrue(announcement.getProject().getCreator().equals(user), "message.error.announcement.user.owner");
		Assert.isTrue(!announcement.getProject().getIsCancelled(), "message.error.announcement.project.isCancelled");

		announcement.setCreationMoment(new Date());

		// Paso 1: realizo la entidad del servicio Announcement

		result = this.save(announcement);

		// Paso 2: persisto el resto de relaciones a las que el objeto Announcement esta relacionada.

		return result;
	}

	public void flush() {
		this.announcementRepository.flush();
	}

	// Other business methods -------------------------------------------------

}
