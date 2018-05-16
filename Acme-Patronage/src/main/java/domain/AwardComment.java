
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class AwardComment extends Comment {

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Award	award;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Award getAward() {
		return this.award;
	}

	public void setAward(final Award award) {
		this.award = award;
	}

}
