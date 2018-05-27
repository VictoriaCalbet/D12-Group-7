
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProjectCommentRepository;
import domain.Administrator;
import domain.ProjectComment;

@Service
@Transactional
public class ProjectCommentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ProjectCommentRepository	projectCommentRepository;


	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService	userService;
	
	@Autowired
	private AdministratorService	administratorService;
	
	// Constructors -----------------------------------------------------------

	public ProjectCommentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: ProjectComment - create
	public ProjectComment create() {
		final ProjectComment result = new ProjectComment();
		
		result.setCreationMoment(new Date(System.currentTimeMillis() - 1));

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

		Assert.isTrue(pC.getId()==0);
		Assert.notNull(pC,"message.error.projectComment.null");
		Assert.notNull(pC.getProject(),"message.error.projectComment.project.null");
		Assert.notNull(pC.getText(),"message.error.projectComment.text.null");
		
		pC.setUser(this.userService.findByPrincipal());
		
		pC.setCreationMoment(new Date(System.currentTimeMillis() - 1));

		result = this.projectCommentRepository.save(pC);

		return result;
	}

	public void flush() {
		this.projectCommentRepository.flush();
	}

	public void delete(ProjectComment pC){
		
		Assert.notNull(pC,"message.error.projectComment.null");
		
		Administrator admin = this.administratorService.findByPrincipal();
		
		Assert.notNull(admin);
		
		this.projectCommentRepository.delete(pC.getId());
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
