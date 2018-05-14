
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	text;
	private Date	creationMoment;
	private Integer	rating;


	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public Date getCreationMoment() {
		return this.creationMoment;
	}

	public void setCreationMoment(final Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	public Integer getRating() {
		return this.rating;
	}

	public void setRating(final Integer rating) {
		this.rating = rating;
	}


	// Relationships ----------------------------------------------------------

	private User	user;


	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

}
