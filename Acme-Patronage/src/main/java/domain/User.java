
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Collection<Announcement>	announcements;
	private Collection<Report>			reports;
	private Collection<Patronage>		patronages;
	private Collection<Project>			projects;
	private Collection<Comment>			comments;


	public Collection<Announcement> getAnnouncements() {
		return this.announcements;
	}

	public void setAnnouncements(final Collection<Announcement> announcements) {
		this.announcements = announcements;
	}

	public Collection<Report> getReports() {
		return this.reports;
	}

	public void setReports(final Collection<Report> reports) {
		this.reports = reports;
	}

	public Collection<Patronage> getPatronages() {
		return this.patronages;
	}

	public void setPatronages(final Collection<Patronage> patronages) {
		this.patronages = patronages;
	}

	public Collection<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(final Collection<Project> projects) {
		this.projects = projects;
	}

	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

}
