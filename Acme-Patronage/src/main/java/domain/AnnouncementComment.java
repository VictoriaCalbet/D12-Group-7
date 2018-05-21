
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class AnnouncementComment extends Comment {

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Announcement	announcement;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Announcement getAnnouncement() {
		return this.announcement;
	}

	public void setAnnouncement(final Announcement announcement) {
		this.announcement = announcement;
	}

}
