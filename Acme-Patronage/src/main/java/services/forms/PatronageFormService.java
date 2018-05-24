
package services.forms;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import services.PatronageService;
import services.ProjectService;
import services.UserService;
import domain.Patronage;
import domain.Project;
import domain.User;
import domain.forms.PatronageForm;

@Service
@Transactional
public class PatronageFormService {

	//Supporting services

	@Autowired
	private PatronageService	patronageService;

	@Autowired
	private ProjectService		projectService;

	@Autowired
	private UserService			userService;


	//Constructor

	public PatronageFormService() {

		super();
	}

	public PatronageForm create() {
		PatronageForm result;

		result = new PatronageForm();

		return result;
	}

	public PatronageForm create(final int projectId) {

		final Patronage p = this.patronageService.create(projectId);

		final Project project = this.projectService.findOne(projectId);
		final PatronageForm patronageForm = new PatronageForm();

		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(principal != project.getCreator());
		patronageForm.setId(p.getId());

		patronageForm.setAmount(p.getAmount());

		patronageForm.setCreditCard(p.getCreditCard());
		patronageForm.setProject(this.projectService.findOne(projectId));

		return patronageForm;
	}
	public Patronage saveFromCreate(final PatronageForm patronageForm) {

		Assert.notNull(patronageForm, "message.error.patronageForm.null");
		Assert.notNull(patronageForm.getAmount(), "message.error.patronageForm.amount.null");
		Assert.notNull(patronageForm.getCreditCard(), "message.error.patronageForm.creditCard.null");
		final Patronage p = this.patronageService.create(patronageForm.getProject().getId());
		p.setId(patronageForm.getId());
		p.setAmount(patronageForm.getAmount());
		p.setCreditCard(patronageForm.getCreditCard());
		p.setProject(this.projectService.findOne(patronageForm.getProject().getId()));
		final Patronage patronagenew = this.patronageService.saveFromCreate(p);

		return patronagenew;
	}
	public Patronage saveFromEdit(final PatronageForm patronageForm) {
		Assert.notNull(patronageForm, "message.error.patronage.null");

		final Patronage patronage = this.patronageService.findOne(patronageForm.getId());

		patronage.setAmount(patronageForm.getAmount());
		patronage.setCreditCard(patronageForm.getCreditCard());
		patronage.setId(patronageForm.getId());
		patronage.setProject(patronageForm.getProject());
		final Patronage result = this.patronageService.saveFromEdit(patronage);

		return result;

	}
}
