
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProjectRepository;
import domain.Actor;
import domain.Announcement;
import domain.Award;
import domain.Patronage;
import domain.Project;
import domain.ProjectComment;
import domain.Report;
import domain.Sponsorship;
import domain.User;

@Service
@Transactional
public class ProjectService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ProjectRepository	projectRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService			userService;
	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public ProjectService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Project create() {
		final Project result = new Project();
		final User u = this.userService.findByPrincipal();
		final Collection<Announcement> announcements = new ArrayList<Announcement>();
		final Collection<Report> reports = new ArrayList<Report>();
		final Collection<Patronage> patronages = new ArrayList<Patronage>();
		final Collection<ProjectComment> projectComments = new ArrayList<ProjectComment>();
		final Collection<Sponsorship> sponsorships = new ArrayList<Sponsorship>();
		final Collection<Award> awards = new ArrayList<Award>();

		result.setIsCancelled(false);
		result.setCreator(u);
		result.setAnnouncements(announcements);
		result.setReports(reports);
		result.setPatronages(patronages);
		result.setProjectComments(projectComments);
		result.setSponsorships(sponsorships);
		result.setAwards(awards);
		result.setCreationMoment(new Date(System.currentTimeMillis() - 1000));

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

	public void delete(final int projectId) {
		final Project p = this.projectRepository.findOne(projectId);
		final User u = this.userService.findByPrincipal();
		Assert.notNull(p, "message.error.project.null");
		Assert.isTrue(p.getCreator().equals(u), "message.error.project.principal.owner");
		Assert.isTrue(p.getDueDate().after(new Date()), "message.error.project.dueDate.future");
		//		Assert.isTrue(p.getIsDraft(), "message.error.project.isDraft");
		Assert.isTrue(!p.getIsCancelled(), "message.error.project.isCancelled");
		Assert.isTrue(p.getPatronages().size() == 0, "message.error.project.patronages");

		u.getProjects().remove(p);
		this.userService.save(u);

		this.projectRepository.delete(p);

	}

	public void cancel(final int projectId) {
		final Project p = this.projectRepository.findOne(projectId);
		final User u = this.userService.findByPrincipal();
		Assert.notNull(p, "message.error.project.null");
		Assert.isTrue(p.getCreator().equals(u), "message.error.project.principal.owner");
		Assert.isTrue(p.getDueDate().after(new Date()), "message.error.project.dueDate.future");
		Assert.isTrue(!p.getIsDraft(), "message.error.project.isDraft");
		Assert.isTrue(!p.getIsCancelled(), "message.error.project.isCancelled");
		Assert.isTrue(p.getPatronages().size() != 0, "message.error.project.patronagesContains");
		for (final Patronage pat : p.getPatronages())
			pat.setIsCancelled(true);
		p.setIsCancelled(true);

		this.projectRepository.save(p);

	}

	public void deleteAdmin(final int projectId) {
		final Project p = this.projectRepository.findOne(projectId);
		final Actor actor = this.actorService.findByPrincipal();

		Assert.isTrue(this.actorService.checkAuthority(actor, "ADMIN"));
		Assert.notNull(p, "message.error.project.null");
		Assert.isTrue(this.isProjectLegitComplaint(p.getId()) > 0, "message.error.project.notLegitComplaint");

		final User creator = p.getCreator();
		creator.getProjects().remove(p);
		this.userService.save(creator);

		this.projectRepository.delete(p);

	}

	public Project saveFromCreate(final Project p) {
		final Project result;

		Assert.notNull(p, "message.error.project.null");
		Assert.isTrue(p.getDueDate().after(new Date()), "message.error.project.dueDate.future");
		Assert.isTrue(p.getMinimumPatronageAmount() < p.getEconomicGoal(), "message.error.project.cash");

		result = this.projectRepository.save(p);
		return result;
	}

	public Project saveFromEdit(final Project p) {
		final Project result;

		final User u = this.userService.findByPrincipal();
		Assert.notNull(p, "message.error.project.null");
		Assert.isTrue(p.getCreator().equals(u), "message.error.project.principal.owner");
		Assert.isTrue(p.getMinimumPatronageAmount() < p.getEconomicGoal(), "message.error.project.cash");
		Assert.isTrue(!p.getIsCancelled(), "message.error.project.isCancelled");
		Assert.isTrue(p.getDueDate().after(new Date()), "message.error.project.dueDate.future");

		result = this.projectRepository.save(p);
		return result;
	}

	public void flush() {
		this.projectRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Collection<Project> findProjectFutureDueDate() {
		return this.projectRepository.findProjectFutureDueDate();
	}

	public Collection<Project> findProjectByKeyWord(final String keyWord) {
		return this.projectRepository.findProjectByKeyWord(keyWord);
	}

	public Collection<Project> findProjectByKeyWordByAdmin(final String keyWord) {
		return this.projectRepository.findProjectByKeyWordByAdmin(keyWord);
	}

	public Integer isProjectLegitComplaint(final int projectId) {
		return this.projectRepository.isProjectLegitComplaint(projectId);
	}

	public Collection<Project> findProjectByKeyWordByUser(final String keyWord, final int userId) {
		return this.projectRepository.findProjectByKeyWordByUser(keyWord, userId);
	}

	public Collection<Project> findAllCreatedByUser(final int userId) {
		return this.projectRepository.findProjects(userId, false, false);
	}

	public Collection<Project> findAllFundedByUser(final int userId) {
		return this.projectRepository.findAllFundedByUser(userId);
	}

	public Collection<Project> findDraftProjects() {
		return this.projectRepository.findDraftProjects();
	}

	public Collection<Project> findProjects(final int userId, final boolean isDraft, final boolean isCancelled) {
		return this.projectRepository.findProjects(userId, isDraft, isCancelled);
	}

	public Collection<Project> findProjectFutureDueDateOrdered() {
		return this.projectRepository.findProjectFutureDueDateOrdered();
	}

	public Collection<Project> findProjectByKeyWordOrdered(final String word) {
		return this.projectRepository.findProjectByKeyWordOrdered(word);
	}

	public Collection<Project> findProjectByCategory(final int categoryId) {
		return this.projectRepository.findProjectByCategory(categoryId);
	}

	public Collection<Project> findProjectByKeyWordCategory(final String word, final int categoryId) {
		return this.projectRepository.findProjectByKeyWordCategory(word, categoryId);
	}

	public Collection<Project> findProjectWithReportLegit() {
		return this.projectRepository.findProjectWithReportLegit();
	}
	public Collection<Project> findProjectsForSponsorships() {
		Collection<Project> projects;
		projects = this.findProjectFutureDueDate();
		projects.retainAll(this.projectRepository.findProjectWithEconomicGoalNoReach());
		return projects;
	}
	// Dashboard

	// Req 12.2.1: The average and standard deviation of projects per user.

	public Double avgProjectsPerUser() {
		return this.projectRepository.avgProjectsPerUser();
	}

	public Double stdProjectsPerUser() {
		return this.projectRepository.stdProjectsPerUser();
	}

	// Req 12.2.4: The ratio of funded projects.

	public Double ratioFundedProjects() {
		return this.projectRepository.ratioFundedProjects();
	}

	// Req 12.2.7: The top-5 active projects (with a limit date not due) with more raised money.

	public Collection<Project> top5ActiveProjectsWithMoreRaisedMoney() {
		Collection<Project> result = new HashSet<Project>();
		Collection<Project> projects = new HashSet<Project>();
		Integer counter = 0;

		projects = this.projectRepository.top5ActiveProjectsWithMoreRaisedMoney();

		if (projects.size() <= 5)
			result = projects;
		else
			for (final Project project : projects)
				if (counter < 5) {
					result.add(project);
					counter++;
				} else
					break;

		return result;
	}

	// Req 12.2.8: The top-5 due projects with more raised money.

	public Collection<Project> top5DueProjectsWithMoreRaisedMoney() {
		Collection<Project> result = new HashSet<Project>();
		Collection<Project> projects = new HashSet<Project>();
		Integer counter = 0;

		projects = this.projectRepository.top5DueProjectsWithMoreRaisedMoney();

		if (projects.size() <= 5)
			result = projects;
		else
			for (final Project project : projects)
				if (counter < 5) {
					result.add(project);
					counter++;
				} else
					break;

		return result;
	}

	// Req 25.2.4: The projects that have, at least, a 10% more sponsorships than the average.

	public Collection<Project> findAllWith10PercentMoreSponsorshipsThanAvg() {
		return this.projectRepository.findAllWith10PercentMoreSponsorshipsThanAvg();
	}
}
