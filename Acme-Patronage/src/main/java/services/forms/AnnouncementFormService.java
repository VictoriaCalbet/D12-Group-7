
package services.forms;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import services.AnnouncementService;
import services.ProjectService;
import services.UserService;
import domain.Announcement;
import domain.Project;
import domain.User;
import domain.forms.AnnouncementForm;

@Service
@Transactional
public class AnnouncementFormService {

	// Supporting services ----------------------------------------------------

	@Autowired
	private AnnouncementService	announcementService;

	@Autowired
	private UserService			userService;

	@Autowired
	private ProjectService		projectService;


	// Constructors -----------------------------------------------------------

	public AnnouncementFormService() {
		super();
	}

	// Creación de formularios ------------------------------------------------

	// Utilizado al crear una nueva entidad de Announcement

	public AnnouncementForm createFromCreate(final int projectId) {
		AnnouncementForm result = null;
		User user = null;

		result = new AnnouncementForm();
		user = this.userService.findByPrincipal();

		result.setUserId(user.getId());
		result.setProjectId(projectId);

		return result;
	}

	// Utilizado al editar una nueva entidad de Announcement (No aplica)

	// Reconstrucción de objetos (Reconstruct) --------------------------------

	public Announcement saveFromCreate(final AnnouncementForm announcementForm) {
		Announcement announcement = null;
		Announcement result = null;
		Project project = null;
		User user = null;

		Assert.notNull(announcementForm, "message.error.announcement.null");

		announcement = this.announcementService.create(announcementForm.getProjectId());
		project = this.projectService.findOne(announcementForm.getProjectId());
		user = this.userService.findByPrincipal();

		announcement.setId(announcementForm.getId());
		announcement.setTitle(announcementForm.getTitle());
		announcement.setDescription(announcementForm.getDescription());
		announcement.setProject(project);
		announcement.setUser(user);

		result = this.announcementService.saveFromCreate(announcement);

		return result;
	}
}
