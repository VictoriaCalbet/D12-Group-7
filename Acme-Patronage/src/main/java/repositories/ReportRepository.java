
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	@Query("select r from Report r where r.isLegit = ?1")
	Collection<Report> findByLegitimacy(Boolean isLegit);

	@Query("select r from Report r where r.isLegit = null")
	Collection<Report> findByLegitimacyNull();

	// Dashboard --------------------------------------------------------------

	// Req 33.3.1: The average and standard deviation of complaints per project.

	@Query("select avg(p.reports.size) from Project p")
	Double avgReportsPerProject();

	@Query("select sqrt(sum(p.reports.size * p.reports.size) / count(p.reports.size) - (avg(p.reports.size) * avg(p.reports.size))) from Project p")
	Double stdReportsPerProject();

	// Req 33.3.2: The ratio of complaints per project.

	@Query("select count(r)*1.0 / (select count(p)*1.0 from Project p) from Report r")
	Double ratioReportsPerProject();

	// Req 33.3.3: The ratio of complaints per user.

	@Query("select count(r)*1.0 / (select count(u)*1.0 from User u) from Report r")
	Double ratioReportsPerUser();

	// Req 33.3.4: The ratio of legit complaints.

	@Query("select count(r)*1.0 / (select count(r2)*1.0 from Report r2) from Report r where r.isLegit IS TRUE")
	Double ratioLegitReports();

}
