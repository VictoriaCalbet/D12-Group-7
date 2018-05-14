
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class AnnouncementComment extends Comment {

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Announcement	announcement;


	public Announcement getAnnouncement() {
		return this.announcement;
	}

	public void setAnnouncement(final Announcement announcement) {
		this.announcement = announcement;
	}

}
