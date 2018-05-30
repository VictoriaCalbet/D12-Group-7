
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "creationMoment")
})
public class Announcement extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	title;
	private String	description;
	private Date	creationMoment;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreationMoment() {
		return this.creationMoment;
	}

	public void setCreationMoment(final Date creationMoment) {
		this.creationMoment = creationMoment;
	}


	// Relationships ----------------------------------------------------------

	private User							user;
	private Collection<AnnouncementComment>	announcementComments;
	private Project							project;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "announcement", cascade = CascadeType.REMOVE)
	public Collection<AnnouncementComment> getAnnouncementComments() {
		return this.announcementComments;
	}

	public void setAnnouncementComments(final Collection<AnnouncementComment> announcementComments) {
		this.announcementComments = announcementComments;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(final Project project) {
		this.project = project;
	}

}
