
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
	public ProjectComment saveFromCreate(final ProjectComment pC) {

		final ProjectComment result;

		Assert.notNull(pC);

		result = this.projectCommentRepository.save(pC);

		return result;
	}

	public void flush() {
		this.projectCommentRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Collection<ProjectComment> listAllProjectComments(final int projectId) {

		final Collection<ProjectComment> result = this.projectCommentRepository.listAllProjectComments(projectId);

		return result;
	}

	public Collection<ProjectComment> listAllProjectCommentsOfUser(final int userId) {

		final Collection<ProjectComment> result = this.projectCommentRepository.listAllProjectCommentsOfUser(userId);

		return result;
	}

	// Dashboard --------------------------------------------------------------

	// Req 25.2.3: The average and standard deviation of comments per project.

	public Double avgCommentsPerProject() {
		return this.projectCommentRepository.avgCommentsPerProject();
	}

	public Double stdCommentsPerProject() {
		return this.projectCommentRepository.stdCommentsPerProject();
	}

}
