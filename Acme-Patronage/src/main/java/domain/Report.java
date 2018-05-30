
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "isLegit")
})
public class Report extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	complaint;
	private String	reason;
	private Boolean	isLegit;


	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getComplaint() {
		return this.complaint;
	}

	public String setComplaint(final String complaint) {
		return this.complaint = complaint;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
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
	@ManyToOne(optional = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(final Project project) {
		this.project = project;
	}

}
