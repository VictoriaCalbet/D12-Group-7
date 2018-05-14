
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	reason;
	private boolean	isLegit;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getReason() {
		return this.reason;
	}

	public void setReason(final String reason) {
		this.reason = reason;
	}

	public boolean getIsLegit() {
		return this.isLegit;
	}

	public void setIsLegit(final boolean isLegit) {
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
