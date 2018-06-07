
package services.forms;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import services.ProjectService;
import services.UserService;
import domain.Project;
import domain.User;
import domain.forms.ProjectForm;

@Service
@Transactional
public class ProjectFormService {

	// Managed repository -----------------------------------------------------

	// Supporting services ----------------------------------------------------
	@Autowired
	private ProjectService	projectService;
	@Autowired
	private UserService		userService;


	// Constructors -----------------------------------------------------------

	public ProjectFormService() {
		super();
	}

	public ProjectForm create() {
		ProjectForm res;

		res = new ProjectForm();

		return res;
	}

	public ProjectForm create(final int projectId) {
		final Project p = this.projectService.findOne(projectId);
		final User u = this.userService.findByPrincipal();

		Assert.isTrue(p.getCreator().equals(u), "message.error.project.principal.owner");
		Assert.isTrue(p.getIsDraft(), "message.error.project.isDraft");
		Assert.isTrue(!p.getIsCancelled(), "message.error.project.isCancelled");

		final ProjectForm pF = new ProjectForm();
		pF.setTitle(p.getTitle());
		pF.setDescription(p.getDescription());
		pF.setDueDate(p.getDueDate());
		pF.setEconomicGoal(p.getEconomicGoal());
		pF.setMinimumPatronageAmount(p.getMinimumPatronageAmount());
		pF.setIsDraft(p.getIsDraft());
		pF.setCategory(p.getCategory());
		pF.setId(p.getId());

		return pF;

	}

	public Project saveFromCreate(final ProjectForm projectForm) {

		final Project p = this.projectService.create();

		//		Assert.notNull(projectForm, "message.error.project.null");
		//		Assert.isTrue(projectForm.getDueDate().after(new Date()), "message.error.project.dueDate.future");
		//		Assert.isTrue(projectForm.getMinimumPatronageAmount() < projectForm.getEconomicGoal(), "message.error.project.cash");

		p.setTitle(projectForm.getTitle());
		p.setDescription(projectForm.getDescription());
		p.setDueDate(projectForm.getDueDate());
		p.setEconomicGoal(projectForm.getEconomicGoal());
		p.setMinimumPatronageAmount(projectForm.getMinimumPatronageAmount());
		p.setCategory(projectForm.getCategory());
		p.setIsDraft(projectForm.getIsDraft());

		final Project project = this.projectService.saveFromCreate(p);

		//		final Collection<Patronage> patronages = new ArrayList<Patronage>();
		//		final Patronage patronage = this.patronageService.create(project);
		//		final Patronage patronageSave = this.patronageService.save(patronage);
		//		patronages.add(patronageSave);
		//		project.setPatronages(patronages);

		final User u = this.userService.findByPrincipal();
		final Collection<Project> projects = u.getProjects();
		projects.add(project);
		this.userService.save(u);

		return project;

	}

	public Project saveFromEdit(final ProjectForm projectForm) {

		final Project p = this.projectService.findOne(projectForm.getId());
		//		final User u = this.userService.findByPrincipal();
		Assert.isTrue(p.getIsDraft(), "message.error.project.isDraft");
		//		Assert.notNull(p, "message.error.project.null");
		//		Assert.isTrue(p.getCreator().equals(u), "message.error.project.principal.owner");
		//		Assert.isTrue(projectForm.getMinimumPatronageAmount() < projectForm.getEconomicGoal(), "message.error.project.cash");
		//		Assert.isTrue(!p.getIsCancelled(), "message.error.project.isCancelled");
		//		Assert.isTrue(projectForm.getDueDate().after(new Date()), "message.error.project.dueDate.future");

		p.setTitle(projectForm.getTitle());
		p.setDescription(projectForm.getDescription());
		p.setDueDate(projectForm.getDueDate());
		p.setEconomicGoal(projectForm.getEconomicGoal());
		p.setMinimumPatronageAmount(projectForm.getMinimumPatronageAmount());
		p.setIsDraft(projectForm.getIsDraft());
		p.setCategory(projectForm.getCategory());

		return this.projectService.saveFromEdit(p);
	}

}
