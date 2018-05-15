
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class AwardComment extends Comment {

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private AwardComment	awardComment;


	@NotNull
	@Valid
	@OneToOne(optional = false)
	public AwardComment getAwardComment() {
		return this.awardComment;
	}

	public void setAwardComment(final AwardComment awardComment) {
		this.awardComment = awardComment;
	}

}
