
package services.forms;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import services.AwardService;
import services.ProjectService;
import services.UserService;
import domain.Award;
import domain.Project;
import domain.User;
import domain.forms.AwardForm;

@Service
@Transactional
public class AwardFormService {

	// Supporting services ----------------------------------------------------

	@Autowired
	private AwardService	awardService;

	@Autowired
	private UserService		userService;

	@Autowired
	private ProjectService	projectService;


	// Constructors -----------------------------------------------------------

	public AwardFormService() {
		super();
	}

	// Creación de formularios ------------------------------------------------

	// Utilizado al crear una nueva entidad de Award

	public AwardForm createFromCreate(final int projectId) {
		AwardForm result = null;
		User user = null;
		Project project = null;

		result = new AwardForm();
		user = this.userService.findByPrincipal();
		project = this.projectService.findOne(projectId);

		result.setUserId(user.getId());
		result.setProjectId(projectId);
		result.setMinimumPatronageAmount(project.getMinimumPatronageAmount());

		return result;
	}

	// Utilizado al editar una nueva entidad de Award
	public AwardForm createFromEdit(final int awardId) {
		Award award = null;
		AwardForm result = null;

		award = this.awardService.findOne(awardId);
		result = new AwardForm();

		result.setId(award.getId());
		result.setName(award.getName());
		result.setDescription(award.getDescription());
		result.setMoneyGoal(award.getMoneyGoal());
		result.setMinimumPatronageAmount(award.getProject().getMinimumPatronageAmount());
		result.setUserId(award.getProject().getCreator().getId());
		result.setProjectId(award.getProject().getId());

		return result;
	}
	// Reconstrucción de objetos (Reconstruct) --------------------------------

	public Award saveFromCreate(final AwardForm awardForm) {
		Award award = null;
		Award result = null;
		Project project = null;

		Assert.notNull(awardForm, "message.error.award.null");

		award = this.awardService.create(awardForm.getProjectId());
		project = this.projectService.findOne(awardForm.getProjectId());

		award.setId(awardForm.getId());
		award.setName(awardForm.getName());
		award.setDescription(awardForm.getDescription());
		award.setMoneyGoal(awardForm.getMoneyGoal());
		award.setProject(project);

		result = this.awardService.saveFromCreate(award);

		return result;
	}

	public Award saveFromEdit(final AwardForm awardForm) {
		Award award = null;
		Award result = null;
		Project project = null;

		Assert.notNull(awardForm, "message.error.award.null");

		award = this.awardService.create(awardForm.getProjectId());
		project = this.projectService.findOne(awardForm.getProjectId());

		award.setId(awardForm.getId());
		award.setName(awardForm.getName());
		award.setDescription(awardForm.getDescription());
		award.setMoneyGoal(awardForm.getMoneyGoal());
		award.setProject(project);

		result = this.awardService.saveFromEdit(award);

		return result;
	}
}
