
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Award extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	name;
	private String	description;
	private double	moneyGoal;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@DecimalMin(value = "0")
	public double getMoneyGoal() {
		return this.moneyGoal;
	}

	public void setMoneyGoal(final double moneyGoal) {
		this.moneyGoal = moneyGoal;
	}


	// Relationships ----------------------------------------------------------

	private AwardComment	awardComment;
	private Project			project;


	public AwardComment getAwardComment() {
		return this.awardComment;
	}

	public void setAwardComment(final AwardComment awardComment) {
		this.awardComment = awardComment;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(final Project project) {
		this.project = project;
	}

}
