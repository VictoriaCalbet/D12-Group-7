
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
	

	@NotNull
	@Valid
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	public Collection<Announcement> getAnnouncements() {
		return this.announcements;
	}

	public void setAnnouncements(final Collection<Announcement> announcements) {
		this.announcements = announcements;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	public Collection<Report> getReports() {
		return this.reports;
	}

	public void setReports(final Collection<Report> reports) {
		this.reports = reports;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	public Collection<Patronage> getPatronages() {
		return this.patronages;
	}

	public void setPatronages(final Collection<Patronage> patronages) {
		this.patronages = patronages;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "creator", cascade = CascadeType.REMOVE)
	public Collection<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(final Collection<Project> projects) {
		this.projects = projects;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

}
