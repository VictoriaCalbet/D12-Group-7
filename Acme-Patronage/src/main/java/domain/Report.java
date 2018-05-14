
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	reason;
	private Boolean	isLegit;


	public String getReason() {
		return this.reason;
	}

	public void setReason(final String reason) {
		this.reason = reason;
	}

	public Boolean getIsLegit() {
		return this.isLegit;
	}

	public void setIsLegit(final Boolean isLegit) {
		this.isLegit = isLegit;
	}


	// Relationships ----------------------------------------------------------

	private User	user;
	private Project	project;


	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(final Project project) {
		this.project = project;
	}

}
