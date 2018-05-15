
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProjectRepository;
import domain.Project;

@Service
@Transactional
public class ProjectService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ProjectRepository	projectRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ProjectService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: Project - create
	public Project create() {
		final Project result = null;

		return result;
	}

	public Collection<Project> findAll() {
		Collection<Project> result = null;
		result = this.projectRepository.findAll();
		return result;
	}

	public Project findOne(final int projectId) {
		Project result = null;
		result = this.projectRepository.findOne(projectId);
		return result;
	}

	public Project save(final Project project) {
		Assert.notNull(project);
		Project result = null;
		result = this.projectRepository.save(project);
		return result;
	}

	// TODO: Project - saveFromCreate
	public Project saveFromCreate() {
		final Project result = null;

		return result;
	}

	// TODO: Project - saveFromEdit
	public Project saveFromEdit() {
		final Project result = null;

		return result;
	}

	public void flush() {
		this.projectRepository.flush();
	}

	// Other business methods -------------------------------------------------

}
