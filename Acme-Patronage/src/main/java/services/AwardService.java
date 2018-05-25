
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AwardRepository;
import domain.Award;
import domain.AwardComment;
import domain.Project;
import domain.User;

@Service
@Transactional
public class AwardService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AwardRepository	awardRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService		userService;

	@Autowired
	private ProjectService	projectService;


	// Constructors -----------------------------------------------------------

	public AwardService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Award create(final int projectId) {
		Award result = null;
		Project project = null;

		result = new Award();
		project = this.projectService.findOne(projectId);

		result.setProject(project);
		result.setAwardComments(new ArrayList<AwardComment>());

		return result;
	}

	public Collection<Award> findAll() {
		Collection<Award> result = null;
		result = this.awardRepository.findAll();
		return result;
	}

	public Award findOne(final int awardId) {
		Award result = null;
		result = this.awardRepository.findOne(awardId);
		return result;
	}

	public Award save(final Award award) {
		Assert.notNull(award);
		Award result = null;
		result = this.awardRepository.save(award);
		return result;
	}

	public Award saveFromCreate(final Award award) {
		Award result = null;
		User user = null;

		user = this.userService.findByPrincipal();

		Assert.notNull(award, "message.error.award.null");
		Assert.notNull(user, "message.error.award.principal.null");
		Assert.isTrue(award.getProject().getCreator().equals(user), "message.error.award.user.owner");

		// Paso 1: realizo la entidad del servicio Award

		result = this.save(award);

		// Paso 2: persisto el resto de relaciones a las que el objeto Award esta relacionada.

		result.getProject().getAwards().add(result);

		return result;
	}

	public Award saveFromEdit(final Award award) {
		Award result = null;
		User user = null;

		user = this.userService.findByPrincipal();

		Assert.notNull(award, "message.error.award.null");
		Assert.notNull(user, "message.error.award.principal.null");
		Assert.isTrue(award.getProject().getCreator().equals(user), "message.error.award.user.owner");
		Assert.isTrue(!award.getProject().getIsCancelled(), "message.error.award.project.isCancelled");
		Assert.isTrue(award.getProject().getIsDraft(), "message.error.award.project.isPublished");
		// Paso 1: realizo la entidad del servicio Award

		result = this.save(award);

		return result;
	}

	public void delete(final Award award) {
		User user = null;

		user = this.userService.findByPrincipal();

		Assert.notNull(award, "message.error.award.null");
		Assert.notNull(user, "message.error.award.principal.null");

		Assert.isTrue(award.getProject().getCreator().equals(user), "message.error.award.user.owner");

		// Paso 1: actualizamos el resto de relaciones con la entidad Award

		// Paso 2: borramos el objeto

		this.awardRepository.delete(award);
	}

	public void flush() {
		this.awardRepository.flush();
	}

	// Other business methods -------------------------------------------------
}
