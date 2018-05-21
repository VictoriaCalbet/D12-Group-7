
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProjectCommentRepository;
import domain.ProjectComment;

@Service
@Transactional
public class ProjectCommentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ProjectCommentRepository	projectCommentRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ProjectCommentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: ProjectComment - create
	public ProjectComment create() {
		final ProjectComment result = null;

		return result;
	}

	public Collection<ProjectComment> findAll() {
		Collection<ProjectComment> result = null;
		result = this.projectCommentRepository.findAll();
		return result;
	}

	public ProjectComment findOne(final int projectCommentId) {
		ProjectComment result = null;
		result = this.projectCommentRepository.findOne(projectCommentId);
		return result;
	}

	public ProjectComment save(final ProjectComment projectComment) {
		Assert.notNull(projectComment);
		ProjectComment result = null;
		result = this.projectCommentRepository.save(projectComment);
		return result;
	}

	// TODO: ProjectComment - saveFromCreate
	public ProjectComment saveFromCreate() {
		final ProjectComment result = null;

		return result;
	}

	// TODO: ProjectComment - saveFromEdit
	public ProjectComment saveFromEdit() {
		final ProjectComment result = null;

		return result;
	}

	public void flush() {
		this.projectCommentRepository.flush();
	}

	// Other business methods -------------------------------------------------

}
