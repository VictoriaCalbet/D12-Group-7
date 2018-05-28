
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	@Query("select p from Project p where p.dueDate > CURRENT_TIMESTAMP and p.isCancelled = false and p.isDraft = false")
	Collection<Project> findProjectFutureDueDate();

	@Query("select p from Project p where p.dueDate > CURRENT_TIMESTAMP and p.isCancelled = false and p.isDraft = false order by p.creationMoment desc")
	Collection<Project> findProjectFutureDueDateOrdered();

	@Query("select p from Project p where (p.title like %?1% or p.description like %?1%) and p.isDraft = false order by p.creationMoment desc")
	Collection<Project> findProjectByKeyWordOrdered(String keyWord);

	@Query("select p from Project p where (p.title like %?1% or p.description like %?1%) and p.isDraft = false")
	Collection<Project> findProjectByKeyWord(String keyWord);

	@Query("select p from Project p where (p.title like %?1% or p.description like %?1%)")
	Collection<Project> findProjectByKeyWordByAdmin(String keyWord);

	@Query("select p from Project p where (p.title like %?1% or p.description like %?1%) and p.creator.id = ?2")
	Collection<Project> findProjectByKeyWordByUser(String keyWord, int userId);

	@Query("select p from Project p where p.creator.id = ?1 and p.isDraft is ?2 and p.isCancelled is ?3")
	Collection<Project> findProjects(int userId, boolean isDraft, boolean isCancelled);

	@Query("select distinct(p.project) from Patronage p where p.user.id = ?1")
	Collection<Project> findAllFundedByUser(int userId);

	@Query("select count(r) from Project p join p.reports r where r.isLegit = true and p.id = ?1")
	Integer isProjectLegitComplaint(int projectId);

	@Query("select p from Project p where p.isDraft=true")
	Collection<Project> findDraftProjects();

	@Query("select p from Project p where p.category.id=?1 and p.dueDate > CURRENT_TIMESTAMP and p.isCancelled = false and p.isDraft = false")
	Collection<Project> findProjectByCategory(int categoryId);

	@Query("select p from Project p where (p.title like %?1% or p.description like %?1%) and p.category.id = ?2 and p.isDraft = false")
	Collection<Project> findProjectByKeyWordCategory(String word, int categoryId);

	@Query("select p from Project p join p.reports r where r.isLegit = true")
	Collection<Project> findProjectWithReportLegit();

	@Query("select p from Project p where (p.economicGoal > (select sum(pt.amount) from Patronage pt where pt.project.id= p.id)) or p.patronages.size=0")
	Collection<Project> findProjectWithEconomicGoalNoReach();

	// Dashboard --------------------------------------------------------------

	// Req 12.2.1: The average and standard deviation of projects per user.

	@Query("select avg(u.projects.size) from User u")
	Double avgProjectsPerUser();

	@Query("select sqrt(sum(u.projects.size * u.projects.size) / count(u.projects.size) - (avg(u.projects.size) * avg(u.projects.size))) from User u")
	Double stdProjectsPerUser();

	// Req 12.2.4: The ratio of funded projects.

	@Query("select count(p)*1.0/(select count(p2)*1.0 from Project p2) from Project p where p.economicGoal < (select sum(pt.amount) from Patronage pt where pt.project.id = p.id)")
	Double ratioFundedProjects();

	// Req 12.2.7: The top-5 active projects (with a limit date not due) with more raised money.

	@Query("select pt.project from Patronage pt where pt.project.dueDate > CURRENT_TIMESTAMP group by pt.project order by sum(pt.amount) desc")
	Collection<Project> top5ActiveProjectsWithMoreRaisedMoney();

	// Req 12.2.8: The top-5 due projects with more raised money.

	@Query("select pt.project from Patronage pt where pt.project.dueDate < CURRENT_TIMESTAMP group by pt.project order by sum(pt.amount) desc")
	Collection<Project> top5DueProjectsWithMoreRaisedMoney();

	// Req 25.2.4: The projects that have, at least, a 10% more sponsorships than the average.

	@Query("select p from Project p where p.sponsorships.size > (select avg(p2.sponsorships.size)*1.1 from Project p2)")
	Collection<Project> findAllWith10PercentMoreSponsorshipsThanAvg();
}
