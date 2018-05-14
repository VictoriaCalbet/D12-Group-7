
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class AwardComment extends Comment {

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private AwardComment	awardComment;


	public AwardComment getAwardComment() {
		return this.awardComment;
	}

	public void setAwardComment(final AwardComment awardComment) {
		this.awardComment = awardComment;
	}

}
